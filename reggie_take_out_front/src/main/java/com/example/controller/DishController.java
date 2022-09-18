package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.R;
import com.example.dto.DishDto;
import com.example.pojo.Category;
import com.example.pojo.Dish;
import com.example.pojo.DishFlavor;
import com.example.service.CategoryService;
import com.example.service.DishFlavorService;
import com.example.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    DishService dishService;
    @Autowired
    DishFlavorService dishFlavorService;

    //首页获取所有菜品
    @GetMapping("/list")
    public R<List<DishDto>> getDish(@RequestParam Long categoryId,@RequestParam Integer status){
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        //根据条件获取
        lqw.eq(Dish::getCategoryId,categoryId);
        lqw.eq(Dish::getStatus,status);
        List<Dish> dishes = dishService.list(lqw);

        //根据dish的id进行查询
        List<DishDto> dishDtos = dishes.stream().map(dish -> {
            //创建dishDto的集合
            DishDto dishDto = new DishDto();
            //浅克隆
            BeanUtils.copyProperties(dish, dishDto);
            //根据dishId获取flavors
            LambdaQueryWrapper<DishFlavor> lqw2 = new LambdaQueryWrapper<>();
            lqw2.eq(DishFlavor::getDishId, dish.getId());
            List<DishFlavor> flavors = dishFlavorService.list(lqw2);
            //把flavors放入到DishDto中
            dishDto.setFlavors(flavors);
            return dishDto;
        }).collect(Collectors.toList());


        return R.success(dishDtos);
    }
}
