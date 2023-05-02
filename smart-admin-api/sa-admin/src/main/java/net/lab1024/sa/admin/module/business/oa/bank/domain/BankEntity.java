package net.lab1024.sa.admin.module.business.oa.bank.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lab1024.sa.common.module.support.datatracer.annoation.DataTracerFieldLabel;

import java.time.LocalDateTime;

@Data
@TableName("t_oa_bank")
public class BankEntity {

    /**
     * 银行信息ID
     */
    @TableId(type = IdType.AUTO)
    @DataTracerFieldLabel("은행 정보 ID")
    private Long bankId;

    /**
     * 开户银行
     */
    @DataTracerFieldLabel("계좌 은행")
    private String bankName;

    /**
     * 账户名称
     */
    @DataTracerFieldLabel("계정 이름")
    private String accountName;

    /**
     * 账号
     */
    @DataTracerFieldLabel("계정 번호")
    private String accountNumber;

    /**
     * 备注
     */
    @DataTracerFieldLabel("비고")
    private String remark;

    /**
     * 是否对公
     */
    @DataTracerFieldLabel("대중이")
    private Boolean businessFlag;

    /**
     * 企业ID
     */
    private Long enterpriseId;

    /**
     * 禁用状态
     */
    @DataTracerFieldLabel("비활성화 상태")
    private Boolean disabledFlag;

    /**
     * 删除状态
     */
    private Boolean deletedFlag;

    /**
     * 创建人ID
     */
    private Long createUserId;

    /**
     * 创建人ID
     */
    private String createUserName;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
