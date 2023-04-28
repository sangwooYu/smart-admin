package net.lab1024.sa.admin.module.business.category.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.admin.module.business.category.constant.CategoryTypeEnum;
import net.lab1024.sa.common.common.swagger.ApiModelPropertyEnum;
import net.lab1024.sa.common.common.validator.enumeration.CheckEnum;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 类目 基础属性 DTO 类
 *
 * @author 胡克
 * @date 2021/1/20 16:17
 */
@Data
public class CategoryBaseDTO {

    @ApiModelProperty(value = "카테고리 이름", required = true)
    @NotBlank(message = "카테고리 이름은 비워둘 수 없습니다.")
    @Length(max = 20, message = "카테고리 이름 최대 20자")
    private String categoryName;

    @ApiModelPropertyEnum(desc = "분류 유형", value = CategoryTypeEnum.class)
    @CheckEnum(value = CategoryTypeEnum.class, required = true, message = "잘못된 분류")
    private Integer categoryType;

    @ApiModelProperty("상위 카테고리 아이디|선택 사항")
    private Long parentId;

    @ApiModelProperty("정렬|선택 사항")
    private Integer sort;

    @ApiModelProperty("비고|선택 사항")
    @Length(max = 200, message = "최대 200자의 메모")
    private String remark;

    @ApiModelProperty("비활성화 상태")
    @NotNull(message = "비활성화 상태는 비워둘 수 없습니다.")
    private Boolean disabledFlag;
}
