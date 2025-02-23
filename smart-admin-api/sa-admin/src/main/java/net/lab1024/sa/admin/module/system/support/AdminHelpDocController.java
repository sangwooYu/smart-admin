package net.lab1024.sa.admin.module.system.support;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.common.common.controller.SupportBaseController;
import net.lab1024.sa.common.common.domain.PageResult;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.constant.SwaggerTagConst;
import net.lab1024.sa.common.module.support.helpdoc.domain.form.*;
import net.lab1024.sa.common.module.support.helpdoc.domain.vo.HelpDocDetailVO;
import net.lab1024.sa.common.module.support.helpdoc.domain.vo.HelpDocVO;
import net.lab1024.sa.common.module.support.helpdoc.service.HelpDocCatalogService;
import net.lab1024.sa.common.module.support.helpdoc.service.HelpDocService;
import net.lab1024.sa.common.module.support.repeatsubmit.annoation.RepeatSubmit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 帮助文档
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-08-20 23:11:42
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Api(tags = SwaggerTagConst.Support.HELP_DOC)
@RestController
public class AdminHelpDocController extends SupportBaseController {

    @Autowired
    private HelpDocService helpDocService;

    @Autowired
    private HelpDocCatalogService helpDocCatalogService;

    // --------------------- 帮助文档 【目录管理】 -------------------------


    @ApiOperation("도움말 파일 디렉토리 - 추가 @author ysw")
    @PreAuthorize("@saAuth.checkPermission('helpDocCatalog:addCategory')")
    @PostMapping("/helpDoc/helpDocCatalog/add")
    public ResponseDTO<String> addHelpDocCatalog(@RequestBody @Valid HelpDocCatalogAddForm helpDocCatalogAddForm) {
        return helpDocCatalogService.add(helpDocCatalogAddForm);
    }

    @ApiOperation("도움말 파일 카탈로그 - 업데이트 @author ysw")
    @PreAuthorize("@saAuth.checkPermission('helpDocCatalog:edit')")
    @PostMapping("/helpDoc/helpDocCatalog/update")
    public ResponseDTO<String> updateHelpDocCatalog(@RequestBody @Valid HelpDocCatalogUpdateForm helpDocCatalogUpdateForm) {
        return helpDocCatalogService.update(helpDocCatalogUpdateForm);
    }

    @ApiOperation("도움말 파일 디렉터리 - 삭제 @author ysw")
    @GetMapping("/helpDoc/helpDocCatalog/delete/{helpDocCatalogId}")
    public ResponseDTO<String> deleteHelpDocCatalog(@PathVariable Long helpDocCatalogId) {
        return helpDocCatalogService.delete(helpDocCatalogId);
    }

    // --------------------- 帮助文档 【管理:增、删、查、改】-------------------------

    @ApiOperation("[관리] 도움말 파일 - 페이징 쿼리 @author ysw")
    @PreAuthorize("@saAuth.checkPermission('helpDoc:query')")
    @PostMapping("/helpDoc/query")
    public ResponseDTO<PageResult<HelpDocVO>> query(@RequestBody @Valid HelpDocQueryForm queryForm) {
        return ResponseDTO.ok(helpDocService.query(queryForm));
    }

    @ApiOperation("【관리] 도움말 파일-자세한 정보 보기 @author ysw")
    @GetMapping("/helpDoc/getDetail/{helpDocId}")
    public ResponseDTO<HelpDocDetailVO> getDetail(@PathVariable Long helpDocId) {
        return ResponseDTO.ok(helpDocService.getDetail(helpDocId));
    }

    @ApiOperation("[관리] 도움말 파일 - 추가 @author ysw")
    @PreAuthorize("@saAuth.checkPermission('helpDoc:add')")
    @PostMapping("/helpDoc/add")
    @RepeatSubmit
    public ResponseDTO<String> add(@RequestBody @Valid HelpDocAddForm addForm) {
        return helpDocService.add(addForm);
    }

    @ApiOperation("【관리] 도움말 파일-업데이트 @author ysw")
    @PreAuthorize("@saAuth.checkPermission('helpDoc:update')")
    @PostMapping("/helpDoc/update")
    @RepeatSubmit
    public ResponseDTO<String> update(@RequestBody @Valid HelpDocUpdateForm updateForm) {
        return helpDocService.update(updateForm);
    }

    @ApiOperation("[관리] 도움말 파일 - 삭제 @author ysw")
    @PreAuthorize("@saAuth.checkPermission('helpDoc:delete')")
    @GetMapping("/helpDoc/delete/{helpDocId}")
    public ResponseDTO<String> delete(@PathVariable Long helpDocId) {
        return helpDocService.delete(helpDocId);
    }

    @ApiOperation("[관리] 도움말 - 연결된 아이디로 검색 @author ysw")
    @GetMapping("/helpDoc/queryHelpDocByRelationId/{relationId}")
    public ResponseDTO<List<HelpDocVO>> queryHelpDocByRelationId(@PathVariable Long relationId) {
        return ResponseDTO.ok(helpDocService.queryHelpDocByRelationId(relationId));
    }

}