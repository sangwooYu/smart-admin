package net.lab1024.sa.admin.module.system.menu.constant;


import net.lab1024.sa.common.common.enumeration.BaseEnum;

/**
 * 菜单类型枚举
 *
 * @Author 1024创新实验室: 善逸
 * @Date 2022-03-06 22:04:37
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
public enum MenuTypeEnum implements BaseEnum {
    /**
     * 目录
     */
    CATALOG(1, "카탈로그"),
    /**
     * 菜单
     */
    MENU(2, "메뉴"),
    /**
     * 功能点
     */
    POINTS(3, "기능 포인트");

    private Integer value;

    private String desc;


    MenuTypeEnum(Integer value, String desc) {
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
