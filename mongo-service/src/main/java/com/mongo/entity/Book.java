package com.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @creater hpp
 * @Date 2023/7/19-19:32
 * @description:
 */
@Data
@AllArgsConstructor
public class Book {

    private String id;

    private String developsLangue;

    private String desc;
}
