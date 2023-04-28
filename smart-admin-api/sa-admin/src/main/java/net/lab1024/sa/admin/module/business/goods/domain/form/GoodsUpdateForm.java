package net.lab1024.sa.admin.module.business.goods.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 商品 更新表单
 *
 * @Author 1024创新实验室: 胡克
 * @Date 2021-10-25 20:26:54
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@Data
public class GoodsUpdateForm extends GoodsAddForm {

    @ApiModelProperty("제품 ID")
    @NotNull(message = "제품 ID는 비워 둘 수 없습니다.")
    private Long goodsId;
}
