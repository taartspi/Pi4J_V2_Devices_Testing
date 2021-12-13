package com.pi4j.plugin.temperaturesensor.bmp280.impl;

import com.pi4j.config.Config;
import com.pi4j.context.Context;
import com.pi4j.io.IO;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProviderBase;
import com.pi4j.plugin.temperaturesensor.bmp280.BMP280;
import com.pi4j.provider.ProviderBase;
import com.pi4j.plugin.temperaturesensor.bmp280.BMP280Provider;

public class BMP280ProviderImpl extends I2CProviderBase implements BMP280Provider {


    // local/internal I2C reference for communication with hardware chip
    protected I2C i2c = null;


        public BMP280ProviderImpl(){
            this.id = ID;
            this.name = NAME;
        }

    @Override
    public float temperatureC() {
        return 0;
    }

    @Override
    public void initSensor() {
           this.i2c.writeRegister(BMP280Provider.reset, BMP280Provider.reset_cmd);
     }

    @Override
    public void setup(Context pi4j, int bmp180I2cBus, int bmp180I2cAddress) {
        this.i2c = pi4j.i2c().create(bmp180I2cBus, bmp180I2cAddress);
        this.initSensor();
        System.out.println(" i2c instance " + this.i2c);
    }



    /** {@inheritDoc} */
    @Override
    public I2C create(I2CConfig config) {
        return new BMP280(this, config);
    }



}
