- 读取 VM Options指定的配置文件
- 初步实现了 Aware接口，与 Spring耦合，获取到当前应用的上下文
- 根据应用上下文读取 设置的环境变量值
- 应用上下文派发事件(extends ApplicationEvent)，监听事件(implements ApplicationListener<? extends ApplicationEvent>)

```
-Dspring.config.location="E:\spring-study-test\ZSMART_HOME\user.properties"
ztesoft=TestZteSoft
```
