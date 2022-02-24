package com.pi4j.plugin.temperaturesensor.bmp280;

/*
 *
 *
 *  #%L
 *  **********************************************************************
 *  ORGANIZATION  :  Pi4J
 *  PROJECT       :  Pi4J ::  Providers
 *  FILENAME      :  BMP280Device.java
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


import com.pi4j.common.Metadata;
import com.pi4j.context.Context;
import com.pi4j.exception.InitializeException;
import com.pi4j.exception.ShutdownException;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.sensor.Sensor;
import com.pi4j.io.sensor.SensorConfig;
import com.pi4j.io.sensor.SensorProvider;





/* For I2C operation CSB pin must be connected to 3.3 V.

 */

/**
 * Implementation os Sensor interface, using I2C communication.
 * Note:  For I2C operation CSB pin must be connected to 3.3 V.
 */
public class BMP280Device implements TemperatureSensorIntf {

    public static final int DEFAULT_ADDRESS = 0x76;

    public static final int DEFAULT_BUS = 0x1;


    /**
     * Constant <code>NAME="BMP280"</code>
     */
    public static final String NAME = "BMP280";
    /**
     * Constant <code>ID="BMP280"</code>
     */
    public static final String ID = "BMP280";


    // I2C Provider name and unique ID
    /**
     * Constant <code>I2C_PROVIDER_NAME="NAME +  I2C Provider"</code>
     */
    public static final String I2C_PROVIDER_NAME = NAME + " BMP280 I2C Provider";
    /**
     * Constant <code>I2C_PROVIDER_ID="ID + -i2c"</code>
     */
    public static final String I2C_PROVIDER_ID = ID + "-i2c";


    private static final int t1 = 0;
    private static final int t2 = 1;
    private static final int t3 = 2;
    private static final int p1 = 3;
    private static final int p2 = 4;
    private static final int p3 = 5;
    private static final int p4 = 6;
    private static final int p5 = 7;
    private static final int p6 = 8;
    private static final int p7 = 9;
    private static final int p8 = 10;
    private static final int p9 = 11;


    // local/internal I2C reference for communication with hardware chip
    protected I2C i2c = null;

    protected SensorConfig config = null;

    protected SensorProvider provider = null;

    protected Context pi4j = null;


    /**
     * @param device  Instantiated I2C device
     */
    public BMP280Device(I2C device) {
        super();
        this.i2c = device;

    }

    /**
     * @param device  Set i2c state
     */
    public void setI2c(I2C device) {
        this.i2c = device;
    }

    /**
     * @return i2c state
     */
    public I2C getI2c() {
        return (this.i2c);
    }


    /**
     * @param config Sensor config.
     */
    public BMP280Device(SensorConfig config) {
        super();
        this.config = config;
    }

    /**
     * @param pi4j Context instance
     * @param deviceConfig Sensor config
     */
    public BMP280Device(Context pi4j, SensorConfig deviceConfig) {
        super();
        this.config = deviceConfig;
        this.pi4j = pi4j;
        this.createI2cDevice();

    }

    /**
     * Use the state from the Sensor config object and the state pi4j to create
     * a BMP280 device instance
     */
    private void createI2cDevice() {

        var address = this.config.address();
        var bus = this.config.bus();

        String id = String.format("0X%02x: ", bus);
        String name = String.format("0X%02x: ", address);
        var i2cDeviceConfig = I2C.newConfigBuilder(this.pi4j)
                .bus(bus)
                .device(address)
                .id(id + " " + name)
                .name(name)
                .provider("linuxfs-i2c")
                .build();
        this.i2c = this.pi4j.create(i2cDeviceConfig);
    }


    /**
     * @return string containing a desription of the attached I2C path
     */
    public String i2cDetail() {
        return (this.i2c.toString() + " bus : " + this.config.bus() + "  address : " + this.config.address());
    }


    /**
     * @param read 8 bits data
     * @return  unsigned value
     */
    private int castOffSignByte(byte read) {
        return ((int) read & 0Xff);
    }

    /**
     * @param read 16 bits of data  stored in 8 bit array
     * @return 16 bit signed
     */
    private int signedInt(byte[] read) {
        int temp = 0;
        temp = (read[0] & 0xff);
        temp += (((long) read[1]) << 8);
        return (temp);
    }

