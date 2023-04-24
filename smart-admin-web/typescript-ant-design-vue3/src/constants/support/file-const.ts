/*
 * 文件类型
 *
 * @Author:    1024创新实验室-主任：卓大
 * @Date:      2022-09-03 22:09:10
 * @Wechat:    zhuda1024
 * @Email:     lab1024@163.com
 * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
 */

import { SmartEnum } from '/@/types/smart-enum';
// 파일 업로드 유형
export const FILE_FOLDER_TYPE_ENUM: SmartEnum<number> = {
  COMMON: {
    value: 1,
    desc: '일반',
  },
  NOTICE: {
    value: 2,
    desc: '공지 사항',
  },
  HELP_DOC: {
    value: 3,
    desc: '도움말 센터',
  },
  FEEDBACK: {
    value: 4,
    desc: '피드백',
  },
};
export default {
  FILE_FOLDER_TYPE_ENUM,
};
