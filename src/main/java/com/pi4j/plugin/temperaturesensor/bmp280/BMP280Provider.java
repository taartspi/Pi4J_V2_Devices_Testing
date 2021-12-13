package com.pi4j.plugin.temperaturesensor.bmp280;


import com.pi4j.context.Context;
import com.pi4j.internal.TemperatureSensorIntf;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.plugin.temperaturesensor.bmp280.impl.BMP280ProviderImpl;


public interface BMP280Provider extends I2CProvider, TemperatureSensorIntf {

      /** Constant <code>NAME="BMP.I2C_PROVIDER_NAME"</code> */
    String NAME = BMP280.I2C_PROVIDER_NAME;
    /** Constant <code>ID="BMP.I2C_PROVIDER_ID"</code> */
    String ID = BMP280.I2C_PROVIDER_ID;

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
    static final int  chipId         = 0xD0;


    // register contents
    static final int  reset_cmd  = 0xB6;  // written to reset

    // Pertaining to 0xF3 status register
    static final int  stat_measure = 0x08;  // set, conversion running
    static final int  stat_update  = 0x01;  // set, NVM being copied

    // Pertaining to 0xF4 ctrl_meas register
    static final int  tempOverSample = 0xE0;  // mask bits 5,6,7
    static final int  presOverSample = 0x1C;  // mask bits 2,3,4
    static final int  pwrMode        = 0x03;  // mask bits 0,1


    // Pertaining to 0xF5 config register
    static final int  inactDuration  = 0xE0;  // mask bits 5,6,7
    static final int  iirFlt         = 0x1C;  // mask bits 2,3,4
    static final int  enableSpi      = 0x01;  // mask bits 0

    // Pertaining to 0xF7 0xF8 0xF9 press  register
    static final int  pressMsb       = 0xFF;  // mask bits 0 - 7
    static final int  pressLsb       = 0xFF;  // mask bits 0 - 7
    static final int  pressXlsb      = 0x0F;  // mask bits 0 - 3

    // Pertaining to 0xFA 0xFB 0xFC temp  register
    static final int  tempMsb       = 0xFF;  // mask bits 0 - 7
    static final int  tempLsb       = 0xFF;  // mask bits 0 - 7
    static final int  tempXlsb      = 0x0F;  // mask bits 0 - 3


    /**
     * <p>newInstance.</p>
     *
     * @return a {@link   BMP280ProviderImpl} object.
     */
    static BMP280Provider newInstance() {
        return new BMP280ProviderImpl();
    }


    public float temperatureC();

    void setup(Context pi4j, int bmp180I2cBus, int bmp180I2cAddress);



}


