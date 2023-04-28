package net.lab1024.sa.admin.constant;

import net.lab1024.sa.common.constant.CacheKeyConst;

/**
 * 缓存 key
 *
 * @Author 1024创新实验室-主任:卓大
 * @Date 2022-01-07 18:59:22
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
public class AdminCacheConst extends CacheKeyConst {

    public static class Department {

        /**
         * 부서 목록
         */
        public static final String DEPARTMENT_LIST_CACHE = "department_list_cache";

        /**
         * 섹터 맵
         */
        public static final String DEPARTMENT_MAP_CACHE = "department_map_cache";

        /**
         * 섹터 트리
         */
        public static final String DEPARTMENT_TREE_CACHE = "department_tree_cache";

        /**
         * 부서 및 하위 부서의 ID 목록
         */
        public static final String DEPARTMENT_SELF_CHILDREN_CACHE = "department_self_children_cache";

        /**
         * 부서 경로 캐시
         */
        public static final String DEPARTMENT_PATH_CACHE = "department_path_cache";

    }

    /**
     * 分类相关缓存
     */
    public static class CATEGORY {

        public static final String CATEGORY_ENTITY = "category_cache";

        public static final String CATEGORY_SUB = "category_sub_cache";

        public static final String CATEGORY_TREE = "category_tree_cache";
    }

}
