# 欢迎使用 easy-id-generator分布式ID生成器

**easy-id-generator是一种轻量级的「分布式ID生成工具」**

easy-id-generator使用redis作为id缓存器，提前将id生成并放入redis中，客户端只需要从redis中读取即可

# 本工具有两种使用方式

## 一、仅使用client
### 1.使用步骤
1).将项目clone到本地，依次编译并安装easy-id-generator-common、easy-id-generator-client

2).在spring boot项目中，maven加入
```
		<dependency>
			<groupId>com.github.lyrric</groupId>
			<artifactId>easy-id-generator-client</artifactId>
			<version>1.0-SNAPSHOT</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
		</dependency>
```
3).配置好你的redis

4).注入并使用
```
@RestController
public class TestController {

    @Resource
    private EasyIdGenerator easyIdGenerator;
    
    @GetMapping(value = "/test")
    String test(){
        return easyIdGenerator.get();
    }
}
```
### 2.说明
在此模式中，客户端每调用一次easyIdGenerator.get()，就会到redis从取出一个id，并判断id的数量是否小于`idListMinSize`，如果为真，则启动一个线程去抢占redis锁，补充id
## 二、使用client和server
### 1.使用步骤
1）.client的使用同上面，唯一的不同点在于需要配置
```
easy:
  generator:
    client:
      generator-model: server
```
2).在easy-id-generator-server中，配置好你的redis后，启动项目即可
### 2.说明
在此模式中，client获取id后，不在处理id数量问题。server端每隔一秒中会去判断redis中的数量是否小于idListMinSize，如果为真，则启动一个线程去抢占redis锁，补充id

# 配置说明
## client端
```
easy:
  generator:
    client:
      generator-model: server #运行模式，默认为client，可设置为server
      redis-lock-key: id:generator:lock #当需要补充id时，实现分布式锁的key
      redis-lock-time: 5000 #分布式锁的最长设置时间，单位毫秒
      id-list-redis-key: id:generator:list #存储在redis中id list的key
      id-list-default-size: 100 #id 默认数量
      id-list-min-size: 50 #当redis中的id数量小于此值时，会触发补充id操作
      id-list-increase-number: 50 #每次补充id的数量
```
## server端
```
easy:
  generator:
    client:
      redis-lock-key: id:generator:lock #当需要补充id时，实现分布式锁的key
      redis-lock-time: 5000 #分布式锁的最长设置时间，单位毫秒
      id-list-redis-key: id:generator:list #存储在redis中id list的key
      id-list-min-size: 50 #当redis中的id数量小于此值时，会触发补充id操作
      id-list-increase-number: 50 #每次补充id的数量
```