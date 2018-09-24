package com.jayzero.games.esportsclub.commonutils.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ReplaceResourceUtils 替换资源工具类
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version ReplaceResourceUtils.java, v0.1
 * @date 2017/09/26
 */
public class ReplaceResourceUtils {

    private static final Logger logger = LoggerFactory.getLogger(ReplaceResourceUtils.class);

    private static final String RESOURCE_REGEX = "(@resource_reference\\{)(.+?)(})";

    private static final String RESOURCE_REFERENCE_KEYWORD = "@resource_reference";

    /**
     * 资源名称支持英文大小写,数字,下划线和.
     */
    private static final String INNER_PATTERN_REGEX = "\\s*(\")(\\w+(.\\w+)*)(\")\\s*";

    private static final int INNER_PATTERN_CONTENT_INDEX = 2;

    private static final Pattern INNER_PATTERN = Pattern.compile(INNER_PATTERN_REGEX);

    private static final Pattern RESOURCE_PATTERN = Pattern.compile(RESOURCE_REGEX);

    /**
     * 对代码中的资源引用进行替换[替换为绝对路径], 注意这里如果出现资源引用的语法, 该资源引用语句会被替换为空字符串
     *
     * @param content         资源引用格式:
     *                        e.g. @resource_reference{resourceRefName}
     *                        e.g. @resource_reference{resourceRefName1,resourceRefName2, ...}
     * @param resourcePathMap 资源refName和path的对应关系
     * @return 替换后的内容
     */
    public static String replaceContent(String content, Map<String, String> resourcePathMap) {
        List<String> resourceKeysOrderByLengthDesc = fetchResourceKeysOrderByLengthDesc(resourcePathMap);

        if (StringUtils.isNotBlank(content)) {
            // 注意:\\R是Java8新提供的换行通配符,支持各种换行符匹配如\r\n等
            String[] originContentSplit = content.split("\\R");
            StringBuilder replacedContentBuilder = new StringBuilder();
            Arrays.stream(originContentSplit)
                .filter(StringUtils::isNotBlank)
                .forEach(line -> {
                    // 替换resource的声明
                    line = removeResourceReferenceDeclaration(line, false);
                    if (StringUtils.isNotBlank(line)) {
                        // 根据resourcePathMap, 替换resource的引用key为绝对路径
                        line = replaceLineWithResourcePathMap(line, resourceKeysOrderByLengthDesc, resourcePathMap);
                        replacedContentBuilder.append(line).append(StringConstants.SAFE_NEXT_LINE);
                    }
                });
            return replacedContentBuilder.toString().trim();
        } else {
            // if resourcePathMap is empty or content is blank, return content
            return content;
        }
    }

    /**
     * 获得resourcePathMap的所有resourceKeys, 并将这些resourceKeys按照其长度进行倒排排序
     *
     * @param resourcePathMap resourcePathMap, resourceKey和其绝对路径的对应关系
     * @return 按照resourceKey长度倒排的resourceKey列表
     */
    private static List<String> fetchResourceKeysOrderByLengthDesc(Map<String, String> resourcePathMap) {
        if (MapUtils.isEmpty(resourcePathMap)) {
            return Lists.newArrayList();
        } else {
            List<String> resourceKeys = Lists.newArrayList(resourcePathMap.keySet());
            return resourceKeys.stream()
                .filter(StringUtils::isNotBlank)
                .sorted(Comparator.comparing(String::length).reversed())
                .collect(Collectors.toList());
        }
    }

    /**
     * 将一行内容根据资源引用id和绝对路径的对应关系进行替换
     * 注意: 引入resourceKeysOrderByLengthDesc参数主要是防止在实际进行资源替换的时候,
     * 防止资源因为替换顺序的原因被误替换的情况.
     * e.g: 资源keys:[1.txt, 11.txt]
     * 如果先替换1.txt, 那么11.txt就会替换为[1+resourcePath], 导致无法正确匹配11.txt的情况出现
     * 不过目前上述情况不会发生, 因为在实际匹配的时候, 是用line.replaceAll("\\b" + replaceKey + "\\b", replaceValue);
     * "\\b"这个表示有间隔, 如换行, 空格等, 因此除非资源引用的key本身包含空格, 不然不至于遇到这种情况
     *
     * @param line                          一行内容
     * @param resourceKeysOrderByLengthDesc 按照resourceKey长度倒排的resourceKey列表
     * @param resourcePathMap               资源引用key和绝对路径的对应关系
     * @return 替换后的内容
     */
    private static String replaceLineWithResourcePathMap(String line,
        List<String> resourceKeysOrderByLengthDesc, Map<String, String> resourcePathMap) {
        if (StringUtils.isNotBlank(line) && CollectionUtils.isNotEmpty(resourceKeysOrderByLengthDesc)) {
            // 替换resource的内容
            for (String resourceKey : resourceKeysOrderByLengthDesc) {
                String replaceKey = resourceKey.replace(".", "\\.");
                String replaceValue = resourcePathMap.get(resourceKey);
                ParamValidator.validateParamNotBlank(replaceValue, "resourcePathMap.resourceAbsolutePath");
                // 注意\\b表示非字符边界
                line = line.replaceAll("\\b" + replaceKey + "\\b", replaceValue);
            }
        }
        return line;
    }

    /**
     * 对于传入的一行内容, 如果该行包含资源定义语句, 将其替换为空字符串
     * 注意, 这里只是替换资源定义语句, 不应该进行trim等操作, 特别是对于python这类需要缩进的内容来说, 空格也是必不可少的
     *
     * @param line 一行内容
     * @param trim 是否对该行进行trim操作
     * @return 替换后的内容
     */
    private static String removeResourceReferenceDeclaration(String line, boolean trim) {
        line = trim ? StringUtils.trim(line) : line;

        if (StringUtils.isNotBlank(line) && StringUtils.containsIgnoreCase(line, RESOURCE_REFERENCE_KEYWORD)) {
            line = line.replaceAll(RESOURCE_REGEX, StringConstants.EMPTY);
        }

        line = trim ? StringUtils.trim(line) : line;
        return line;
    }

    /**
     * 从代码中解析出使用到的resources, 返回资源引用名称列表
     *
     * @param content 代码内容
     * @return resourceRefName列表
     */
    public static List<String> parseResourcesFromContent(String content) {
        return parseResourcesFromContent(content, RESOURCE_PATTERN);
    }

    public static List<String> parseResourcesFromContent(String content, Pattern pattern) {
        if (StringUtils.isBlank(content)) {
            return Collections.emptyList();
        }

        // 注意:\\R是Java8新提供的换行通配符,支持各种换行符匹配如\r\n等
        String[] originContentSplit = content.split("\\R");
        List<String> resourceRefs = new ArrayList<>();
        Arrays.stream(originContentSplit)
            .filter(StringUtils::isNotBlank)
            .forEach(line -> {
                // 找到resource声明
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    resourceRefs.addAll(resolveRefNamesFromInnerPatternContent(matcher.group(2)));
                }
            });
        return resourceRefs;
    }

    private static Set<String> resolveRefNamesFromInnerPatternContent(String innerPatternContent) {
        if (StringUtils.isBlank(innerPatternContent)) {
            return Collections.emptySet();
        }
        Set<String> resourceRefNames = new HashSet<>();
        Matcher matcher = INNER_PATTERN.matcher(innerPatternContent);
        while (matcher.find()) {
            resourceRefNames.add(matcher.group(INNER_PATTERN_CONTENT_INDEX));
        }
        return resourceRefNames;
    }

}
