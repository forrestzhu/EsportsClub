package com.jayzero.games.esportsclub.commonutils.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ShellUtils
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version ShellUtils.java, v0.1
 * @date 2017/09/21
 */
public class ShellUtils {

    private static final Logger logger = LoggerFactory.getLogger(ShellUtils.class);

    /**
     * 标准shell编码
     */
    private static final String STANDARD_SHELL_ENCODE = "en_US.UTF-8";

    private static final String LANG = "LANG";

    private static final String LC_CTYPE = "LC_CTYPE";

    /**
     * 执行shell命令
     *
     * @param outputRedirectFile 输出重定向的文件, 不能为null
     * @param errorRedirectFile  异常重定向的文件, 可以为null,
     *                           如果为null, 就会把异常信息也重定向到{@param outputRedirectFile}中
     * @param parameters         系统参数
     * @param commands           命令
     * @return Process
     * @throws IOException 异常
     */
    public static Process runShellAndRedirectStreamWithParameters(File outputRedirectFile, File errorRedirectFile,
        Map<String, String> parameters, String... commands) throws IOException {
        ParamValidator.validateParamNotNull(outputRedirectFile, "outputRedirectFile");
        createIfNotExists(outputRedirectFile);

        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        setStandardShellEnvironment(processBuilder);
        setEnvironmentParameters(processBuilder, parameters);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(outputRedirectFile));
        if (errorRedirectFile == null) {
            processBuilder.redirectErrorStream(true);
        } else {
            createIfNotExists(errorRedirectFile);
            processBuilder.redirectError(ProcessBuilder.Redirect.appendTo(errorRedirectFile));
        }

        return processBuilder.start();
    }

    /**
     * 执行shell命令, 并将流进行重定向
     *
     * @param outputRedirectFile 输出重定向的文件, 不能为null
     * @param errorRedirectFile  异常重定向的文件, 可以为null,
     *                           如果为null, 就会把异常信息也重定向到{@param outputRedirectFile}中
     * @param commands           命令
     * @return Process
     * @throws IOException 异常
     */
    public static Process runShellAndRedirectStream(File outputRedirectFile, File errorRedirectFile, String... commands)
        throws IOException {
        return runShellAndRedirectStreamWithParameters(outputRedirectFile, errorRedirectFile, null, commands);
    }

    /**
     * 执行shell命令, 默认使用inheritIO
     *
     * @param parameters 系统参数
     * @param commands   命令
     * @return Process
     * @throws IOException 异常
     */
    public static Process runShellWithParametersAndInheritIo(Map<String, String> parameters, String... commands)
        throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        setStandardShellEnvironment(processBuilder);
        setEnvironmentParameters(processBuilder, parameters);
        processBuilder.redirectErrorStream(true);
        return processBuilder.inheritIO().start();
    }

    /**
     * 执行shell命令
     *
     * @param parameters 系统参数
     * @param commands   命令
     * @return Process
     * @throws IOException 异常
     */
    public static Process runShellWithParameters(Map<String, String> parameters, String... commands)
        throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        setStandardShellEnvironment(processBuilder);
        setEnvironmentParameters(processBuilder, parameters);
        processBuilder.redirectErrorStream(true);
        return processBuilder.start();
    }

    /**
     * 执行shell命令, command由string数组组成
     *
     * @param command 命令 格式为 command arg1 arg2 ...
     * @return Process
     * @throws IOException 异常
     */
    public static Process runShell(String... command) throws IOException {
        return runShellWithParameters(null, command);
    }

    /**
     * 执行shell命令, command为单个String
     *
     * @param command bash中的命令 格式为 command arg1 arg2 ...
     * @return Process
     * @throws IOException 异常
     */
    public static Process runShell(String command) throws IOException {
        return runShell(StringUtils.split(command));
    }

    /**
     * 设置标准shell的环境
     *
     * @param processBuilder ProcessBuilder
     */
    private static void setStandardShellEnvironment(ProcessBuilder processBuilder) {
        if (processBuilder != null) {
            processBuilder.environment().put(LANG, STANDARD_SHELL_ENCODE);
            processBuilder.environment().put(LC_CTYPE, STANDARD_SHELL_ENCODE);
        }
    }

    /**
     * 设置系统变量
     *
     * @param processBuilder ProcessBuilder
     * @param parameters     系统变量参数
     */
    private static void setEnvironmentParameters(ProcessBuilder processBuilder, Map<String, String> parameters) {
        if (processBuilder != null && MapUtils.isNotEmpty(parameters)) {
            parameters.forEach((key, value) -> {
                if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                    processBuilder.environment().put(key, value);
                }
            });
        }
    }

    /**
     * 如果文件不存在则创建之
     *
     * @param file 文件
     */
    private static void createIfNotExists(File file) {
        try {
            FileUtils.createFileIfNotExists(file);
        } catch (IOException e) {
            logger.error(String.format("create file:[%s] failed.", file.toString()), e);
        }
    }

}