    /**
     * @param read  16 bits of data  stored in 8 bit array
     * @return 64 bit unsigned value
     */
    private long castOffSignInt(byte[] read) {
        long temp = 0;
        temp = ((long) read[0] & 0xff);
        temp += (((long) read[1] & 0xff)) << 8;
        return (temp);
    }


    /**
     *
     * Reset BMP280 chip to remove any previous applications configuration details.
     *
     * Configure BMP280 for 1x oversamplimg and single measurement.
     *
     * Read and store all factory set conversion data.
     * Read measure registers 0xf7 - 0xFC in single read to ensure all the data pertains to
     * a single  measurement.
     *
     * Use conversion data and measure data to calculate temperature in C and pressure in Pa.
     *
     * @return   double[2],  temperature in C and pressure in Pa
     */
    public double[] readBMP280() {
        double[] rval = new double[2];
        // set forced mode to leave sleep ode state and initiate measurements.
        // At measurement completion chip returns to sleep mode
        int ctlReg = this.i2c.readRegister(BMP280Provider.ctrl_meas);
        ctlReg |= BMP280Provider.ctl_forced;
        ctlReg &= ~BMP280Provider.tempOverSampleMsk;   // mask off all temperauire bits
        ctlReg |= BMP280Provider.ctl_tempSamp1;      // Temperature oversample 1
        ctlReg &= ~BMP280Provider.presOverSampleMsk;   // mask off all pressure bits
        ctlReg |= BMP280Provider.ctl_pressSamp1;   //  Pressure oversample 1

        this.i2c.writeRegister(BMP280Provider.ctrl_meas, ctlReg);


        // Next delay for 100 ms to provide chip time to perform measurements
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // read the temp factory errata

        byte[] compVal = new byte[2];

        this.i2c.readRegister(BMP280Provider.reg_dig_t1, compVal);
        long dig_t1 = castOffSignInt(compVal);

        this.i2c.readRegister(BMP280Provider.reg_dig_t2, compVal);
        int dig_t2 = signedInt(compVal);

        this.i2c.readRegister(BMP280Provider.reg_dig_t3, compVal);
        int dig_t3 = signedInt(compVal);

        this.i2c.readRegister(BMP280Provider.reg_dig_p1, compVal);
        long dig_p1 = castOffSignInt(compVal);

        this.i2c.readRegister(BMP280Provider.reg_dig_p2, compVal);
        int dig_p2 = signedInt(compVal);

        this.i2c.readRegister(BMP280Provider.reg_dig_p3, compVal);
        int dig_p3 = signedInt(compVal);

        this.i2c.readRegister(BMP280Provider.reg_dig_p4, compVal);
        int dig_p4 = signedInt(compVal);

        this.i2c.readRegister(BMP280Provider.reg_dig_p5, compVal);
        int dig_p5 = signedInt(compVal);

        this.i2c.readRegister(BMP280Provider.reg_dig_p6, compVal);
        int dig_p6 = signedInt(compVal);

        this.i2c.readRegister(BMP280Provider.reg_dig_p7, compVal);
        int dig_p7 = signedInt(compVal);

        this.i2c.readRegister(BMP280Provider.reg_dig_p8, compVal);
        int dig_p8 = signedInt(compVal);

        this.i2c.readRegister(BMP280Provider.reg_dig_p9, compVal);
        int dig_p9 = signedInt(compVal);


        byte[] buff = new byte[6];
        this.i2c.readRegister(BMP280Provider.press_msb, buff);


        int p_msb = castOffSignByte(buff[0]);
        int p_lsb = castOffSignByte(buff[1]);
        int p_xlsb = castOffSignByte(buff[2]);

        int t_msb = castOffSignByte(buff[3]);
        int t_lsb = castOffSignByte(buff[4]);
        int t_xlsb = castOffSignByte(buff[5]);


        long adc_T = (long) ((buff[3] & 0xFF) << 12) + (long) ((buff[4] & 0xFF) << 4) + (long) (buff[5] & 0xFF);

        long adc_P = (long) ((buff[0] & 0xFF) << 12) + (long) ((buff[1] & 0xFF) << 4) + (long) (buff[2] & 0xFF);

        // Temperature
        int t_fine;
        double var1, var2, T, P;
        var1 = (((double) adc_T) / 16384.0 - ((double) dig_t1) / 1024.0) * ((double) dig_t2);
        var2 = ((((double) adc_T) / 131072.0 - ((double) dig_t1) / 8192.0) *
                (((double) adc_T) / 131072.0 - ((double) dig_t1) / 8192.0)) * ((double) dig_t3);
        t_fine = (int) (var1 + var2);
        T = (var1 + var2) / 5120.0;


        rval[0] = T;

        // Pressure
        var1 = ((double) t_fine / 2.0) - 64000.0;
        var2 = var1 * var1 * ((double) dig_p6) / 32768.0;
        var2 = var2 + var1 * ((double) dig_p5) * 2.0;
        var2 = (var2 / 4.0) + (((double) dig_p4) * 65536.0);
        var1 = (((double) dig_p3) * var1 * var1 / 524288.0 + ((double) dig_p2) * var1) / 524288.0;
        var1 = (1.0 + var1 / 32768.0) * ((double) dig_p1);
        if (var1 == 0.0) {
            P = 0;   // // avoid exception caused by division by zero
        } else {
            P = 1048576.0 - (double) adc_P;
            P = (P - (var2 / 4096.0)) * 6250.0 / var1;
            var1 = ((double) dig_p9) * P * P / 2147483648.0;
            var2 = P * ((double) dig_p8) / 32768.0;
            P = P + (var1 + var2 + ((double) dig_p7)) / 16.0;
        }
        rval[1] = P;
        return rval;   // “5123” equals 51.23 DegC.
    }


