package com.wangsu.retrofittest;

import androidx.annotation.NonNull;

class GetModel {
    private int status;

    private content content;

    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }


    @NonNull
    @Override
    public String toString() {
        return "status=" + status
                + " from=" + content.from
                + " to=" + content.to
                + " vendor=" + content.vendor
                + " out=" + content.out
                + " errNo=" + content.errNo;
    }

    //定义 输出返回数据 的方法
    public void show() {
        System.out.println(status);

        System.out.println(content.from);
        System.out.println(content.to);
        System.out.println(content.vendor);
        System.out.println(content.out);
        System.out.println(content.errNo);
    }
}
