
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


# enable PWM
sudo mousepad /boot/config.txt

dtoverlay=pwm-2chan

# enable PWM
sudo mousepad /boot/config.txt

# normal for pwm tests
#dtoverlay=pwm,pin=18,func=2

# sys default  gpio 12 and 13 default alt0
dtoverlay=pwm-2chan

/sys/class/pwm/pwmchip0 $ ls
device  export  npwm  power  pwm0  pwm1  subsystem  uevent  unexport


# enable PWM     Single channel func=2 means alt5, which permits pin 18
dtoverlay=pwm,pin=18,func=2

Default uses 18 and 19    -2chan sets up func which is alt5
##dtoverlay=pwm-2chan

Use gpio12
dtoverlay=pwm-2chan,pin=12,func=4,pin2=13,func2=4

Name:   pwm
Info:   Configures a single PWM channel
Legal pin,function combinations for each channel:
PWM0: 12,4(Alt0) 18,2(Alt5) 40,4(Alt0)            52,5(Alt1)
PWM1: 13,4(Alt0) 19,2(Alt5) 41,4(Alt0) 45,4(Alt0) 53,5(Alt1)
N.B.:
1) Pin 18 is the only one available on all platforms, and
it is the one used by the I2S audio interface.
Pins 12 and 13 might be better choices on an A+, B+ or Pi2.
2) The onboard analogue audio output uses both PWM channels.
3) So be careful mixing audio and PWM.
4) Currently the clock must have been enabled and configured
by other means.
Load:   dtoverlay=pwm,<param>=<val>
Params: pin                     Output pin (default 18) - see table
func                    Pin function (default 2 = Alt5) - see above
clock                   PWM clock frequency (informational)


Name:   pwm-2chan
Info:   Configures both PWM channels
Legal pin,function combinations for each channel:
PWM0: 12,4(Alt0) 18,2(Alt5) 40,4(Alt0)            52,5(Alt1)
PWM1: 13,4(Alt0) 19,2(Alt5) 41,4(Alt0) 45,4(Alt0) 53,5(Alt1)
N.B.:
1) Pin 18 is the only one available on all platforms, and
it is the one used by the I2S audio interface.
Pins 12 and 13 might be better choices on an A+, B+ or Pi2.
2) The onboard analogue audio output uses both PWM channels.
3) So be careful mixing audio and PWM.
4) Currently the clock must have been enabled and configured
by other means.
Load:   dtoverlay=pwm-2chan,<param>=<val>
Params: pin                     Output pin (default 18) - see table
pin2                    Output pin for other channel (default 19)
func                    Pin function (default 2 = Alt5) - see above
func2                   Function for pin2 (default 2 = Alt5)
clock                   PWM clock frequency (informational)

#! /bin/sh
# Export channel 0
# Set the period 1,000,000 nS (1kHz)
# Set the duty_cycle 50%
# Enable the PWM signal
# 2023-07-06

cd /sys/class/pwm/pwmchip0
echo 0 > export
sleep 0.1
echo 10000000 > pwm0/period
echo  5000000 > pwm0/duty_cycle
echo 1 > pwm0/enable
echo 0 > /sys/class/pwm/pwmchip0/pwm0/enable can be used to stop PWM.


Parameter examples

sudo java -cp <>   -p <> -m com.pi4j.devices.test/com.pi4j.test.devices.pwmTest.PwmTest -type H -gpio 19   -duty 50 -freq 1 

linuxfs

/usr/lib/jvm/java-1.11.0-openjdk-arm64/bin/java -javaagent:/home/pi/Tools/Intellij/idea-IC-222.4167.29/lib/idea_rt.jar=40105:/home/pi/Tools/Intellij/idea-IC-222.4167.29/bin -Dfile.encoding=UTF-8 -classpath /home/pi/Pi4J_V2/Pi4J_V2_Devices_Testing/target/classes:/home/pi/.m2/repository/net/java/dev/jna/jna/5.12.1/jna-5.12.1.jar:/home/pi/.m2/repository/org/jetbrains/annotations/24.0.1/annotations-24.0.1.jar -p /home/pi/.m2/repository/com/pi4j/pi4j-v2-devices/0.0.1/pi4j-v2-devices-0.0.1.jar:/home/pi/.m2/repository/com/pi4j/pi4j-plugin-pigpio/2.3.0-SNAPSHOT/pi4j-plugin-pigpio-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/com/pi4j/pi4j-library-linuxfs/2.3.0-SNAPSHOT/pi4j-library-linuxfs-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/com/pi4j/pi4j-core/2.3.0-SNAPSHOT/pi4j-core-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/com/pi4j/pi4j-plugin-linuxfs/2.3.0-SNAPSHOT/pi4j-plugin-linuxfs-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/com/jcraft/jsch/0.1.55/jsch-0.1.55.jar:/home/pi/.m2/repository/com/pi4j/pi4j-library-pigpio/2.3.0-SNAPSHOT/pi4j-library-pigpio-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/org/slf4j/slf4j-api/1.7.32/slf4j-api-1.7.32.jar:/home/pi/.m2/repository/com/pi4j/pi4j-plugin-raspberrypi/2.3.0-SNAPSHOT/pi4j-plugin-raspberrypi-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/org/slf4j/slf4j-simple/1.7.30/slf4j-simple-1.7.30.jar:/home/pi/Pi4J_V2/Pi4J_V2_Devices_Testing/target/test-classes -m com.pi4j.devices.test/com.pi4j.test.devices.pwmTest.PwmTest -gpio 18 -duty 50 -freq 1 -type H



