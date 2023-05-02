package net.lab1024.sa.admin.module.business.oa.notice.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoticeTypeVO {

    @ApiModelProperty("공지 유형 ID")
    private Long noticeTypeId;

    @ApiModelProperty("통지 유형 - 이름")
    private String noticeTypeName;

}
