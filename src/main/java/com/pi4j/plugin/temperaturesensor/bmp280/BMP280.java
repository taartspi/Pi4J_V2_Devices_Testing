package com.pi4j.plugin.temperaturesensor.bmp280;


import com.pi4j.context.Context;
import com.pi4j.io.i2c.*;

import java.nio.ByteBuffer;


public class BMP280 extends I2CBase implements I2C {

    public static final int DEFAULT_ADDRESS = 0x70;

    public static final int DEFAULT_BUS = 0x1;

    /** Constant <code>NAME="Mock"</code> */
    public static final String NAME = "BMP280";
    /** Constant <code>ID="mock"</code> */
    public static final String ID = "BMP280";


    // I2C Provider name and unique ID
    /** Constant <code>I2C_PROVIDER_NAME="NAME +  I2C Provider"</code> */
    public static final String I2C_PROVIDER_NAME = NAME + " I2C Provider";
    /** Constant <code>I2C_PROVIDER_ID="ID + -i2c"</code> */
    public static final String I2C_PROVIDER_ID = ID + "-i2c";
    /**
     * <p>Constructor for BMP180.</p>
     *
     * @param provider a {@link com.pi4j.io.i2c.I2CProvider} object.
     * @param config a {@link com.pi4j.io.i2c.I2CConfig} object.
     */





    public BMP280(I2CProvider provider, I2CConfig config){
        super(provider, config);
    }



    @Override
    public int read() {
        return 0;
    }

    @Override
    public int read(byte[] bytes, int i, int i1) {
        return 0;
    }

    @Override
    public int read(ByteBuffer buffer, int offset, int length) {
        return 0;
    }

    @Override
    public int write(byte b) {
        return 0;
    }

    @Override
    public int write(int b) {
        return 0;
    }

    @Override
    public int write(byte[] bytes, int i, int i1) {
        return 0;
    }

    @Override
    public int write(byte[] data, int length) {
        return 0;
    }

    @Override
    public int readRegister(int i) {
        return 0;
    }

    @Override
    public int readRegister(int i, byte[] bytes, int i1, int i2) {
        return 0;
    }

    @Override
    public int writeRegister(int i, byte b) {
        return 0;
    }

    /* @Override
        public int writeRegister(int i, byte b) {
            return 0;
        }
    */
    @Override
    public int writeRegister(int i, byte[] bytes, int i1, int i2) {
        return 0;
    }
}
