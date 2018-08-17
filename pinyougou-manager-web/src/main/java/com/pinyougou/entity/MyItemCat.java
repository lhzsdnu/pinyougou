package com.pinyougou.entity;

public class MyItemCat {

    private String name;
    private MyTypeTemplate typeId;

    public MyItemCat() {

    }

    public MyItemCat(String name, MyTypeTemplate typeId) {
        this.name = name;
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyTypeTemplate getTypeId() {
        return typeId;
    }

    public void setTypeId(MyTypeTemplate typeId) {
        this.typeId = typeId;
    }
}
