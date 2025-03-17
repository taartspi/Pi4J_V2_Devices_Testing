package com.pi4j.test.devices.dht22;



import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.Digital;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;

import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.util.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class GpioTest {

    private static final Logger logger = LoggerFactory.getLogger(GpioTest.class);

    // Connect a LED to PIN 15 = BCM 22
    private static final int PIN_LED_YELLOW = 17;
    private static final int PIN_LED_RED = 27;
    private static final int PIN_LED_GREEN = 22;
    private static final int PIN_BTN_RED = 5;
    private static final int MYDOUT = 18;
    private static final int PIN_SWAP = 13;

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Context pi4j = Pi4J.newAutoContext();
        var console = new Console();
        System.out.println("----------------------------------------------------------");
        System.out.println("PI4J PROVIDERS");
        System.out.println("----------------------------------------------------------");
        pi4j.providers().describe().print(System.out);
        System.out.println("----------------------------------------------------------");

        DigitalOutput ledRed = pi4j.dout().create(PIN_LED_RED);
        DigitalOutput ledYellow = pi4j.dout().create(PIN_LED_YELLOW);
        DigitalOutput ledGreen = pi4j.dout().create(PIN_LED_GREEN);
        DigitalInput redBtn = pi4j.din().create(PIN_BTN_RED);


//		ledRed.blink(1, 5, TimeUnit.SECONDS);
//		ledYellow.pulse(1, TimeUnit.SECONDS);
//		Future<?> blinkRed = ledRed.blinkAsync(1, 5, TimeUnit.SECONDS);
//		Future<?> pulseYellow = ledYellow.pulseAsync(1, TimeUnit.SECONDS);
//		blinkRed.get();
//		pulseYellow.get();


        var ledConfig = DigitalOutput.newConfigBuilder(pi4j)
                .id("my-dout")
                .name("My LED")
                .address(MYDOUT)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.HIGH);

        var led = pi4j.create(ledConfig);

        logger.info("pin detail " + led);

//     ledRed.blink(1, 5, TimeUnit.SECONDS);
//     ledYellow.pulse(1, TimeUnit.SECONDS);
//     Future<?> blinkRed = ledRed.blinkAsync(1, 5, TimeUnit.SECONDS);
//     Future<?> pulseYellow = ledYellow.pulseAsync(1, TimeUnit.SECONDS);
//     blinkRed.get();
//     pulseYellow.get();


        AtomicReference<Digital> greenBtnRef = new AtomicReference<>();

        redBtn.addListener(e -> {
            if (e.state().isHigh())
                return;

            logger.info("Toggling state...");
            Digital<?, ?, ?> digital = greenBtnRef.get();
            if (digital != null && pi4j.hasIO(digital.id())) {
                logger.info("Shutting down " + digital.id() + "...");
                pi4j.shutdown(digital.id());
                logger.info("Done.");
            }

            // toggle PIN_SWAP between output or input
            if (digital instanceof DigitalInput) {
                // set to output
                logger.info("Setting pin " + PIN_SWAP + " to output.");
                digital = pi4j.dout().create(PIN_SWAP);
                logger.info("Pin " + PIN_SWAP + " is now output " + digital.id());
            } else {
                // set to input
                logger.info("Setting pin " + PIN_SWAP + " to input.");
                digital = pi4j.din().create(PIN_SWAP);
                digital.addListener(e1 -> {
                    if (e1.state().isHigh()) {
                        logger.info("Drive  : " + PIN_LED_RED + " HIGH");
                        ledRed.high();
                    }
                    else {
                        logger.info("Drive  : " + PIN_LED_RED + " LOW");
                        ledRed.low();
                    }
                });
                logger.info("Toggle  : " + PIN_LED_RED );
                ledRed.toggle();
               logger.info("Pin " + PIN_SWAP + " is now input " + digital.id());
            }

            greenBtnRef.set(digital);
            logger.info("");
            logger.info("");
        });

        ledRed.low();
        ledYellow.low();
        ledRed.high();
        ledYellow.high();
        Thread.sleep(2000L);
        ledRed.low();
        ledYellow.low();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    logger.info("Shutting down...");
                    pi4j.shutdown();
                } catch (Exception e) {
                    logger.error("Failed to shutdown", e);
                }
            }
        });

        synchronized (Thread.currentThread()) {
            Thread.currentThread().wait();
        }
    }

}

