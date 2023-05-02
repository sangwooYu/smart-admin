package net.lab1024.sa.admin.module.business.oa.notice.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NoticeEmployeeVO extends NoticeVO {

    @ApiModelProperty("보기 또는 보기 안 함")
    private Boolean viewFlag;

    @ApiModelProperty("릴리스 날짜")
    private LocalDate publishDate;

}
