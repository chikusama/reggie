/**
 * 后台员工管理：分页查询员工接口
 * @param params
 * @returns {*}
 */
function getEmployList(params) {
    return $axios({
        url: '/employee/page',
        method: 'get',
        params
    })
}

/**
 * 后台员工管理：修改---启用禁用员工账号接口
 * @param params
 * @returns {*}
 */
function enableOrDisableEmployee(params) {
    return $axios({
        url: '/employee',
        method: 'put',
        data: {...params}
    })
}

/**
 *后台员工管理：新增员工账号接口
 * @param params
 * @returns {*}
 */
function addEmployee(params) {
    return $axios({
        url: '/employee',
        method: 'post',
        data: {...params}
    })
}

/**
 * 后台员工管理：修改员工账号接口
 * @param params
 * @returns {*}
 */
function editEmployee(params) {
    return $axios({
        url: '/employee',
        method: 'put',
        data: {...params}
    })
}

/**
 * 后台员工管理：根据主键查询员工信息接口
 * @param id
 * @returns {*}
 */
function queryEmployeeById(id) {
    return $axios({
        url: `/employee/${id}`,
        method: 'get'
    })
}