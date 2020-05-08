package com.opensource.componet.rest.service;

import com.opensource.componet.rest.bean.Poets;

import java.util.List;

public interface PoetsService {
    void initData();
    Poets get(Integer id);
    List<Poets> list();
}
