import { getRequest, postRequest } from '/src/lib/axios';

export const categoryApi = {
    // 添加类目 @author 卓大
    addCategory: (param) => {
        return postRequest('/category/add', param);
    },
    // GET
    // 删除类目 @author 卓大
    deleteCategoryById: (categoryId) => {
        return getRequest(`/category/delete/${categoryId}`);
    },
    // 查询类目层级树 @author 卓大
    queryCategoryTree: (param) => {
        return postRequest('/category/tree', param);
    },
    // 更新类目 @author 卓大
    updateCategory: (param) => {
        return postRequest('/category/update', param);
    },
    // 查询类目详情 @author 卓大
    getCategory: (categoryId) => {
        return getRequest(`/category/${categoryId}`);
    },
};
