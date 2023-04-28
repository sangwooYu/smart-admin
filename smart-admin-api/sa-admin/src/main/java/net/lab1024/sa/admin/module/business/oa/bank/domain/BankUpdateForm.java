package net.lab1024.sa.admin.module.business.oa.bank.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * OA办公-银行信息更新
 *
 * @Author 1024创新实验室:善逸
 * @Date 2022/6/23 21:59:22
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@Data
public class BankUpdateForm extends BankCreateForm {

    @ApiModelProperty("은행 정보 ID")
    @NotNull(message = "은행 정보 ID는 비워둘 수 없습니다.")
    private Long bankId;
}
