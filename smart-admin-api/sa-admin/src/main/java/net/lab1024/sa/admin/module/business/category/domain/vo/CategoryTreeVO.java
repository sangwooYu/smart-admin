package net.lab1024.sa.admin.module.business.category.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CategoryTreeVO {

    @ApiModelProperty("카테고리 ID")
    private Long categoryId;

    @ApiModelProperty("카테고리 이름")
    private String categoryName;

    @ApiModelProperty("클래스 계층 구조의 전체 이름")
    private String categoryFullName;

    @ApiModelProperty("부모 ID")
    private Long parentId;

    @ApiModelProperty("카테고리 ID")
    private Long value;

    @ApiModelProperty("카테고리 이름")
    private String label;

    @ApiModelProperty("하위 카테고리")
    private List<CategoryTreeVO> children;
}
