package net.lab1024.sa.admin.module.business.oa.notice.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.json.serializer.FileKeyVoSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 通知公告 详情
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-08-12 21:40:39
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@Data
public class NoticeDetailVO {

    @ApiModelProperty("id")
    private Long noticeId;

    @ApiModelProperty("제목")
    private String title;

    @ApiModelProperty("분류")
    private Long noticeTypeId;

    @ApiModelProperty("분류 이름")
    private Long noticeTypeName;

    @ApiModelProperty("모두 표시됨")
    @NotNull(message = "표시 여부와 상관없이 모두 비워둘 수 없습니다.")
    private Boolean allVisibleFlag;

    @ApiModelProperty("정기적으로 게시할지 여부")
    @NotNull(message = "정기적으로 게시할지 여부는 비워 둘 수 없습니다.")
    private Boolean scheduledPublishFlag;

    @ApiModelProperty("일반 텍스트 콘텐츠")
    private String contentText;

    @ApiModelProperty("HTML 콘텐츠")
    private String contentHtml;

    @ApiModelProperty("부록")
    @JsonSerialize(using = FileKeyVoSerializer.class)
    private String attachment;

    @ApiModelProperty("릴리스 날짜")
    @NotNull(message = "릴리스 날짜는 비워둘 수 없습니다")
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

    @ApiModelProperty("제작자 이름")
    private Long createUserName;

    @ApiModelProperty("생성 시간")
    private LocalDateTime createTime;

    @ApiModelProperty("업데이트 시간")
    private LocalDateTime updateTime;

}
