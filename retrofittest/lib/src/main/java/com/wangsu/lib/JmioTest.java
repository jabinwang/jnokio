package com.wangsu.lib;

import com.wangsu.lib.myio.BufferedSink;
import com.wangsu.lib.myio.BufferedSource;
import com.wangsu.lib.myio.JmIo;
import com.wangsu.lib.myio.Sink;
import com.wangsu.lib.myio.Source;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;




public class JmioTest {
    private static void read(){
        String fileName = "test.txt";
        Source source = null;
        BufferedSource bufferedSource = null;
        File file = new File(fileName);
        try {
            InputStream inputStream = new FileInputStream(file);
            source = JmIo.source(inputStream);
            bufferedSource = JmIo.buffer(source);
            String content = bufferedSource.readString();
            System.out.println(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
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
            OutputStream outputStream = new FileOutputStream(file);
            sink = JmIo.sink(outputStream);
            bufferedSink = JmIo.buffer(sink);
            for (int i = 0; i < 10; i++) {
                bufferedSink.writeString("hello okio test");
            }
            bufferedSink.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }

    }

    public static void main(String[] args) {
//        read();
        write();
    }
}