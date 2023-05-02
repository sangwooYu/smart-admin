package net.lab1024.sa.admin.module.business.oa.enterprise.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.admin.module.business.oa.enterprise.constant.EnterpriseTypeEnum;
import net.lab1024.sa.common.common.json.serializer.FileKeyVoSerializer;
import net.lab1024.sa.common.common.swagger.ApiModelPropertyEnum;

import java.time.LocalDateTime;

@Data
public class EnterpriseVO {

    @ApiModelProperty("기업 ID")
    private Long enterpriseId;

    @ApiModelProperty("회사 이름")
    private String enterpriseName;

    @ApiModelProperty("기업 로고")
    @JsonSerialize(using = FileKeyVoSerializer.class)
    private String enterpriseLogo;

    @ApiModelProperty("통합 소셜 신용 코드")
    private String unifiedSocialCreditCode;

    @ApiModelPropertyEnum(desc = "유형", value = EnterpriseTypeEnum.class)
    private Integer type;

    @ApiModelProperty("담당자")
    private String contact;

    @ApiModelProperty("연락처 전화")
    private String contactPhone;

    @ApiModelProperty("이메일")
    private String email;

    @ApiModelProperty("주")
    private Integer province;

    @ApiModelProperty("주 이름")
    private String provinceName;

    @ApiModelProperty("도시")
    private Integer city;

    @ApiModelProperty("도시 이름")
    private String cityName;

    @ApiModelProperty("지구 및 카운티")
    private Integer district;

    @ApiModelProperty("지구 및 카운티 이름")
    private String districtName;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("비즈니스 라이선스")
    @JsonSerialize(using = FileKeyVoSerializer.class)
    private String businessLicense;

    @ApiModelProperty("비활성화 상태")
    private Boolean disabledFlag;

    @ApiModelProperty("createUserId")
    private Long createUserId;

    @ApiModelProperty("createUserName")
    private String createUserName;

    @ApiModelProperty("생성 시간")
    private LocalDateTime createTime;

    @ApiModelProperty("업데이트 시간")
    private LocalDateTime updateTime;

}
