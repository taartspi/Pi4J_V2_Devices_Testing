package com.pi4j.test.devices.dht22;


import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalInputConfig;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;

    public class DHT22
    {
        public double temperature, humidity;

        public DHT22()
        {
        }

        public void update()
        {
            double [] res;
            for (int i=0;i<40;i++)
                if ((res = read()) != null)
                {
                    temperature = res[0];
                    humidity = res[1];
                    System.out.println("RH : " + humidity + "  T : " + ((temperature* 1.8) + 32));
                    return;
                }
                else try {Thread.sleep(10);} catch (Exception e) {}
        }

        private static Context pi4j = null;
        private void init()
        {
            if (pi4j == null)
                pi4j = Pi4J.newAutoContext();

            cfgOut = DigitalOutput.newConfigBuilder(pi4j)
                    .address(22)
                    .initial(DigitalState.HIGH)
                    .shutdown(DigitalState.HIGH)
                    .provider("pigpio-digital-output")
                    .build();
            cfgIn = DigitalInput.newConfigBuilder(pi4j)
                    .address(22)
                    .pull(PullResistance.OFF)
                    .provider("pigpio-digital-input")
                    .build();
        }
        private DigitalInputConfig cfgIn = null;
        private DigitalOutputConfig cfgOut = null;
        private DigitalOutput out = null;
        private DigitalInput in = null;
        private double [] read()
        {
            if (cfgIn == null)
                init();

            if (out == null)
                out = pi4j.create(cfgOut);
            else out.initialize(pi4j);
            out.state(DigitalState.LOW);
            long now = System.nanoTime();
            while (System.nanoTime()-now < 2000000);
            out.state(DigitalState.HIGH);
            out.shutdown(pi4j);

            if (in == null)
                in = pi4j.create(cfgIn);
            else in.initialize(pi4j);

            now = System.nanoTime();
            DigitalState state = in.state();
            long val = 0, lastHi = now;
            int read = 0;
            while (System.nanoTime()-now < 10000000)
            {
                DigitalState next = in.state();
                if (state != next)
                {
                    if (next == DigitalState.HIGH)
                        lastHi = System.nanoTime();
                    else
                    {
                        val = (val << 1);
                        read++;
                        if ((System.nanoTime()-lastHi)/1000 > 48)
                            val++;
                    }
                    state = next;
                }
            }
            in.shutdown(pi4j);

            //should be 40 but the first few bits are often missed and often equal 0
            if (read >= 38)
            {
                int hi = (int)((val & 0xff00000000L) >> 32), hd = (int)((val & 0xff000000L) >> 24),
                        ti = (int)((val & 0xff0000) >> 16), td = (int)((val & 0xff00) >> 8),
                        cs = (int)(val & 0xff);
                //checksum
                if (cs == ((hi+hd+ti+td) & 0xff))
                {
                    double temperature = ((((ti & 0x7f) << 8)+td)/10.)*((ti & 0x80) != 0 ? -1 : 1);
                    double humidity = ((hi << 8)+hd)/10.;
                    return new double [] {temperature, humidity};
                }
            }
            return null;
        }

        public static void main(String[] args) {
            DHT22 dht = new DHT22();
            dht.update();
        }
    }

