package com.jayzero.games.esportsclub.commonutils.http.bodycopier;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * ReusableRequestWrapper.java
 *
 * @author 成至
 */
public class ReusableRequestWrapper extends HttpServletRequestWrapper {

    /**
     * payload
     */
    private final byte[] body;

    public ReusableRequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            body = input2byte(request.getInputStream());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public byte[] getBody() {
        return body;
    }

    public static byte[] input2byte(InputStream inStream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int bufferLength = 1024;
        byte[] buff = new byte[bufferLength];
        int readCount = 0;
        while ((readCount = inStream.read(buff, 0, bufferLength)) > 0) {
            swapStream.write(buff, 0, readCount);
        }
        return swapStream.toByteArray();
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return inputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {
                throw new RuntimeException("Not implemented");
            }

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream(), Charset.forName("UTF-8")));
    }

}