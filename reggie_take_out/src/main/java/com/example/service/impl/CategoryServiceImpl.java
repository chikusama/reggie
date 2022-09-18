package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.CustomerException;
import com.example.common.R;
import com.example.mapper.CategoryMapper;
import com.example.mapper.DishMapper;
import com.example.pojo.Category;
import com.example.pojo.Dish;
import com.example.pojo.Setmeal;
import com.example.service.CategoryService;
import com.example.service.DishService;
import com.example.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    DishService dishService;
    @Autowired
    SetmealService setmealService;

    //重写方法
    @Override
    public void remove(Long id) {
        //对category的id和setmeal中的category_id进行查询
        QueryWrapper<Setmeal> qw1 = new QueryWrapper<>();
        //select count(*) from setmeal where category_id = id
        qw1.eq("category_id", id);
        int setmealCount = setmealService.count(qw1);

        QueryWrapper<Dish> qw2 = new QueryWrapper<>();
        qw2.eq("category_id", id);
        int dishCount = dishService.count(qw2);

        if (setmealCount > 0) {
            throw new CustomerException("该分类关联套餐,不能删除!");
        }

        if (dishCount > 0) {
            throw new CustomerException("该分类关联菜品,不能删除!");
        }
        //调用本类的移除方法
        this.removeById(id);

    }
}
