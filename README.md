# easy-id-generator
分布式ID生成器  
实现基本逻辑  
1.在redis中提前生成一批id  
2.封装工具，直接调用方法获取id  
3.如果id数量少于设定的idListMinSize，则启动一个线程生成ID  
