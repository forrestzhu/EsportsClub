package com.jayzero.games.esportsclub.commonutils.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Objects;

import com.jayzero.games.esportsclub.commonutils.constant.CommonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IO Utils
 *
 * @author 成至
 */
public class IoUtils {

    private static final Logger logger = LoggerFactory.getLogger(IoUtils.class);

    public static final int READ_BUFFER_SIZE = 4096;

    private static final int EOF = -1;

    /**
     * Read fully from the InputStream
     *
     * @param inputStream InputStream
     * @return byte[]
     * @throws IOException IO异常
     */
    public static byte[] readFully(InputStream inputStream) throws IOException {
        try {
            byte[] buf = new byte[READ_BUFFER_SIZE];
            int read = EOF;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            while ((read = inputStream.read(buf)) != -1) {
                os.write(buf, 0, read);
            }
            os.close();
            return os.toByteArray();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * Get InputStream bytes count without reading
     *
     * @param inputStream InputStream
     * @return inputStream的长度
     * @throws IOException IO异常
     */
    public static long getInputStreamLength(InputStream inputStream) throws IOException {
        if (inputStream instanceof FileInputStream) {
            return ((FileInputStream)inputStream).getChannel().size();
        }
        return inputStream.available();
    }

    /**
     * Reset the InputStream to the beginning/mark position without a large buffer
     *
     * @param inputStream InputStream
     * @throws IOException IO异常
     */
    public static void resetInputStream(InputStream inputStream) throws IOException {
        if (inputStream.markSupported()) {
            inputStream.reset();
        }
        if (inputStream instanceof FileInputStream) {
            ((FileInputStream)inputStream).getChannel().position(0);
        }
    }

    /**
     * Copy bytes from a large (over 2GB) <code>InputStream</code> to an
     * <code>OutputStream</code>.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedInputStream</code>.
     * <p>
     * The buffer size is given by {@link #READ_BUFFER_SIZE}.
     *
     * @param input  the <code>InputStream</code> to read from
     * @param output the <code>OutputStream</code> to write to
     * @return the number of bytes copied
     * @throws NullPointerException if the input or output is null
     * @throws IOException if an I/O error occurs
     * @since Shamelessly cloned from Apache Commons IO 1.3 IOUtils
     */
    public static long copyLarge(InputStream input, OutputStream output) throws IOException {
        return copyLarge(input, output, new byte[READ_BUFFER_SIZE]);
    }

    /**
     * Copy bytes from a large (over 2GB) <code>InputStream</code> to an
     * <code>OutputStream</code>.
     * <p>
     * This method uses the provided buffer, so there is no need to use a
     * <code>BufferedInputStream</code>.
     * <p>
     *
     * @param input  the <code>InputStream</code> to read from
     * @param output the <code>OutputStream</code> to write to
     * @param buffer the buffer to use for the copy
     * @return the number of bytes copied
     * @throws NullPointerException if the input or output is null
     * @throws IOException if an I/O error occurs
     * @since Shamelessly cloned from Apache Commons IO 2.2 IOUtils
     */
    public static long copyLarge(InputStream input, OutputStream output, byte[] buffer)
        throws IOException {
        long count = 0;
        int n = 0;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    /**
     * 将InputStream根据编码转换为String
     *
     * @param inputStream InputStream
     * @param charset     编码
     * @return 字符串
     * @throws IOException IO异常
     */
    public static String readStreamAsString(InputStream inputStream, String charset) throws IOException {
        return new String(readFully(inputStream), charset);
    }

    /**
     * 将InputStream根据默认的UTF-8编码转换为String
     *
     * @param inputStream InputStream
     * @return 字符串
     * @throws IOException IO异常
     */
    public static String readStreamAsString(InputStream inputStream) throws IOException {
        return new String(readFully(inputStream), Charset.forName(CommonConstants.UTF_8));
    }

    /**
     * 关闭inputStream
     *
     * @param inputStream InputStream
     */
    public static void safeClose(InputStream inputStream) {
        if (Objects.nonNull(inputStream)) {
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.error("error occurred when close inputStream safely.", e);
            }
        }
    }

}
