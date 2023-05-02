package net.lab1024.sa.admin.module.business.category.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.admin.module.business.category.domain.dto.CategoryBaseDTO;

import javax.validation.constraints.NotNull;

@Data
public class CategoryUpdateForm extends CategoryAddForm {

    @ApiModelProperty("카테고리 ID")
    @NotNull(message = "카테고리 ID는 비워둘 수 없습니다.")
    private Long categoryId;
}
