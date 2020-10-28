package com.jzh.designpattern.proxy.asm;

import org.objectweb.asm.ClassWriter;

import static org.objectweb.asm.Opcodes.*;

public class ClassWriteTest {
    public static void main(String[] args) {
        ClassWriter cw = new ClassWriter(0);
        // 生成类或者接口
        cw.visit(V1_8, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE, "pkg/ASMWriteClassTest",
                null, "java/lang/Object", null);
        // 生成成员变量
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "testField", "I", null, 10).visitEnd();
        // 生成方法
        cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "testMethod", "(Ljava/lang/Object;)I", null, null).visitEnd();

        cw.visitEnd();



        byte[] b = cw.toByteArray();

        MyClassLoader myClassLoader = new MyClassLoader();
        Class c = myClassLoader.defineClass("pkg.ASMWriteClassTest", b);
        System.out.println(c.getMethods()[0].getName());
    }
}
