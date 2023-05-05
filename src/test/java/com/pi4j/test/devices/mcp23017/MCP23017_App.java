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
            reset(0x20);
        }

        protected static void reset(int address) throws InterruptedException {
            Context pi4j = Pi4J.newAutoContext();
            I2CProvider i2CProvider = pi4j.provider("linuxfs-i2c");
            I2CConfig i2cConfig = I2C.newConfigBuilder(pi4j).id("TCA9534").bus(1).device(address).build();

            try (I2C mcp23017Dev = i2CProvider.create(i2cConfig)) {
                System.out.println("i2c created");


                //configue all PinA output
                mcp23017Dev.writeRegister(IODIRA, (byte) 0x00);
                System.out.println("GPIOA all pins configed as output");

                mcp23017Dev.writeRegister(GPIOA, getAsInteger("00000000"));
                System.out.println("GPIOA all pins set to 0");
                Thread.sleep(5000);

                //configue all PinB output
                mcp23017Dev.writeRegister(IODIRB, (byte) 0x00);
                System.out.println("register B is now output");


                mcp23017Dev.writeRegister(GPIOA, getAsInteger("00000001"));
                System.out.println("GPIOA pin 0 driven high, LED on");
                Thread.sleep(5000);
                mcp23017Dev.writeRegister(GPIOA, getAsInteger("00000000"));
                System.out.println("GPIOA pin 0 driven high, LED off");
                Thread.sleep(1000);


                mcp23017Dev.writeRegister(GPIOB, getAsInteger("00000001"));
                Thread.sleep(1000);
            }
        }

        protected static int getAsInteger(String binaryString) {
            return Integer.parseInt(binaryString, 2);
        }
    }

