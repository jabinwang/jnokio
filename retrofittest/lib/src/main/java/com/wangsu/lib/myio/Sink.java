package com.wangsu.lib.myio;

import java.io.IOException;

public interface Sink {
    void write(Buffer source, long byteCount) throws IOException;
}
