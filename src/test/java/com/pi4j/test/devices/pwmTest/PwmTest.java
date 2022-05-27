package com.pi4j.test.devices.pwmTest;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.devices.mcp3008.MCP3008;
import com.pi4j.exception.LifecycleException;
import com.pi4j.io.pwm.Pwm;
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
    private Pwm pwm;

    /**
     * <p>Constructor for MonitorInterrupt.</p>
     */
    public PwmTest(Context pi4j, Console console, int gpio, int duty, int freq, int duration) {
        super();
        this.pi4j = pi4j;
        this.console = console;
        this.address = gpio;
        this.init();
    }

    private void init() {
        var cfg = Pwm.newConfigBuilder(pi4j)
                .id("BCM" + this.address)
                .name("LED")
                .address(this.address)
                .pwmType(PwmType.HARDWARE)
                .provider("pigpio-pwm")
                .initial(0)
                .shutdown(0)
                .build();

        this.pwm = this.pi4j.create(cfg);
    }


    public void PwmActivate(int duty, int freq, long duration) {

        this.pwm.on(duty, freq);
        if (duration > 0) {
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.pwm.off();
        }
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


        console.title("<-- The Pi4J V2 Project Extension  -->", "MCP3008App");
        String helpString = " parms: -gpio  18 or 19,  -duty dutyCycle, -freq frequency, -duration milliSec";

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
            if (o.contentEquals("-gpio")) { // pin
                String a = args[i + 1];
                gpioNumber = Integer.parseInt(a.substring(0));
                i++;
                if ((gpioNumber != 18) & (gpioNumber != 19)) {
                    console.println("  !!! Invalid Pin " + gpioNumber);
                    console.println(helpString);
                    System.exit(40);
                }
            } else if (o.contentEquals("-duty")) { // pin
                String a = args[i + 1];
                duty = Integer.parseInt(a.substring(0));
                i++;
            } else if (o.contentEquals("-freq")) { // pin
                String a = args[i + 1];
                freq = Integer.parseInt(a.substring(0));
                i++;
            } else if (o.contentEquals("-duration")) { // pin
                String a = args[i + 1];
                duration = Integer.parseInt(a.substring(0));
                i++;
            } else if (o.contentEquals("-h")) {
                console.println(helpString);
                System.exit(41);
            } else {
                console.println("  !!! Invalid Parm " + o);
                console.println(helpString);
                System.exit(43);
            }
        }


        short pinCount = 8;
        console.println("----------------------------------------------------------");
        console.println("PI4J PROVIDERS");
        console.println("----------------------------------------------------------");
        pi4j.providers().describe().print(System.out);
        System.out.println("----------------------------------------------------------");

        PwmTest pwm = new PwmTest(pi4j, console, gpioNumber, duty, freq, duration);

        pwm.PwmActivate(duty, freq, duration);


        console.waitForExit();
    }
}
