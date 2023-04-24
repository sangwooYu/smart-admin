package net.lab1024.sa.admin.module.system.login.service;

import cn.hutool.extra.servlet.ServletUtil;
import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.system.department.domain.vo.DepartmentVO;
import net.lab1024.sa.admin.module.system.department.service.DepartmentService;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.service.EmployeePermissionService;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.login.domain.LoginEmployeeDetail;
import net.lab1024.sa.admin.module.system.login.domain.LoginForm;
import net.lab1024.sa.admin.module.system.menu.domain.vo.MenuVO;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.common.constant.StringConst;
import net.lab1024.sa.common.common.domain.RequestUser;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.enumeration.UserTypeEnum;
import net.lab1024.sa.common.common.util.SmartBeanUtil;
import net.lab1024.sa.common.common.util.SmartEnumUtil;
import net.lab1024.sa.common.module.support.captcha.CaptchaService;
import net.lab1024.sa.common.module.support.captcha.domain.CaptchaVO;
import net.lab1024.sa.common.module.support.config.ConfigKeyEnum;
import net.lab1024.sa.common.module.support.config.ConfigService;
import net.lab1024.sa.common.module.support.loginlog.LoginLogResultEnum;
import net.lab1024.sa.common.module.support.loginlog.LoginLogService;
import net.lab1024.sa.common.module.support.loginlog.domain.LoginLogEntity;
import net.lab1024.sa.common.module.support.loginlog.domain.LoginLogVO;
import net.lab1024.sa.common.module.support.token.LoginDeviceEnum;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * 员工 登录服务
 *
 * @Author 1024创新实验室: 开云
 * @Date 2021-12-01 22:56:34
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@Slf4j
@Service
public class LoginService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private EmployeePermissionService employeePermissionService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private LoginLogService loginLogService;

    /**
     * 로그인 정보의 보조 캐시
     */
    private ConcurrentMap<Long, LoginEmployeeDetail> loginUserDetailCache = new ConcurrentLinkedHashMap.Builder<Long, LoginEmployeeDetail>().maximumWeightedCapacity(1000).build();

    /**
     * 인증 코드 받기
     *
     * @return
     */
    public ResponseDTO<CaptchaVO> getCaptcha() {
        return ResponseDTO.ok(captchaService.generateCaptcha());
    }

    /**
     * 직원 로그인
     *
     * @param loginForm
     * @return 사용자 로그인 정보 반환
     */
    public ResponseDTO<LoginEmployeeDetail> login(LoginForm loginForm, String ip, String userAgent) {
        LoginDeviceEnum loginDeviceEnum = SmartEnumUtil.getEnumByValue(loginForm.getLoginDevice(), LoginDeviceEnum.class);
        if (loginDeviceEnum == null) {
            return ResponseDTO.userErrorParam("현재 로그인 장치는 지원되지 않습니다!");
        }
        // 캘리브레이션 그래픽 확인 코드
        ResponseDTO<String> checkCaptcha = captchaService.checkCaptcha(loginForm);
        if (!checkCaptcha.getOk()) {
            return ResponseDTO.error(checkCaptcha);
        }

        /**
         * 계정 번호 및 계정 상태 확인
         */
        EmployeeEntity employeeEntity = employeeService.getByLoginName(loginForm.getLoginName());
        if (null == employeeEntity) {
            return ResponseDTO.userErrorParam("로그인 이름이 존재하지 않습니다!");
        }

        if (employeeEntity.getDisabledFlag()) {
            saveLoginLog(employeeEntity, ip, userAgent, "계정이 비활성화됨", LoginLogResultEnum.LOGIN_FAIL);
            return ResponseDTO.userErrorParam("계정이 비활성화되었으니 담당자에게 문의하세요!");
        }
        /**
         * 비밀번호를 인증합니다:
         * 1、범용 비밀번호
         * 2、실제 코드
         */
        String superPassword = EmployeeService.getEncryptPwd(configService.getConfigValue(ConfigKeyEnum.SUPER_PASSWORD));
        String requestPassword = EmployeeService.getEncryptPwd(loginForm.getPassword());
        if (!(superPassword.equals(requestPassword) || employeeEntity.getLoginPwd().equals(requestPassword))) {
            saveLoginLog(employeeEntity, ip, userAgent, "비밀번호 오류", LoginLogResultEnum.LOGIN_FAIL);
            return ResponseDTO.userErrorParam("로그인 이름 또는 비밀번호가 잘못되었습니다!");
        }

        // 로그인 토큰 생성, 토큰 저장
        Boolean superPasswordFlag = superPassword.equals(requestPassword);
        String token = tokenService.generateToken(employeeEntity.getEmployeeId(), employeeEntity.getActualName(), UserTypeEnum.ADMIN_EMPLOYEE, loginDeviceEnum, superPasswordFlag);

        // 직원 로그인 정보 가져오기
        LoginEmployeeDetail loginEmployeeDetail = loadLoginInfo(employeeEntity);
        loginEmployeeDetail.setToken(token);

        // 캐시에 저장
        loginUserDetailCache.put(employeeEntity.getEmployeeId(), loginEmployeeDetail);

        // 로그인 기록 저장
        saveLoginLog(employeeEntity, ip, userAgent, superPasswordFlag ? "범용 비밀번호 로그인" : loginDeviceEnum.getDesc(), LoginLogResultEnum.LOGIN_SUCCESS);

        return ResponseDTO.ok(loginEmployeeDetail);
    }


    /**
     * 로그인한 사용자에 대한 정보 가져오기
     *
     * @return
     */
    private LoginEmployeeDetail loadLoginInfo(EmployeeEntity employeeEntity) {
        LoginEmployeeDetail loginEmployeeDetail = SmartBeanUtil.copy(employeeEntity, LoginEmployeeDetail.class);
        loginEmployeeDetail.setUserType(UserTypeEnum.ADMIN_EMPLOYEE);

        //섹터 정보
        DepartmentVO department = departmentService.getDepartmentById(employeeEntity.getDepartmentId());
        loginEmployeeDetail.setDepartmentName(null == department ? StringConst.EMPTY : department.getName());

        /**
         * 프런트엔드 메뉴 및 백엔드 권한 얻기
         * 1、데이터베이스에서 모든 권한 가져오기
         * 2、메뉴 및 백엔드 권한 조합하기
         */
        List<MenuVO> menuAndPointsList = employeePermissionService.getEmployeeMenuAndPointsList(employeeEntity.getEmployeeId(), employeeEntity.getAdministratorFlag());
        //프런트 엔드 메뉴
        loginEmployeeDetail.setMenuList(menuAndPointsList);
        //백엔드 권한
        loginEmployeeDetail.setAuthorities(employeePermissionService.buildAuthorities(menuAndPointsList));

        //마지막 로그인 정보
        LoginLogVO loginLogVO = loginLogService.queryLastByUserId(employeeEntity.getEmployeeId(), UserTypeEnum.ADMIN_EMPLOYEE);
        if (loginLogVO != null) {
            loginEmployeeDetail.setLastLoginIp(loginLogVO.getLoginIp());
            loginEmployeeDetail.setLastLoginTime(loginLogVO.getCreateTime());
            loginEmployeeDetail.setLastLoginUserAgent(loginLogVO.getUserAgent());
        }

        return loginEmployeeDetail;
    }

    /**
     * 로그인 로그 보관
     *
     * @param employeeEntity
     * @param ip
     * @param userAgent
     */
    private void saveLoginLog(EmployeeEntity employeeEntity, String ip, String userAgent, String remark, LoginLogResultEnum result) {
        LoginLogEntity loginEntity = LoginLogEntity.builder()
                .userId(employeeEntity.getEmployeeId())
                .userType(UserTypeEnum.ADMIN_EMPLOYEE.getValue())
                .userName(employeeEntity.getActualName())
                .userAgent(userAgent)
                .loginIp(ip)
                .remark(remark)
                .loginResult(result.getValue())
                .createTime(LocalDateTime.now())
                .build();
        loginLogService.log(loginEntity);
    }


    /**
     * 사용자 정보 캐시 제거
     *
     * @param requestUserId
     */
    public void removeLoginUserDetailCache(Long requestUserId) {
        loginUserDetailCache.remove(requestUserId);
    }

    /**
     * 로그인 토큰에서 요청자의 정보를 가져옵니다.
     *
     * @param
     * @return
     */
    public LoginEmployeeDetail getLoginUserDetail(String token, HttpServletRequest request) {
        Long requestUserId = tokenService.getUserIdAndValidateToken(token);
        if (requestUserId == null) {
            return null;
        }
        // 사용자 정보 검색
        LoginEmployeeDetail loginEmployeeDetail = loginUserDetailCache.get(requestUserId);
        if (loginEmployeeDetail == null) {
            // 기본 직원 정보
            EmployeeEntity employeeEntity = employeeService.getById(requestUserId);
            if (employeeEntity == null) {
                return null;
            }

            loginEmployeeDetail = this.loadLoginInfo(employeeEntity);
            loginEmployeeDetail.setToken(token);
            loginUserDetailCache.put(requestUserId, loginEmployeeDetail);
        }

        //요청 IP 및 사용자 에이전트 업데이트
        loginEmployeeDetail.setUserAgent(ServletUtil.getHeaderIgnoreCase(request, RequestHeaderConst.USER_AGENT));
        loginEmployeeDetail.setIp(ServletUtil.getClientIP(request));

        return loginEmployeeDetail;
    }


    /**
     * 로그아웃하고 토큰 캐시 지우기
     *
     * @return
     */
    public ResponseDTO<String> logout(String token, RequestUser requestUser) {
        loginUserDetailCache.remove(requestUser.getUserId());
        tokenService.removeToken(token);
        //로그아웃 로그 저장
        saveLogoutLog(requestUser, requestUser.getIp(), requestUser.getUserAgent());
        return ResponseDTO.ok();
    }

    /**
     * 로그아웃 로그 저장
     */
    private void saveLogoutLog(RequestUser requestUser, String ip, String userAgent) {
        LoginLogEntity loginEntity = LoginLogEntity.builder()
                .userId(requestUser.getUserId())
                .userType(requestUser.getUserType().getValue())
                .userName(requestUser.getUserName())
                .userAgent(userAgent)
                .loginIp(ip)
                .loginResult(LoginLogResultEnum.LOGIN_OUT.getValue())
                .createTime(LocalDateTime.now())
                .build();
        loginLogService.log(loginEntity);
    }
}
