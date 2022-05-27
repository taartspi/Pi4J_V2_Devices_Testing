import RPi.GPIO as GPIO
import time 
GPIO.setmode(GPIO.BCM)
GPIO.setup(16, GPIO.OUT) 
GPIO.output(16,GPIO.LOW)
time. sleep(5)
GPIO.output( 16 , GPIO.HIGH)
time. sleep(5)
GPIO.output(16,GPIO.LOW)
time. sleep(5)
GPIO.output( 16 , GPIO.HIGH)


GPIO.setup(12, GPIO.OUT) 
GPIO.output(12,GPIO.LOW)
time. sleep(5)
GPIO.output( 12 , GPIO.HIGH)
time. sleep(5)
GPIO.output(12,GPIO.LOW)
time. sleep(5)
GPIO.output( 12 , GPIO.HIGH)


GPIO.setup(18, GPIO.OUT) 
GPIO.output(18,GPIO.LOW)
time. sleep(5)
GPIO.output( 18 , GPIO.HIGH)
time. sleep(5)
GPIO.output(18,GPIO.LOW)
time. sleep(5)
GPIO.output( 18 , GPIO.HIGH)





