package net.lab1024.sa.admin.module.business.goods.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lab1024.sa.common.common.enumeration.BaseEnum;

@AllArgsConstructor
@Getter
public enum GoodsStatusEnum implements BaseEnum {

    /**
     * 1 预约中
     */
    APPOINTMENT(1, "예약 시"),

    /**
     * 2 售卖
     */
    SELL(2, "판매 중"),

    /**
     * 3 售罄
     */
    SELL_OUT(3, "매진"),


    ;

    private final Integer value;

    private final String desc;
}
