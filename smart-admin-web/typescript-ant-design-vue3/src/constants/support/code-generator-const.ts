/*
 * 代码生成 枚举
 *
 * @Author:    1024创新实验室-主任：卓大
 * @Date:      2022-09-03 22:09:10
 * @Wechat:    zhuda1024
 * @Email:     lab1024@163.com
 * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
 */

import { SmartEnum } from '/@/types/smart-enum';

// 프론트엔드 컴포넌트 유형
export const CODE_FRONT_COMPONENT_ENUM: SmartEnum<string> = {
  INPUT: {
    value: 'Input',
    desc: '입력 상자',
  },
  INPUT_NUMBER: {
    value: 'InputNumber',
    desc: '디지털 입력 상자',
  },
  TEXTAREA: {
    value: 'Textarea',
    desc: '텍스트',
  },
  BOOLEAN_SELECT: {
    value: 'BooleanSelect',
    desc: '부울 드롭다운 상자',
  },
  ENUM_SELECT: {
    value: 'SmartEnumSelect',
    desc: '열거형 드롭다운 상자',
  },
  DICT_SELECT: {
    value: 'DictSelect',
    desc: '사전 드롭다운',
  },
  DATE: {
    value: 'Date',
    desc: '날짜 선택',
  },
  DATE_TIME: {
    value: 'DateTime',
    desc: '타이밍',
  },
  FILE_UPLOAD: {
    value: 'FileUpload',
    desc: '파일 업로드',
  },
};

// 페이지 유형 추가 및 수정
export const CODE_INSERT_AND_UPDATE_PAGE_ENUM = {
  MODAL: {
    value: 'modal',
    desc: '팝업 창',
  },
  DRAWER: {
    value: 'drawer',
    desc: '서랍',
  },
  PAGE: {
    value: 'Page',
    desc: '새 페이지',
  },
};

// 删除类型
export const CODE_DELETE_ENUM = {
  SINGLE: {
    value: 'Single',
    desc: '개별적으로 삭제',
  },
  BATCH: {
    value: 'Batch',
    desc: '일괄 삭제',
  },
  SINGLE_AND_BATCH: {
    value: 'SingleAndBatch',
    desc: '개별 및 일괄 삭제',
  },
};

/**
 * 문의 유형
 */
export const CODE_QUERY_FIELD_QUERY_TYPE_ENUM = {
  LIKE: {
    value: 'Like',
    desc: 'Like',
  },
  EQUAL: {
    value: 'Equal',
    desc: 'Equal',
  },
  DATE_RANGE: {
    value: 'DateRange',
    desc: '날짜 범위',
  },
  DATE: {
    value: 'Date',
    desc: '날짜 지정',
  },
  ENUM: {
    value: 'Enum',
    desc: '열거형',
  },
  DICT: {
    value: 'Dict',
    desc: '사전',
  },
};

export default {
  CODE_FRONT_COMPONENT_ENUM,
  CODE_INSERT_AND_UPDATE_PAGE_ENUM,
  CODE_DELETE_ENUM,
  CODE_QUERY_FIELD_QUERY_TYPE_ENUM,
};
