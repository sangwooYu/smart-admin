/*
 * 通用常量
 *
 * @Author:    1024创新实验室-主任：卓大
 * @Date:      2022-09-06 19:57:29
 * @Wechat:    zhuda1024
 * @Email:     lab1024@163.com
 * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
 */
import { SmartEnum } from '/@/types/smart-enum';

export const PAGE_SIZE = 10;

export const PAGE_SIZE_OPTIONS = ['10', '15', '20', '30', '40', '50', '75', '100', '150', '200', '300', '500'];

//登录页面名字
export const PAGE_PATH_LOGIN = '/login';

//404页面名字
export const PAGE_PATH_404 = '/404';

export const showTableTotal = function (total: number | string) {
  return `共${total}条`;
};

export const FLAG_NUMBER_ENUM: SmartEnum<number> = {
  TRUE: {
    value: 1,
    desc: '是',
  },
  FALSE: {
    value: 0,
    desc: '否',
  },
};

export const GENDER_ENUM: SmartEnum<number> = {
  UNKNOWN: {
    value: 0,
    desc: '알 수 없음',
  },
  MAN: {
    value: 1,
    desc: '남성',
  },
  WOMAN: {
    value: 2,
    desc: '여성',
  },
};

export const USER_TYPE_ENUM: SmartEnum<number> = {
  ADMIN_EMPLOYEE: {
    value: 1,
    desc: '직원',
  },
};
