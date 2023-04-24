/*
 * 数据变动记录
 *
 * @Author:    1024创新实验室-主任：卓大
 * @Date:      2022-09-03 22:09:10
 * @Wechat:    zhuda1024
 * @Email:     lab1024@163.com
 * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
 */

import { SmartEnum } from '/@/types/smart-enum';

// 业务类型
export const DATA_TRACER_TYPE_ENUM: SmartEnum<number> = {
  GOODS: {
    value: 1,
    desc: '상품',
  },
  OA_NOTICE: {
    value: 2,
    desc: 'OA 공지사항',
  },
  OA_ENTERPRISE: {
    value: 3,
    desc: 'OA - 기업 정보',
  },
};

export default {
  DATA_TRACER_TYPE_ENUM,
};
