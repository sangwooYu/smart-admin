/*
 * @Description:
 * @Author: zhuoda
 * @Date: 2021-08-11 18:12:07
 * @LastEditTime: 2021-08-12 15:11:54
 * @LastEditors: zhuoda
 */
/**
 *
 * @export
 * @interface PageResultModel
 */
export interface PageResultModel<T> {
  /**
   * 비어 있음
   * @type {boolean}
   * @memberof PageResultModel
   */
  emptyFlag?: boolean;
  /**
   * 결과 집합
   * @type {Array<T>}
   * @memberof PageResultModel
   */
  list: Array<T>;
  /**
   * 현재 페이지
   * @type {number}
   * @memberof PageResultModel
   */
  pageNum?: number;
  /**
   * 페이지당 페이지 수
   * @type {number}
   * @memberof PageResultModel
   */
  pageSize?: number;
  /**
   * 총 페이지 수
   * @type {number}
   * @memberof PageResultModel
   */
  pages?: number;
  /**
   * 총 레코드 수
   * @type {number}
   * @memberof PageResultModel
   */
  total: number;
}
