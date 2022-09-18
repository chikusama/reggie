package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.DishFlavorMapper;
import com.example.mapper.DishMapper;
import com.example.pojo.Dish;
import com.example.pojo.DishFlavor;
import com.example.service.DishFlavorService;
import com.example.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
