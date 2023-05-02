package net.lab1024.sa.common.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户级别的错误码（用户引起的错误返回码，可以不用关注）
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2021/09/21 22:12:27
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {

    PARAM_ERROR(30001, "매개변수 오류"),

    DATA_NOT_EXIST(30002, "데이터를 찾을 수 없습니다."),

    ALREADY_EXIST(30003, "데이터가 이미 존재합니다."),

    REPEAT_SUBMIT(30004, "요청이 너무 빈번합니다. 조금만 기다렸다가 다시 요청해주세요."),

    NO_PERMISSION(30005, "죄송합니다. 이 리소스에 액세스할 수 없습니다."),

    DEVELOPING(30006, "이 시스템은 개발 중이므로 계속 지켜봐 주세요!"),

    LOGIN_STATE_INVALID(30007, "로그인하지 않았거나 로그인 정보가 유효하지 않습니다. 다시 로그인하세요!"),

    USER_STATUS_ERROR(30008, "사용자 상태 예외"),

    FORM_REPEAT_SUBMIT(30009, "중복 요청하지 마세요.");

    private final int code;

    private final String msg;

    private final String level;

    UserErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.level = LEVEL_USER;
    }
}
