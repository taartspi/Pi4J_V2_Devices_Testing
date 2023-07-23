package com.pi4j.test.devices.gpio.example;
import com.pi4j.Pi4J;
import com.pi4j.io.gpio.digital.*;
import com.pi4j.util.Console;
import java.util.concurrent.TimeUnit;
public class SimpleGpio {


    /**
     * <p>DigitalOutputExample class.</p>
     *
     * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
     * @version $Id: $Id
     */

        /** Constant <code>DIGITAL_OUTPUT_PIN=4</code> */
        public static final int DIGITAL_OUTPUT_PIN = 18;

      public SimpleGpio(){}


        /**
         * <p>main.</p>
         *
         * @param args an array of {@link java.lang.String} objects.
         * @throws java.lang.Exception if any.
         */
        public static void main(String[] args) throws Exception {

            // create Pi4J console wrapper/helper
            // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
            final var console = new Console();

            // print program title/header
            console.title("<-- The Pi4J Project -->", "Basic Digital Output Example");

            // allow for user to exit program using CTRL-C
            console.promptForExit();

            // Initialize Pi4J with an auto context
            // An auto context includes AUTO-DETECT BINDINGS enabled
            // which will load all detected Pi4J extension libraries
            // (Platforms and Providers) in the class path
            var pi4j = Pi4J.newAutoContext();

// copied

            DigitalOutput output = null;
            var outputConfig3 = DigitalOutput.newConfigBuilder(pi4j)
                    .address(DIGITAL_OUTPUT_PIN)
                    .provider("pigpio-digital-output");
            output = pi4j.create(outputConfig3);
            output.low();
            pi4j.shutdown();

            pi4j = Pi4J.newAutoContext();  //  Appears the shutdown call effected the
            // list of providers and the following  .provider("pigpio-digital-input"); may fail.


           // read

            DigitalInput input = null;
            var inputConfig3 = DigitalInput.newConfigBuilder(pi4j)
                    .address(DIGITAL_OUTPUT_PIN)
                    .provider("pigpio-digital-input");
            input = pi4j.create(inputConfig3);
            String result = input.state().toString();
            console.println(" Read state : " + result);

            pi4j.shutdown();

            pi4j = Pi4J.newAutoContext();



////////
            // create a digital input instance using the default digital input provider
            // we will use the PULL_DOWN argument to set the pin pull-down resistance on this GPIO pin
            var config = DigitalOutput.newConfigBuilder(pi4j)
                    .address(DIGITAL_OUTPUT_PIN)
                    .shutdown(DigitalState.HIGH)
                    .build();

            // get a Digital output I/O provider from the Pi4J context
            DigitalOutputProvider digitalOutputProvider = pi4j.provider("pigpio-digital-output");

            var output42 = digitalOutputProvider.create(config);


            // setup a digital output listener to listen for any state changes on the digital output
            output42.addListener(System.out::println);

            // lets invoke some changes on the digital output
            output42.state(DigitalState.HIGH)
                    .state(DigitalState.LOW)
                    .state(DigitalState.HIGH)
                    .state(DigitalState.LOW);

            // lets toggle the digital output state a few times
            output42.toggle()
                    .toggle()
                    .toggle();


            // another friendly method of setting output state
            output42.high()
                    .low();

            // lets read the digital output state
            System.out.print("CURRENT DIGITAL OUTPUT [" + output42 + "] STATE IS [");
            System.out.println(output42.state() + "]");

            // pulse to HIGH state for 3 seconds
            System.out.println("PULSING OUTPUT STATE TO HIGH FOR 3 SECONDS");
            output42.pulse(3, TimeUnit.SECONDS, DigitalState.HIGH);
            System.out.println("PULSING OUTPUT STATE COMPLETE");

            // shutdown Pi4J
            console.println("ATTEMPTING TO SHUTDOWN/TERMINATE THIS PROGRAM");
            pi4j.shutdown();    // pin set high as .shutdown(DigitalState.HIGH)
            pi4j = Pi4J.newAutoContext();

            ////////
          //  pi4j = Pi4J.newAutoContext();
            DigitalOutput output5 = null;
            var outputConfig5 = DigitalOutput.newConfigBuilder(pi4j)
                    .address(DIGITAL_OUTPUT_PIN)
                    .provider("pigpio-digital-output");
            output5 = pi4j.create(outputConfig5);
            output5.low();
            pi4j.shutdown();
            pi4j = Pi4J.newAutoContext();

            DigitalInput input5 = null;
            var inputConfig5 = DigitalInput.newConfigBuilder(pi4j)
                    .address(DIGITAL_OUTPUT_PIN)
                    .provider("pigpio-digital-input");
            input5 = pi4j.create(inputConfig5);
            String result5 = input5.state().toString();
            console.println(" Read state : " + result5);

            pi4j.shutdown();
            pi4j = Pi4J.newAutoContext();
            //return result;

        }
}
