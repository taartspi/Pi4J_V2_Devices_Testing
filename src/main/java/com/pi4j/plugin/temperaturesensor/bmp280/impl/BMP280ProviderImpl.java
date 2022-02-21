package com.pi4j.plugin.temperaturesensor.bmp280.impl;

/*
 *
 *
 *  #%L
 *  **********************************************************************
 *  ORGANIZATION  :  Pi4J
 *  PROJECT       :  Pi4J ::  Providers
 *  FILENAME      :  BMP280ProviderImpl.java
 *
 *  This file is part of the Pi4J project. More information about
 *  this project can be found here:  https://pi4j.com/
 *  **********************************************************************
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Lesser Public License for more details.
 *
 *  You should have received a copy of the GNU General Lesser Public
 *  License along with this program.  If not, see
 *  <http://www.gnu.org/licenses/lgpl-3.0.html>.
 *  #L%
 *
 */

import com.pi4j.context.Context;
import com.pi4j.io.sensor.Sensor;
import com.pi4j.io.sensor.SensorConfig;
import com.pi4j.io.sensor.SensorProviderBase;
import com.pi4j.plugin.temperaturesensor.bmp280.BMP280Device;
import com.pi4j.plugin.temperaturesensor.bmp280.BMP280Provider;


public class BMP280ProviderImpl extends SensorProviderBase implements BMP280Provider {

    /**
     * State for instantiate device
     */
    BMP280Device bmp280Device = null;

    public BMP280ProviderImpl() {
        this.id = BMP280Device.I2C_PROVIDER_ID;
        this.name = BMP280Device.I2C_PROVIDER_NAME;
    }

    /**
     * @param pi4j Context
     * @param provider BMP280Provider
     * @param bmp280I2cBus  Int value of Pi bus used
     * @param bmp280I2cAddress   Int value of devices I2C address
     * @return Instantiated BMP280 device
     */
    @Override
    public BMP280Device setup(Context pi4j, BMP280Provider provider, int bmp280I2cBus, int bmp280I2cAddress) {
       var deviceConfig = Sensor.newConfigBuilder(pi4j)
                .id(id + " " + name)
                .name(name)
                .address(bmp280I2cAddress)
                .provider("pigpio-i2c")
                .build();
        this.bmp280Device = new BMP280Device(pi4j, deviceConfig);
        //       System.out.println(" i2c instance " + this.bmp280Device.i2cDetail());
        return (this.bmp280Device);
    }

    /**
     * @param config Pertient state for device instantiation
     * @return  Instantiated BMP280 device
     */
    @Override
    public Sensor create(SensorConfig config) {
        var bmp280Device = new BMP280Device(config);
        return (bmp280Device);
    }


}
