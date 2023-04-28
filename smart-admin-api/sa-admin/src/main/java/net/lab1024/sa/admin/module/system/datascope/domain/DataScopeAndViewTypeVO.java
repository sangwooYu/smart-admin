package net.lab1024.sa.admin.module.system.datascope.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 数据范围
 *
 * @Author 1024创新实验室: 罗伊
 * @Date 2020/11/28  20:59:17
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Data
public class DataScopeAndViewTypeVO {

    @ApiModelProperty("데이터 범위 유형")
    private Integer dataScopeType;

    @ApiModelProperty("데이터 범위 이름")
    private String dataScopeTypeName;

    @ApiModelProperty("설명")
    private String dataScopeTypeDesc;

    @ApiModelProperty("주문")
    private Integer dataScopeTypeSort;

    @ApiModelProperty("표시 범위 목록")
    private List<DataScopeViewTypeVO> viewTypeList;

}
