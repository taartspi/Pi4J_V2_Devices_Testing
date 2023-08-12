
#
# /*
#  *
#  * -
#  *   * #%L
#  *   * **********************************************************************
#  *   * ORGANIZATION  :  Pi4J
#  *   * PROJECT       :  Pi4J :: EXTENSION
#  *   * FILENAME      :  README.md
#  *   *
#  *   * This file is part of the Pi4J project. More information about
#  *   * this project can be found here:  https://pi4j.com/
#  *   * **********************************************************************
# *   * %%
#  *   * Copyright (C) 2012 - 2021 Pi4J
# *    * %%
# *   * Licensed under the Apache License, Version 2.0 (the "License");
# *   * you may not use this file except in compliance with the License.
# *   * You may obtain a copy of the License at
# *   *
# *   *      http://www.apache.org/licenses/LICENSE-2.0
# *   *
# *   * Unless required by applicable law or agreed to in writing, software
# *   * distributed under the License is distributed on an "AS IS" BASIS, -v

# *   * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# *   * See the License for the specific language governing permissions and
# *   * limitations under the License.
# *   * #L%
#  *
#  *
#  */
#
#


Parameter examples

Monitor pon 0
sudo java -cp <>  <> -m com.pi4j.devices.test/com.pi4j.test.devices.mcp3008.MCP3008Test -p 0x0

Change the logging detail to 'trace'
sudo java -cp <>  -m com.pi4j.devices.test/com.pi4j.test.devices.mcp3008.MCP3008Test -p 0x0 -t trace


Use SPI 1
sudo java -cp <>  -m com.pi4j.devices.test/com.pi4j.test.devices.mcp3008.MCP3008Test -p 0x0 -s 0x01


Use ChipSelect 1
sudo java -cp <>  -m com.pi4j.devices.test/com.pi4j.test.devices.mcp3008.MCP3008Test -p 0x0 -c 0x01


sudo java -cp <>  -m com.pi4j.devices.test/com.pi4j.test.devices.mcp3008.MCP3008Test -p 0x0

Change the logging detail to 'trace'
sudo java -cp <>  -m com.pi4j.devices.test/com.pi4j.test.devices.mcp3008.MCP3008Test -p 0x0 -t trace


Use SPI 1
sudo java -cp <>  -m com.pi4j.devices.test/com.pi4j.test.devices.mcp3008.MCP3008Test -p 0x0 -s 0x01


Use ChipSelect 1
sudo java -cp <>  -m com.pi4j.devices.test/com.pi4j.test.devices.mcp3008.MCP3008Test -p 0x0 -c 0x01











Example java command


sudo /usr/lib/jvm/java-11-openjdk-arm64/bin/java  -Dfile.encoding=UTF-8 -classpath /home/pi/.m2/repository/org/apache/logging/log4j/log4j-slf4j-impl/2.17.1/log4j-slf4j-impl-2.17.1.jar:/home/pi/.m2/repository/org/jetbrains/annotations/23.0.0/annotations-23.0.0.jar -p /home/pi/.m2/repository/com/jcraft/jsch/0.1.55/jsch-0.1.55.jar:/home/pi/.m2/repository/org/slf4j/slf4j-simple/1.7.30/slf4j-simple-1.7.30.jar:/home/pi/.m2/repository/org/apache/logging/log4j/log4j-api/2.17.1/log4j-api-2.17.1.jar:/home/pi/.m2/repository/com/pi4j/pi4j-core/2.2.0-SNAPSHOT/pi4j-core-2.2.0-20220505.120622-18.jar:/home/pi/.m2/repository/org/apache/logging/log4j/log4j-core/2.17.1/log4j-core-2.17.1.jar:/home/pi/.m2/repository/com/pi4j/pi4j-plugin-raspberrypi/2.2.0-SNAPSHOT/pi4j-plugin-raspberrypi-2.2.0-20220505.120658-18.jar:/home/pi/.m2/repository/org/slf4j/slf4j-api/1.7.30/slf4j-api-1.7.30.jar:/home/pi/.m2/repository/com/pi4j/pi4j-library-pigpio/2.2.0-SNAPSHOT/pi4j-library-pigpio-2.2.0-20220505.120643-18.jar:/home/pi/.m2/repository/com/pi4j/pi4j-plugin-pigpio/2.2.0-SNAPSHOT/pi4j-plugin-pigpio-2.2.0-20220505.120654-18.jar:/home/pi/Pi4J_V2/Pi4J_V2_Devices_Testing/target/test-classes:/home/pi/.m2/repository/com/pi4j/pi4j-plugin-linuxfs/2.2.0-SNAPSHOT/pi4j-plugin-linuxfs-2.2.0-SNAPSHOT.jar:/home/pi/.m2/repository/com/pi4j/pi4j-v2-devices/0.0.1/pi4j-v2-devices-0.0.1.jar:/home/pi/.m2/repository/com/pi4j/pi4j-library-linuxfs/2.2.0-SNAPSHOT/pi4j-library-linuxfs-2.2.0-20220505.120646-18.jar -m com.pi4j.devices.test/com.pi4j.test.devices.mcp3008.MCP3008Test -p 0x00 -t trace

