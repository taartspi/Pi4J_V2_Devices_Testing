package com.pi4j.plugin.temperaturesensor.bmp280;


/*
 *
 *
 *  #%L
 *  **********************************************************************
 *  ORGANIZATION  :  Pi4J
 *  PROJECT       :  Pi4J ::  Providers
 *  FILENAME      :  BMP280Provider.java
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
import com.pi4j.io.sensor.SensorProvider;
import com.pi4j.plugin.temperaturesensor.bmp280.impl.BMP280ProviderImpl;


public interface BMP280Provider extends SensorProvider {

    /**
     * Constant <code>NAME="BMP.I2C_PROVIDER_NAME"</code>
     */
    String NAME = BMP280Device.I2C_PROVIDER_NAME;
    /**
     * Constant <code>ID="BMP.I2C_PROVIDER_ID"</code>
     */
    String ID = BMP280Device.I2C_PROVIDER_ID;

    /*  Begin device register definitions.        */
    int temp_xlsb = 0xFC;
    int temp_lsb = 0xFB;
    int temp_msb = 0xFA;
    int press_xlsb = 0xF9;
    int press_lsb = 0xF8;
    int press_msb = 0xF7;
    int config = 0xF5;
    int ctrl_meas = 0xF4;
    int status = 0xF3;
    int reset = 0xE0;
    int chipId = 0xD0;


    // errata register definitions
    int reg_dig_t1 = 0x88;
    int reg_dig_t2 = 0x8A;
    int reg_dig_t3 = 0x8C;

    int reg_dig_p1 = 0x8E;
    int reg_dig_p2 = 0x90;
    int reg_dig_p3 = 0x92;
    int reg_dig_p4 = 0x94;
    int reg_dig_p5 = 0x96;
    int reg_dig_p6 = 0x98;
    int reg_dig_p7 = 0x9A;
    int reg_dig_p8 = 0x9C;
    int reg_dig_p9 = 0x9E;

    // register contents
    int reset_cmd = 0xB6;  // written to reset

    // Pertaining to 0xF3 status register
    int stat_measure = 0x08;  // set, conversion running
    int stat_update = 0x01;  // set, NVM being copied

    // Pertaining to 0xF4 ctrl_meas register
    int tempOverSampleMsk = 0xE0;  // mask bits 5,6,7
    int presOverSampleMsk = 0x1C;  // mask bits 2,3,4
    int pwrModeMsk = 0x03;  // mask bits 0,1


    // Pertaining to 0xF5 config register
    int inactDurationMsk = 0xE0;  // mask bits 5,6,7
    int iirFltMsk = 0x1C;  // mask bits 2,3,4
    int enableSpiMsk = 0x01;  // mask bits 0

    // Pertaining to 0xF7 0xF8 0xF9 press  register
    int pressMsbMsk = 0xFF;  // mask bits 0 - 7
    int pressLsbMsk = 0xFF;  // mask bits 0 - 7
    int pressXlsbMsk = 0x0F;  // mask bits 0 - 3

    // Pertaining to 0xFA 0xFB 0xFC temp  register
    int tempMsbMsk = 0xFF;  // mask bits 0 - 7
    int tempLsbMsk = 0xFF;  // mask bits 0 - 7
    int tempXlsbMsk = 0x0F;  // mask bits 0 - 3
    int idValueMsk = 0x58;   // expected chpId value

    // For the control reg 0xf4
    int ctl_forced = 0x01;
    int ctl_tempSamp1 = 0x20;   // oversample *1
    int ctl_pressSamp1 = 0x04;   // oversample *1


    /**
     * @param pi4j  Context
     * @param provider Proider of this clas
     * @param bmp280I2cBus      BMP280 connected to this Pi bus
     * @param bmp280I2cAddress     BMP280 usinng this I2C device address
     * @return   instantiated BMP280 device
     */
    BMP280Device setup(Context pi4j, BMP280Provider provider, int bmp280I2cBus, int bmp280I2cAddress);


    /**
     * <p>newInstance.</p>
     *
     * @return a {@link   BMP280ProviderImpl} object.
     */
    static BMP280Provider newInstance() {
        return new BMP280ProviderImpl();
    }


}


