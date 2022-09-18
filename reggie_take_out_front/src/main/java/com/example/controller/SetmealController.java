package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.common.R;
import com.example.dto.SetmealDto;
import com.example.pojo.Dish;
import com.example.pojo.Setmeal;
import com.example.pojo.SetmealDish;
import com.example.service.DishService;
import com.example.service.SetmealDishService;
import com.example.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    SetmealService setmealService;
    @Autowired
    SetmealDishService setmealDishService;

    @GetMapping("/list")
    public R<List<Setmeal>> getSetmeal(@RequestParam Long categoryId, @RequestParam Integer status) {
        List<Setmeal> setmeals = setmealService.list(Wrappers.<Setmeal>lambdaQuery().
                eq(Setmeal::getCategoryId, categoryId).
                eq(Setmeal::getStatus, status).
                orderByDesc(Setmeal::getUpdateTime));
        return R.success(setmeals);
    }

    @GetMapping("/dish/{id}")
    public R<List<SetmealDto>> getSingleSetmeal(@PathVariable Long id) {
        //套餐管连菜品因此返回值是SetmealDto
        //首先根据id查询setmeal
        List<Setmeal> setmeals = setmealService.list(Wrappers.<Setmeal>lambdaQuery().eq(Setmeal::getId, id));
        //创建一个setmealDto
        SetmealDto setmealDto = new SetmealDto();
        //根据setmeal的id查询setmealDish的详情
        List<SetmealDish> setmealDishes = setmealDishService.list(Wrappers.<SetmealDish>lambdaQuery().eq(SetmealDish::getSetmealId, id));
        setmealDto.setSetmealDishes(setmealDishes);
        //遍历setmeals集合把setmealDto转换成list集合
        List<SetmealDto> setmealDtos = setmeals.stream().map(setmeal -> {
            //浅克隆
            BeanUtils.copyProperties(setmeal, setmealDto);
            return setmealDto;
        }).collect(Collectors.toList());

        return R.success(setmealDtos);

    }
}
