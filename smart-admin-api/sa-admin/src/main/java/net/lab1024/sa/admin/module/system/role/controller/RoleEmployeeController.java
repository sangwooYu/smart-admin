package net.lab1024.sa.admin.module.system.role.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.common.AdminBaseController;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.employee.domain.vo.EmployeeVO;
import net.lab1024.sa.admin.module.system.role.domain.form.RoleEmployeeQueryForm;
import net.lab1024.sa.admin.module.system.role.domain.form.RoleEmployeeUpdateForm;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleSelectedVO;
import net.lab1024.sa.admin.module.system.role.service.RoleEmployeeService;
import net.lab1024.sa.common.common.domain.PageResult;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.module.support.operatelog.annoation.OperateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色的员工
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-02-26 22:09:59
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@OperateLog
@RestController
@Api(tags = {AdminSwaggerTagConst.System.SYSTEM_ROLE_EMPLOYEE})
public class RoleEmployeeController extends AdminBaseController {

    @Autowired
    private RoleEmployeeService roleEmployeeService;

    @ApiOperation(value = "역할 아래의 직원 목록 쿼리  @author ysw")
    @PostMapping("/role/employee/queryEmployee")
    public ResponseDTO<PageResult<EmployeeVO>> queryEmployee(@Valid @RequestBody RoleEmployeeQueryForm roleEmployeeQueryForm) {
        return roleEmployeeService.queryEmployee(roleEmployeeQueryForm);
    }

    @ApiOperation(value = "역할 아래의 모든 직원 목록 가져오기(페이징 없음)  @author ysw")
    @GetMapping("/role/employee/getAllEmployeeByRoleId/{roleId}")
    public ResponseDTO<List<EmployeeVO>> listAllEmployeeRoleId(@PathVariable Long roleId) {
        return ResponseDTO.ok(roleEmployeeService.getAllEmployeeByRoleId(roleId));
    }

    @ApiOperation(value = "역할 구성원 목록에서 직원 제거 @author ysw")
    @GetMapping("/role/employee/removeEmployee")
    @PreAuthorize("@saAuth.checkPermission('system:role:employee:delete')")
    public ResponseDTO<String> removeEmployee(Long employeeId, Long roleId) {
        return roleEmployeeService.removeRoleEmployee(employeeId, roleId);
    }

    @ApiOperation(value = "역할 구성원 목록에서 직원 일괄 제거 @author ysw")
    @PostMapping("/role/employee/batchRemoveRoleEmployee")
    @PreAuthorize("@saAuth.checkPermission('system:role:employee:batch:delete')")
    public ResponseDTO<String> batchRemoveEmployee(@Valid @RequestBody RoleEmployeeUpdateForm updateForm) {
        return roleEmployeeService.batchRemoveRoleEmployee(updateForm);
    }

    @ApiOperation(value = "역할 구성원 목록에 직원 일괄 추가 @author ysw")
    @PostMapping("/role/employee/batchAddRoleEmployee")
    @PreAuthorize("@saAuth.checkPermission('system:role:employee:add')")
    public ResponseDTO<String> addEmployeeList(@Valid @RequestBody RoleEmployeeUpdateForm addForm) {
        return roleEmployeeService.batchAddRoleEmployee(addForm);
    }

    @ApiOperation(value = "선택한 모든 역할 및 직원에 대한 모든 역할 가져오기 @author ysw")
    @GetMapping("/role/employee/getRoles/{employeeId}")
    public ResponseDTO<List<RoleSelectedVO>> getRoleByEmployeeId(@PathVariable Long employeeId) {
        return ResponseDTO.ok(roleEmployeeService.getRoleInfoListByEmployeeId(employeeId));
    }
}
