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
@RunWith(SpringRunner.class)
@SpringBootTest
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class RestDemoApplicationTest {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testGetForObject() {
        // 使用方法一，不带参数
        String url = "http://127.0.0.1:8080/poets/get?id=30";
        Poets res = restTemplate.getForObject(url, Poets.class);
        System.out.println(res);


        // 使用方法一，传参替换
        url = "http://127.0.0.1:8080/poets/get?id={?}";
        res = restTemplate.getForObject(url, Poets.class, "30");
        System.out.println(res);

        // 使用方法二，map传参
        url = "http://127.0.0.1:8080/poets/get?id={id}";
        Map<String, Object> params = new HashMap<>();
        params.put("id", 30L);
        res = restTemplate.getForObject(url, Poets.class, params);
        System.out.println(res);

        // 使用方法三，URI访问
        URI uri = URI.create("http://127.0.0.1:8080/poets/get?id=30");
        res = restTemplate.getForObject(uri, Poets.class);
        System.out.println(res);
    }

    @Test
    public void testGetForEntity() {
        String url = "http://127.0.0.1:8080/poets/get?id=30";
        ResponseEntity<Poets> res = restTemplate.getForEntity(url, Poets.class);
        System.out.println(res);
    }

    @Test
    public void testPost() {
        String url = "http://localhost:8080/poets/add";
        int id = 111;
        String name = "孙权";

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("id", id+"");
        request.add("name", name);

        // 使用方法三
        URI uri = URI.create(url);
        String result = restTemplate.postForObject(uri, request, String.class);
        System.out.println(result);

        // 使用方法一
        result = restTemplate.postForObject(url, request, String.class);
        System.out.println(result);

        // 使用方法一，但是结合表单参数和uri参数的方式，其中uri参数的填充和get请求一致
        request.clear();
        request.add("id", id+"");
        result = restTemplate.postForObject(url + "?name={?}", request, String.class, name);
        System.out.println(result);

        // 使用方法二
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        result = restTemplate.postForObject(url + "?name={name}", request, String.class, params);
        System.out.println(result);
    }

}
```

日志输出示例：
```shell
request uri : http://127.0.0.1:8080/poets/get?id=30
request headers : [Accept:"application/json, application/*+json", Content-Length:"0"]
request body : 
response status code : 200 OK
response body: {"id":30,"name":"李从善","createdAt":"2014-06-02T03:48:26.000+0000","updatedAt":"2014-06-02T03:48:26.000+0000"}```