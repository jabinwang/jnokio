package com.wangsu.lib.myio;

public class Segment {
    static final int SIZE = 8192;
    static final int SHARED_MINNUM = 1024;

    final byte[] data;

    int pos;
    int limit;
    boolean shared;
    boolean owner;

    Segment next;
    Segment prev;

    Segment() {
        data = new byte[SIZE];
        this.owner = true;
        this.shared = false;
    }

    //删除当前节点,返回successor
    public Segment pop() {
        Segment result = next != this ? next : null;
        prev.next = next;
        next.prev = prev;
        next = null;
        prev = null;
        return result;
    }

    //当前节点之后插入
    public Segment push(Segment segment) {
        segment.prev = this;
        segment.next = next;
        next.prev = segment;
        next = segment;
        return segment;
    }
}