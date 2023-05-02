package net.lab1024.sa.admin.module.business.oa.enterprise.domain.form;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.admin.module.business.oa.enterprise.constant.EnterpriseTypeEnum;
import net.lab1024.sa.common.common.json.deserializer.FileKeyVoDeserializer;
import net.lab1024.sa.common.common.json.serializer.FileKeyVoSerializer;
import net.lab1024.sa.common.common.swagger.ApiModelPropertyEnum;
import net.lab1024.sa.common.common.util.SmartVerificationUtil;
import net.lab1024.sa.common.common.validator.enumeration.CheckEnum;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class EnterpriseCreateForm {

    @ApiModelProperty("회사 이름")
    @NotBlank(message = "비즈니스 이름은 비워 둘 수 없습니다.")
    @Length(max = 200, message = "비즈니스 이름 최대 200자")
    private String enterpriseName;

    @ApiModelProperty("기업 로고")
    @JsonSerialize(using = FileKeyVoSerializer.class)
    @JsonDeserialize(using = FileKeyVoDeserializer.class)
    private String enterpriseLogo;

    @ApiModelProperty("통합 소셜 신용 코드")
    @NotBlank(message = "통합 소셜 신용 코드는 비어 있을 수 없습니다.")
    @Length(max = 200, message = "최대 200자의 통합 소셜 신용 코드")
    private String unifiedSocialCreditCode;

    @ApiModelProperty("담당자")
    @NotBlank(message = "연락처는 비워 둘 수 없습니다.")
    @Length(max = 100, message = "최대 100자까지 연락처 입력")
    private String contact;

    @ApiModelProperty("휴대폰 번호")
    @NotBlank(message = "휴대폰 번호는 비워 둘 수 없습니다.")
    @Pattern(regexp = SmartVerificationUtil.PHONE_REGEXP, message = "잘못된 휴대폰 번호 형식")
    private String contactPhone;

    @ApiModelPropertyEnum(desc = "유형", value = EnterpriseTypeEnum.class)
    @CheckEnum(message = "잘못된 유형", value = EnterpriseTypeEnum.class)
    private Integer type;

    @ApiModelProperty("이메일")
    @Pattern(regexp = SmartVerificationUtil.EMAIL, message = "잘못된 이메일 형식")
    private String email;

    @ApiModelProperty("주")
    private Integer province;

    @ApiModelProperty("주 이름")
    private String provinceName;

    @ApiModelProperty("도시")
    private Integer city;

    @ApiModelProperty("도시 이름")
    private String cityName;

    @ApiModelProperty("지구 및 구역")
    private Integer district;

    @ApiModelProperty("지구 및 구역 이름")
    private String districtName;

    @ApiModelProperty("주소 세부 정보")
    @Length(max = 500, message = "최대 500자의 주소 세부 정보")
    private String address;

    @ApiModelProperty("비즈니스 라이선스")
    @JsonSerialize(using = FileKeyVoSerializer.class)
    @JsonDeserialize(using = FileKeyVoDeserializer.class)
    private String businessLicense;

    @ApiModelProperty("비활성화 상태")
    @NotNull(message = "비활성화 상태는 비워둘 수 없습니다.")
    private Boolean disabledFlag;

    @ApiModelProperty(value = "작성자", hidden = true)
    private Long createUserId;

    @ApiModelProperty(value = "작성자", hidden = true)
    private String createUserName;

}
