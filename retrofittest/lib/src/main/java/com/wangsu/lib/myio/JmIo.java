package com.wangsu.lib.myio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class JmIo {

    private JmIo() {

    }

    public static BufferedSource buffer(Source source) {
        return new RealBufferedSource(source);
    }

    public static BufferedSink buffer(Sink sink) {
        return new RealBufferedSink(sink);
    }

    public static Sink sink(final OutputStream out) {
        return new Sink() {
            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                while (byteCount > 0) {
                    Segment head = source.head;
                    int toCopy = (int) Math.min(byteCount, head.limit - head.pos);
                    out.write(head.data, head.pos, toCopy);
                    head.pos += toCopy;
                    byteCount -= toCopy;
                    source.size -= toCopy;
                    if (head.pos == head.limit) {
                        source.head = head.pop();
                        SegmentPool.recycle(head);
                    }
                }
            }
        };
    }

    public static Source source(final InputStream in) {
        return new Source() {
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                //从inputstream读取
                //取出最后一个segment
                Segment tail = sink.writableSegment(1);
                int max2Copy = (int) Math.min(byteCount, (Segment.SIZE - tail.limit));

                int bytesRead = in.read(tail.data, tail.limit, max2Copy);
                if (bytesRead == -1) {
                    return -1;
                }
                tail.limit += bytesRead;
                sink.size += bytesRead;
                return bytesRead;
            }
        };
    }
}
