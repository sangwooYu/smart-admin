package net.lab1024.sa.admin.module.system.menu.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 菜单功能点操作Form
 *
 * @Author 1024创新实验室: 善逸
 * @Date 2022-03-06 22:04:37
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Data
public class MenuPointsOperateForm {

    @ApiModelProperty("메뉴 ID")
    private Long menuId;

    @ApiModelProperty("함수 포인트 이름")
    @NotBlank(message = "함수점은 비어있을 수 없습니다.")
    @Length(max = 30, message = "최대 30자의 함수 포인트")
    private String menuName;

    @ApiModelProperty("비활성화 상태")
    @NotNull(message = "비활성화 상태는 비워둘 수 없습니다.")
    private Boolean disabledFlag;

    @ApiModelProperty("백엔드 인터페이스 권한 수집")
    private List<String> apiPermsList;

    @ApiModelProperty("권한 문자열")
    private String webPerms;

    @ApiModelProperty("기능 포인트 관련 메뉴 ID")
    private Long contextMenuId;
}
