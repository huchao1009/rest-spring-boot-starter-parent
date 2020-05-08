package com.opensource.componet.rest.controller;

import com.opensource.componet.rest.bean.Poets;
import com.opensource.componet.rest.service.PoetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

@RestController
@RequestMapping("/poets")
public class PoetsController {
    @Autowired
    private PoetsService poetsService;

    @RequestMapping("/get")
    public Poets get(Integer id) {
        return poetsService.get(id);
    }

    @RequestMapping("/add")
    public Poets add(Integer id, String name) {
        Poets poets = new Poets();
        poets.setId(id);
        poets.setName(name);
        poets.setCreatedAt(new Date());
        poets.setUpdatedAt(new Date());
        return poets;
    }
}
