package net.lab1024.sa.admin.module.business.goods.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GoodsUpdateForm extends GoodsAddForm {

    @ApiModelProperty("제품 ID")
    @NotNull(message = "제품 ID는 비워 둘 수 없습니다.")
    private Long goodsId;
}
