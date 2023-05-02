package net.lab1024.sa.admin.module.business.oa.enterprise.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lab1024.sa.admin.module.business.oa.enterprise.constant.EnterpriseTypeEnum;
import net.lab1024.sa.common.module.support.datatracer.annoation.DataTracerFieldEnum;
import net.lab1024.sa.common.module.support.datatracer.annoation.DataTracerFieldLabel;

import java.time.LocalDateTime;

@Data
@TableName("t_oa_enterprise")
public class EnterpriseEntity {

    /**
     * 企业ID
     */
    @TableId(type = IdType.AUTO)
    private Long enterpriseId;

    /**
     * 企业名称
     */
    @DataTracerFieldLabel("회사 이름")
    private String enterpriseName;

    /**
     * 企业logo
     */
    @DataTracerFieldLabel("기업 로고")
    private String enterpriseLogo;

    /**
     * 统一社会信用代码
     */
    @DataTracerFieldLabel("통합 소셜 신용 코드")
    private String unifiedSocialCreditCode;

    /**
     * 类型
     *
     * @see EnterpriseTypeEnum
     */
    @DataTracerFieldLabel("유형")
    @DataTracerFieldEnum(enumClass = EnterpriseTypeEnum.class)
    private Integer type;

    /**
     * 联系人
     */
    @DataTracerFieldLabel("담당자")
    private String contact;

    /**
     * 联系人电话
     */
    @DataTracerFieldLabel("연락처 전화")
    private String contactPhone;

    /**
     * 邮箱
     */
    @DataTracerFieldLabel("이메일")
    private String email;

    /**
     * 省份
     */
    private Integer province;

    /**
     * 省份名称
     */
    @DataTracerFieldLabel("주 이름")
    private String provinceName;

    /**
     * 城市
     */
    private Integer city;

    /**
     * 城市名称
     */
    @DataTracerFieldLabel("도시 이름")
    private String cityName;

    /**
     * 区县
     */
    private Integer district;

    /**
     * 区县名称
     */
    @DataTracerFieldLabel("지구 및 카운티 이름")
    private String districtName;

    /**
     * 详细地址
     */
    @DataTracerFieldLabel("주소 세부 정보")
    private String address;

    /**
     * 营业执照
     */
    @DataTracerFieldLabel("비즈니스 라이선스")
    private String businessLicense;

    /**
     * 禁用状态
     */
    @DataTracerFieldLabel("비활성화 상태")
    private Boolean disabledFlag;

    /**
     * 删除状态
     */
    @DataTracerFieldLabel("상태 삭제")
    private Boolean deletedFlag;

    /**
     * 创建人ID
     */
    private Long createUserId;

    /**
     * 创建人ID
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
