package com.mongo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @creater hpp
 * @Date 2023/8/22-22:16
 * @description:  操作结果类
 */
@Data
public class OperationResult {

    private Long id;

    private String operate;

    private String tableName;

    private String operateColumn;

    private String operateDetail;

    private Date createTime;

    private Date updateTime;
}
