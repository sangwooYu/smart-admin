/*
 * 菜单类型
 *
 * @Author:    1024创新实验室-主任：卓大
 * @Date:      2022-09-06 19:57:04
 * @Wechat:    zhuda1024
 * @Email:     lab1024@163.com
 * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
 */

import { SmartEnum } from '/@/types/smart-enum';

export const MENU_TYPE_ENUM: SmartEnum<number> = {
  CATALOG: {
    value: 1,
    desc: '카탈로그',
  },
  MENU: {
    value: 2,
    desc: '메뉴',
  },
  POINTS: {
    value: 3,
    desc: '버튼',
  },
};

/**
 * 권한 유형
 */
export const MENU_PERMS_TYPE_ENUM: SmartEnum<number> = {
  SPRING_SECURITY: {
    value: 1,
    desc: '보안 모드',
  },
  URL: {
    value: 2,
    desc: 'URL 모드',
  },
};

/**
 * 기본 상단 메뉴 ID는 0입니다.
 */
export const MENU_DEFAULT_PARENT_ID = 0;

export default {
  MENU_TYPE_ENUM,
  MENU_PERMS_TYPE_ENUM,
};
