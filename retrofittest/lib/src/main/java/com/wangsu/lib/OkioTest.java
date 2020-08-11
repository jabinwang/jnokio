package com.wangsu.lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

public class OkioTest {

    private static void read(){
        String fileName = "test.txt";
        Source source = null;
        BufferedSource bufferedSource = null;
        File file = new File(fileName);
        try {
            source = Okio.source(file);
            bufferedSource = Okio.buffer(source);
            String content = bufferedSource.readString(Charset.forName("utf-8"));
            System.out.println(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bufferedSource != null) {
                try {
                    bufferedSource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void write(){
        String writeName = "write.txt";
        boolean isExist = false;
        Sink sink = null;
        BufferedSink bufferedSink = null;


            try {
                File file = new File(writeName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                sink = Okio.sink(file);
                bufferedSink = Okio.buffer(sink);
                bufferedSink.writeUtf8("test");
                bufferedSink.writeUtf8("generate new file");
                bufferedSink.writeString("hello okio test", Charset.forName("utf-8"));
                bufferedSink.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (bufferedSink != null) {
                    try {
                        bufferedSink.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

    }
    public static void main(String[] args) {
        System.out.println("hello test");
        read();
        write();
    }
}