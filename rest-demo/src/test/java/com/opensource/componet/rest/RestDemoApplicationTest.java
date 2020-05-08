package com.opensource.componet.rest;

import com.opensource.componet.rest.bean.Poets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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
