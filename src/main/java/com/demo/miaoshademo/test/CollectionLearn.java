package com.demo.miaoshademo.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class CollectionLearn {
    public static void main(String[] args) {
        Arrays.sort(new int[3]);

        HashMap map = new HashMap();
        map.put(1,2);
        map.get(1);

        String s = "abc";
        s = s + "a";
        s.substring(1,2);

        StringBuilder sb = new StringBuilder();
        sb.append("avc");

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("aaa");


        Stack<String> stack = new Stack<>();
        stack.push("1");
        stack.push("1");
        stack.push("1");
        stack.push("1");
        System.out.println(String.join("",stack));

    }
}
