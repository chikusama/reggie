package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dto.DishDto;
import com.example.pojo.Category;
import com.example.pojo.Dish;

import java.util.List;

public interface DishMapper extends BaseMapper<Dish> {

    //根据菜品查询口味
    List<DishDto> selectWithFalvors();
}
