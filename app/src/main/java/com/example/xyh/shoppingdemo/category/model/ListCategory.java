package com.example.xyh.shoppingdemo.category.model;

import java.util.List;

/**
 * Created by xyh on 2016/9/14.
 */
public class ListCategory<T> {
    /**
     * totalCount : 23
     * currentPage : 1
     * totalPage : 5
     * pageSize : 5
     * list : [{"id":12,"name":"希捷（seagate）Expansion 新睿翼1TB 2.5英寸 USB3.0 移动硬盘 (STEA1000400)","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_5519fdd0N02706f5e.jpg","description":null,"price":399,"sale":402},{"id":18,"name":"华硕（ASUS）经典系列X554LP 15.6英寸笔记本 （i5-5200U 4G 500G R5-M230 1G独显 蓝牙 Win8.1 黑色）","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_5604aab9N7a91521b.jpg","description":null,"price":2999,"sale":2906},{"id":29,"name":"联想（ThinkPad）E550（20DFA00RCD）15.6英寸笔记本电脑（i7-5500U 4G 500GB 2G独显 FHD高分屏 Win8.1）","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_rmd55fbcea8N8d520a22.jpg","description":null,"price":5499,"sale":6738},{"id":35,"name":"索尼 （SONY） G9 48英寸全高清 LED液晶电视 （金色）","imgUrl":"http://m.360buyimg.com/n4/jfs/t1465/56/1152971182/175914/b39652b4/55e417fcN7d7f9363.jpg!q70.jpg","description":null,"price":3299,"sale":4487},{"id":45,"name":"海信（Hisense）LED58EC620UA 58英寸 炫彩4K 14核 VIDAA3智能电视(黑色)","imgUrl":"http://m.360buyimg.com/n4/jfs/t2035/113/115726791/214888/5e4203f2/55efc9b5N820416a4.jpg!q70.jpg","description":null,"price":5299,"sale":3967}]
     */

    private int totalCount;
    private int currentPage;
    private int totalPage;
    private int pageSize;
    private List<T> list;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
