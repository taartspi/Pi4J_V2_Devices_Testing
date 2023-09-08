package com.pi4j.test.devices.pwmTest;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmType;
import com.pi4j.library.pigpio.PiGpio;

public class TestPwm {
    private static Pwm pwm = null;
    private static Context pi4j;

    private static  PiGpio pigpio;


    public static void main(String[] args) throws Exception {
     //   initPiGpio();
        pi4j = Pi4J.newAutoContext();
       // initPiGpio();
        initGPIOCM4();
        System.out.println("pigpio pin13 before pin.on  actual  frequ  " +pwm.actualFrequency());
        pwm.on(50, 200);
        System.out.println("pigpio pin13 after pin.on  freq 200  actual  frequ  " +pwm.actualFrequency());
        pwm.off();
        pwm.on(50, 100);
        System.out.println("pigpio pin13 after pin.on  freq 100  actual  frequ  " +pwm.actualFrequency());
        pwm.off();
        pwm.on(50, 1);
        System.out.println("pigpio pin13 after pin.on  freq 1  actual  frequ  " +pwm.actualFrequency());

        while (true) {
            // Timeout here?
        }
    }

    private static void initPiGpio() {
        try {
            pigpio = PiGpio.newNativeInstance();
            System.out.println("pigpio new instance  " + pigpio.toString());
            pigpio.gpioCfgClock(5, 1, 0);
         //           pigpio.initialize();
        } catch (Exception e) {
            System.out.println("Error in initPiGpio " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initGPIOCM4() {
        var configPwm = Pwm.newConfigBuilder(pi4j)
                .address(13)
                .pwmType(PwmType.HARDWARE)
                .provider("pigpio-pwm")
                .initial(0)
                .shutdown(0)
                .build();
        try {
            pwm = pi4j.create(configPwm);
        } catch (Exception e) {
            System.out.println("Error in initGPIOCM4 " + e.getMessage());
            e.printStackTrace();
        }
    }
}
