package com.pi4j.test.devices.core_validation.test_program;


import com.pi4j.context.Context;
import com.pi4j.util.Console;

public class TestDriver {
    private static Context pi4j;
    static final Console console = new Console();

    public static void main(String[] args) throws Exception {


        pi4j = com.pi4j.Pi4J.newAutoContext();


        // print installed providers
        System.out.println("----------------------------------------------------------");
        System.out.println("PI4J PROVIDERS");
        System.out.println("----------------------------------------------------------");
        pi4j.providers().describe().print(System.out);
        System.out.println("----------------------------------------------------------");

        console.print("==============================================================");
        console.print("startup  TestDriver   ");
        console.print("=============================================================");


    }
}
