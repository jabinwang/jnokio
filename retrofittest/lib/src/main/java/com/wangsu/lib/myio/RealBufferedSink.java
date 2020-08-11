package com.wangsu.lib.myio;

import java.io.IOException;

public class RealBufferedSink implements BufferedSink{

    public final Buffer buffer = new Buffer();
    private final Sink sink;

    public RealBufferedSink(Sink sink) {
        this.sink = sink;
    }

    @Override
    public void write(Buffer source, long byteCount) throws IOException {

    }

    @Override
    public BufferedSink writeString(String string) throws IOException {
        buffer.writeString(string);
        long byteCount = buffer.completeSegmentByteCount();
        if (byteCount > 0) {
            sink.write(buffer, byteCount);
        }
        return this;
    }

    @Override
    public void flush() throws IOException {
        if (buffer.size > 0) {
            sink.write(buffer, buffer.size);
        }
    }
}