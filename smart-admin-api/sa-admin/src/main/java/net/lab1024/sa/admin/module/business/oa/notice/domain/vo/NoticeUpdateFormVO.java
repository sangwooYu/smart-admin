package net.lab1024.sa.admin.module.business.oa.notice.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.json.serializer.FileKeyVoSerializer;

import java.util.List;

@Data
public class NoticeUpdateFormVO extends NoticeVO {

    @ApiModelProperty("일반 텍스트 콘텐츠")
    private String contentText;

    @ApiModelProperty("HTML 콘텐츠")
    private String contentHtml;

    @ApiModelProperty("부록")
    @JsonSerialize(using = FileKeyVoSerializer.class)
    private String attachment;

    @ApiModelProperty("가시 범위")
    private List<NoticeVisibleRangeVO> visibleRangeList;

}
