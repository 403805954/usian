package com.usian.vo;

import java.util.List;

public class ADItemCatVo {
    private List data;

    @Override
    public String toString() {
        return "ADItemCatVo{" +
                "data=" + data +
                '}';
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
