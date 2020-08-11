package com.wangsu.lib.myio;

import java.io.IOException;

public interface Source {
   long read(Buffer sink, long byteCount) throws IOException;
}
