package com.pi4j.plugin.temperaturesensor.bmp280;


import com.pi4j.common.Metadata;
import com.pi4j.context.Context;
import com.pi4j.exception.InitializeException;
import com.pi4j.exception.ShutdownException;
import com.pi4j.internal.TemperatureSensorIntf;
import com.pi4j.io.IO;
import com.pi4j.io.i2c.*;
import com.pi4j.io.sensor.Sensor;
import com.pi4j.io.sensor.SensorConfig;
import com.pi4j.io.sensor.SensorProvider;
import com.pi4j.plugin.temperaturesensor.bmp280.impl.BMP280ProviderImpl;

/* For I2C operation CSB pin must be connected to 3.3 V.

 */

public class BMP280Device implements TemperatureSensorIntf {

    public static final int DEFAULT_ADDRESS = 0x76;

    public static final int DEFAULT_BUS = 0x1;



    /**
     * Constant <code>NAME="Mock"</code>
     */
    public static final String NAME = "BMP280";
    /**
     * Constant <code>ID="mock"</code>
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


    private static int  t1 = 0;
    private static int  t2 = 1;
    private static int  t3 = 2;
    private static int  p1 = 3;
    private static int  p2 = 4;
    private static int  p3 = 5;
    private static int  p4 = 6;
    private static int  p5 = 7;
    private static int  p6 = 8;
    private static int  p7 = 9;
    private static int  p8 = 10;
    private static int  p9 = 11;


    // local/internal I2C reference for communication with hardware chip
    protected I2C i2c = null;

    protected SensorConfig config = null;

    protected SensorProvider provider = null;

    protected Context pi4j = null;


    public BMP280Device(I2C device) {
        this.i2c = device;

    }

    public void setI2c(I2C device){
        this.i2c = device;
    }

    public I2C getI2c(){
        return(this.i2c);
    }

    public BMP280Device(SensorConfig config) {
        super();
        this.config = config;
   }

    public BMP280Device(Context pi4j, SensorConfig deviceConfig) {
        super();
        this.config = deviceConfig;
        this.pi4j = pi4j;
        this.createI2cDevice();

    }

    private void createI2cDevice(){

        var address = this.config.address();
        var bus = this.config.bus();

        String id = String.format("0X%02x: ", bus);
        String name = String.format("0X%02x: ", address);
        var i2cDeviceConfig = I2C.newConfigBuilder(this.pi4j)
                .bus(bus)
                .device(address)
                .id(id+" "+name)
                .name(name)
                .provider("linuxfs-i2c")
                .build();
        this.i2c = pi4j.create(i2cDeviceConfig);
    }

    public String i2cDetail() {
        return (this.i2c.toString());
    }


    private int castOffSignByte(byte read){
        return ((int)read & 0Xff);
    }

    private int signedInt(byte[] read){
        int temp = 0;
        temp = (read[0] & 0xff);
        temp += (((long)read[1] ) << 8);
        return (temp);
    }

    private long castOffSignInt(byte[] read){
        long temp = 0;
            temp = ((long)read[0] & 0xff);
            temp += (((long)read[1] & 0xff)) << 8;
        return (temp);
    }



    /**
     * Read registers 0xf7 - 0xFC in single read to ensure all teh data pertains to
     *  a single  measurement.
     * @return
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
        long dig_t1     =   castOffSignInt(compVal);

         this.i2c.readRegister(BMP280Provider.reg_dig_t2, compVal);
         int dig_t2     = signedInt(compVal);

         this.i2c.readRegister(BMP280Provider.reg_dig_t3, compVal);
        int dig_t3     = signedInt(compVal);

         this.i2c.readRegister(BMP280Provider.reg_dig_p1, compVal);
         long dig_p1     =  castOffSignInt(compVal);

         this.i2c.readRegister(BMP280Provider.reg_dig_p2, compVal);
        int dig_p2     = signedInt(compVal);

         this.i2c.readRegister(BMP280Provider.reg_dig_p3, compVal);
        int dig_p3     = signedInt(compVal);

         this.i2c.readRegister(BMP280Provider.reg_dig_p4, compVal);
         int dig_p4     = signedInt(compVal);

         this.i2c.readRegister(BMP280Provider.reg_dig_p5, compVal);
         int dig_p5     = signedInt(compVal);

         this.i2c.readRegister(BMP280Provider.reg_dig_p6, compVal);
         int dig_p6     = signedInt(compVal);

         this.i2c.readRegister(BMP280Provider.reg_dig_p7, compVal);
         int dig_p7     = signedInt(compVal);

         this.i2c.readRegister(BMP280Provider.reg_dig_p8, compVal);
         int dig_p8     = signedInt(compVal);

         this.i2c.readRegister(BMP280Provider.reg_dig_p9, compVal);
         int dig_p9     = signedInt(compVal);




        byte[] buff = new byte[6];
        this.i2c.readRegister(BMP280Provider.press_msb, buff);


        int p_msb  = castOffSignByte(buff[0]);
        int p_lsb  = castOffSignByte(buff[1]);
        int p_xlsb = castOffSignByte(buff[2]);

        int t_msb  = castOffSignByte(buff[3]);
        int t_lsb  = castOffSignByte(buff[4]);
        int t_xlsb = castOffSignByte(buff[5]);


        long adc_T = (long)((buff[3] & 0xFF) << 12) + (long)((buff[4] & 0xFF) << 4) + (long)(buff[5] & 0xFF);

         long adc_P = (long)((buff[0] & 0xFF) << 12) + (long)((buff[1] & 0xFF) << 4) + (long)(buff[2] & 0xFF);

        // Temperature
         int t_fine;
         double var1, var2, T, P;
         var1 = (((double)adc_T)/16384.0 - ((double)dig_t1)/1024.0) * ((double)dig_t2);
         var2 = ((((double)adc_T)/131072.0 - ((double)dig_t1)/8192.0) *
         (((double)adc_T)/131072.0 - ((double) dig_t1)/8192.0)) * ((double)dig_t3);
         t_fine = (int)(var1 + var2);
         T = (var1 + var2) / 5120.0;


        rval[0] = T;

        // Pressure
         var1 = ((double)t_fine/2.0) - 64000.0;
         var2 = var1 * var1 * ((double)dig_p6) / 32768.0;
         var2 = var2 + var1 * ((double)dig_p5) * 2.0;
         var2 = (var2/4.0)+(((double)dig_p4) * 65536.0);
         var1 = (((double)dig_p3) * var1 * var1 / 524288.0 + ((double)dig_p2) * var1) / 524288.0;
         var1 = (1.0 + var1 / 32768.0)*((double)dig_p1);
         if (var1 == 0.0)
         {
             P = 0;   // // avoid exception caused by division by zero
         }else {
             P = 1048576.0 -(double) adc_P;
             P = (P -(var2 / 4096.0)) *6250.0 / var1;
             var1 = ((double) dig_p9) * P * P / 2147483648.0;
             var2 = P * ((double) dig_p8) / 32768.0;
             P = P + (var1 + var2 + ((double) dig_p7)) / 16.0;
         }
         rval[1] = P;
        return rval;   // “5123” equals 51.23 DegC.
    }


    @Override
    public double temperatureC() {
        double[] rval = this.readBMP280();
        return rval[0];
    }
        @Override
    public double temperatureF() {
            double  fTemp = this.temperatureC() * 1.8 + 32;
            return fTemp;
    }

    @Override
    public double pressurePa() {
        double[] rval = this.readBMP280();
        return rval[1];
    }

    @Override
    // MilliBars
    public double pressureMb() {
        double[] rval = this.readBMP280();
        double mbar =  rval[1]/100;
        return(mbar);
    }
    @Override
    //inches of mercury
     public double pressureIn() {
        double[] rval = this.readBMP280();
        double inches =  (rval[1]/3386);
        return(inches);
    }


    public void initSensor() {
        this.i2c.writeRegister(BMP280Provider.reset, BMP280Provider.reset_cmd);

        // Next delay for 100 ms to provide chip time to perform reset
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int ctlReg = this.i2c.readRegister(BMP280Provider.ctrl_meas);
        ctlReg |= BMP280Provider.presOverSampleMsk;
        this.i2c.writeRegister(BMP280Provider.ctrl_meas, ctlReg);

        // read 0xD0 validate data equal 0x58
        int id = this.i2c.readRegister(BMP280Provider.chipId);
        if(id != BMP280Provider.idValueMsk){
            ; // log error conditi0n
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
