package com.example.sungsoyeon.couplediary;

/**
 * Created by sungsoyeon on 2018-06-15.
 */

public class Data {
    private String title;
    private String memo;

    public Data(String title, String memo) {
        this.title = title;
        this.memo = memo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
