package net.lab1024.sa.admin.module.business.goods.domain.form;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.admin.module.business.goods.constant.GoodsStatusEnum;
import net.lab1024.sa.common.common.json.deserializer.DictValueVoDeserializer;
import net.lab1024.sa.common.common.swagger.ApiModelPropertyEnum;
import net.lab1024.sa.common.common.validator.enumeration.CheckEnum;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class GoodsAddForm {

    @ApiModelProperty("제품 카테고리")
    @NotNull(message = "제품 카테고리는 비워둘 수 없습니다.")
    private Long categoryId;

    @ApiModelProperty("제품 이름")
    @NotBlank(message = "제품 이름은 비워 둘 수 없습니다.")
    private String goodsName;

    @ApiModelPropertyEnum(GoodsStatusEnum.class)
    @CheckEnum(message = "제품 상태 오류", value = GoodsStatusEnum.class, required = true)
    private Integer goodsStatus;

    @ApiModelProperty("원산지")
    @NotBlank(message = "원산지 비워 둘 수 없습니다.")
    @JsonDeserialize(using = DictValueVoDeserializer.class)
    private String place;

    @ApiModelProperty("제품 가격")
    @NotNull(message = "제품 가격은 비워 둘 수 없습니다.")
    @DecimalMin(value = "0", message = "최소 제품 가격 0")
    private BigDecimal price;

    @ApiModelProperty("선반 상태")
    @NotNull(message = "상태는 비어 있을 수 없습니다.")
    private Boolean shelvesFlag;

    @ApiModelProperty("비고|선택 사항")
    private String remark;
}
