package net.lab1024.sa.admin.module.business.oa.enterprise.constant;


import net.lab1024.sa.common.common.enumeration.BaseEnum;

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
