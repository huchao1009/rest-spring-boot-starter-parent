package com.opensource.componet.rest.bean;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;


@Data
public class Poets implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 作者姓名
     */
    private String name;

    /**
     * 创建日期
     */
    private Date createdAt;

    /**
     * 修改日期
     */
    private Date updatedAt;

}