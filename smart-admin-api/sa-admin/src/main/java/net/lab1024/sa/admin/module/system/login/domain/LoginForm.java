package net.lab1024.sa.admin.module.system.login.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.swagger.ApiModelPropertyEnum;
import net.lab1024.sa.common.common.util.SmartVerificationUtil;
import net.lab1024.sa.common.common.validator.enumeration.CheckEnum;
import net.lab1024.sa.common.module.support.captcha.domain.CaptchaForm;
import net.lab1024.sa.common.module.support.token.LoginDeviceEnum;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 员工登录
 *
 * @Author 1024创新实验室: 开云
 * @Date 2021-12-19 11:49:45
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@Data
public class LoginForm extends CaptchaForm {

    @ApiModelProperty("로그인 계정")
    @NotBlank(message = "로그인 계정은 비워 둘 수 없습니다.")
    @Length(max = 30, message = "로그인 계정 최대 30자")
    private String loginName;

    @ApiModelProperty("비밀번호")
    @NotBlank(message = "비밀번호는 비워 둘 수 없습니다.")
    @Pattern(regexp = SmartVerificationUtil.PWD_REGEXP, message = "6~15자리 비밀번호(숫자 | 대문자 및 소문자 | 소수점)를 입력하세요.")
    private String password;

    @ApiModelProperty(value = "로그인 터미널")
    @ApiModelPropertyEnum(LoginDeviceEnum.class)
    @CheckEnum(value = LoginDeviceEnum.class, required = true, message = "이 단말기는 로그인을 허용하지 않습니다.")
    private Integer loginDevice;
}
