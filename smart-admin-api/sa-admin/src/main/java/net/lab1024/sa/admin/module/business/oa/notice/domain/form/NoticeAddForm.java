package net.lab1024.sa.admin.module.business.oa.notice.domain.form;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.json.deserializer.FileKeyVoDeserializer;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class NoticeAddForm {

    @ApiModelProperty("제목")
    @NotBlank(message = "제목은 비워 둘 수 없습니다.")
    @Length(max = 200, message = "제목 최대 200자")
    private String title;

    @ApiModelProperty("분류")
    @NotNull(message = "카테고리는 비워둘 수 없습니다.")
    private Long noticeTypeId;

    @ApiModelProperty("모두 표시됨")
    @NotNull(message = "표시 여부와 상관없이 모두 비워둘 수 없습니다.")
    private Boolean allVisibleFlag;

    @ApiModelProperty("정기적으로 게시할지 여부")
    @NotNull(message = "정기적으로 게시할지 여부는 비워 둘 수 없습니다.")
    private Boolean scheduledPublishFlag;

    @ApiModelProperty("릴리스 날짜")
    @NotNull(message = "게시 시간은 비워둘 수 없습니다.")
    private LocalDateTime publishTime;

    @ApiModelProperty("일반 텍스트 콘텐츠")
    @NotNull(message = "텍스트 콘텐츠는 비워 둘 수 없습니다.")
    private String contentText;

    @ApiModelProperty("HTML 콘텐츠")
    @NotNull(message = "HTML 콘텐츠는 비어 있을 수 없습니다.")
    private String contentHtml;

    @ApiModelProperty("부록, 여러 쉼표로 구분|선택 사항")
    @Length(max = 1000, message = "최대 1000자")
    @JsonDeserialize(using = FileKeyVoDeserializer.class)
    private String attachment;

    @ApiModelProperty("작성자")
    @NotBlank(message = "작성자 값은 비어있을 수 없습니다.")
    private String author;

    @ApiModelProperty("출처")
    @NotBlank(message = "제목은 비워 둘 수 없습니다.")
    private String source;

    @ApiModelProperty("문서 번호")
    private String documentNumber;

    @ApiModelProperty(hidden = true)
    private Long createUserId;

    @ApiModelProperty("가시 범위 설정|선택 사항")
    @Valid
    private List<NoticeVisibleRangeForm> visibleRangeList;
}
