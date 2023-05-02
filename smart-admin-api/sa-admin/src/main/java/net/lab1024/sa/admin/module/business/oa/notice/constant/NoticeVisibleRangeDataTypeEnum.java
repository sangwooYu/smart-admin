package net.lab1024.sa.admin.module.business.oa.notice.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lab1024.sa.common.common.enumeration.BaseEnum;

@Getter
@AllArgsConstructor
public enum NoticeVisibleRangeDataTypeEnum implements BaseEnum {

    EMPLOYEE(1, "직원"),

    DEPARTMENT(2, "섹터"),

    ;

    private final Integer value;

    private final String desc;
}
