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
import org.springframework.data.redis.core.RedisTemplate;
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

        return R.success(dishService.getDish(categoryId,status));
    }
}
