package com.wangsu.lib.myio;

import java.io.IOException;
import java.nio.charset.Charset;

final class Buffer implements BufferedSource, BufferedSink {

    Segment head;
    long size;

    @Override
    public void write(Buffer source, long byteCount) throws IOException {
        if (byteCount > size) {
            byteCount = size;
        }

    }

    @Override
    public long read(Buffer sink, long byteCount) throws IOException {
        sink.write(this, byteCount);
        return byteCount;
    }

    @Override
    public void flush() throws IOException {

    }

    /**
     * @param miniNumCapacity 最少的字节数
     * @return
     */
    Segment writableSegment(int miniNumCapacity) {
        if (head == null) {
            head = SegmentPool.take();
            return head.next = head.prev = head;
        }
        Segment tail = head.prev;
        if (tail.limit + miniNumCapacity > Segment.SIZE || !tail.owner) {
            tail = tail.push(SegmentPool.take());
        }
        return tail;
    }

    @Override
    public String readString() {
        return readString(size);
    }

    public String readString(long byteCount) {
        Segment s = head;
        //跨段
        if (s.pos + byteCount > s.limit) {
            return new String(readByteArray(byteCount));
        }

        String result = new String(s.data, s.pos, (int) byteCount, Charset.defaultCharset());
        s.pos += byteCount;
        size -= byteCount;
        if (s.pos == s.limit) {
            head = s.pop();
            SegmentPool.recycle(s);
        }
        return result;
    }

    public byte[] readByteArray(long byteCount) {
        byte[] result = new byte[(int) byteCount];
        readFully(result);
        return result;
    }

    public void readFully(byte[] sink) {
        int offset = 0;
        while (offset < sink.length) {
            int readBytes = (int) read(sink, offset, sink.length - offset);
            offset += readBytes;
        }
    }

    @Override
    public int read(byte[] sink, int offset, int byteCount) {
        Segment s = head;
        if (s == null) {
            return -1;
        }
        int toCopy = Math.min(byteCount, s.limit - s.pos);
        System.arraycopy(s.data, s.pos, sink, offset, toCopy);
        s.pos += toCopy;
        size -= toCopy;
        if (s.pos == s.limit) {
            head = s.pop();
            SegmentPool.recycle(s);
        }
        return toCopy;
    }

    public long writeAll(Source source) throws IOException {
        long total = 0;
        for (long readCount; (readCount = source.read(this, Segment.SIZE)) != -1; ) {
            total += readCount;
        }
        return total;
    }

    public long completeSegmentByteCount() {
        long result = size;
        if (result == 0) {
            return 0;
        }
        Segment tail = head.prev;
        if (tail.limit < Segment.SIZE && tail.owner) {
            result -= tail.limit - tail.pos;
        }
        return result;
    }

    @Override
    public BufferedSink writeString(String string) throws IOException {
        return writeString(string, 0, string.length());
    }

    public Buffer writeString(String string, int bIndex, int eIndex) {
        byte[] data = string.substring(bIndex, eIndex).getBytes();
        return write(data, 0, data.length);
    }

    public Buffer write(byte[] source, int offset, int byteCount) {
        int limit = offset + byteCount;
        while (offset < limit) {
            Segment tail = writableSegment(1);
            int toCopy = Math.min(limit - offset, Segment.SIZE - tail.limit);
            System.arraycopy(source, offset, tail.data, tail.limit, toCopy);
            offset += toCopy;
            tail.limit += toCopy;
        }
        size += byteCount;
        return this;
    }
}
