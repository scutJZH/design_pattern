package com.jzh.designpattern.proxy.asm;

import com.jzh.designpattern.proxy.Movable;
import org.objectweb.asm.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

import static org.objectweb.asm.Opcodes.*;

public class ASMProxyTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException {
        ClassReader reader = new ClassReader(ASMProxyTest.class.getClassLoader().getResourceAsStream("com/jzh/designpattern/proxy/Car.class"));
        ClassWriter classWriter = new ClassWriter(0);
//        ClassVisitor classVisitor = new ClassVisitor(ASM4, classWriter) {
//            @Override
//            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
//                if (name.equals("move")) {
//                    return new MethodVisitor(ASM4, super.visitMethod(access, name, descriptor, signature, exceptions)) {
//                        @Override
//                        public void visitCode() {
//                            super.visitMethodInsn(INVOKESTATIC, "com/jzh/designpattern/proxy/asm/LogProxy", "before", "()V", false);
//                            super.visitCode();
//                        }
//
//                        @Override
//                        public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
//                            super.visitFieldInsn(opcode, owner, name, descriptor);
//                        }
//
//                        @Override
//                        public void visitEnd() {
//                            super.visitEnd();
//                        }
//                    };
//                } else if (name.equals("move2")) {
//                    return new MethodVisitor(ASM4, super.visitMethod(access, name, descriptor, signature, exceptions)) {
//                        @Override
//                        public void visitCode() {
//                            super.visitMethodInsn(INVOKESTATIC, "com/jzh/designpattern/proxy/asm/LogProxy", "before2", "()V", false);
//                            super.visitCode();
//                        }
//
//                    };
//                }
//                return new MethodVisitor(ASM4, super.visitMethod(access, name, descriptor, signature, exceptions)) {};
//            }
//        };
        ClassVisitor classVisitor = new ClassPrinter(classWriter);
        reader.accept(classVisitor, 0);

        byte[] bytes = classWriter.toByteArray();
        MyClassLoader classLoader = new MyClassLoader();
        classLoader.loadClass("com.jzh.designpattern.proxy.Car");
        Class c2 = classLoader.defineClass("com.jzh.designpattern.proxy.Car", bytes);

        Movable moveable = null;
        try {
            moveable = (Movable) c2.getDeclaredConstructor().newInstance(null);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        moveable.move();
        moveable.move2();

        String path = System.getProperty("user.dir");
        File file = new File(path + "/com/jzh/designpattern/proxy");
        file.mkdirs();

        OutputStream outputStream = new FileOutputStream(new File(path + "/com/jzh/designpattern/proxy/Car_0.class"));
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();


    }
}
