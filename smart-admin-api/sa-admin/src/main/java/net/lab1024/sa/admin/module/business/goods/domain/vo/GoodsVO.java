package net.lab1024.sa.admin.module.business.goods.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.admin.module.business.goods.constant.GoodsStatusEnum;
import net.lab1024.sa.common.common.json.serializer.DictValueVoSerializer;
import net.lab1024.sa.common.common.swagger.ApiModelPropertyEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品
 *
 * @Author 1024创新实验室: 胡克
 * @Date 2021-10-25 20:26:54
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@Data
public class GoodsVO  {

    @ApiModelProperty("제품 카테고리")
    private Long categoryId;

    @ApiModelProperty("제품 이름")
    private String goodsName;

    @ApiModelPropertyEnum(GoodsStatusEnum.class)
    private Integer goodsStatus;

    @ApiModelProperty("원산지")
    @JsonSerialize(using = DictValueVoSerializer.class)
    private String place;

    @ApiModelProperty("제품 가격")
    private BigDecimal price;

    @ApiModelProperty("선반 상태")
    private Boolean shelvesFlag;

    @ApiModelProperty("비고|선택 사항")
    private String remark;

    @ApiModelProperty("제품 ID")
    private Long goodsId;

    @ApiModelProperty("제품 카테고리")
    private String categoryName;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;
}
