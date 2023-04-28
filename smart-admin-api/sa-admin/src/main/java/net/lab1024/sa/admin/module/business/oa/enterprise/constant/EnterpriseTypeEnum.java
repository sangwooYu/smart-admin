package net.lab1024.sa.admin.module.business.oa.enterprise.constant;


import net.lab1024.sa.common.common.enumeration.BaseEnum;

/**
 * 企业类型
 *
 * @Author 1024创新实验室: 开云
 * @Date 2022/7/28 20:37:15
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
public enum EnterpriseTypeEnum implements BaseEnum {

    NORMAL(1, "제한 기업"),

    FOREIGN(2, "외국 소유 기업"),
    ;

    private Integer value;
    private String desc;

    EnterpriseTypeEnum(Integer value, String desc) {
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
