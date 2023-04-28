package net.lab1024.sa.admin.module.business.oa.enterprise;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.business.oa.enterprise.domain.form.*;
import net.lab1024.sa.admin.module.business.oa.enterprise.domain.vo.EnterpriseEmployeeVO;
import net.lab1024.sa.admin.module.business.oa.enterprise.domain.vo.EnterpriseListVO;
import net.lab1024.sa.admin.module.business.oa.enterprise.domain.vo.EnterpriseVO;
import net.lab1024.sa.common.common.annoation.SaAuth;
import net.lab1024.sa.common.common.domain.PageResult;
import net.lab1024.sa.common.common.domain.RequestUser;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.util.SmartRequestUtil;
import net.lab1024.sa.common.module.support.operatelog.annoation.OperateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 企业
 *
 * @Author 1024创新实验室: 开云
 * @Date 2022/7/28 20:37:15
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@Slf4j
@RestController
@OperateLog
@Api(tags = {AdminSwaggerTagConst.Business.OA_ENTERPRISE})
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    @ApiOperation(value = "페이징 검색 엔터프라이즈 모듈 @author ysw")
    @PostMapping("/oa/enterprise/page/query")
    @SaAuth
    public ResponseDTO<PageResult<EnterpriseVO>> queryByPage(@RequestBody @Valid EnterpriseQueryForm queryDTO) {
        return enterpriseService.queryByPage(queryDTO);
    }

    @ApiOperation(value = "회사 세부 정보 찾기 @author ysw")
    @GetMapping("/oa/enterprise/get/{enterpriseId}")
    @SaAuth
    public ResponseDTO<EnterpriseVO> getDetail(@PathVariable Long enterpriseId) {
        return ResponseDTO.ok(enterpriseService.getDetail(enterpriseId));
    }

    @ApiOperation(value = "신규 비즈니스 @author ysw")
    @PostMapping("/oa/enterprise/create")
    @SaAuth
    public ResponseDTO<String> createEnterprise(@RequestBody @Valid EnterpriseCreateForm createVO) {
        RequestUser requestUser = SmartRequestUtil.getRequestUser();
        createVO.setCreateUserId(requestUser.getUserId());
        createVO.setCreateUserName(requestUser.getUserName());
        return enterpriseService.createEnterprise(createVO);
    }

    @ApiOperation(value = "편집 회사 @author ysw")
    @PostMapping("/oa/enterprise/update")
    @SaAuth
    public ResponseDTO<String> updateEnterprise(@RequestBody @Valid EnterpriseUpdateForm updateVO) {
        return enterpriseService.updateEnterprise(updateVO);
    }

    @ApiOperation(value = "비즈니스 삭제 @author ysw")
    @GetMapping("/oa/enterprise/delete/{enterpriseId}")
    @SaAuth
    public ResponseDTO<String> deleteEnterprise(@PathVariable Long enterpriseId) {
        return enterpriseService.deleteEnterprise(enterpriseId);
    }

    @ApiOperation(value = "비즈니스 목록 검색 @author ysw")
    @GetMapping("/oa/enterprise/query/list")
    @SaAuth
    public ResponseDTO<List<EnterpriseListVO>> queryList(@RequestParam(value = "type", required = false) Integer type) {
        return enterpriseService.queryList(type);
    }


    @ApiOperation(value = "비즈니스에 직원 추가하기 @author ysw")
    @PostMapping("/oa/enterprise/employee/add")
    @SaAuth
    public ResponseDTO<String> addEmployee(@RequestBody @Valid EnterpriseEmployeeForm enterpriseEmployeeForm) {
        return enterpriseService.addEmployee(enterpriseEmployeeForm);
    }

    @ApiOperation(value = "회사 내 모든 직원 검색 @author ysw")
    @PostMapping("/oa/enterprise/employee/list")
    @SaAuth
    public ResponseDTO<List<EnterpriseEmployeeVO>> employeeList(@RequestBody @Valid List<Long> enterpriseIdList) {
        return ResponseDTO.ok(enterpriseService.employeeList(enterpriseIdList));
    }

    @ApiOperation(value = "페이지 매김 검색 기업 직원 @author ysw")
    @PostMapping("/oa/enterprise/employee/queryPage")
    @SaAuth
    public ResponseDTO<PageResult<EnterpriseEmployeeVO>> queryPageEmployeeList(@RequestBody @Valid EnterpriseEmployeeQueryForm queryForm) {
        return ResponseDTO.ok(enterpriseService.queryPageEmployeeList(queryForm));
    }


    @ApiOperation(value = "기업 직원 삭제 @author ysw")
    @PostMapping("/oa/enterprise/employee/delete")
    @SaAuth
    public ResponseDTO<String> deleteEmployee(@RequestBody @Valid EnterpriseEmployeeForm enterpriseEmployeeForm) {
        return enterpriseService.deleteEmployee(enterpriseEmployeeForm);
    }
}
