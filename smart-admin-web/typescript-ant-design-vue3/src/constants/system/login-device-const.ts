/*
 * 登录设备
 *
 * @Author:    1024创新实验室-主任：卓大
 * @Date:      2022-09-06 19:56:56
 * @Wechat:    zhuda1024
 * @Email:     lab1024@163.com
 * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
 */

import { SmartEnum } from '/@/types/smart-enum';

export const LOGIN_DEVICE_ENUM: SmartEnum<number> = {
  PC: {
    value: 1,
    desc: '컴퓨터 측',
  },
  ANDROID: {
    value: 2,
    desc: 'Android',
  },
  APPLE: {
    value: 3,
    desc: 'Apple',
  },
  H5: {
    value: 3,
    desc: 'H5',
  },
};

export default {
  LOGIN_DEVICE_ENUM,
};
