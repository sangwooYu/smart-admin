package net.lab1024.sa.admin.module.business.category.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.admin.module.business.category.constant.CategoryTypeEnum;
import net.lab1024.sa.common.common.swagger.ApiModelPropertyEnum;

@Data
public class CategoryTreeQueryForm {

    @ApiModelPropertyEnum(desc = "분류 유형|선택 사항", value = CategoryTypeEnum.class)
    private Integer categoryType;

    @ApiModelProperty("상위 카테고리 아이디|선택 사항")
    private Long parentId;
}
