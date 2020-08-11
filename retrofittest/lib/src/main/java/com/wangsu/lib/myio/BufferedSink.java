package com.wangsu.lib.myio;

import java.io.IOException;

public interface BufferedSink extends Sink {

    BufferedSink writeString(String string) throws IOException;
    void flush() throws IOException;
}
