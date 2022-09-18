/**
 * 后台套餐管理：分页查询套餐接口
 * @param params
 * @returns {*}
 */
const getSetmealPage = (params) => {
    return $axios({
        url: '/setmeal/page',
        method: 'get',
        params
    })
}

/**
 * 后台套餐管理；删除套餐接口
 * @param ids
 * @returns {*}
 */
const deleteSetmeal = (ids) => {
    return $axios({
        url: '/setmeal',
        method: 'delete',
        params: {ids}
    })
}

/**
 * 后台套餐管理：修改套餐接口
 * @param params
 * @returns {*}
 */
const editSetmeal = (params) => {
    return $axios({
        url: '/setmeal',
        method: 'put',
        data: {...params}
    })
}

/**
 * 后台套餐管理：新增套餐接口
 * @param params
 * @returns {*}
 */
const addSetmeal = (params) => {
    return $axios({
        url: '/setmeal',
        method: 'post',
        data: {...params}
    })
}

/**
 * 后套套餐管理：查询套餐详情接口
 * @param id
 * @returns {*}
 */
const querySetmealById = (id) => {
    return $axios({
        url: `/setmeal/${id}`,
        method: 'get'
    })
}

/**
 * 后台套餐管理：批量起售禁售修改套餐接口
 * @param params
 * @returns {*}
 */
const setmealStatusByStatus = (params) => {
    return $axios({
        url: `/setmeal/status/${params.status}`,
        method: 'post',
        params: {ids: params.ids}
    })
}
