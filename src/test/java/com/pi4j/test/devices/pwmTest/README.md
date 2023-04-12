
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

sudo java -cp <>   -p <> -m com.pi4j.devices.test/com.pi4j.test.devices.pwmTest.PwmTest -type H -gpio 19   -duty 50 -freq 1 

sudo /usr/lib/jvm/java-1.11.0-openjdk-arm64/bin/java -javaagent:/home/pi/Tools/Intellij/idea-IC-222.4167.29/lib/idea_rt.jar=40751:/home/pi/Tools/Intellij/idea-IC-222.4167.29/bin -Dfile.encoding=UTF-8 -classpath /home/pi/Pi4J_V2/Pi4J_V2_Devices_Testing/target/classes:/home/pi/.m2/repository/net/java/dev/jna/jna/5.12.1/jna-5.12.1.jar:/home/pi/.m2/repository/org/jetbrains/annotations/24.0.1/annotations-24.0.1.jar -p /home/pi/.m2/repository/com/pi4j/pi4j-plugin-pigpio/2.3.0-SNAPSHOT/pi4j-plugin-pigpio-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/com/pi4j/pi4j-library-pigpio/2.3.0-SNAPSHOT/pi4j-library-pigpio-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/com/pi4j/pi4j-core/2.3.0-SNAPSHOT/pi4j-core-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/com/pi4j/pi4j-library-linuxfs/2.3.0-SNAPSHOT/pi4j-library-linuxfs-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/com/pi4j/pi4j-plugin-raspberrypi/2.3.0-SNAPSHOT/pi4j-plugin-raspberrypi-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/com/jcraft/jsch/0.1.55/jsch-0.1.55.jar:/home/pi/.m2/repository/org/slf4j/slf4j-simple/1.7.30/slf4j-simple-1.7.30.jar:/home/pi/.m2/repository/com/pi4j/pi4j-plugin-linuxfs/2.3.0-SNAPSHOT/pi4j-plugin-linuxfs-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/org/slf4j/slf4j-api/1.7.32/slf4j-api-1.7.32.jar:/home/pi/.m2/repository/com/pi4j/pi4j-v2-devices/0.0.1/pi4j-v2-devices-0.0.1.jar:/home/pi/Pi4J_V2/Pi4J_V2_Devices_Testing/target/test-classes -m com.pi4j.devices.test/com.pi4j.test.devices.pwmTest.PwmTest -gpio 19 -duty 50 -freq 1 -type H
