package net.lab1024.sa.admin.module.business.oa.notice.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.admin.module.business.oa.notice.constant.NoticeVisibleRangeDataTypeEnum;
import net.lab1024.sa.common.common.swagger.ApiModelPropertyEnum;

@Data
public class NoticeVisibleRangeVO {

    @ApiModelPropertyEnum(NoticeVisibleRangeDataTypeEnum.class)
    private Integer dataType;

    @ApiModelProperty("직원/부서 ID")
    private Long dataId;

    @ApiModelProperty("직원/부서 이름")
    private String dataName;

}
