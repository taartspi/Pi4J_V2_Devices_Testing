

module com.pi4j.plugin.bmp280sensor {

    // Pi4J MODULES
    requires com.pi4j;

    requires com.pi4j.plugin.linuxfs;



    requires java.logging;
    requires jdk.unsupported;



    uses com.pi4j.extension.Extension;
    uses com.pi4j.provider.Provider;

    // allow access to classes in the following namespaces for Pi4J annotation processing
    opens com.pi4j.plugin.temperaturesensor.bmp280  to com.pi4j;

}