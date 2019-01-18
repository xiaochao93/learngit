package com.duia.english.common.utils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiaochao on 2018/7/30.
 */
public class StringsUtil extends StringUtils {
    public static String delHtmlTag(String content) {
        if (isNotBlank(content)) {
            content=StringEscapeUtils.unescapeHtml4(content);
            String regEx = "\\t*<[^>]+>\\t*";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(content);
            String result = content;
            if (m.find()) {
                result = m.replaceAll("");
            }
            return result;
        } else {
            return "";
        }
    }
}
