package com.opensource.componet.rest.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensource.componet.rest.bean.Poets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PoetsServiceImpl implements PoetsService {
    static Map<Integer, Poets> poetsMap = new HashMap<>(1024);

    @PostConstruct
    @Override
    public void initData() {
        log.info("init poets data start");
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:poets.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        //指定遇到date按照这种格式转换
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        objectMapper.setDateFormat(fmt);
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, Poets.class);
        try {
            List<Poets> poetsList = objectMapper.readValue(file, javaType);
            for (Poets poets : poetsList) {
                poetsMap.put(poets.getId(), poets);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("init poets data end");
    }

    @Override
    public Poets get(Integer id) {
        return poetsMap.get(id);
    }

    @Override
    public List<Poets> list() {
        return null;
    }
}