    /**
     * @return Temperature centigrade
     */
    @Override
    public double temperatureC() {
        double[] rval = this.readBMP280();
        return rval[0];
    }

    /**
     * @return Temperature fahrenheit
     */ @Override
    public double temperatureF() {
        double fTemp = this.temperatureC() * 1.8 + 32;
        return fTemp;
    }

    /**
     * @return Pressure in Pa units
     */
    @Override
    public double pressurePa() {
        double[] rval = this.readBMP280();
        return rval[1];
    }

    /**
     * @return Pressure in millBar
     */
    @Override
    // MilliBars
    public double pressureMb() {
        double[] rval = this.readBMP280();
        double mbar = rval[1] / 100;
        return (mbar);
    }

    /**
     * @return Pressure in inches mercury
     */
    @Override
    //inches of mercury
    public double pressureIn() {
        double[] rval = this.readBMP280();
        double inches = (rval[1] / 3386);
        return (inches);
    }

    /**
     * Write the reset command to the BMP280, Sleep 100 ms
     * to allow the chip to complete the reset
     */
    @Override
    public void resetSensor() {
        this.i2c.writeRegister(BMP280Provider.reset, BMP280Provider.reset_cmd);

        // Next delay for 100 ms to provide chip time to perform reset
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Reset the BMP280, validate the chips ID.
     */
    @Override
    public void initSensor() {

        this.resetSensor();
        // read 0xD0 validate data equal 0x58
        int id = this.i2c.readRegister(BMP280Provider.chipId);
        if (id != BMP280Provider.idValueMsk) {
            System.out.println("In correct chip ID read");
            System.exit(42);
        }
    }


    @Override
    public SensorConfig config() {
        return this.config;
    }

    @Override
    public Sensor name(String s) {
        return this;
    }

    @Override
    public Sensor description(String s) {
        return this;
    }

    @Override
    public SensorProvider provider() {
        return this.provider;
    }


    @Override
    public String id() {
        return this.config.id();
    }

    @Override
    public String name() {
        return this.config.name();
    }

    @Override
    public String description() {
        return this.config.description();
    }

    @Override
    public Metadata metadata() {
        return null;
    }

    @Override
    public Object initialize(Context context) throws InitializeException {
        this.pi4j = context;
        this.createI2cDevice();
        return this;
    }

    @Override
    public Object shutdown(Context context) throws ShutdownException {
        // TODO  close bus
        return this;
    }


}
