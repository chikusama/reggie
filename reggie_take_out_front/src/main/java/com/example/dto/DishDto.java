package com.example.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.pojo.Dish;
import com.example.pojo.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
