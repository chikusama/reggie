package com.example.dto;

import com.example.pojo.Dish;
import com.example.pojo.DishFlavor;
import com.example.pojo.ShoppingCart;
import lombok.Data;

import java.util.List;

@Data
public class ShoppingCartDto extends ShoppingCart {

    private List<Dish> dishes;

    private List<DishFlavor> dishFlavors;
}
