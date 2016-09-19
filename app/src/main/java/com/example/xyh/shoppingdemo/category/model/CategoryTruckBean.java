package com.example.xyh.shoppingdemo.category.model;

import java.io.Serializable;

/**
 * Created by xyh on 2016/9/14.
 */
public class CategoryTruckBean implements Serializable{

    /**
     * id : 12
     * name : 希捷（seagate）Expansion 新睿翼1TB 2.5英寸 USB3.0 移动硬盘 (STEA1000400)
     * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_5519fdd0N02706f5e.jpg
     * description : null
     * price : 399.0
     * sale : 402
     */

    private int id;
    private String name;
    private String imgUrl;
    private Object description;
    private float price;
    private int sale;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }
}
