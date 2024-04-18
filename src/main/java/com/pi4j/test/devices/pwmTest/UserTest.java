package com.pi4j.test.devices.pwmTest;
import com.pi4j.*;
        import com.pi4j.io.pwm.*;
        import com.pi4j.context.Context;
        import com.pi4j.io.gpio.digital.*;
import com.pi4j.io.spi.SpiChipSelect;
//import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.gpiod.provider.gpio.digital.GpioDDigitalInputProvider;
import com.pi4j.plugin.gpiod.provider.gpio.digital.GpioDDigitalOutputProvider;
import com.pi4j.plugin.linuxfs.provider.i2c.LinuxFsI2CProvider;
import com.pi4j.plugin.linuxfs.provider.pwm.LinuxFsPwmProvider;
import com.pi4j.util.Console;
import java.util.Scanner;



public class UserTest {
    private static Pwm pwm = null;
    private static Context pi4j;

    public static void main(String[] args) throws Exception
    {
        pi4j = Pi4J.newAutoContext();     //  remove var
      /*  pi4j = Pi4J.newContextBuilder().add(
                LinuxFsI2CProvider.newInstance(),
                LinuxFsPwmProvider.newInstance(2),
                GpioDDigitalInputProvider.newInstance(),
                GpioDDigitalOutputProvider.newInstance()).build();
*/

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
        initGPIOCM4(address);
       // System.out.println("pigpio pin before pin.on  actual  frequ  " +pwm.actualFrequency());
        pwm.on(50, 2);
        System.out.println("linuxfs pin after pin.on  freq 2  actual  frequ  " +pwm.actualFrequency());
 
        waitForInput(console);
 
        pwm.off();
        pwm.on(50, 10);
        System.out.println("linuxfs pin after pin.on  freq 10  actual  frequ  " +pwm.actualFrequency());
        waitForInput(console);
 
         pwm.off();
        pwm.on(50, 50);
        System.out.println("linuxfs pin after pin.on  freq 50  actual  frequ  " +pwm.actualFrequency());
 
        waitForInput(console);
 
        pwm.off();
        pwm.on(50, 100);
        System.out.println("linuxfs pin after pin.on  freq 100  actual  frequ  " +pwm.actualFrequency());
        waitForInput(console);
       
        pwm.off();
        pwm.on(50, 10);
                System.out.println("linuxfs pin after pin.on  freq 10  actual  frequ  " +pwm.actualFrequency());
        waitForInput(console);
 

        System.out.println("linuxfs pin call pwm.shutdown " );
        waitForInput(console);
        
        pwm.shutdown(pi4j);

        System.out.println("linuxfs pi4j  call pi4j.shutdown " );
        waitForInput(console);
 
        pi4j.shutdown();
       while (true){
            // Timeout here?
        }
    }

 /*   private static void initPiGpio() {
        try {
            var pigpio = PiGpio.newNativeInstance();
            pigpio.gpioCfgClock(2, 1, 0);  // leave clock using the PCM
            pigpio.initialize();
        } catch (Exception e) {
            System.out.println("Error in initPiGpio " + e.getMessage());
            e.printStackTrace();
        }
    }
*/

private static int waitForInput(Console console) {
        int rval = 0;
        Scanner scan = new Scanner(System.in);

        console.println("Hit any key to continue");
        scan.next();

        return (rval);
    }

    private static void initGPIOCM4(int address)
    {
        var configPwm = Pwm.newConfigBuilder(pi4j)
                .address(address)
                .pwmType(PwmType.HARDWARE)
                .provider("linuxfs-pwm")
                .initial(0)
                .shutdown(10) 
                .build();
        try {
            pwm = pi4j.create(configPwm);
        } catch (Exception e) {
            System.out.println("Error in initGPIOCM4 " + e.getMessage());
            e.printStackTrace();
        }
    }
}
