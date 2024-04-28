package com.pi4j.test.devices.pwmTest;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmType;
import com.pi4j.util.Console;

import java.util.Scanner;


public class UserTest {
    private static Pwm pwm = null;
    private static Context pi4j;

    public static void main(String[] args) throws Exception {
        pi4j = Pi4J.newAutoContext();     //  remove var


        var console = new Console();
        System.out.println("----------------------------------------------------------");
        System.out.println("PI4J PROVIDERS");
        System.out.println("----------------------------------------------------------");
        pi4j.providers().describe().print(System.out);
        System.out.println("----------------------------------------------------------");

        int address = 2;
        //  initPiGpio();
        for (int i = 0; i < args.length; i++) {
            String o = args[i];
            if (o.contentEquals("-a")) { // pin
                String a = args[i + 1];
                address = Short.parseShort(a.substring(0), 10);
                i++;
            } else {
                console.println("  No valid Parm, -a address ");
                System.exit(42);
            }
        }
        initGPIO(address);

        System.out.println("linuxfs pin after creation at duty_cycle 50  ");
        waitForInput(console);

        pwm.on(50, 2);
        System.out.println("linuxfs pin after pin.on  freq 2    ");

        waitForInput(console);

        pwm.off();
        pwm.on(50, 10);
        System.out.println("linuxfs pin after pin.on  freq 10  ");
        waitForInput(console);

        pwm.off();
        pwm.on(50, 50);
        System.out.println("linuxfs pin after pin.on  freq 50   ");

        waitForInput(console);


        pwm.off();
        pwm.on(50, 1);
        System.out.println("linuxfs pin after pin.on  freq 1");
        waitForInput(console);


        System.out.println("linuxfs pin call pwm.shutdown ");
        waitForInput(console);


        pwm.shutdown(pi4j);

        System.out.println("linuxfs pi4j  call pi4j.shutdown ");
        waitForInput(console);

        pi4j.shutdown();

        // Wait a bit for shutdown
        Thread.sleep(2000);
    }


    private static int waitForInput(Console console) {
        int rval = 0;
        Scanner scan = new Scanner(System.in);

        console.println("Hit any key to continue");
        scan.next();

        return (rval);
    }

    private static void initGPIO(int address) {
        var configPwm = Pwm.newConfigBuilder(pi4j)
                .address(address)
                .pwmType(PwmType.HARDWARE)
                .provider("linuxfs-pwm")
                .initial(50)
                .frequency(1)
                /* .shutdown(10) */
                .build();
        try {
            pwm = pi4j.create(configPwm);
        } catch (Exception e) {
            System.out.println("Error in initGPIO " + e.getMessage());
            e.printStackTrace();
        }
    }
}
