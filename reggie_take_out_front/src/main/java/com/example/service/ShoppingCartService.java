package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.ShoppingCartDto;
import com.example.pojo.ShoppingCart;

public interface ShoppingCartService extends IService<ShoppingCart> {

    //新增方法
    void savaWithDishAndFlavor(ShoppingCart shoppingCart);
}
