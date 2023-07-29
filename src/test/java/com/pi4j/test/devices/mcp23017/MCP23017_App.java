package com.pi4j.test.devices.mcp23017;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;


    import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;

public class MCP23017_App {

        private static final int IODIRA = 0x00;
        private static final int IODIRB = 0x01;
        private static final int GPIOA = 0x12;

        private static final int GPIOB = 0x13;

        public static void main(String[] args) throws InterruptedException {
            reset(0x27);
        }

        /* Assumption.  Chip operating bank=0.  If otherwise, see
        * https://github.com/Pi4J/pi4j-example-devices/tree/master/src/main/java/com/pi4j/devices/mcp23017
        *  and follow code paths to calculate the register address.
         */
        protected static void reset(int address) throws InterruptedException {
            Context pi4j = Pi4J.newAutoContext();
            I2CProvider i2CProvider = pi4j.provider("linuxfs-i2c");
            I2CConfig i2cConfig = I2C.newConfigBuilder(pi4j).id("TCA9534").bus(1).device(address).build();

            try (I2C mcp23017Dev = i2CProvider.create(i2cConfig)) {
                System.out.println("i2c created");


                //configue all PinA output
                mcp23017Dev.writeRegister(IODIRA, (byte) 0x00);
                System.out.println("GPIOA all pins configed as output");

                // ensure all outputs are low
                mcp23017Dev.writeRegister(GPIOA, getAsInteger("00000000"));
                System.out.println("GPIOA all A pins set to 0");
                mcp23017Dev.writeRegister(IODIRB, (byte) 0x00);
                System.out.println("register B is now output");
                mcp23017Dev.writeRegister(GPIOB, getAsInteger("00000000"));
                System.out.println("GPIOB all B pins set to 0");
                Thread.sleep(5000);

                // set the bit to drive gpio high Since this is setting
                // the register we need only read the current reg vaue and set the bit.
                // No worry of losing bit values
                int pinOut = 0;
                int currentOutVal = mcp23017Dev.readRegister(GPIOA);
                mcp23017Dev.writeRegister(GPIOA, currentOutVal|(1<<pinOut));
                System.out.println("GPIOA pin 0 driven high, LED on");
                Thread.sleep(5000);

                // Now clear the bit .  Use a mask to preserve all present
                // bit values and clear only the bit for pin 0
                currentOutVal = mcp23017Dev.readRegister(GPIOA);
                Integer integerObject = ~(1 << pinOut);
                byte b = integerObject.byteValue();
                currentOutVal = (byte) (currentOutVal & b);
                mcp23017Dev.writeRegister(GPIOA, currentOutVal);
                System.out.println("GPIOA pin 0 driven low, LED off");
                Thread.sleep(1000);


                // read current value of the IODIRA register
                int currentVal = mcp23017Dev.readRegister(IODIRA);
                // Configure pin 1 (A1, PA1) to be an input by setting its bit
                int pin1In = 1;
                currentVal = (currentVal | 1<<pin1In);
                mcp23017Dev.writeRegister(IODIRA, (byte) currentVal);

                // Although 'I' know its configed input, add the code that would normally
                // validate this fact
                int configed = mcp23017Dev.readRegister(IODIRA);

                if ((configed & (1 << pin1In)) == 0) {
                    System.out.println("Pin" + String.format("0x%02X", pin1In) + "  not configured for input");
                }

                // read IO reg and see whether pin1 is high or low
                int reg = mcp23017Dev.readRegister(GPIOA);
                if ((reg & (1 << pin1In)) == 0) {
                    System.out.println("Pin" + pin1In + " Low");
                } else {
                    System.out.println("Pin" + pin1In + " High");
                }
                System.out.println("Pin" + pin1In + " wire high");
                Thread.sleep(5000);

                reg = mcp23017Dev.readRegister(GPIOA);
                if ((reg & (1 << pin1In)) == 0) {
                    System.out.println("Pin" + pin1In + " Low");
                } else {
                    System.out.println("Pin" + pin1In + " High");
                }
                Thread.sleep(5000);
            }
        }

        protected static int getAsInteger(String binaryString) {
            return Integer.parseInt(binaryString, 2);
        }
    }

