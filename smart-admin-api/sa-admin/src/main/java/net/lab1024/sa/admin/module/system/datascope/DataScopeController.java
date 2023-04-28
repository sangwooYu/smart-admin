package net.lab1024.sa.admin.module.system.datascope;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.common.AdminBaseController;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.datascope.domain.DataScopeAndViewTypeVO;
import net.lab1024.sa.admin.module.system.datascope.service.DataScopeService;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.module.support.operatelog.annoation.OperateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@OperateLog
@RestController
@Api(tags = {AdminSwaggerTagConst.System.SYSTEM_DATA_SCOPE})
public class DataScopeController extends AdminBaseController {

    @Autowired
    private DataScopeService dataScopeService;

    @ApiOperation(value = "현재 시스템에서 구성한 모든 데이터 범위 가져오기 @author ysw")
    @GetMapping("/dataScope/list")
    public ResponseDTO<List<DataScopeAndViewTypeVO>> dataScopeList() {
        return dataScopeService.dataScopeList();
    }


}
