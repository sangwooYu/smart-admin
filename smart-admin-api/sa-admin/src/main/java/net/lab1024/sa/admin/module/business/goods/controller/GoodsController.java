package net.lab1024.sa.admin.module.business.goods.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.common.AdminBaseController;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.business.goods.domain.form.GoodsAddForm;
import net.lab1024.sa.admin.module.business.goods.domain.form.GoodsQueryForm;
import net.lab1024.sa.admin.module.business.goods.domain.form.GoodsUpdateForm;
import net.lab1024.sa.admin.module.business.goods.domain.vo.GoodsVO;
import net.lab1024.sa.admin.module.business.goods.service.GoodsService;
import net.lab1024.sa.common.common.domain.PageResult;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.domain.ValidateList;
import net.lab1024.sa.common.module.support.operatelog.annoation.OperateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@OperateLog
@RestController
@Api(tags = AdminSwaggerTagConst.Business.MANAGER_GOODS)
public class GoodsController extends AdminBaseController {

    @Autowired
    private GoodsService goodsService;

    @ApiOperation("페이징 쿼리 @author 후커")
    @PostMapping("/goods/query")
    @PreAuthorize("@saAuth.checkPermission('goods:query ')")
    public ResponseDTO<PageResult<GoodsVO>> query(@RequestBody @Valid GoodsQueryForm queryForm) {
        return goodsService.query(queryForm);
    }

    @ApiOperation("제품 추가 @author 후커")
    @PostMapping("/goods/add")
    @PreAuthorize("@saAuth.checkPermission('goods:add')")
    public ResponseDTO<String> add(@RequestBody @Valid GoodsAddForm addForm) {
        return goodsService.add(addForm);
    }

    @ApiOperation("제품 업데이트 @author 후커")
    @PostMapping("/goods/update")
    @PreAuthorize("@saAuth.checkPermission('goods:update')")
    public ResponseDTO<String> update(@RequestBody @Valid GoodsUpdateForm updateForm) {
        return goodsService.update(updateForm);
    }

    @ApiOperation("삭제 @author 초대")
    @GetMapping("/goods/delete/{goodsId}")
    @PreAuthorize("@saAuth.checkPermission('goods:delete')")
    public ResponseDTO<String> delete(@PathVariable Long goodsId) {
        return goodsService.delete(goodsId);
    }

    @ApiOperation("대량 @author 초대")
    @PostMapping("/goods/batchDelete")
    @PreAuthorize("@saAuth.checkPermission('goods:batchDelete')")
    public ResponseDTO<String> batchDelete(@RequestBody @Valid ValidateList<Long> idList) {
        return goodsService.batchDelete(idList);
    }


}
