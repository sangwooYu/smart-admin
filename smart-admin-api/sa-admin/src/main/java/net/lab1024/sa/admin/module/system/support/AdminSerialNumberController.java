package net.lab1024.sa.admin.module.system.support;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.common.common.controller.SupportBaseController;
import net.lab1024.sa.common.common.domain.PageResult;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.util.SmartEnumUtil;
import net.lab1024.sa.common.constant.SwaggerTagConst;
import net.lab1024.sa.common.module.support.serialnumber.constant.SerialNumberIdEnum;
import net.lab1024.sa.common.module.support.serialnumber.dao.SerialNumberDao;
import net.lab1024.sa.common.module.support.serialnumber.domain.SerialNumberEntity;
import net.lab1024.sa.common.module.support.serialnumber.domain.SerialNumberGenerateForm;
import net.lab1024.sa.common.module.support.serialnumber.domain.SerialNumberRecordEntity;
import net.lab1024.sa.common.module.support.serialnumber.domain.SerialNumberRecordQueryForm;
import net.lab1024.sa.common.module.support.serialnumber.service.SerialNumberRecordService;
import net.lab1024.sa.common.module.support.serialnumber.service.SerialNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(tags = SwaggerTagConst.Support.SERIAL_NUMBER)
@RestController
public class AdminSerialNumberController extends SupportBaseController {

    @Autowired
    private SerialNumberDao serialNumberDao;

    @Autowired
    private SerialNumberService serialNumberService;

    @Autowired
    private SerialNumberRecordService serialNumberRecordService;

    @ApiOperation("주문 번호 생성 @author ysw")
    @PreAuthorize("@saAuth.checkPermission('support:serial:number:generate')")
    @PostMapping("/serialNumber/generate")
    public ResponseDTO<List<String>> generate(@RequestBody @Valid SerialNumberGenerateForm generateForm) {
        SerialNumberIdEnum serialNumberIdEnum = SmartEnumUtil.getEnumByValue(generateForm.getSerialNumberId(), SerialNumberIdEnum.class);
        if (null == serialNumberIdEnum) {
            return ResponseDTO.userErrorParam("SerialNumberId，존재하지 않음" + generateForm.getSerialNumberId());
        }
        return ResponseDTO.ok(serialNumberService.generate(serialNumberIdEnum, generateForm.getCount()));
    }

    @ApiOperation("모든 단일 숫자 정의 가져오기 @author ysw")
    @GetMapping("/serialNumber/all")
    public ResponseDTO<List<SerialNumberEntity>> getAll() {
        return ResponseDTO.ok(serialNumberDao.selectList(null));
    }

    @ApiOperation("생성된 레코드 가져오기 @author ysw")
    @PreAuthorize("@saAuth.checkPermission('support:serial:number:record')")
    @PostMapping("/serialNumber/queryRecord")
    public ResponseDTO<PageResult<SerialNumberRecordEntity>> queryRecord(@RequestBody @Valid SerialNumberRecordQueryForm queryForm) {
        return ResponseDTO.ok(serialNumberRecordService.query(queryForm));
    }

}
