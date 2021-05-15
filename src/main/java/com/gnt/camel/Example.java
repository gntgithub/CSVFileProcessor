package com.gnt.camel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Example {
    public static void main(String[] args) {
        final String regex = "\\\"{1}+\\n\\\"";
        final String string = "\"girish\",\"ta\n"
	 + "vag\",\"a\n"
	 + "bc\"\n"
	 + "\"tavag\",\"te\n"
	 + "st\",\"123\"";
        
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(string);
        
        if (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));
            
            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.println("Group " + i + ": " + matcher.group(i));
            }
        }
    }
}
