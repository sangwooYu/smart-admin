package net.lab1024.sa.admin.module.system.login.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.admin.module.system.menu.domain.vo.MenuVO;
import net.lab1024.sa.common.common.domain.RequestUser;
import net.lab1024.sa.common.common.enumeration.GenderEnum;
import net.lab1024.sa.common.common.enumeration.UserTypeEnum;
import net.lab1024.sa.common.common.swagger.ApiModelPropertyEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 员工登录
 *
 * @Author 1024创新实验室: 善逸
 * @Date 2021/8/4 21:15
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@Data
public class LoginEmployeeDetail implements UserDetails, RequestUser {

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("직원 ID")
    private Long employeeId;

    @ApiModelPropertyEnum(UserTypeEnum.class)
    private UserTypeEnum userType;

    @ApiModelProperty("로그인 계정")
    private String loginName;

    @ApiModelProperty("직원 이름")
    private String actualName;

    @ApiModelPropertyEnum(GenderEnum.class)
    private Integer gender;

    @ApiModelProperty("휴대폰 번호")
    private String phone;

    @ApiModelProperty("부서 ID")
    private Long departmentId;

    @ApiModelProperty("부서 이름")
    private String departmentName;

    @ApiModelProperty("administratorFlag")
    private Boolean administratorFlag;

    @ApiModelProperty("메뉴 목록")
    private List<MenuVO> menuList;

    @JsonIgnore
    private String loginPassword;

    @ApiModelProperty("마지막 로그인 ID")
    private String lastLoginIp;

    @ApiModelProperty("마지막 로그인 사용자 에이전트")
    private String lastLoginUserAgent;

    @ApiModelProperty("마지막 로그인 시간")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty("IP 요청")
    private String ip;

    @ApiModelProperty("사용자 에이전트 요청")
    private String userAgent;

    /**
     * security 权限串
     */
    private Set<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.loginPassword;
    }

    @Override
    public String getUsername() {
        return this.getLoginName();
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Long getUserId() {
        return employeeId;
    }

    @Override
    public String getUserName() {
        return actualName;
    }

    @Override
    public UserTypeEnum getUserType() {
        return userType;
    }

    @Override
    public String getIp() {
        return this.ip;
    }

    @Override
    public String getUserAgent() {
        return this.userAgent;
    }
}
