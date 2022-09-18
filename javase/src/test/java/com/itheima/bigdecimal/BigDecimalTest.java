package com.itheima.bigdecimal;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * BigDecimal
 */
public class BigDecimalTest {
    /**
     * 计算机是二进制的。浮点数没有办法是用二进制进行精确表示。这样的表示方法一般都会失去一定的精确度，
     * 有些浮点数运算也会产生一定的误差。
     */
    @Test
    public void test() {
        System.out.println(0.2 + 0.1);
        System.out.println(0.3 - 0.1);
        System.out.println(0.2 * 0.1);
        System.out.println(0.3 / 0.1);
    }

    /**
     * float/double计算中产生的精度丢失：
     * 超过小数点16未就会有精度丢失
     */
    @Test
    public void testDouble() {
        Integer number = 2;
        Double price = 3.123456789012345678;
        Double account = number * price;
        System.out.println("account = " + account);
    }

    @Test
    public void test2() {
        BigDecimal bigDecimal = new BigDecimal(2);
        BigDecimal bDouble = new BigDecimal(2.3);
        BigDecimal bString = new BigDecimal("2.3");
        System.out.println("bigDecimal=" + bigDecimal);
        System.out.println("bDouble=" + bDouble);
        System.out.println("bString=" + bString);
    }

    /**
     * 在金融支付行业领域：使用BigDecimal来计算
     * BigDecimal 对象不能直接使用+，-，*，/符号运算;相关API如下：
     * public BigDecimal add(BigDecimal value);                        //加法
     * public BigDecimal subtract(BigDecimal value);                   //减法
     * public BigDecimal multiply(BigDecimal value);                   //乘法
     * public BigDecimal divide(BigDecimal value);                     //除法
     */
    @Test
    public void testBigDecimal() {
        BigDecimal number = new BigDecimal("2");
        BigDecimal price = new BigDecimal("3.123456789012345678");
        //乘法
        BigDecimal account = price.multiply(number);
        System.out.println("account = " + account);
    }
}
