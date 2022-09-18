package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.Category;
import com.example.pojo.Dish;

import java.util.List;

public interface CategoryService extends IService<Category> {

    //对关联的数据进行查询
    void remove(Long id);
}
