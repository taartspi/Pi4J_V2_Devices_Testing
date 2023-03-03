package com.pi4j.test.devices.pwmTest;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.devices.mcp3008.MCP3008;
import com.pi4j.exception.LifecycleException;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.pwm.PwmConfigBuilder;
import com.pi4j.io.pwm.PwmType;
import com.pi4j.io.spi.SpiBus;
import com.pi4j.io.spi.SpiChipSelect;
import com.pi4j.util.Console;
import sun.misc.Signal;
import sun.misc.SignalHandler;

public class PwmTest {
    private final Context pi4j;
    private final Console console;
    private final int address;
    private final int duty;
    private final int freq;

    private String pwmType = "H";
    private Pwm pwm;

    /**
     * <p>Constructor for MonitorInterrupt.</p>
     */
    public PwmTest(Context pi4j, Console console, String pwmType, int gpio, int duty, int freq) {
        super();
        this.pi4j = pi4j;
        this.console = console;
        this.address = gpio;
        this.duty = duty;
        this.freq = freq;
        this.pwmType = pwmType;
        this.init();
    }



    private void init(){
        PwmType pinType = PwmType.SOFTWARE;
        if(pwmType.equalsIgnoreCase("H")){
            pinType = PwmType.HARDWARE;
        }
        final PwmConfig config = PwmConfigBuilder.newInstance (pi4j)
                .id ("BCM26")
                .name ("PWM")
                .address (this.address)
                .pwmType(pinType)
                .initial(this.duty)
                .frequency(this.freq)
                .provider ("pigpio-pwm")
                .shutdown (0)
                .frequency(this.freq)
                 .build ();

        pwm = pi4j.create (config);

    }




    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     * @throws java.lang.Exception if any.
     */
    public static void main(String[] args) throws Exception {

        var console = new Console();
        Context pi4j = Pi4J.newAutoContext();
        int gpioNumber = 0;
        int duty = 0;
        int freq = 1;
        int duration = 0;
        String pwmType = "H";


        console.title("<-- The Pi4J V2 Project Extension  -->", "MCP3008App");
        String helpString = " parms: -type PwmType H S,  -gpio  18 or 19 [-t H},  -duty dutyCycle, -freq frequency";

        Signal.handle(new Signal("INT"), new SignalHandler() {
            public void handle(Signal sig) {
                System.out.println("Performing ctl-C shutdown");
                try {
                    pi4j.shutdown();
                } catch (LifecycleException e) {
                    e.printStackTrace();
                }
                //Thread.dumpStack();
                System.exit(2);
            }
        });


        for (int i = 0; i < args.length; i++) {
            String o = args[i];
            if (o.contentEquals("-gpio")) {
                String a = args[i + 1];
                gpioNumber = Integer.parseInt(a.substring(0));
                i++;
           } else if (o.contentEquals("-duty")) {
                String a = args[i + 1];
                duty = Integer.parseInt(a.substring(0));
                i++;
            } else if (o.contentEquals("-type")) {
                String a = args[i + 1];
                pwmType = a;
                i++;
            }else if (o.contentEquals("-freq")) {
                String a = args[i + 1];
                freq = Integer.parseInt(a.substring(0));
                i++;
            }  else if (o.contentEquals("-h")) {
                console.println(helpString);
                System.exit(41);
            } else {
                console.println("  !!! Invalid Parm " + o);
                console.println(helpString);
                System.exit(42);
            }
        }

        if ((pwmType.equalsIgnoreCase("H")) || (pwmType.equalsIgnoreCase("S"))) {
            if (pwmType.equalsIgnoreCase("H")) {
                // SOFTWARE PWM is supported on all GPIOs
                if ((gpioNumber != 18) & (gpioNumber != 19)) {
                    console.println("  !!! Invalid Pin " + gpioNumber  + " for PwmType");
                    console.println(helpString);
                    System.exit(43);
                }
            }
            else if (pwmType.equalsIgnoreCase("S")) {
                // SOFTWARE PWM is supported on all GPIOs
                if ((gpioNumber == 18) & (gpioNumber == 19)) {
                    console.println("  !!! Invalid Pin " + gpioNumber + " for PwmType");
                    console.println(helpString);
                    System.exit(44);
                }
            }
        } else {
            console.println("  !!! Invalid -type  pwmType");
            console.println(helpString);
            System.exit(45);

        }


        short pinCount = 8;
        console.println("----------------------------------------------------------");
        console.println("PI4J PROVIDERS");
        console.println("----------------------------------------------------------");
        pi4j.providers().describe().print(System.out);
        System.out.println("----------------------------------------------------------");

        PwmTest pwm = new PwmTest(pi4j, console, pwmType, gpioNumber, duty, freq);


        console.waitForExit();
    }
}