/usr/lib/jvm/java-1.11.0-openjdk-arm64/bin/java -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:55585,suspend=y,server=n -javaagent:/home/pi/Tools/Intellij/idea-IC-222.4167.29/plugins/java/lib/rt/debugger-agent.jar -Dfile.encoding=UTF-8 -classpath /home/pi/Pi4J_V2/Pi4J_V2_Devices_Testing/target/classes:/home/pi/.m2/repository/net/java/dev/jna/jna/5.12.1/jna-5.12.1.jar:/home/pi/.m2/repository/org/jetbrains/annotations/24.0.1/annotations-24.0.1.jar:/home/pi/Tools/Intellij/idea-IC-222.4167.29/lib/idea_rt.jar -p /home/pi/.m2/repository/com/pi4j/pi4j-plugin-pigpio/2.3.0-SNAPSHOT/pi4j-plugin-pigpio-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/com/pi4j/pi4j-plugin-linuxfs/2.3.0-SNAPSHOT/pi4j-plugin-linuxfs-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/com/pi4j/pi4j-core/2.3.0-SNAPSHOT/pi4j-core-2.3.0-SNAPSHOT.jar:/home/pi/Pi4J_V2/Pi4J_V2_Devices_Testing/target/test-classes:/home/pi/.m2/repository/com/pi4j/pi4j-library-pigpio/2.3.0-SNAPSHOT/pi4j-library-pigpio-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/com/pi4j/pi4j-plugin-raspberrypi/2.3.0-SNAPSHOT/pi4j-plugin-raspberrypi-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/com/jcraft/jsch/0.1.55/jsch-0.1.55.jar:/home/pi/.m2/repository/com/pi4j/pi4j-v2-devices/0.0.1/pi4j-v2-devices-0.0.1.jar:/home/pi/.m2/repository/com/pi4j/pi4j-library-linuxfs/2.3.0-SNAPSHOT/pi4j-library-linuxfs-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/org/slf4j/slf4j-api/1.7.32/slf4j-api-1.7.32.jar:/home/pi/.m2/repository/org/slf4j/slf4j-simple/1.7.30/slf4j-simple-1.7.30.jar -m com.pi4j.devices.test/com.pi4j.test.devices.pwmTest.PwmTest -gpio 18 -duty 50 -freq 1 -type H


sudo /usr/lib/jvm/java-1.11.0-openjdk-arm64/bin/java -javaagent:/home/pi/Tools/Intellij/idea-IC-222.4167.29/lib/idea_rt.jar=40751:/home/pi/Tools/Intellij/idea-IC-222.4167.29/bin -Dfile.encoding=UTF-8 -classpath /home/pi/Pi4J_V2/Pi4J_V2_Devices_Testing/target/classes:/home/pi/.m2/repository/net/java/dev/jna/jna/5.12.1/jna-5.12.1.jar:/home/pi/.m2/repository/org/jetbrains/annotations/24.0.1/annotations-24.0.1.jar -p /home/pi/.m2/repository/com/pi4j/pi4j-plugin-pigpio/2.3.0-SNAPSHOT/pi4j-plugin-pigpio-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/com/pi4j/pi4j-library-pigpio/2.3.0-SNAPSHOT/pi4j-library-pigpio-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/com/pi4j/pi4j-core/2.3.0-SNAPSHOT/pi4j-core-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/com/pi4j/pi4j-library-linuxfs/2.3.0-SNAPSHOT/pi4j-library-linuxfs-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/com/pi4j/pi4j-plugin-raspberrypi/2.3.0-SNAPSHOT/pi4j-plugin-raspberrypi-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/com/jcraft/jsch/0.1.55/jsch-0.1.55.jar:/home/pi/.m2/repository/org/slf4j/slf4j-simple/1.7.30/slf4j-simple-1.7.30.jar:/home/pi/.m2/repository/com/pi4j/pi4j-plugin-linuxfs/2.3.0-SNAPSHOT/pi4j-plugin-linuxfs-2.3.0-SNAPSHOT.jar:/home/pi/.m2/repository/org/slf4j/slf4j-api/1.7.32/slf4j-api-1.7.32.jar:/home/pi/.m2/repository/com/pi4j/pi4j-v2-devices/0.0.1/pi4j-v2-devices-0.0.1.jar:/home/pi/Pi4J_V2/Pi4J_V2_Devices_Testing/target/test-classes -m com.pi4j.devices.test/com.pi4j.test.devices.pwmTest.PwmTest -gpio 19 -duty 50 -freq 1 -type H
