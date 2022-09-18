/**
 * 地址簿：获取所有地址
 * @returns {*}
 */
function addressListApi() {
    return $axios({
        'url': '/addressBook/list',
        'method': 'get',
    })
}

/**
 * 地址簿：获取最新地址
 * @returns {*}
 */
function addressLastUpdateApi() {
    return $axios({
        'url': '/addressBook/lastUpdate',
        'method': 'get',
    })
}

/**
 * 地址簿：新增地址
 * @param data
 * @returns {*}
 */
function addAddressApi(data) {
    return $axios({
        'url': '/addressBook',
        'method': 'post',
        data
    })
}

/**
 * 地址簿：修改地址
 * @param data
 * @returns {*}
 */
function updateAddressApi(data) {
    return $axios({
        'url': '/addressBook',
        'method': 'put',
        data
    })
}

/**
 * 地址簿：删除地址
 * @param params
 * @returns {*}
 */
function deleteAddressApi(params) {
    return $axios({
        'url': '/addressBook',
        'method': 'delete',
        params
    })
}

/**
 * 地址簿：查询单个地址
 * @param id
 * @returns {*}
 */
function addressFindOneApi(id) {
    return $axios({
        'url': `/addressBook/${id}`,
        'method': 'get',
    })
}

/**
 * 地址簿: 设置默认地址
 * @param data
 * @returns {*}
 */
function setDefaultAddressApi(data) {
    return $axios({
        'url': '/addressBook/default',
        'method': 'put',
        data
    })
}

/**
 * 地址簿：获取默认地址
 * @returns {*}
 */
function getDefaultAddressApi() {
    return $axios({
        'url': `/addressBook/default`,
        'method': 'get',
    })
}