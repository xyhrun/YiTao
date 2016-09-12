package com.example.xyh.shoppingdemo.homepage.model;

/**
 * Created by xyh on 2016/9/11.
 */
public class CampaignBean {

    /**
     * id : 1
     * title : 超值购
     * cpOne : {"id":17,"title":"手机专享","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/555c6c90Ncb4fe515.jpg"}
     * cpTwo : {"id":15,"title":"闪购","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/560a26d2N78974496.jpg"}
     * cpThree : {"id":11,"title":"团购","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/560be0c3N9e77a22a.jpg"}
     */

    private int id;
    private String title;
    /**
     * id : 17
     * title : 手机专享
     * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/555c6c90Ncb4fe515.jpg
     */

    private Bean cpOne;
    /**
     * id : 15
     * title : 闪购
     * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/560a26d2N78974496.jpg
     */

    private Bean cpTwo;
    /**
     * id : 11
     * title : 团购
     * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/560be0c3N9e77a22a.jpg
     */

    private Bean cpThree;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bean getCpOne() {
        return cpOne;
    }

    public void setCpOne(Bean cpOne) {
        this.cpOne = cpOne;
    }

    public Bean getCpTwo() {
        return cpTwo;
    }

    public void setCpTwo(Bean cpTwo) {
        this.cpTwo = cpTwo;
    }

    public Bean getCpThree() {
        return cpThree;
    }

    public void setCpThree(Bean cpThree) {
        this.cpThree = cpThree;
    }

    public static class Bean {
        private int id;
        private String title;
        private String imgUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
