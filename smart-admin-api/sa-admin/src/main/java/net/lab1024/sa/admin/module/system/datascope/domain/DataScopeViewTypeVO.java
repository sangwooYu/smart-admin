package net.lab1024.sa.admin.module.system.datascope.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

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
@Builder
public class DataScopeViewTypeVO {

    @ApiModelProperty("가시 범위")
    private Integer viewType;

    @ApiModelProperty("표시 범위 이름")
    private String viewTypeName;

    @ApiModelProperty("레벨: 범위 크기를 나타내는 데 사용됩니다.")
    private Integer viewTypeLevel;
}
