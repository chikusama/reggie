package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BaseContext;
import com.example.mapper.ShoppingCartMapper;
import com.example.pojo.ShoppingCart;
import com.example.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {


    @Override
    public void savaWithDishAndFlavor(ShoppingCart shoppingCart) {
        //保存userId到shoppingDto中
        shoppingCart.setUserId((Long) BaseContext.getThreadLocal());
        //先保存shoppingcart
        this.save(shoppingCart);

    }
}
