package com.pi4j.plugin.temperaturesensor.bmp280;


import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.io.sensor.SensorConfig;
import com.pi4j.io.sensor.SensorProvider;
import com.pi4j.plugin.temperaturesensor.bmp280.impl.BMP280ProviderImpl;


public interface BMP280Provider extends SensorProvider {

      /** Constant <code>NAME="BMP.I2C_PROVIDER_NAME"</code> */
    String NAME = BMP280Device.I2C_PROVIDER_NAME;
    /** Constant <code>ID="BMP.I2C_PROVIDER_ID"</code> */
    String ID = BMP280Device.I2C_PROVIDER_ID;

    /*  Begin device register definitions.        */
    static final int  temp_xlsb  = 0xFC;
    static final int  temp_lsb   = 0xFB;
    static final int  temp_msb   = 0xFA;
    static final int  press_xlsb = 0xF9;
    static final int  press_lsb  = 0xF8;
    static final int  press_msb  = 0xF7;
    static final int  config     = 0xF5;
    static final int  ctrl_meas  = 0xF4;
    static final int  status     = 0xF3;
    static final int  reset      = 0xE0;
    static final int  chipId     = 0xD0;


    // errata register definitions
    static final int reg_dig_t1     = 0x88;
    static final int reg_dig_t2     = 0x8A;
    static final int reg_dig_t3     = 0x8C;

    static final int reg_dig_p1     = 0x8E;
    static final int reg_dig_p2     = 0x90;
    static final int reg_dig_p3     = 0x92;
    static final int reg_dig_p4     = 0x94;
    static final int reg_dig_p5     = 0x96;
    static final int reg_dig_p6     = 0x98;
    static final int reg_dig_p7     = 0x9A;
    static final int reg_dig_p8     = 0x9C;
    static final int reg_dig_p9     = 0x9E;

    // register contents
    static final int  reset_cmd  = 0xB6;  // written to reset

    // Pertaining to 0xF3 status register
    static final int  stat_measure = 0x08;  // set, conversion running
    static final int  stat_update  = 0x01;  // set, NVM being copied

    // Pertaining to 0xF4 ctrl_meas register
    static final int  tempOverSampleMsk = 0xE0;  // mask bits 5,6,7
    static final int  presOverSampleMsk = 0x1C;  // mask bits 2,3,4
    static final int  pwrModeMsk        = 0x03;  // mask bits 0,1


    // Pertaining to 0xF5 config register
    static final int  inactDurationMsk  = 0xE0;  // mask bits 5,6,7
    static final int  iirFltMsk         = 0x1C;  // mask bits 2,3,4
    static final int  enableSpiMsk      = 0x01;  // mask bits 0

    // Pertaining to 0xF7 0xF8 0xF9 press  register
    static final int  pressMsbMsk       = 0xFF;  // mask bits 0 - 7
    static final int  pressLsbMsk       = 0xFF;  // mask bits 0 - 7
    static final int  pressXlsbMsk      = 0x0F;  // mask bits 0 - 3

    // Pertaining to 0xFA 0xFB 0xFC temp  register
    static final int  tempMsbMsk       = 0xFF;  // mask bits 0 - 7
    static final int  tempLsbMsk        = 0xFF;  // mask bits 0 - 7
    static final int  tempXlsbMsk      = 0x0F;  // mask bits 0 - 3
    static final int  idValueMsk       = 0x58;   // expected chpId value

    // For the control reg 0xf4
    static final int ctl_forced         = 0x01;
    static final int ctl_tempSamp1      = 0x20;   // oversample *1
    static final int ctl_pressSamp1     = 0x04;   // oversample *1


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


