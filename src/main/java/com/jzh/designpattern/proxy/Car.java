package com.jzh.designpattern.proxy;

public class Car implements Movable {

    public void move() {
        System.out.println("car moving 1...");
    }

    @Override
    public void move2() {
        System.out.println("car moving 2...");
    }
}
