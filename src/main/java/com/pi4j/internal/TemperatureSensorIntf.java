package com.pi4j.internal;

import com.pi4j.io.i2c.I2C;

public interface TemperatureSensorIntf {
    float temperatureC();

   void initSensor();
}
