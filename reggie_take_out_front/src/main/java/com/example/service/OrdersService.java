package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.Dish;
import com.example.pojo.Orders;

public interface OrdersService extends IService<Orders> {

    void saveOrders(Orders orders);
}
