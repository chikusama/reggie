package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.common.BaseContext;
import com.example.common.R;
import com.example.pojo.ShoppingCart;
import com.example.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/shoppingCart")
@RestController
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    //查询购物车
    @GetMapping("/list")
    public R<List<ShoppingCart>> getShoppingList() {
        //根据userId进行购物车查询
        List<ShoppingCart> carts = shoppingCartService.list(Wrappers.<ShoppingCart>lambdaQuery().
                eq(ShoppingCart::getUserId, BaseContext.getThreadLocal()));
        return R.success(carts);
    }

    //添加到购物车
    @PostMapping("/add")
    public R<ShoppingCart> addDish(@RequestBody ShoppingCart shoppingCart) {
        //根据shoppingcart里的菜品和套餐id进行查询
        ShoppingCart shoppingCart1 = shoppingCartService.getOne(Wrappers.<ShoppingCart>lambdaQuery().
                eq(shoppingCart.getDishId() != null, ShoppingCart::getDishId, shoppingCart.getDishId()).
                eq(shoppingCart.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCart.getSetmealId()));

        //判断shoppingcart1是否为空,不为空update+1
        if (shoppingCart1 != null) {
            shoppingCart1.setNumber(shoppingCart1.getNumber() + 1);
            shoppingCartService.updateById(shoppingCart1);
            return R.success(shoppingCart1);
        }
        //如果是第一次添加返回shoppingcart1的值
        shoppingCart1 = shoppingCart;
        shoppingCart1.setUserId((Long) BaseContext.getThreadLocal());
        shoppingCartService.save(shoppingCart1);
        return R.success(shoppingCart1);
    }

    //清空,删除购物车
    @DeleteMapping("/clean")
    public R<String> cleanShoppingCart() {
        //根据session中的userId对shopping表进行查询
        //delete from shopping_cart where user_id in
        QueryWrapper<ShoppingCart> qw = new QueryWrapper<>();
        qw.eq("user_id", BaseContext.getThreadLocal());
        shoppingCartService.remove(qw);
        return R.success("删除成功");
    }

    //修改数量接口
    @PostMapping("/sub")
    public R<List<ShoppingCart>> updateNumber(@RequestBody ShoppingCart shoppingCart) {
        //根据shoppingcart里的菜品和套餐id进行查询
        ShoppingCart shoppingCart1 = shoppingCartService.getOne(Wrappers.<ShoppingCart>lambdaQuery().
                eq(shoppingCart.getDishId() != null, ShoppingCart::getDishId, shoppingCart.getDishId()).
                eq(shoppingCart.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCart.getSetmealId()));

        //判断shoppingcart1是否为空,不为空update-1
        shoppingCart1.setNumber(shoppingCart1.getNumber() - 1);
        shoppingCartService.updateById(shoppingCart1);
        if (shoppingCart1.getNumber()==0) {
            shoppingCartService.removeById(shoppingCart1);
        }

        //重新查询一遍返回shoppingcart1集合
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(Wrappers.<ShoppingCart>lambdaQuery().eq(ShoppingCart::getUserId, BaseContext.getThreadLocal()));

        return R.success(shoppingCarts);
    }
}
