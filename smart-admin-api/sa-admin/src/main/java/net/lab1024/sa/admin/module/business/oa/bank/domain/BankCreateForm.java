package net.lab1024.sa.admin.module.business.oa.bank.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BankCreateForm {

    @ApiModelProperty("계좌 은행")
    @NotBlank(message = "계좌 은행이 비어 있으면 안 됩니다.")
    @Length(max = 200, message = "계좌 개설 은행 최대 200자")
    private String bankName;

    @ApiModelProperty("계정 이름")
    @NotBlank(message = "계정 이름은 비워둘 수 없습니다.")
    @Length(max = 200, message = "계정 이름 최대 200자")
    private String accountName;

    @ApiModelProperty("계정 번호")
    @NotBlank(message = "계정 번호는 비워 둘 수 없습니다.")
    @Length(max = 200, message = "계정 번호 최대 200자")
    private String accountNumber;

    @ApiModelProperty("비고")
    @Length(max = 500, message = "최대 500자의 메모")
    private String remark;

    @ApiModelProperty("대중이")
    @NotNull(message = "공개 여부는 비워둘 수 없습니다.")
    private Boolean businessFlag;

    @ApiModelProperty("기업")
    @NotNull(message = "비즈니스는 비워둘 수 없습니다.")
    private Long enterpriseId;

    @ApiModelProperty("비활성화 상태")
    @NotNull(message = "비활성화 상태는 비워둘 수 없습니다.")
    private Boolean disabledFlag;

    @ApiModelProperty(value = "작성자", hidden = true)
    private Long createUserId;

    @ApiModelProperty(value = "작성자", hidden = true)
    private String createUserName;
}
