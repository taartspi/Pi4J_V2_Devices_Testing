module com.pi4j.devices.test {
    requires com.pi4j;
    //requires com.pi4j.devices;

    requires com.pi4j.plugin.pigpio;

    // SLF4J MODULES   LOG4J
    requires org.slf4j;
    requires org.slf4j.simple;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;


    requires jdk.unsupported;
    requires com.pi4j.plugin.linuxfs;
    requires com.pi4j.devices;




}