package net.lab1024.sa.admin.module.business.category.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 类目 基础属性 DTO 类
 *
 * @author 胡克
 * @date 2021/1/20 16:17
 */
@Data
public class CategorySimpleDTO {

    @ApiModelProperty("카테고리 ID")
    private Long categoryId;

    @ApiModelProperty("카테고리 이름")
    private String categoryName;

    @ApiModelProperty("클래스 계층 구조의 전체 이름")
    private String categoryFullName;

    @ApiModelProperty("부모 ID")
    private Long parentId;
}
