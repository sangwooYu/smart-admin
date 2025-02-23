/*
 * 登录日志
 *
 * @Author:    1024创新实验室-主任：卓大
 * @Date:      2022-09-03 22:08:45
 * @Wechat:    zhuda1024
 * @Email:     lab1024@163.com
 * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
 */

import { SmartEnum } from '/@/types/smart-enum';

export const LOGIN_RESULT_ENUM: SmartEnum<number> = {
  LOGIN_SUCCESS: {
    value: 0,
    desc: '로그인 성공',
  },
  LOGIN_FAIL: {
    value: 1,
    desc: '로그인 실패',
  },
  LOGIN_OUT: {
    value: 2,
    desc: '로그아웃',
  },
};

export default {
  LOGIN_RESULT_ENUM,
};
