/**
 * 首页：获取所有的菜品分类接口
 * @returns {*}
 */
function categoryListApi() {
    return $axios({
        'url': '/category/list',
        'method': 'get',
    })
}

/**
 * 首页：获取菜品分类对应的菜品接口
 * @param data
 * @returns {*}
 */
function dishListApi(data) {
    return $axios({
        'url': '/dish/list',
        'method': 'get',
        params: {...data}
    })
}

/**
 * 首页：获取菜品分类对应的套餐接口
 * @param data
 * @returns {*}
 */
function setmealListApi(data) {
    return $axios({
        'url': '/setmeal/list',
        'method': 'get',
        params: {...data}
    })
}

/**
 * 首页购物车：获取购物车内商品的集合接口
 * @param data
 * @returns {*}
 */
function cartListApi(data) {
    return $axios({
        'url': '/shoppingCart/list',
        // 'url': '/front/cartData.json',
        'method': 'get',
        params: {...data}
    })
}

/**
 * 首页购物车：购物车中添加商品接口
 * @param data
 * @returns {*}
 */
function addCartApi(data) {
    return $axios({
        'url': '/shoppingCart/add',
        'method': 'post',
        data
    })
}

/**
 * 首页购物车：购物车中修改商品接口
 * @param data
 * @returns {*}
 */
function updateCartApi(data) {
    return $axios({
        'url': '/shoppingCart/sub',
        'method': 'post',
        data
    })
}

/**
 *  首页购物车：删除购物车的商品
 * @returns {*}
 */
function clearCartApi() {
    return $axios({
        'url': '/shoppingCart/clean',
        'method': 'delete',
    })
}

/**
 * 获取套餐的全部菜品
 * @param id
 * @returns {*}
 */
function setMealDishDetailsApi(id) {
    return $axios({
        'url': `/setmeal/dish/${id}`,
        'method': 'get',
    })
}


