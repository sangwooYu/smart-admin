/*
 *  菜单表格列
 *
 * @Author:    1024创新实验室-主任：卓大
 * @Date:      2022-05-12 19:46:11
 * @Wechat:    zhuda1024
 * @Email:     lab1024@163.com
 * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
 */
import { ref } from 'vue';
export const columns = ref([
  {
    title: '이름',
    dataIndex: 'menuName',
    key: 'ID',
    width: 200,
  },
  {
    title: '타입',
    dataIndex: 'menuType',
    width: 80,
  },
  {
    title: '아이콘',
    dataIndex: 'icon',
    width: 50,
  },
  {
    title: '경로',
    dataIndex: 'path',
    ellipsis: true,
  },
  {
    title: '컴포넌트',
    dataIndex: 'component',
    ellipsis: true,
  },
  {
    title: '권한 모드',
    dataIndex: 'permsType',
    width: 100,
  },
  {
    title: '백엔드 권한',
    dataIndex: 'apiPerms',
    ellipsis: true,
  },
  {
    title: '프런트엔드 권한',
    dataIndex: 'webPerms',
    ellipsis: true,
  },
  {
    title: '외부 링크',
    dataIndex: 'frameFlag',
    width: 45,
  },
  {
    title: '캐시',
    dataIndex: 'cacheFlag',
    width: 45,
  },
  {
    title: '표시',
    dataIndex: 'visibleFlag',
    width: 45,
  },
  {
    title: '사용 금지',
    dataIndex: 'disabledFlag',
    width: 45,
  },
  {
    title: '주문',
    dataIndex: 'sort',
    width: 80,
  },
  {
    title: '운영',
    dataIndex: 'operate',
    width: 100,
  },
]);
