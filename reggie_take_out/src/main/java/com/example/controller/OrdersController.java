package com.example.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.pojo.Orders;
import com.example.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    OrdersService ordersService;


    //查询用户信息
    @GetMapping("/page")
    public R<Page> selectUser(@RequestParam Integer page, @RequestParam Integer pageSize){
        Page<Orders> ordersPage = new Page<>(page,pageSize);

        ordersService.page(ordersPage, Wrappers.<Orders>lambdaQuery().orderByDesc(Orders::getOrderTime));
        return R.success(ordersPage);
    }

}
