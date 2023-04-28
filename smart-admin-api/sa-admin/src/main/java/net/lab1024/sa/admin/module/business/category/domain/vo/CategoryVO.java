package net.lab1024.sa.admin.module.business.category.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.admin.module.business.category.constant.CategoryTypeEnum;
import net.lab1024.sa.admin.module.business.category.domain.dto.CategoryBaseDTO;
import net.lab1024.sa.common.common.swagger.ApiModelPropertyEnum;
import net.lab1024.sa.common.common.validator.enumeration.CheckEnum;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 类目
 *
 * @Author 1024创新实验室: 胡克
 * @Date 2021/08/05 21:26:58
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@Data
public class CategoryVO {

    @ApiModelProperty(value = "카테고리 이름", required = true)
    private String categoryName;

    @ApiModelPropertyEnum(desc = "분류 유형", value = CategoryTypeEnum.class)
    private Integer categoryType;

    @ApiModelProperty("상위 카테고리 아이디|선택 사항")
    private Long parentId;

    @ApiModelProperty("정렬|선택 사항")
    private Integer sort;

    @ApiModelProperty("비고|선택 사항")
    private String remark;

    @ApiModelProperty("비활성화 상태")
    private Boolean disabledFlag;

    @ApiModelProperty("카테고리 아이디")
    private Long categoryId;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;
}
