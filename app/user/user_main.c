#include "ets_sys.h"
#include "osapi.h"
#include "gpio.h"
#include "os_type.h"
#include "user_config.h"
#include "c_types.h"
#include "driver/uart.h"
#include "audio_analyzer.h"

#define user_procTaskPrio        0
#define user_procTaskQueueLen    1

extern void wdt_feed (void);

os_event_t    user_procTaskQueue[user_procTaskQueueLen];
static void user_procTask(os_event_t *events);

static volatile os_timer_t some_timer;


void some_timerfunc(void *arg)
{
    /*//Do blinky stuff
    if (GPIO_REG_READ(GPIO_OUT_ADDRESS) & BIT2)
    {
        //Set GPIO2 to LOW
        gpio_output_set(0, BIT2, BIT2, 0);
    }
    else
    {
        //Set GPIO2 to HIGH
        gpio_output_set(BIT2, 0, BIT2, 0);
    }*/
	//Do blinky stuff
    if (GPIO_INPUT_GET(0))
    {
        //Set GPIO2 to LOW
        GPIO_OUTPUT_SET(0, 0);
    }
    else
    {
        //Set GPIO2 to INPUT
        GPIO_DIS_OUTPUT(0);
    }
	uart0_sendStr("Check\n");
	ets_wdt_disable();	//necessary if we use too much time without OS touching it.
	checkMicrophone(10*1000*1000);
	os_delay_us(500*1000);	//give time for serial to finish
	ets_wdt_enable();
	wdt_feed();
	
	
	os_timer_arm(&some_timer, 100, 0);
}

//Do nothing function
static void ICACHE_FLASH_ATTR
user_procTask(os_event_t *events)
{
    os_delay_us(10);
}

//Init function 
void ICACHE_FLASH_ATTR
user_init()
{

	// Configure the UART
	uart_init(BIT_RATE_230400,BIT_RATE_230400);
	
    // Initialize the GPIO subsystem.
    gpio_init();
	
	uart0_sendStr("\r\nHello World!\r\n");

    /*//Set GPIO2 to output mode
    PIN_FUNC_SELECT(PERIPHS_IO_MUX_GPIO2_U, FUNC_GPIO2);

    //Set GPIO2 low
    gpio_output_set(0, BIT2, BIT2, 0);*/

    //Set GPIO0 to output mode
    PIN_FUNC_SELECT(PERIPHS_IO_MUX_GPIO0_U, FUNC_GPIO0);

    //Set GPIO0 low
	GPIO_OUTPUT_SET(0, 0);	//(gpio_no, bit_value)
	
    //Disarm timer
    os_timer_disarm(&some_timer);

    //Setup timer
    os_timer_setfn(&some_timer, (os_timer_func_t *)some_timerfunc, NULL);

    //Arm the timer
    //&some_timer is the pointer
    //1000 is the fire time in ms
    //0 for once and 1 for repeating
    os_timer_arm(&some_timer, 100, 0);
    
    //Start os task
    system_os_task(user_procTask, user_procTaskPrio,user_procTaskQueue, user_procTaskQueueLen);
}
