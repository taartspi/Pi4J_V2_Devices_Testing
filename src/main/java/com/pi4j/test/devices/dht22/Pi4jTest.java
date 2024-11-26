package com.pi4j.test.devices.dht22;
import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiProvider;
//import com.pi4j.plugin.pigpio.*;
//import com.pi4j.plugin.pigpio.provider.spi.PiGpioSpiProvider;
import java.nio.ByteBuffer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.pi4j.plugin.pigpio.provider.spi.PiGpioSpiProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pi4jTest {

    private static final Logger logger = LoggerFactory.getLogger(Pi4jTest.class);

    public static void main(String[] args) throws InterruptedException {
        Context pi4JContext = Pi4J.newAutoContext();
        SpiProvider spiProvider = pi4JContext.provider(PiGpioSpiProvider.class);
        Spi spi = spiProvider.create(Spi.newConfigBuilder(pi4JContext)
                .id("my-spi-device")
                .name("My SPI Device")
                .address(0)
                .baud(2 * Spi.DEFAULT_BAUD)
                .build());

        final SpiReader spiReader = new SpiReader(spi);

        ExecutorService executor = Executors.newSingleThreadExecutor();//newFixedThreadPool(10);

        for (int i = 0; i < 60; i++) {
            executor.submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    return spiReader.read(0);
                }
            });

            Thread.sleep(1000);
        }
        executor.shutdown();
        pi4JContext.shutdown();
    }

    private static class SpiReader {

        private final Spi spi;

        public SpiReader(Spi spi) {
            this.spi = spi;
        }

        public synchronized Object read(int channel) {
            byte[] rxBuffer = new byte[]{-1, -1, -1};
            ByteBuffer readBuffer = ByteBuffer.allocate(3);
            spi.transfer(createWriteBuffer(channel), readBuffer, 3);
            logData(readBuffer.array());
            return readBuffer;
        }

        private ByteBuffer createWriteBuffer(int channel) {
            return ByteBuffer.wrap(createWriteBytes(channel));
        }

        private byte[] createWriteBytes(int channel) {
            return new byte[]{
                    (byte) (0b00000110 | ((channel & 0x0007) >> 2)), // first byte, start bit
                    (byte) (((channel & 0x0007) << 6)), // second byte transmitted -> (SGL/DIF = 1, D2=D1=D0=0)
                    (byte) 0b00000000 // third byte transmitted....don't care
            };
        }

        private void logData(byte[] result) {
            int value = ((result[1] & 0x0f) << 8);
            value |= (result[2] & 0xff);

            logger.info("received value: {}", (value & 0x0fff));
        }
    }

}
