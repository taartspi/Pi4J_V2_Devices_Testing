package com.pi4j.test.devices.dualDevice;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.exception.LifecycleException;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.util.Console;
import sun.misc.Signal;
import sun.misc.SignalHandler;

public class DualDevice {

    Context pi4j;
    Console con;

    public DualDevice() {
    }

    public DualDevice(Context pi4j, Console con) {
        this.pi4j = pi4j;
        this.con = con;
    }

    public int doit(){
        int rval = 0;
        I2CProvider i2CProvider = pi4j.provider("linuxfs-i2c");

        I2CConfig i2cConfig20 = I2C.newConfigBuilder(pi4j).id("MCP23017_TWENTY").bus(1).device(0x20).build();
        I2C dev20 = i2CProvider.create(i2cConfig20);

        
        I2CConfig i2cConfig60 = I2C.newConfigBuilder(pi4j).id("MPL3115_SIXTY").bus(1).device(0x60).build();
        I2C dev60 = i2CProvider.create(i2cConfig60);


        I2CConfig i2cConfig70 = I2C.newConfigBuilder(pi4j).id("TCA9548_SEVENTY").bus(1).device(0x70).build();
        I2C dev70 = i2CProvider.create(i2cConfig70);
        
        int ret60;
        int ret70;
        int ret20;
        

      //  var x = dev20.readByte();

        ret20 = dev20.readRegister(0x01);
        con.println("MCP23017_ret20  properly read from 0x01  expect 0XFF (255) " + ret20);
        
        
        ret60 = dev60.readRegister(0x0C);
        con.println("MPL3115_ret60  properly read from 0x0c  expect 0XC4 (196) " + ret60);


        ret20 = dev20.readRegister(0x02);
        con.println("MCP23017_ret20  properly read from 0x02  expect 0X00 (00) " + ret20);

        
        
       

        ret70 = dev70.readRegister(0x0C);
        con.println("TCA9548_ret70  Read from 0x0c as a test " + ret70);

        ret70 = dev70.readByte();
        con.println("TCA9548_ret70  expect 12 " + ret70);


        ret70 = dev70.readByte();
        con.println("TCA9548_ret70  " + ret70);



        ret60 = dev60.readRegister(0x0C);
        con.println("MPL3115_ret60  " + ret60);

        ret70 = dev70.readByte();
        con.println("TCA9548_ret70  " + ret70);


        return(rval);
    }
    public static void main(String[] args) throws Exception {

        var console = new Console();
        Context pi4j = Pi4J.newAutoContext();
        console.title("<-- The Pi4J V2 Project Extension  -->", "DualDevice -- access");

        Signal.handle(new Signal("INT"), new SignalHandler() {
            public void handle(Signal sig) {
                System.out.println("Performing ctl-C shutdown");
                try {
                    pi4j.shutdown();
                } catch (LifecycleException e) {
                    e.printStackTrace();
                }
                //Thread.dumpStack();
                System.exit(2);
            }
        });

        console.println("PI4J PROVIDERS");
        console.println("----------------------------------------------------------");
        pi4j.providers().describe().print(System.out);
        System.out.println("----------------------------------------------------------");


        DualDevice dd = new DualDevice(pi4j, console);
        dd.doit();
    }

}
