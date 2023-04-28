package net.lab1024.sa.admin.module.business.category.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.common.AdminBaseController;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.business.category.domain.form.CategoryAddForm;
import net.lab1024.sa.admin.module.business.category.domain.form.CategoryTreeQueryForm;
import net.lab1024.sa.admin.module.business.category.domain.form.CategoryUpdateForm;
import net.lab1024.sa.admin.module.business.category.domain.vo.CategoryTreeVO;
import net.lab1024.sa.admin.module.business.category.domain.vo.CategoryVO;
import net.lab1024.sa.admin.module.business.category.service.CategoryService;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.module.support.operatelog.annoation.OperateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 类目
 *
 * @Author 1024创新实验室: 胡克
 * @Date 2021/08/05 21:26:58
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@OperateLog
@RestController
@Api(tags = AdminSwaggerTagConst.Business.MANAGER_CATEGORY)
public class CategoryController extends AdminBaseController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation("카테고리 추가 @author 후커")
    @PostMapping("/category/add")
    public ResponseDTO<String> add(@RequestBody @Valid CategoryAddForm addForm) {
        return categoryService.add(addForm);
    }

    @ApiOperation("카테고리 업데이트 @author 후커")
    @PostMapping("/category/update")
    public ResponseDTO<String> update(@RequestBody @Valid CategoryUpdateForm updateForm) {
        return categoryService.update(updateForm);
    }

    @ApiOperation("카테고리 세부 정보 찾기 @author 후커")
    @GetMapping("/category/{categoryId}")
    public ResponseDTO<CategoryVO> queryDetail(@PathVariable Long categoryId) {
        return categoryService.queryDetail(categoryId);
    }

    @ApiOperation("쿼리 카테고리 계층 구조 트리 @author 후커")
    @PostMapping("/category/tree")
    public ResponseDTO<List<CategoryTreeVO>> queryTree(@RequestBody @Valid CategoryTreeQueryForm queryForm) {
        return categoryService.queryTree(queryForm);
    }

    @ApiOperation("카테고리 삭제 @author 후커")
    @GetMapping("/category/delete/{categoryId}")
    public ResponseDTO<String> delete(@PathVariable Long categoryId) {
        return categoryService.delete(categoryId);
    }
}
