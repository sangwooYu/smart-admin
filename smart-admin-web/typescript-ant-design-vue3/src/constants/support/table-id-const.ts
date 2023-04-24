/*
 * @Description: 表格id
 * @Author: zhuoda
 * @Date: 2022-08-21
 * @LastEditTime: 2022-08-21
 * @LastEditors: zhuoda
 */

//시스템 기능 양식 초기화 ID
let systemInitTableId = 10000;

//지원 기능 양식 초기화 ID
let supportInitTableId = 20000;

//비즈니스 양식 초기화 ID
let businessOAInitTableId = 30000;

let businessERPInitTableId = 40000;

export const TABLE_ID_CONST = {
  /**
   * 业务
   */
  BUSINESS: {
    OA: {
      NOTICE: businessOAInitTableId + 1, //공지 사항
      ENTERPRISE: businessOAInitTableId + 2, //기업 정보
      ENTERPRISE_EMPLOYEE: businessOAInitTableId + 3, //기업 직원
      ENTERPRISE_BANK: businessOAInitTableId + 4, // 기업 뱅킹
      ENTERPRISE_INVOICE: businessOAInitTableId + 5, // 기업 송장
    },
    ERP: {
      GOODS: businessERPInitTableId + 1, //상품 관리
    },
  },

  /**
   * 시스템
   */
  SYSTEM: {
    EMPLOYEE: systemInitTableId + 1, //직원
    MENU: systemInitTableId + 2, //메뉴
  },
  /**
   * 기반
   */
  SUPPORT: {
    CONFIG: supportInitTableId + 1, //매개변수 구성
    DICT: supportInitTableId + 2, //사전
    SERIAL_NUMBER: supportInitTableId + 3, //단일 번호
    OPERATE_LOG: supportInitTableId + 4, //모니터링 요청
    HEART_BEAT: supportInitTableId + 5, //하트비트
    LOGIN_LOG: supportInitTableId + 6, //로그인 로그
    RELOAD: supportInitTableId + 7, //reload
    HELP_DOC: supportInitTableId + 8, //도움말 파일
  },
};
