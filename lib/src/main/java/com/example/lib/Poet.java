package com.example.lib;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;

import javax.lang.model.element.Modifier;

/**
 * author : will
 * date   : 2020/5/16 9:43
 * desc   :
 */
public class Poet {
    public static void main(String[] args) {
        //test方法
        MethodSpec testMethod = createSwitchTest();
        //主方法
        MethodSpec mainMethod = createMain();
        //Test class
        TypeSpec typeSpec = TypeSpec.classBuilder("Test")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(mainMethod)
                .addMethod(testMethod)
                .build();
        //Test.java文件
        JavaFile javaFile = JavaFile.builder("com.example.lib", typeSpec)
                .build();

        try {
            //将代码输出到文件
            javaFile.writeTo(new File("E:\\download\\StickHeaderView-master\\LearnGradle\\lib\\src\\main\\java"));
            System.out.println("吟诗成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MethodSpec createMain() {
        return MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("test(1)")
                .build();
    }

    public static MethodSpec createSwitchTest() {
        int count = 5000;
        //test方法
        MethodSpec.Builder builder = MethodSpec.methodBuilder("test")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(Integer.class, "value")
                .addStatement("$T.out.println($S)", System.class, "让我们来吟诗");
        //开始时间
        builder.addStatement("$T beginTime=$T.currentTimeMillis()", long.class, System.class);
        //结束时间
        builder.addStatement("$T endTime=0", long.class);
        //switch语句
        builder.beginControlFlow("switch(value)");
        for (int i = 0; i < count; i++) {
            builder.addCode("case $L:\n", i)
                    .addStatement("endTime=$T.currentTimeMillis()", System.class)
                    .addStatement("break");
        }
        builder.addCode("default:\n")
                .addStatement("break")
                .endControlFlow();
        builder.beginControlFlow("if(endTime==0)")
                .addStatement("$T.out.println($S)", System.class, "value的范围是[0," + count + ")")
                .addStatement("return")
                .endControlFlow();
        builder.addStatement("$T.out.println($S+(endTime-beginTime))", System.class, "endTime-beginTime=");
        return builder.build();
    }
}
