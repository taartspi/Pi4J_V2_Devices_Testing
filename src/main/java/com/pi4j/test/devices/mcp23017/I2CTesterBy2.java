package com.pi4j.test.devices.mcp23017;


import com.pi4j.Pi4J;
        import com.pi4j.context.Context;
        import com.pi4j.io.i2c.I2C;
        import com.pi4j.io.i2c.I2CConfig;
        import com.pi4j.io.i2c.I2CProvider;
        import java.io.IOException;

public class I2CTesterBy2 {

    private static final int IODIRA = 0x00;
    private static final int GPIOA = 0x12;
    private static final int IODIRB = 0x01;

    public static void main(String[] args) throws Exception {
        blink(0x27);
    }

    protected static void blink(int address) throws InterruptedException, IOException {
        Context pi4j = Pi4J.newAutoContext();

        I2CProvider i2CProvider = pi4j.provider("linuxfs-i2c");
        I2CConfig i2cConfig = I2C.newConfigBuilder(pi4j).id("TCA9534").bus(1).device(address).build();

        try (I2C mcp23017Dev = i2CProvider.create(i2cConfig)) {
            System.out.println("I2C is created");
            mcp23017Dev.writeRegister(IODIRA, (byte) 0x00);
            System.out.println("Register A is configured as output");
            mcp23017Dev.writeRegister(IODIRB, (byte) 0x00);
            System.out.println("Register B is configured as output");

            Thread.sleep(1000);

            for (int i = 0; i < 10; i++) {
                mcp23017Dev.writeRegister(GPIOA, 0b00000011);
                Thread.sleep(1000);
                mcp23017Dev.writeRegister(GPIOA, 0x00);
                Thread.sleep(1000);
            }
        }
    }
}
