package com.github.braians.springblog.util;

public class SummaryUtil {
    public static String makeSummary(String context) {
        if(context.length() >= 97){
            return context.substring(0, 97);
        }
        return context+"...";
    }
}
