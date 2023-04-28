package net.lab1024.sa.admin.module.system.menu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.common.AdminBaseController;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.menu.domain.form.MenuAddForm;
import net.lab1024.sa.admin.module.system.menu.domain.form.MenuUpdateForm;
import net.lab1024.sa.admin.module.system.menu.domain.vo.MenuTreeVO;
import net.lab1024.sa.admin.module.system.menu.domain.vo.MenuVO;
import net.lab1024.sa.admin.module.system.menu.service.MenuService;
import net.lab1024.sa.common.common.domain.RequestUrlVO;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.util.SmartRequestUtil;
import net.lab1024.sa.common.module.support.operatelog.annoation.OperateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 菜单
 *
 * @Author 1024创新实验室: 善逸
 * @Date 2022-03-06 22:04:37
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@OperateLog
@RestController
@Api(tags = {AdminSwaggerTagConst.System.SYSTEM_MENU})
public class MenuController extends AdminBaseController {

    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "메뉴 추가 @author ysw")
    @PostMapping("/menu/add")
    @PreAuthorize("@saAuth.checkPermission('system:menu:add')")
    public ResponseDTO<String> addMenu(@RequestBody @Valid MenuAddForm menuAddForm) {
        menuAddForm.setCreateUserId(SmartRequestUtil.getRequestUserId());
        return menuService.addMenu(menuAddForm);
    }

    @ApiOperation(value = "업데이트 메뉴 @author ysw")
    @PostMapping("/menu/update")
    @PreAuthorize("@saAuth.checkPermission('system:menu:update')")
    public ResponseDTO<String> updateMenu(@RequestBody @Valid MenuUpdateForm menuUpdateForm) {
        menuUpdateForm.setUpdateUserId(SmartRequestUtil.getRequestUserId());
        return menuService.updateMenu(menuUpdateForm);
    }

    @ApiOperation(value = "일괄 삭제 메뉴 @author ysw")
    @GetMapping("/menu/batchDelete")
    @PreAuthorize("@saAuth.checkPermission('system:menu:delete,system:menu:batch:delete')")
    public ResponseDTO<String> batchDeleteMenu(@RequestParam("menuIdList") List<Long> menuIdList) {
        return menuService.batchDeleteMenu(menuIdList, SmartRequestUtil.getRequestUserId());
    }

    @ApiOperation(value = "메뉴 목록 검색 @author ysw")
    @GetMapping("/menu/query")
    public ResponseDTO<List<MenuVO>> queryMenuList() {
        return ResponseDTO.ok(menuService.queryMenuList(null));
    }

    @ApiOperation(value = "메뉴 세부 정보 확인 @author ysw")
    @GetMapping("/menu/detail/{menuId}")
    public ResponseDTO<MenuVO> getMenuDetail(@PathVariable Long menuId) {
        return menuService.getMenuDetail(menuId);
    }

    @ApiOperation(value = "쿼리 메뉴 트리 @author ysw")
    @GetMapping("/menu/tree")
    public ResponseDTO<List<MenuTreeVO>> queryMenuTree(@RequestParam("onlyMenu") Boolean onlyMenu) {
        return menuService.queryMenuTree(onlyMenu);
    }

    @ApiOperation(value = "모든 요청 경로 가져오기 @author ysw")
    @GetMapping("/menu/auth/url")
    public ResponseDTO<List<RequestUrlVO>> getAuthUrl() {
        return menuService.getAuthUrl();
    }
}
