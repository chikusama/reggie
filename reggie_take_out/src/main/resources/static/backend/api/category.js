/**
 * 菜品分类：分页查询分类接口
 * @param params
 * @returns {*}
 */
const getCategoryPage = (params) => {
    return $axios({
        url: '/category/page',
        method: 'get',
        params
    })
}

/**
 * 菜品分类：菜品分类详情接口
 * @param id
 * @returns {*}
 */
const queryCategoryById = (id) => {
    return $axios({
        url: `/category/${id}`,
        method: 'get'
    })
}

/**
 * 菜品分类：删除分类接口
 * @param id
 * @returns {*}
 */
const deleCategory = (id) => {
    return $axios({
        url: '/category',
        method: 'delete',
        params: {id}
    })
}

/**
 * 菜品分类：修改分类接口
 * @param params
 * @returns {*}
 */
const editCategory = (params) => {
    return $axios({
        url: '/category',
        method: 'put',
        data: {...params}
    })
}

/**
 * 菜品分类：新增分类接口
 * @param params
 * @returns {*}
 */
const addCategory = (params) => {
    return $axios({
        url: '/category',
        method: 'post',
        data: {...params}
    })
}