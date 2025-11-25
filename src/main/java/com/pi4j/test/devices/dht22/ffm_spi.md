SPI considerations for FFM

Some time back a user offered a fix for pigpio, it was accepted. Recently
that user asked for that fix in linuxfs and I said I would move it.  In doing
the move I saw the fix breaks a buffer larger than 
/sys/module/spidev/parameters/bufsiz into chunks and each sent as a unique
transfer.  I worry that even if the app holds the chipselect line the entire time 
the SPI target IC may not handle the clocking interruptions.  Not holding the 
chipselect would increase the chance the spi chip operation fail.

This should be a single transfer with a spi_ioc_transfer[] .

In addition there is conversation on creating a general use SPI write API for the
drivers.

*A  
First, should we handle the large buffer for the user or tell them to 
increase the buffer size to the MAX and call spi_write multiple times  
if needed and control the chipselect ?

*B  
If 'A' is we handle it.  Do we do this in various providers or add 
this support to the above mentioned API, implementing the API code so it
is usable from the providers ?  

I have a version of the linuxfs-spi with the above changes. transfers with one 
element in the array works, more than one fails. I've done this in C no problem
and don't see my error in this java implementation.  If the plan is we do this, 
I will continue debug.  