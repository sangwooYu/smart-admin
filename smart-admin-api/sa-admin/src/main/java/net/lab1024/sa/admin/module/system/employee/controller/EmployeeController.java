package net.lab1024.sa.admin.module.system.employee.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.common.AdminBaseController;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.employee.domain.form.*;
import net.lab1024.sa.admin.module.system.employee.domain.vo.EmployeeVO;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.common.common.domain.PageResult;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.util.SmartRequestUtil;
import net.lab1024.sa.common.module.support.operatelog.annoation.OperateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 员工
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2021-12-09 22:57:49
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@RestController
@OperateLog
@Api(tags = {AdminSwaggerTagConst.System.SYSTEM_EMPLOYEE})
public class EmployeeController extends AdminBaseController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee/query")
    @ApiOperation(value = "직원 관리 문의 @author ysw")
    public ResponseDTO<PageResult<EmployeeVO>> query(@Valid @RequestBody EmployeeQueryForm query) {
        return employeeService.queryEmployee(query);
    }

    @ApiOperation(value = "직원 추가(직원 추가를 위한 비밀번호 반환) @author ysw")
    @PostMapping("/employee/add")
    @PreAuthorize("@saAuth.checkPermission('system:employee:add')")
    public ResponseDTO<String> addEmployee(@Valid @RequestBody EmployeeAddForm employeeAddForm) {
        return employeeService.addEmployee(employeeAddForm);
    }

    @ApiOperation(value = "직원 업데이트 @author ysw")
    @PostMapping("/employee/update")
    @PreAuthorize("@saAuth.checkPermission('system:employee:update')")
    public ResponseDTO<String> updateEmployee(@Valid @RequestBody EmployeeUpdateForm employeeUpdateForm) {
        return employeeService.updateEmployee(employeeUpdateForm);
    }

    @ApiOperation(value = "직원 비활성화/활성화 상태 업데이트 @author ysw")
    @GetMapping("/employee/update/disabled/{employeeId}")
    @PreAuthorize("@saAuth.checkPermission('system:employee:disabled')")
    public ResponseDTO<String> updateDisableFlag(@PathVariable Long employeeId) {
        return employeeService.updateDisableFlag(employeeId);
    }

    @ApiOperation(value = "직원 일괄 삭제 @author ysw")
    @PostMapping("/employee/update/batch/delete")
    @PreAuthorize("@saAuth.checkPermission('system:employee:delete')")
    public ResponseDTO<String> batchUpdateDeleteFlag(@RequestBody List<Long> employeeIdList) {
        return employeeService.batchUpdateDeleteFlag(employeeIdList);
    }

    @ApiOperation(value = "직원 부서 일괄 조정 @author ysw")
    @PostMapping("/employee/update/batch/department")
    @PreAuthorize("@saAuth.checkPermission('system:employee:department:update')")
    public ResponseDTO<String> batchUpdateDepartment(@Valid @RequestBody EmployeeBatchUpdateDepartmentForm batchUpdateDepartmentForm) {
        return employeeService.batchUpdateDepartment(batchUpdateDepartmentForm);
    }

    @ApiOperation(value = "비밀번호 변경 @author ysw")
    @PostMapping("/employee/update/password")
    public ResponseDTO<String> updatePassword(@Valid @RequestBody EmployeeUpdatePasswordForm updatePasswordForm) {
        updatePasswordForm.setEmployeeId(SmartRequestUtil.getRequestUserId());
        return employeeService.updatePassword(updatePasswordForm);
    }

    @ApiOperation(value = "직원 비밀번호 재설정 @author ysw")
    @GetMapping("/employee/update/password/reset/{employeeId}")
    @PreAuthorize("@saAuth.checkPermission('system:employee:password:reset')")
    public ResponseDTO<String> resetPassword(@PathVariable Integer employeeId) {
        return employeeService.resetPassword(employeeId);
    }

    @ApiOperation(value = "직원 검색 - 부서 ID로 검색 @author ysw")
    @GetMapping("/employee/getAllEmployeeByDepartmentId/{departmentId}")
    public ResponseDTO<List<EmployeeVO>> getAllEmployeeByDepartmentId(@PathVariable Long departmentId) {
        return employeeService.getAllEmployeeByDepartmentId(departmentId, Boolean.FALSE);
    }

    @ApiOperation("모든 직원 검색 @author ysw")
    @GetMapping("/employee/queryAll")
    public ResponseDTO<List<EmployeeVO>> queryAllEmployee(@RequestParam(value = "disabledFlag", required = false) Boolean disabledFlag) {
        return employeeService.queryAllEmployee(disabledFlag);
    }

}
