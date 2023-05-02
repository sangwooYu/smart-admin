package net.lab1024.sa.admin.module.business.oa.enterprise.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.domain.PageParam;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class EnterpriseEmployeeQueryForm extends PageParam {

    @ApiModelProperty("검색어")
    @Length(max = 20, message = "검색어 최대 20자")
    private String keyword;

    @ApiModelProperty("회사 ID")
    @NotNull(message = "회사 ID는 비워둘 수 없습니다.")
    private Long enterpriseId;

    @ApiModelProperty(value = "로고 삭제", hidden = true)
    private Boolean deletedFlag;

}
