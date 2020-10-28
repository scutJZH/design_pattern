package com.jzh.designpattern.proxy;

public class MovableLogProxy implements Movable {

    private Movable m;

    public MovableLogProxy(Movable m) {
        this.m = m;
    }

    @Override
    public void move() {
        System.out.println("CarLogProxy start...");
        m.move();
        System.out.println("CarLogProxy end...");
    }

    @Override
    public void move2() {
        System.out.println("CarLogProxy start...");
        m.move2();
        System.out.println("CarLogProxy end...");
    }
}
