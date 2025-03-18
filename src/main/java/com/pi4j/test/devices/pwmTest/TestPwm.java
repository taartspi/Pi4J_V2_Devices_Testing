package com.pi4j.test.devices.pwmTest;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmType;
//import com.pi4j.library.pigpio.PiGpio;

public class TestPwm {
    private static Pwm pwm = null;
    private static Context pi4j;

 //   private static  PiGpio pigpio;

    private static void hold(int secs){
        try {
            Thread.sleep(secs * 1000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        pi4j = Pi4J.newAutoContext();
        initGPIOCM4();
        System.out.println("linuxfs before pin.on  actual  frequ  " +pwm.actualFrequency());
        pwm.off();
        System.out.println("linuxfs after pin.off  frequ  " +pwm.actualFrequency());
        hold(3); pwm.off();
        System.out.println("linuxfs pwm OFF");
        hold(3);
        pwm.on(50, 4);
        System.out.println("linuxfs after pin.on  freq 4  actual  frequ  " +pwm.actualFrequency());
        hold(3);
        pwm.off();
        System.out.println("linuxfs pwm OFF");
        hold(3);



        pwm.on(50, 200);
        System.out.println("linuxfs after pin.on  freq 200  actual  frequ  " +pwm.actualFrequency());
        hold(3);
        pwm.off();
        System.out.println("linuxfs pwm OFF");
        hold(3);
        pwm.on(50, 100);
        System.out.println("linuxfs after pin.on  freq 100  actual  frequ  " +pwm.actualFrequency());
        hold(3);
        pwm.off();
        System.out.println("linuxfs pwm OFF");
        hold(3);
        pwm.on(50, 5000);
        System.out.println("linuxfs after pin.on  freq 5000  actual  frequ  " +pwm.actualFrequency());
        hold(3);
        pwm.off();
        System.out.println("linuxfs pwm OFF");
        hold(3);
        pwm.on(50, 10000);
        System.out.println("linuxfs after pin.on  freq 10000  actual  frequ  " +pwm.actualFrequency());
        hold(3);

        pwm.off();
        System.out.println("linuxfs pwm OFF");
        hold(3);
        pwm.on(50, 20000);
        System.out.println("linuxfs after pin.on  freq 20000  actual  frequ  " +pwm.actualFrequency());
        hold(3);

        pwm.off();
        System.out.println("linuxfs pwm OFF");
        hold(3);
        pwm.on(50, 50000);
        System.out.println("linuxfs after pin.on  freq 50000  actual  frequ  " +pwm.actualFrequency());
        hold(3);
        pwm.off();
        System.out.println("linuxfs pwm OFF");
        hold(3);
        pwm.on(50, 1);
        System.out.println("linuxfs after pin.on  freq 1  actual  frequ  " +pwm.actualFrequency());

        hold(3);
        pwm.off();
        System.out.println("linuxfs pwm OFF");
        hold(3);

        pwm.shutdown(pi4j);
        System.out.println("linuxfs pwm SHUTDOWN");
        hold(3);



    }



    private static void initGPIOCM4() {
        var configPwm = Pwm.newConfigBuilder(pi4j)
                .address(2)
                .pwmType(PwmType.HARDWARE)
                .provider("linuxfs-pwm")
                .initial(0)
              //  .shutdown(0)
                .build();
        try {
            pwm = pi4j.create(configPwm);
        } catch (Exception e) {
            System.out.println("Error in initGPIOCM4 " + e.getMessage());
            e.printStackTrace();
            System.exit(42);
        }
    }
}
