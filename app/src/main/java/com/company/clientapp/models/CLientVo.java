package com.company.clientapp.models;

import java.util.List;

public class  CLientVo {
    private String name;
    private List<AddressVo> address;
    private int id;

    public CLientVo(String name, List<AddressVo> address, int id) {
        this.name = name;
        this.address = address;
        this.id = id;
    }

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

    public List<AddressVo> getAddress() {
        return address;
    }

    public void setAddress(List<AddressVo> address) {
        this.address = address;
    }
}
