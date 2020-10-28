package com.jzh.designpattern.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxyTest {
    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Car.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("proxy start...");
                Object result = methodProxy.invokeSuper(o, args);
                System.out.println("proxy end...");
                return result;
            }
        });
        Movable m = (Movable) enhancer.create();
        m.move();

//        System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
//
//        Movable m = (Movable) Proxy.newProxyInstance(Car.class.getClassLoader(), new Class[]{Movable.class}, (proxy, method, args1) -> {
//            System.out.println("proxy start...");
//            Object o = method.invoke(new Car(), args1);
//            System.out.println("proxy end...");
//            return o;
//
//        });
//        m.move();
//
//        Movable car = new Car();
//        Movable m2 = new MovableLogProxy(car);
//        m2.move();
    }
}
