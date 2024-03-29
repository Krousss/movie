package com.yph.bean;

/**
 * Created by Yph on 2021/3/20.
 */
public enum MovieType {
    Love("爱情"),
    Comedy("喜剧"),
    Action("动作"),
    Plot("剧情"),
    Science("科幻"),
    Terror("恐怖"),
    Cartoon("动画"),
    Horror("惊悚"),
    Crime("犯罪"),
    Fantasy("奇幻"),
    Adventure("冒险"),
    Other("另类"),
    ;

    private String name;

    private MovieType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
