package net.lab1024.sa.admin.module.business.oa.notice.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.json.serializer.FileKeyVoSerializer;

import java.util.List;

/**
 * 用于更新 【通知、公告】 的 VO 对象
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-08-12 21:40:39
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
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
