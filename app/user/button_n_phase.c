#include "button_n_phase.h"
#include "ets_sys.h"
#include "osapi.h"
#include "gpio.h"

uint8 current_state=BUTTONSTATE_BOOT;

void ICACHE_FLASH_ATTR change_state(int8_t state){
	switch (state){
		case BUTTONSTATE_BOOT:
			os_printf("STATE:BOOT\n");
			break;
		case BUTTONSTATE_ERR_SPIDATA_INVALID:
			os_printf("STATE:DATA INVALID\n");
			//SHOW LED and turn off
			break;	
		case BUTTONSTATE_ESPTOUCH:
			os_printf("STATE:ESPTOUCH\n");
			break;
		case BUTTONSTATE_UDP_URL:
			os_printf("STATE:UDP_URL\n");
			break;
		case BUTTONSTATE_WIFI_LOOK_FOR_AP:
			os_printf("STATE:BUTTONSTATE_WIFI_LOOK_FOR_AP\n");
			break;
		case BUTTONSTATE_ERR_WIFI_FAILED:
			os_printf("STATE:WIFI_FAILED\n");
			break;
	}
	current_state=state;
}
 
void ICACHE_FLASH_ATTR button_intr_handler(int8_t key)
{
    static uint32 last_edge_time=0;
	static uint8 last_key_state=255;
	uint32 current_time;
	uint32 gpio_status;
	
	gpio_status = GPIO_REG_READ(GPIO_STATUS_ADDRESS);
	//clear interrupt status
	GPIO_REG_WRITE(GPIO_STATUS_W1TC_ADDRESS, gpio_status);
	//os_printf("%04X\n",gpio_input_get());
	
	current_time=system_get_time();
	
	
	
	if (GPIO_INPUT_GET(14)){
		if (last_key_state!=0 && (current_time-last_edge_time)>5000){
			uint16 duration=(current_time-last_edge_time)/1000;
			last_key_state=0;
			os_printf("Released %d\n",duration);
			last_edge_time=current_time;
			if (duration>5000){
				os_printf("Long Press!\n",duration);
				//system_restart();	//change to Power down !!!!
			}else{
				switch (current_state){
					case BUTTONSTATE_ESPTOUCH:
						smartconfig_stop();
						change_state(BUTTONSTATE_UDP_URL);
						break;
				
				}
			}
		}
	}else{	//pressed
		if (last_key_state!=1 && (current_time-last_edge_time)>5000){
			last_key_state=1;
			os_printf("Pressed\n");
			last_edge_time=current_time;
		}
	}
		
	
}

