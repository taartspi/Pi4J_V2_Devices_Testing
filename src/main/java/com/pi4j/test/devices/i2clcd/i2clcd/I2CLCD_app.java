package com.pi4j.test.devices.i2clcd.i2clcd;


import com.pi4j.Pi4J;
import com.pi4j.io.i2c.I2C;

import com.pi4j.plugin.linuxfs.provider.i2c.LinuxFsI2CProvider;
import com.pi4j.util.Console;

public class I2CLCD_app {


        /**
         * @param args the command line arguments
         */
        public static void main(String[] args) {
            I2C _device = null;
            I2CLCD _lcd = null;
            final Console console = new Console();
            console.print("==============================================================");
            console.print("startup  I2CLCD_app ");
            console.print("==============================================================");



            var pi4j = Pi4J.newContextBuilder().add(
                    LinuxFsI2CProvider.newInstance()).build();

           var i2cDeviceConfig = I2C.newConfigBuilder(pi4j)
                    .bus(1)
                    .device(0x27)
                    .id("LCD_I2C")
                    .name("LCD_I2C")
                    .provider("linuxfs-i2c")
                    .build();

            _device = pi4j.create(i2cDeviceConfig);
            _lcd = new I2CLCD(_device);
            _lcd.init();
            _lcd.backlight(true);
            _lcd.display_string_pos("Hello, world!", 1, 2);


            console.println("Test completed");
        }

    }
