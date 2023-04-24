/*
 * 国际化入口文件
 *
 * @Author:    1024创新实验室-主任：卓大
 * @Date:      2022-09-06 20:01:19
 * @Wechat:    zhuda1024
 * @Email:     lab1024@163.com
 * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
 */

import en_US from './lang/en-US/index';
import zh_CN from './lang/zh-CN/index';
import { createI18n } from 'vue-i18n';
import { getInitializedLanguage } from '/@/store/modules/system/app-config';

// 语言选择数组
export const i18nList = [
  {
    text: '简体中文',
    value: 'zh_CN',
  },
  {
    text: 'English',
    value: 'en_US',
  },
];

export const messages = {
  zh_CN: zh_CN,
  en_US: en_US,
};

const i18n = createI18n({
  fallbackLocale: 'en_US', //사전 설정된 언어 환경
  globalInjection: true,
  legacy: false, //
  locale: getInitializedLanguage(), //기본 초기화 언어
  messages,
});

export default i18n;
