package net.lab1024.sa.admin.module.business.oa.invoice.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lab1024.sa.common.module.support.datatracer.annoation.DataTracerFieldLabel;

import java.time.LocalDateTime;

/**
 * OA发票信息
 *
 * @Author 1024创新实验室: 善逸
 * @Date 2022-06-23 19:32:59
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@Data
@TableName("t_oa_invoice")
public class InvoiceEntity {

    /**
     * 发票信息ID
     */
    @TableId(type = IdType.AUTO)
    private Long invoiceId;

    /**
     * 开票抬头
     */
    @DataTracerFieldLabel("송장 수취인")
    private String invoiceHeads;

    /**
     * 纳税人识别号
     */
    @DataTracerFieldLabel("납세자 식별 번호")
    private String taxpayerIdentificationNumber;

    /**
     * 银行账户
     */
    @DataTracerFieldLabel("은행 계좌")
    private String accountNumber;

    /**
     * 开户行
     */
    @DataTracerFieldLabel("계좌 개설 은행")
    private String bankName;

    /**
     * 备注
     */
    @DataTracerFieldLabel("비고")
    private String remark;

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
    @DataTracerFieldLabel("상태 삭제")
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
