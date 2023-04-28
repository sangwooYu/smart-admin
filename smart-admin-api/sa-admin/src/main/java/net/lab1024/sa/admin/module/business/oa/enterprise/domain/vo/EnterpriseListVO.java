package net.lab1024.sa.admin.module.business.oa.enterprise.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * OA企业模块列表
 *
 * @author lihaifan
 * @date 2022/6/23 14:31
 */
@Data
public class EnterpriseListVO {

    @ApiModelProperty("기업 ID")
    private Long enterpriseId;

    @ApiModelProperty("회사 이름")
    private String enterpriseName;
}
