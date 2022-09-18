package com.example.controller;


import com.example.common.R;
import com.example.pojo.Category;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    //首页获取所有分类
    @GetMapping("/list")
    public R<List<Category>> getCategory(){
        return R.success(categoryService.list());
    }
}
