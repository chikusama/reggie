/**
 * 菜品：分页查询菜品列表接口
 * @param params
 * @returns {*}
 */
const getDishPage = (params) => {
    return $axios({
        url: '/dish/page',
        method: 'get',
        params
    })
}

/**
 * 菜品：删除菜品接口
 * @param ids
 * @returns {*}
 */
const deleteDish = (ids) => {
    return $axios({
        url: '/dish',
        method: 'delete',
        params: {ids}
    })
}

/**
 * 菜品：修改菜品接口
 * @param params
 * @returns {*}
 */
const editDish = (params) => {
    return $axios({
        url: '/dish',
        method: 'put',
        data: {...params}
    })
}

/**
 * 菜品：新增菜品接口
 * @param params
 * @returns {*}
 */
const addDish = (params) => {
    return $axios({
        url: '/dish',
        method: 'post',
        data: {...params}
    })
}

/**
 * 菜品：查询菜品详情
 * @param id
 * @returns {*}
 */
const queryDishById = (id) => {
    return $axios({
        url: `/dish/${id}`,
        method: 'get'
    })
}

/**
 * 菜品：获取菜品分类列表
 * @param params
 * @returns {*}
 */
const getCategoryList = (params) => {
    return $axios({
        url: '/category/list',
        method: 'get',
        params
    })
}

/**
 * 菜品：查询菜品列表的接口
 * @param params
 * @returns {*}
 */
const queryDishList = (params) => {
    return $axios({
        url: '/dish/list',
        method: 'get',
        params
    })
}

/**
 * 菜品：新增修改菜品时，图片文件下载预览接口
 * @param params
 * @returns {*}
 */
const commonDownload = (params) => {
    return $axios({
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        url: '/common/download',
        method: 'get',
        params
    })
}

/**
 * 菜品：起售停售---批量起售停售接口
 * @param params
 * @returns {*}
 */
const dishStatusByStatus = (params) => {
    return $axios({
        url: `/dish/status/${params.status}`,
        method: 'post',
        params: {ids: params.id}
    })
}