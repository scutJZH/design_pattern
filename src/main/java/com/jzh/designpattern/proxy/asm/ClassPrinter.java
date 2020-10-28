package com.jzh.designpattern.proxy.asm;

import org.objectweb.asm.*;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.ASM4;

public class ClassPrinter extends ClassVisitor {
    public ClassPrinter() {
        super(ASM4);
    }

    public ClassPrinter(ClassWriter classWriter) {
        super(ASM4, classWriter);
    }

    /**
     * 访问类
     * @param version jdk版本
     * @param access 访问修饰符
     * @param name 类名
     * @param signature 泛型信息
     * @param superName 父类类名
     * @param interfaces 实现的接口
     */
    @Override
    public void visit(int version, int access, String name,
                      String signature, String superName, String[] interfaces) {
        System.out.print(name + " ");
        if (signature != null) {
            System.out.print("<" + signature + "> ");
        }
        if (signature != null) {
            System.out.print("extends " + superName + " ");
        }
        if (interfaces != null && interfaces.length > 0) {
            System.out.print("implements " + interfaces[0]);
            for (int i = 1; i < interfaces.length; i++) {
                System.out.print(", " + interfaces[i]);
            }
        }
        System.out.println(" {");
        super.visit(version, access, name, signature, superName, interfaces);
    }

    /**
     * 访问类成员变量
     * @param access 访问修饰符
     * @param name 方法名
     * @param desc 返回类型
     * @param signature 泛型类型
     * @param value 初始值（final时才会打印）
     * @return
     */
    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        System.out.println("        " + desc + " " + name + " ");
        return super.visitField(access, name, desc, signature, value);
    }

    /**
     * 访问类方法
     * @param access 访问修饰符
     * @param name 方法名称
     * @param desc 方法返回类型
     * @param signature 泛型
     * @param exceptions 抛出的异常
     * @return
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println("        " + desc + " " + name + "();");
        return super.visitMethod(access, name, desc, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        System.out.println("}");
        super.visitEnd();
    }

    public static void main(String[] args) throws IOException {
        ClassPrinter classPrinter = new ClassPrinter();
//        ClassReader reader = new ClassReader(ClassPrinter.class.getClassLoader().getResourceAsStream("com/jzh/designpattern/proxy/Movable.class"));
//        ClassReader reader = new ClassReader("com.jzh.designpattern.proxy.Movable");
        ClassReader reader = new ClassReader("java.lang.Runnable");


        reader.accept(classPrinter, 0);
    }
}
