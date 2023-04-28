package net.lab1024.sa.admin.module.business.category.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.admin.module.business.category.domain.dto.CategoryBaseDTO;

import javax.validation.constraints.NotNull;

/**
 * 类目 更新
 *
 * @Author 1024创新实验室: 胡克
 * @Date 2021/08/05 21:26:58
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@Data
public class CategoryUpdateForm extends CategoryAddForm {

    @ApiModelProperty("카테고리 ID")
    @NotNull(message = "카테고리 ID는 비워둘 수 없습니다.")
    private Long categoryId;
}
