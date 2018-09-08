package com.pinyougou.entity;

public class MyItemCat {

    private long id;
    private String name;
    private MyTypeTemplate typeId;

    public MyItemCat() {

    }

    public MyItemCat(long id,String name, MyTypeTemplate typeId) {
        this.name = name;
        this.typeId = typeId;
        this.id=id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
