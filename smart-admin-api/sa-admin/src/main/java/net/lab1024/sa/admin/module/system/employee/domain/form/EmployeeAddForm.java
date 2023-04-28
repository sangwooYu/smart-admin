package net.lab1024.sa.admin.module.system.employee.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.enumeration.GenderEnum;
import net.lab1024.sa.common.common.swagger.ApiModelPropertyEnum;
import net.lab1024.sa.common.common.validator.enumeration.CheckEnum;
import org.hibernate.validator.constraints.Length;
import net.lab1024.sa.common.common.util.SmartVerificationUtil;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * 添加员工
 *
 * @Author 1024创新实验室: 开云
 * @Date 2021-12-20 21:06:49
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Data
public class EmployeeAddForm {

    @ApiModelProperty("이름")
    @NotNull(message = "이름은 비워 둘 수 없습니다.")
    @Length(max = 30, message = "최대 30자까지 이름 지정")
    private String actualName;

    @ApiModelProperty("로그인 계정")
    @NotNull(message = "로그인 계정은 비어 있을 수 없습니다.")
    @Length(max = 30, message = "로그인 계정 최대 30자")
    private String loginName;

    @ApiModelPropertyEnum(GenderEnum.class)
    @CheckEnum(value = GenderEnum.class, message = "성별 오류")
    private Integer gender;

    @ApiModelProperty("부서 ID")
    @NotNull(message = "부서 ID는 비워둘 수 없습니다.")
    private Long departmentId;

    @ApiModelProperty("비활성화 여부")
    @NotNull(message = "비활성화 여부는 비워둘 수 없습니다.")
    private Boolean disabledFlag;

    @ApiModelProperty("휴대폰 번호")
    @NotNull(message = "휴대폰 번호는 비워 둘 수 없습니다.")
    @Pattern(regexp = SmartVerificationUtil.PHONE_REGEXP, message = "잘못된 휴대폰 번호 형식")
    private String phone;

    @ApiModelProperty("캐릭터 목록")
    private List<Long> roleIdList;
}
