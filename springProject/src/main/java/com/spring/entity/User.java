package com.spring.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.excel.annotation.ExportField;
import lombok.Data;

@TableName("t_user")
@Data
public class User {
    @ExportField(name = "id",order = 1)
    private Long id;
    @ExportField(name = "姓名",order = 2)
    private String userName;
}
