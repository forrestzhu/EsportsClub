package com.jayzero.games.esportsclub.commonutils.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jayzero.games.esportsclub.commonutils.exception.InvalidParameterException;
import com.jayzero.games.esportsclub.commonutils.exception.errorcode.UtilErrorCode;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FileUtils 文件相关工具类
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version FileUtils.java, v0.1
 * @date 2017/09/24
 */
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    private static Set<PosixFilePermission> fullPermission = Sets.newHashSet(
        PosixFilePermission.OWNER_READ,
        PosixFilePermission.OWNER_WRITE,
        PosixFilePermission.OWNER_EXECUTE,
        PosixFilePermission.OTHERS_READ,
        PosixFilePermission.OTHERS_WRITE,
        PosixFilePermission.OWNER_EXECUTE,
        PosixFilePermission.GROUP_READ,
        PosixFilePermission.GROUP_WRITE,
        PosixFilePermission.GROUP_EXECUTE
    );

    /**
     * 从url地址下载文件, 注意这里已经把IOException封装为UncheckedIOException,方便java8 stream调用
     *
     * @param urlText  url地址
     * @param filePath 文件路径
     */
    public static void downloadFileFromUrl(String urlText, String filePath) {
        int initPosition = 0;
        FileOutputStream fileOutputStream = null;
        ReadableByteChannel readableByteChannel = null;
        try {
            URL url = new URL(urlText);
            readableByteChannel = Channels.newChannel(url.openStream());
            fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream.getChannel().transferFrom(readableByteChannel, initPosition, Long.MAX_VALUE);
        } catch (IOException e) {
            logger.error("error occurred when downloading file.", e);
            throw new UncheckedIOException(e);
        } finally {
            closeChannelQuietly(readableByteChannel);
            closeOutputStreamQuietly(fileOutputStream);
        }
    }

    /**
     * 静静地关闭OutputStream, 不抛出异常
     *
     * @param outputStream {@link OutputStream}
     */
    private static void closeOutputStreamQuietly(OutputStream outputStream) {
        if (Objects.nonNull(outputStream)) {
            try {
                outputStream.close();
            } catch (Exception e) {
                logger.error("error occurred when close OutputStream.", e);

            }
        }
    }

    /**
     * 静静地关闭Channel, 不抛出异常
     *
     * @param channel {@link Channel}
     */
    private static void closeChannelQuietly(Channel channel) {
        if (Objects.nonNull(channel)) {
            try {
                channel.close();
            } catch (Exception e) {
                logger.error("error occurred when close Channel.", e);
            }
        }
    }

    /**
     * 静静地关闭Closeable, 不抛出异常
     *
     * @param closeable {@link Closeable}
     */
    private static void closeCloseableQuietly(Closeable closeable) {
        if (Objects.nonNull(closeable)) {
            try {
                closeable.close();
            } catch (Exception e) {
                logger.error(String.format("error occurred when close %s.", closeable.getClass().getName()), e);
            }
        }
    }

    /**
     * 给每个传入的文件 chmod777
     *
     * @param filePaths 文件路径, 可以传入多个
     */
    public static void changeFileMod777(String... filePaths) {
        if (filePaths.length < 1) {
            return;
        }

        for (String filePath : filePaths) {
            try {
                Files.setPosixFilePermissions(Paths.get(filePath), fullPermission);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    /**
     * 获得当前目录
     *
     * @return currentDir
     */
    public static String getCurrentDir() {
        return Paths.get(StringConstants.DOT).toAbsolutePath().normalize().toString();
    }

    /**
     * 删除文件, 不抛出异常
     *
     * @param path 文件路径
     */
    public static void deleteFileWithoutException(Path path) {
        logger.info("deleting file {}", path);
        try {
            Files.deleteIfExists(path);
        } catch (Exception e) {
            logger.error(String.format("delete file-[%s] error", path), e);
        }
    }

    /**
     * 删除文件, 不抛出异常
     *
     * @param file 文件
     */
    public static void deleteFileWithoutException(File file) {
        deleteFileWithoutException(file.toPath());
    }

    /**
     * 将多个文本文件merge到单个文件中
     *
     * @param targetFile  要merge到的文件
     * @param sourceFiles 被merge的源文件列表
     * @throws IOException IO异常
     */
    public static void mergeFilesInOne(File targetFile, File... sourceFiles) throws IOException {
        if (targetFile == null) {
            throw new InvalidParameterException(UtilErrorCode.MERGE_FILE_ERROR_NO_TARGET_FILE);
        }
        if (sourceFiles == null || sourceFiles.length == 0) {
            throw new InvalidParameterException(UtilErrorCode.MERGE_FILE_ERROR_NO_SOURCE_FILE);
        }

        List<File> existSourceFiles = Lists.newArrayListWithCapacity(sourceFiles.length);
        Arrays.stream(sourceFiles)
            .filter(sourceFile -> Files.exists(sourceFile.toPath()))
            .forEach(existSourceFiles::add);

        if (CollectionUtils.isEmpty(existSourceFiles)) {
            throw new InvalidParameterException(UtilErrorCode.MERGE_FILE_ERROR_NO_SOURCE_FILE);
        }

        // 这个时候sourceFiles一定是存在的, 则看targetFile是否存在, 如果不存在, 则创建之
        createFileIfNotExists(targetFile);

        doMergeFilesInOne(targetFile, existSourceFiles);
    }

    /**
     * 将多个文本文件merge到单个文件中, 这里所有的参数都已经被提前check过了
     *
     * @param targetFile  要merge到的文件
     * @param sourceFiles 被merge的源文件列表
     */
    private static void doMergeFilesInOne(File targetFile, List<File> sourceFiles) throws IOException {
        logger.info("Begin merging files into one. source files:[{}]. targetFile:[{}]", sourceFiles, targetFile);

        // 在merge之前先删除文件中的内容
        deleteFileContentIfExists(targetFile);

        for (File sourceFile : sourceFiles) {
            try {
                byte[] bytes = Files.readAllBytes(sourceFile.toPath());
                Files.write(targetFile.toPath(), bytes, StandardOpenOption.APPEND);
            } catch (IOException e) {
                logger.error("IOException occurred when reading sourceFiles.", e);
            }
        }
        logger.info("Merged Success.");
    }

    /**
     * 如果文件存在, 则删除文件内存
     *
     * @param file File
     */
    private static void deleteFileContentIfExists(File file) {
        if (file.exists()) {
            try {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    /**
     * 如果文件不存在, 则创建之
     *
     * @param file File
     * @throws IOException IO异常
     */
    public static void createFileIfNotExists(File file) throws IOException {
        if (file != null) {
            Path filePath = file.toPath();
            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
            }
        }

    }

    /**
     * 判断文件是否存在且存在内容
     *
     * @param file 文件
     * @return true if exits and contains content, false otherwise
     */
    public static boolean existsAndContainsContent(File file) {
        if (file == null || Files.notExists(file.toPath())) {
            return false;
        } else {
            final int minSizeInByte = 4;
            // 这里说明文件是存在的
            try {
                return Files.size(file.toPath()) > minSizeInByte;
            } catch (IOException e) {
                logger.error(String.format("fetch file[%s] size error.", file.toPath()), e);
                return false;
            }
        }
    }

    /**
     * 如果传入的文件存在, 则将文件内容删除而不删除文件
     *
     * @param file File
     */
    public static void truncateFileIfExists(File file) throws IOException {
        if (file != null) {
            Path filePath = file.toPath();
            if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
                RandomAccessFile randomAccessFile = null;
                try {
                    randomAccessFile = new RandomAccessFile(file, "rw");
                    randomAccessFile.setLength(0);
                } finally {
                    closeCloseableQuietly(randomAccessFile);
                }
            }
        }
    }
}
