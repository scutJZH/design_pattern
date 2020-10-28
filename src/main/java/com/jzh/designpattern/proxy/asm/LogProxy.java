package com.jzh.designpattern.proxy.asm;

public class LogProxy {
    public static void readMethod() {
        System.out.println("it's a method...");
    }

    public static void readFiled() {
        System.out.println("it's a field...");
    }

    public static void before() {
        System.out.println("proxy start...");
    }

    public static void before2() {
        System.out.println("proxy start2...");
    }

}
