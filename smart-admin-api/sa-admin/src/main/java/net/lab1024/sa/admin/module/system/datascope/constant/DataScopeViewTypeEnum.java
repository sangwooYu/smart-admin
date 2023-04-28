package net.lab1024.sa.admin.module.system.datascope.constant;


import net.lab1024.sa.common.common.enumeration.BaseEnum;


/**
 * 数据范围 种类
 *
 * @Author 1024创新实验室: 罗伊
 * @Date 2020/11/28  20:59:17
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
public enum DataScopeViewTypeEnum implements BaseEnum {

    ME(0, 0, "본인"),

    DEPARTMENT(1, 5, "이 부서"),

    DEPARTMENT_AND_SUB(2, 10, "이 부서 및 하위 부서"),

    ALL(10, 100, "모두");



    private Integer value;
    private Integer level;
    private String desc;

    DataScopeViewTypeEnum(Integer value, Integer level, String desc) {
        this.value = value;
        this.level = level;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public Integer getLevel() {
        return level;
    }

    @Override
    public String getDesc() {
        return desc;
    }


}
