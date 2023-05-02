package net.lab1024.sa.admin.module.business.goods.domain.form;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.admin.module.business.goods.constant.GoodsStatusEnum;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.json.deserializer.DictValueVoDeserializer;
import net.lab1024.sa.common.common.swagger.ApiModelPropertyEnum;
import net.lab1024.sa.common.common.validator.enumeration.CheckEnum;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class GoodsQueryForm extends PageParam {

    @ApiModelProperty("제품 카테고리")
    private Integer categoryId;

    @ApiModelProperty("검색어")
    @Length(max = 30, message = "검색어 최대 30자")
    private String searchWord;

    @ApiModelPropertyEnum(GoodsStatusEnum.class)
    @CheckEnum(message = "제품 상태 오류", value = GoodsStatusEnum.class, required = false)
    private Integer goodsStatus;

    @ApiModelProperty("원산지")
    private String place;

    @ApiModelProperty("선반 상태")
    private Boolean shelvesFlag;

    @ApiModelProperty(hidden = true)
    private Boolean deletedFlag;
}
