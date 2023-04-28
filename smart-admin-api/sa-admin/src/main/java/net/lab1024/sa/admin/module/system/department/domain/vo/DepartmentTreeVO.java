package net.lab1024.sa.admin.module.system.department.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 部门
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-01-12 20:37:48
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Data
public class DepartmentTreeVO extends DepartmentVO {

    @ApiModelProperty("동일한 레벨의 이전 요소 ID")
    private Long preId;

    @ApiModelProperty("같은 레벨의 다음 요소 ID")
    private Long nextId;

    @ApiModelProperty("하위 부문")
    private List<DepartmentTreeVO> children;

    @ApiModelProperty("자신과 모든 재귀 하위 섹터에 대한 ID 모음")
    private List<Long> selfAndAllChildrenIdList;

}
