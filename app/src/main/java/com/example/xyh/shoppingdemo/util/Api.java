package com.example.xyh.shoppingdemo.util;

/**
 * Created by xyh on 2016/9/11.
 */
public class Api {
    //活动推荐
    public static final String CAMPAIGN_URL = "http://112.124.22.238:8081/course_api/campaign/recommend";

    //基础网址
    public static final String BASE_URL = "http://112.124.22.238:8081/course_api/";

    //广告类型1， 首页广告2
    public static final String BANNER_URL = BASE_URL + "banner/query?type=1";

    //热卖商品 http://112.124.22.238:8081/course_api/wares/hot?curPage=1&pageSize =10;
    public static final String HOT_TRUCK_URL = BASE_URL + "wares/hot";

    //商品列表http://112.124.22.238:8081/course_api/wares/list?categoryId=0&curPage=1&pageSize=10
    public static final String WARES_LIST = BASE_URL + "wares/list";
    public static final String WARES_CAMPAIN_LIST = BASE_URL + "wares/campaign/list";
    public static final String WARES_DETAIL = BASE_URL + "wares/detail.html";

    //商品分类http://112.124.22.238:8081/course_api/category/list
    public static final String CATEGORY_LIST = BASE_URL + "category/list";
}
