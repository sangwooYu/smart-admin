package net.lab1024.sa.admin.module.business.oa.notice.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class NoticeVO {

    @ApiModelProperty("id")
    private Long noticeId;

    @ApiModelProperty("제목")
    private String title;

    @ApiModelProperty("분류")
    private Long noticeTypeId;

    @ApiModelProperty("분류 이름")
    private String noticeTypeName;

    @ApiModelProperty("모두 표시됨")
    private Boolean allVisibleFlag;

    @ApiModelProperty("정기적으로 게시할지 여부")
    private Boolean scheduledPublishFlag;

    @ApiModelProperty("릴리스 상태")
    private Boolean publishFlag;

    @ApiModelProperty("릴리스 날짜")
    private LocalDateTime publishTime;

    @ApiModelProperty("작성자")
    @NotBlank(message = "작성자는 비어있을 수 없습니다.")
    private String author;

    @ApiModelProperty("출처")
    @NotBlank(message = "제목은 비워 둘 수 없습니다.")
    private String source;

    @ApiModelProperty("문서 번호")
    private String documentNumber;

    @ApiModelProperty("페이지 조회수")
    private Integer pageViewCount;

    @ApiModelProperty("사용자 보기")
    private Integer userViewCount;

    @ApiModelProperty("로고 삭제")
    private Boolean deletedFlag;

    @ApiModelProperty("제작자 이름")
    private String createUserName;

    @ApiModelProperty("생성 시간")
    private LocalDateTime createTime;

    @ApiModelProperty("업데이트 시간")
    private LocalDateTime updateTime;

}
