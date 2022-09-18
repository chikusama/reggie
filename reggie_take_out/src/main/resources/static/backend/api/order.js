/**
 * 后台订单管理：分页查询订单接口
 * @param params
 * @returns {*}
 */
const getOrderDetailPage = (params) => {
    return $axios({
        url: '/order/page',
        method: 'get',
        params
    })
}

/**
 * 后台订单管理：根据主键查询订单详情接口
 * @param id
 * @returns {*}
 */
const queryOrderDetailById = (id) => {
    return $axios({
        url: `/orderDetail/${id}`,
        method: 'get'
    })
}

/**
 * 后台订单管理：修改订单详情接口
 * @param params
 * @returns {*}
 */
const editOrderDetail = (params) => {
    return $axios({
        url: '/order',
        method: 'put',
        data: {...params}
    })
}
