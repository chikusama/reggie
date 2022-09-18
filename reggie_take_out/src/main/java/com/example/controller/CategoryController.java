package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.pojo.Category;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

//分类管理
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    //定义分页查询接口
    @GetMapping("/page")
    public R<Page> getCategory(@RequestParam Integer page, @RequestParam Integer pageSize) {
        /*QueryWrapper<Category> qw = new QueryWrapper<>();
        //根据sort升序
        qw.orderByAsc("sort");*/
        LambdaQueryWrapper<Category> lqw= new LambdaQueryWrapper<>();
        lqw.orderByAsc(Category::getSort);

        Page<Category> pageCategory = new Page<>(page, pageSize);
        categoryService.page(pageCategory,lqw);
        return R.success(pageCategory);
    }

    //新增分类,新增套餐
    @PostMapping
    public R<String> addCategory(@RequestBody Category category) {
        if (category == null) {
            return R.error("什么都没有写哦~");
        }
        categoryService.save(category);
        return R.success("新增成功");
    }

    //修改分类
    @PutMapping
    public R<Category> updateCategory(@RequestBody Category category) {
        //根据id查询修改
        UpdateWrapper<Category> uw =new UpdateWrapper<>();
        uw.eq("id",category.getId());
        category.setUpdateUser(null);
        category.setUpdateTime(null);
        categoryService.update(category,uw);
        return R.success(category);
    }
    //删除分类
    //如果删除分类,关联的分类也会一并删除,因此要先进行判断有哪些表回去关联category_id
    //先查询
    //select * from category,dish,setmeal where dish.category_id = category.id and setmeal.category_id = category.id
    @DeleteMapping
    public R<String> deleteCategory(@RequestParam Long id){
        //调用service中的自定义方法
        categoryService.remove(id);
        return R.success("删除成功");
    }

    //菜品查询
    @GetMapping("/list")
    public R<List<Category>> selectDish(@RequestParam Integer type){
        //根据type的值进行查询
        /*QueryWrapper<Category> qw = new QueryWrapper<>();
        qw.eq("type",type);
        List<Category> categories = categoryService.getBaseMapper().selectList(qw);*/

        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Category::getType,type);
        return R.success(categoryService.list(lqw));
    }

}
