package net.lab1024.sa.admin.module.business.oa.notice.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NoticeUpdateForm extends NoticeAddForm {

    @ApiModelProperty("id")
    @NotNull(message = "알림 ID는 비워둘 수 없습니다.")
    private Long noticeId;

}
