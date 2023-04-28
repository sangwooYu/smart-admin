package net.lab1024.sa.admin.module.system.menu.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.admin.module.system.menu.constant.MenuPermsTypeEnum;
import net.lab1024.sa.common.common.swagger.ApiModelPropertyEnum;
import net.lab1024.sa.common.common.validator.enumeration.CheckEnum;
import org.hibernate.validator.constraints.Length;
import net.lab1024.sa.admin.module.system.menu.constant.MenuTypeEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 菜单基础
 *
 * @Author 1024创新实验室: 善逸
 * @Date 2022-03-06 22:04:37
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Data
public class MenuBaseForm {

    @ApiModelProperty("메뉴 이름")
    @NotBlank(message = "메뉴 이름은 비워둘 수 없습니다.")
    @Length(max = 30, message = "메뉴 이름 최대 30자")
    private String menuName;

    @ApiModelPropertyEnum(value = MenuTypeEnum.class, desc = "유형")
    @CheckEnum(value = MenuTypeEnum.class, message = "유형 오류")
    private Integer menuType;

    @ApiModelProperty("상위 메뉴 ID 상위 메뉴는 0을 전달할 수 없습니다.")
    @NotNull(message = "상위 메뉴 ID는 비어 있을 수 없습니다.")
    private Long parentId;

    @ApiModelProperty("순서 표시")
    private Integer sort;

    @ApiModelProperty("라우팅 주소")
    private String path;

    @ApiModelProperty("컴포넌트 경로")
    private String component;

    @ApiModelProperty("frameFlag")
    @NotNull(message = "외부 링크는 비어 있을 수 없습니다.")
    private Boolean frameFlag;

    @ApiModelProperty("외부 링크 주소")
    private String frameUrl;

    @ApiModelProperty("캐시 여부")
    @NotNull(message = "캐시를 비울 수 없는지 여부")
    private Boolean cacheFlag;

    @ApiModelProperty("상태 표시")
    @NotNull(message = "디스플레이 상태는 비어 있을 수 없습니다.")
    private Boolean visibleFlag;

    @ApiModelProperty("비활성화 상태")
    @NotNull(message = "비활성화 상태는 비워둘 수 없습니다.")
    private Boolean disabledFlag;

    @ApiModelPropertyEnum(value = MenuPermsTypeEnum.class, desc = "권한 유형")
    @CheckEnum(value = MenuPermsTypeEnum.class, message = "권한 유형")
    private Integer permsType;

    @ApiModelProperty("프런트엔드 권한 문자열")
    private String webPerms;

    @ApiModelProperty("백엔드 권한 문자열")
    private String apiPerms;

    @ApiModelProperty("백엔드 인터페이스 권한 수집(분할)")
    private List<String> apiPermsList;

    @ApiModelProperty("메뉴 아이콘")
    private String icon;

    @ApiModelProperty("기능 포인트 관련 메뉴 ID")
    private Long contextMenuId;
}
