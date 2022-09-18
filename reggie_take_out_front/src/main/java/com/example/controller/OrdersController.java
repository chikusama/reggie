package com.example.controller;


import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.BaseContext;
import com.example.common.R;
import com.example.pojo.OrderDetail;
import com.example.pojo.Orders;
import com.example.pojo.ShoppingCart;
import com.example.service.AddressBookService;
import com.example.service.OrderDetailService;
import com.example.service.OrdersService;
import com.example.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    OrdersService ordersService;


    //查询用户信息
    @GetMapping("/userPage")
    public R<Page> selectUser(@RequestParam Integer page, @RequestParam Integer pageSize){
        //根据userid进行查询
       return R.success(ordersService.page(new Page<Orders>(page,pageSize),
               Wrappers.<Orders>lambdaQuery().
                       //根据userId进行查询
                       eq(Orders::getUserId, BaseContext.getThreadLocal()).
                       //根据下单时间倒叙查询
                       orderByDesc(Orders::getOrderTime)));
    }

    //提交订单
    @PostMapping("submit")
    public R<String> submitOrder(@RequestBody Orders orders){
       ordersService.saveOrders(orders);
       return R.success("下单成功");
    }
}
