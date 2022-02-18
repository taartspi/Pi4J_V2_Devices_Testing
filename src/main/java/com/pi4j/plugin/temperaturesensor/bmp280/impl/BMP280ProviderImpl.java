package com.pi4j.plugin.temperaturesensor.bmp280.impl;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.sensor.Sensor;
import com.pi4j.io.sensor.SensorConfig;
import com.pi4j.io.sensor.SensorProvider;
import com.pi4j.plugin.temperaturesensor.bmp280.BMP280Device;
import com.pi4j.plugin.temperaturesensor.bmp280.BMP280Provider;

import com.pi4j.io.sensor.SensorProviderBase;


public class BMP280ProviderImpl extends SensorProviderBase implements BMP280Provider  {



BMP280Device bmp280Device = null;

        public BMP280ProviderImpl(){
            this.id = BMP280Device.I2C_PROVIDER_ID;
            this.name = BMP280Device.I2C_PROVIDER_NAME;
        }

    @Override
    public BMP280Device setup(Context pi4j, BMP280Provider provider, int bmp280I2cBus, int bmp280I2cAddress) {
         BMP280Device rval = null;
        var deviceConfig = Sensor.newConfigBuilder(pi4j)
                .id(id+" "+name)
                .name(name)
                .address(bmp280I2cAddress)
                .provider("pigpio-i2c")
                .build();
        this.bmp280Device = new BMP280Device(pi4j,deviceConfig);
 //       System.out.println(" i2c instance " + this.bmp280Device.i2cDetail());
         return(this.bmp280Device);
    }

    @Override
    public Sensor create(SensorConfig config) {
        var bmp280Device = new BMP280Device(config);
        return (bmp280Device);
    }



}
