package com.mongo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author hpp
 * @since 2023-08-22
 */
@TableName("opeate_result")
//@ApiModel(value = "OpeateResult对象", description = "")
public class OpeateResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //    @ApiModelProperty("操作")
    private String operate;

    //    @ApiModelProperty("操作表名")
    private String tableName;

    //    @ApiModelProperty("字段名")
    private String field;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "OpeateResult{" +
                "id=" + id +
                ", operate=" + operate +
                ", tableName=" + tableName +
                ", field=" + field +
                "}";
    }
}
