package com.example.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BaseContext;
import com.example.mapper.DishMapper;
import com.example.mapper.OrdersMapper;
import com.example.pojo.*;
import com.example.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    OrdersService ordersService;
    @Autowired
    AddressBookService addressBookService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    UserService userService;



    @Override
    public void saveOrders(Orders orders) {
        //原子计数器
        AtomicInteger amount = new AtomicInteger();
        //生成订单详情的order_id
        long id = IdWorker.getId();
        //根据userId对地址簿进行查询
        AddressBook addressBook = addressBookService.getOne(Wrappers.<AddressBook>lambdaQuery().eq(AddressBook::getUserId, (Long) BaseContext.getThreadLocal()));
        //根据id对用户进行查询
        User user = userService.getById((Long)BaseContext.getThreadLocal());
        //把购物车的信息保存到order detail中
        List<ShoppingCart> carts = shoppingCartService.list(Wrappers.<ShoppingCart>lambdaQuery().eq(ShoppingCart::getUserId, BaseContext.getThreadLocal()));

        //遍历carts浅克隆 order detail
        List<OrderDetail> orderDetails = carts.stream().map(cart -> {
            OrderDetail detail = new OrderDetail();
            BeanUtils.copyProperties(cart, detail);
            detail.setOrderId(id);
//            orders.setAmount(new BigDecimal(1000));
            //数量*单价=总额
            /**
             * intValue():将big decimal的值转换成int类型
             * amount.get():获取累加后的结果
             */
            amount.addAndGet(cart.getAmount().multiply(new BigDecimal(String.valueOf(cart.getNumber()))).intValue());
            return detail;
        }).collect(Collectors.toList());

        //保存order detail
        orderDetailService.saveBatch(orderDetails);
        //设置orders的数据
        orders.setNumber(String.valueOf(id));
        orders.setStatus(1);
        orders.setUserId((Long)BaseContext.getThreadLocal());
        //将原子计数器中的值取出,转换为String类型赋值给BigDecimal,塞入Amount中
        orders.setAmount(new BigDecimal(String.valueOf(amount.get())));
        orders.setAddress(addressBook.getDetail());
        orders.setPhone(addressBook.getPhone());
        orders.setConsignee(addressBook.getConsignee());
        orders.setUserName(user.getName());

        //保存信息
        this.save(orders);
        //清空购物车
        shoppingCartService.remove(Wrappers.<ShoppingCart>lambdaQuery().eq(ShoppingCart::getUserId,BaseContext.getThreadLocal()));
    }
}
