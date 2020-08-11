package com.wangsu.lib.myio;

import java.io.IOException;

class RealBufferedSource implements BufferedSource{

    public final Buffer buffer = new Buffer();

    public final Source source;
    public RealBufferedSource(Source source) {
        this.source = source;
    }

    @Override
    public int read(byte[] sink, int offset, int byteCount) throws IOException {
        return 0;
    }

    @Override
    public long read(Buffer Sink, long byteCount) throws IOException {
        return 0;
    }

    @Override
    public String readString() throws IOException {
        buffer.writeAll(source);
        return buffer.readString();
    }
}
