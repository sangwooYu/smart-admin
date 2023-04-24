/*
 * @Description: 分页查询你参数
 * @Author: zhuoda
 * @Date: 2021-08-12
 * @LastEditTime: 2021-08-14
 * @LastEditors: zhuoda
 */
import { SortItemModel } from './sort-item-model';

/**
 *
 * @export
 * @interface PageResultDto
 */
export interface PageParamModel {
  /**
   * 페이지 번호(비워둘 수 없음)
   * @type {number}
   * @memberof ClueUserQueryForm
   */
  pageNum: number;
  /**
   * 페이지당 개수(비워둘 수 없음)
   * @type {number}
   * @memberof ClueUserQueryForm
   */
  pageSize: number;

  /**
   * 정렬된 필드 집합
   * @type {Array<SortItemDto>}
   * @memberof ClueUserQueryForm
   */
  sortItemList?: Array<SortItemModel>;
}
