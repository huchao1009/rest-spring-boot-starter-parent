## rest-spring-boot-starter

### 背景

在微服务中，rest服务互相调用是很普遍的，我们该如何优雅地调用，其实在Spring框架使用RestTemplate类可以优雅地进行rest服务互相调用，它简化了与http服务的通信方式，统一了RESTful的标准，封装了http链接，操作使用简便，还可以自定义RestTemplate所需的模式。其中：
- RestTemplate默认使用HttpMessageConverter实例将HTTP消息转换成POJO或者从POJO转换成HTTP消息。默认情况下会注册主mime类型的转换器，但也可以通过setMessageConverters注册自定义转换器。
- RestTemplate使用了默认的DefaultResponseErrorHandler，对40X Bad Request或50X internal异常error等错误信息捕捉。
- RestTemplate还可以使用拦截器interceptor，进行对请求链接跟踪，以及统一head的设置。

### 一、简介

- 说明：RestTemplate接口调用操作组件，可以设置相关超时时间，开启请求、响应日志记录

### 二、引入依赖

###### gradle：
```groovy
    implementation 'com.opensource.component:rest-spring-boot-starter:1.0.0-SNAPSHOT'
```

###### maven:
```xml
    <dependency>
          <groupId>com.opensource.component</groupId>
          <artifactId>rest-spring-boot-starter</artifactId>
          <version>1.0.0-SNAPSHOT</version>
    </dependency>
```
### 三、引入配置

#### 1、引入rest相关配置
```yml
component:
  restTemplate:
    connectTimeout: 1000
    connectionRequestTimeout: 500
    readTimeout: 10000
    logging:
      enabled: true
    maxTotal: 500
    defaultMaxPerRoute: 300
```


#### 2、参数说明：
- connectionRequestTimeout：从连接池获取连接的timeout，默认值：1000ms
- connectTimeout：客户端和服务器建立连接的timeout，默认值：1000ms
- readTimeout：客户端从服务器读取数据的timeout，超出后会抛出SocketTimeOutException，默认值：10000ms
- logging.enabled: 请求、响应日志记录开关，默认值：true
- maxTotal：HttpClient连接池最大连接数，默认值：500
- defaultMaxPerRoute：每一个路由的最大连接数，默认值：300

### 四、构建发布

```groovy
gradle -x test clean build publish
```

### 五、使用例子

```java
Map<String,String> map = new HashMap();
map.put("start","1");
map.put("page","5");
Notice notice = restTemplate.getForObject("http://fantj.top/notice/list/"
        , Notice.class,map);

```