package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.dto.DishDto;
import com.example.pojo.Dish;
import com.example.service.DishFlavorService;
import com.example.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

//菜品管理
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    DishService dishService;
    @Autowired
    DishFlavorService flavorService;


    //分页查询
    //select * from dish,category where category_id = category.id and type = ?
    @GetMapping("/page")
    public R<Page> selectDish(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              String name) throws IOException {

        return R.success(dishService.dishPageInfo(page, pageSize, name));
    }

    //删除
    @DeleteMapping

    public R<String> deleteDish(@RequestParam String[] ids) {
        dishService.deleteDish(ids);
        return R.success("删除成功");
    }

    //批量停售,启售
    @PostMapping("/status/{status}")
    public R<List<Dish>> dishStatus(@PathVariable Integer status, @RequestParam String[] ids) {
        //根据id进行修改状态
        UpdateWrapper<Dish> uw = new UpdateWrapper<>();
        //开启启售
        uw.in("id", Arrays.asList(ids));
        uw.set("status", status);
        //根据id进行查询
        QueryWrapper<Dish> qw = new QueryWrapper<>();
        qw.in("id", Arrays.asList(ids));
        List<Dish> dishes = dishService.getBaseMapper().selectBatchIds(Arrays.asList(ids));
        for (Dish dish : dishes) {
            dish.setUpdateTime(null);
            dish.setUpdateUser(null);
        }
        dishService.update(uw);
        return R.success(dishes);
    }

    //新增菜品

    /**
     * DishFlavor对象
     * private List<DishFlavor> flavors = new ArrayList<>();
     * 分类名称
     * private String categoryName;
     * <p>
     * private Integer copies;
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> addDish(@RequestBody DishDto dishDto) {
       /*  业务层写
       dishService.save(dishDto);
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishDto.getId());
            flavorService.save(flavor);
        }*/
        dishService.saveDishWithFlavors(dishDto);
        return R.success("新增成功");
    }

    //回显数据
    @GetMapping("/{id}")
    public R<DishDto> getDish(@PathVariable Long id) {
        //根据id进行查询
        DishDto dishDto = dishService.selectById(id);
        return R.success(dishDto);
    }

    //修改数据提交保存
    @PutMapping
    public R<String> updateDish(@RequestBody DishDto dishDto) {
        //调用service层update方法,返回R<DishDto>数据
        dishService.updateDish(dishDto);
        return R.success("修改成功");
    }

    //select * from dish,category where category_id = category.id

    /**
     * 根据categoryId和Status进行套餐的查询
     *
     * @param categoryId
     * @param status
     * @return
     */
    @GetMapping("/list")
    public R<List<Dish>> getDishList(@RequestParam Long categoryId, @RequestParam Integer status) {
        return R.success(dishService.searchDishList(categoryId, status));
    }
}
