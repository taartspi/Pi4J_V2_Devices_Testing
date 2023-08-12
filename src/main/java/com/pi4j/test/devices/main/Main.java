package com.pi4j.test.devices.main;


    
    import com.pi4j.Pi4J;
    import com.pi4j.io.gpio.digital.DigitalOutput;
    import com.pi4j.io.gpio.digital.DigitalOutputProvider;
    import com.pi4j.io.gpio.digital.DigitalState;
    import com.pi4j.util.Console;
//import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

    public class Main {

        public static final int DIGITAL_OUTPUT_PIN = 16;

        public static void main(String[] args) throws Exception{
            Console console = new Console();
            console.println("Start");
            // Initialize Pi4J with an auto context
            // An auto context includes AUTO-DETECT BINDINGS enabled
            // which will load all detected Pi4J extension libraries
            // (Platforms and Providers) in the class path
            var pi4j = Pi4J.newAutoContext();


            // create a digital output instance using the default digital output provider
       /*     var output = pi4j.dout().create(DIGITAL_OUTPUT_PIN);

            output.config().shutdownState(DigitalState.HIGH);
*/
            DigitalOutput output = null;
            var outputConfig3 = DigitalOutput.newConfigBuilder(pi4j)
                    .address(DIGITAL_OUTPUT_PIN)
                    .shutdown(DigitalState.HIGH)
                    .provider("pigpio-digital-output");
            try {
                output = pi4j.create(outputConfig3);
            } catch (Exception e) {
                e.printStackTrace();
                console.println("create DigOut EN failed");
                System.exit(203);
            }

            // lets invoke some changes on the digital output
            output.state(DigitalState.HIGH)
                    .state(DigitalState.LOW)
                    .state(DigitalState.HIGH)
                    .state(DigitalState.LOW);

            // lets toggle the digital output state a few times
            output.toggle()
                    .toggle()
                    .toggle();


            // another friendly method of setting output state
            output.high()
                    .low();

            // lets read the digital output state
            console.println("CURRENT DIGITAL OUTPUT [" + output + "] STATE IS [");
            console.println(output.state() + "]");

            // pulse to HIGH state for 3 seconds
            console.println("PULSING OUTPUT STATE TO HIGH FOR 3 SECONDS");
            output.pulse(3, TimeUnit.SECONDS, DigitalState.HIGH);
            console.println("PULSING OUTPUT STATE COMPLETE");

            // shutdown Pi4J
            pi4j.shutdown();
            console.println("End");
        }
    }

