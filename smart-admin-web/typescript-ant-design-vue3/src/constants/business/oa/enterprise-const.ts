/*
 * 企业
 *
 * @Author:    1024创新实验室-主任：卓大
 * @Date:      2022-09-03 22:07:27
 * @Wechat:    zhuda1024
 * @Email:     lab1024@163.com
 * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
 */

import { SmartEnum } from '/@/types/smart-enum';
export const ENTERPRISE_TYPE_ENUM: SmartEnum<number> = {
  NORMAL: {
    value: 1,
    desc: '제한 기업',
  },
  FOREIGN: {
    value: 2,
    desc: '외국 소유 기업',
  },
};

export default {
  ENTERPRISE_TYPE_ENUM,
};
