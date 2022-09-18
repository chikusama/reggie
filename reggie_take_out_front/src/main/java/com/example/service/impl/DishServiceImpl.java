package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.DishDto;
import com.example.mapper.DishMapper;
import com.example.pojo.Dish;
import com.example.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
//开启事务
@Transactional
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    DishMapper dishMapper;

    @Override
    public List<DishDto> selectWithFlavors() {
        return dishMapper.selectWithFalvors();
    }
}
