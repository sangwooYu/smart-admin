package net.lab1024.sa.admin.module.business.oa.notice.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@TableName("t_notice_type")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeTypeEntity {

    @TableId(type = IdType.AUTO)
    private Long noticeTypeId;

    /**
     * 名称
     */
    private String noticeTypeName;


    private LocalDateTime updateTime;

    private LocalDateTime createTime;
}
