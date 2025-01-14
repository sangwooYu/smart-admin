/*
 * 登录页面
 *
 * @Author:    1024创新实验室-主任：卓大
 * @Date:      2022-09-06 20:51:50
 * @Wechat:    zhuda1024
 * @Email:     lab1024@163.com
 * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
 */

import { RouteRecordRaw } from 'vue-router';
export const loginRouters: Array<RouteRecordRaw> = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('/@/views/system/login/login.vue'),
    meta: {
      title: '로그인',
      hideInMenu: true,
    },
  },
];
