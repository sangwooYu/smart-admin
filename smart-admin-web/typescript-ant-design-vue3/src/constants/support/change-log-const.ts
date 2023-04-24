/**
 * 系统更新日志 枚举
 *
 * @Author:    卓大
 * @Date:      2022-09-26 14:53:50
 * @Copyright  1024创新实验室
 */

/**
 * 업데이트 유형:[1:메가 버전 기능 업데이트, 2:기능 업데이트, 3:버그 수정]
 */

import { SmartEnum } from '/@/types/smart-enum';

export const CHANGE_LOG_TYPE_ENUM: SmartEnum<number> = {
  MAJOR_UPDATE: {
    value: 1,
    desc: '주요 업데이트',
  },
  FUNCTION_UPDATE: {
    value: 2,
    desc: '기능 업데이트',
  },
  BUG_FIX: {
    value: 3,
    desc: '버그 수정',
  },
};

export default {
  CHANGE_LOG_TYPE_ENUM,
};
