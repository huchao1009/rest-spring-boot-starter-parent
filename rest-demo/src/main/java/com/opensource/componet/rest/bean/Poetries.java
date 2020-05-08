package com.opensource.componet.rest.bean;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;

@Data
public class Poetries implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * poet_id
     */
    private Integer poetId;

    /**
     * content
     */
    private String content;

    /**
     * title
     */
    private String title;

    /**
     * created_at
     */
    private Date createdAt;

    /**
     * updated_at
     */
    private Date updatedAt;

    public Poetries() {
    }

}
