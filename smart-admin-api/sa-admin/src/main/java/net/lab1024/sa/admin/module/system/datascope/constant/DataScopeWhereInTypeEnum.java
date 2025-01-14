package net.lab1024.sa.admin.module.system.datascope.constant;


import net.lab1024.sa.common.common.enumeration.BaseEnum;

/**
 * 数据范围 sql where
 *
 * @Author 1024创新实验室: 罗伊
 * @Date 2020/11/28  20:59:17
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
public enum DataScopeWhereInTypeEnum implements BaseEnum {

    EMPLOYEE(0, "직원과 함께"),

    DEPARTMENT(1, "분야별 IN"),

    CUSTOM_STRATEGY(2, "맞춤형 전략");

    private Integer value;
    private String desc;

    DataScopeWhereInTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }


}
