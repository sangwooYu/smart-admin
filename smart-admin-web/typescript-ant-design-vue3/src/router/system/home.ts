/*
 * 首页路由
 *
 * @Author:    1024创新实验室-主任：卓大
 * @Date:      2022-09-06 20:51:41
 * @Wechat:    zhuda1024
 * @Email:     lab1024@163.com
 * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
 */
import { HOME_PAGE_NAME } from '/@/constants/system/home-const';
import { MENU_TYPE_ENUM } from '/@/constants/system/menu-const';
import SmartLayout from '/@/layout/smart-layout.vue';
import { RouteRecordRaw } from 'vue-router';

export const homeRouters:Array<RouteRecordRaw> = [
  {
    path: '/',
    name: '_home',
    redirect: { name: HOME_PAGE_NAME },
    component: SmartLayout,
    meta: {
      title: '홈',
      menuType: MENU_TYPE_ENUM.CATALOG.value,
      icon: 'HomeOutlined',
    },
    children: [
      {
        path: '/home',
        name: HOME_PAGE_NAME,
        meta: {
          title: '홈',
          menuType: MENU_TYPE_ENUM.MENU.value,
          icon: 'HomeOutlined',
          parentMenuList: [{ name: '_home', title: '홈' }],
        },
        component: () => import('/@/views/system/home/index.vue'),
      },
    ],
  },
];
