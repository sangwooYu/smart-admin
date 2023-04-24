/*
 *  key  常量
 *
 * @Author:    1024创新实验室-主任：卓大
 * @Date:      2022-09-06 19:58:50
 * @Wechat:    zhuda1024
 * @Email:     lab1024@163.com
 * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
 */

/**
 * key前缀
 */
const KEY_PREFIX = 'smart_admin_';
/**
 * localStorageKey集合
 */
export default {
  // 用户信息
  USER_INFO: `${KEY_PREFIX}user_info`,
  // 用户权限点
  USER_POINTS: `${KEY_PREFIX}user_points`,
  // 用户的tag列表
  USER_TAG_NAV: `${KEY_PREFIX}user_tag_nav`,
  // app config 配置信息
  APP_CONFIG: `${KEY_PREFIX}app_config`,
  // 홈페이지에 빠르게 액세스
  HOME_QUICK_ENTRY: `${KEY_PREFIX}home_quick_entry`,
  // 알림 메시지를 읽었습니다.
  NOTICE_READ: `${KEY_PREFIX}notice_read`,
};
