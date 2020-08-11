package com.wangsu.lib.myio;

/**
 * 单链表串联
 */
public class SegmentPool {

    static final long MAX_SIZE = 64 * 1024;
    static Segment head;
    static long byteCount;

    private SegmentPool() {

    }

    static Segment take() {
        synchronized (SegmentPool.class) {
            if (head != null) {
                Segment result = head;
                head = result.next;
                result.next = null;
                byteCount -= Segment.SIZE;
                return result;
            }
        }
        return new Segment();
    }

    static void recycle(Segment segment) {
        synchronized (SegmentPool.class) {
            byteCount += Segment.SIZE;
            segment.next = head;
            segment.pos = segment.limit = 0;
            head = segment;
        }
    }
}