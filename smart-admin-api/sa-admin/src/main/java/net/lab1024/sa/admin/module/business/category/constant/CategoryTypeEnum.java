package net.lab1024.sa.admin.module.business.category.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lab1024.sa.common.common.enumeration.BaseEnum;

@AllArgsConstructor
@Getter
public enum CategoryTypeEnum implements BaseEnum {

    GOODS(1, "상품"),

    CUSTOM(2, "사용자 지정"),

    ;

    private final Integer value;

    private final String desc;
}
