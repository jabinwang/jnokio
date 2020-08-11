package com.wangsu.lib.myio;

import java.io.IOException;

public interface BufferedSource extends Source {

    int read(byte[] sink, int offset, int byteCount) throws IOException;
    String readString() throws IOException;
}
