#include "button_n_phase.h"
#include "ets_sys.h"
#include "osapi.h"
#include "user_interface.h"
#include "gpio.h"

uint8 current_state=BUTTONSTATE_BOOT;

void (* startUDPServer_CB)(void);

void register_button_Callback(void (* _startUDPServer_CB)(void)){
	startUDPServer_CB=_startUDPServer_CB;

}

uint8 ICACHE_FLASH_ATTR get_state(void){
	return current_state;
}

uint32 last_edge_time=0;
uint8 last_key_state=255;

static volatile os_timer_t red_led_timer,green_led_timer,turn_off_timer;
uint16 red_on,red_off,green_on,green_off;
bool red_state=false,green_state=false;

void ICACHE_FLASH_ATTR turnOffhandler(void *arg){
	GPIO_OUTPUT_SET(12, 0);	//release mosfet
}

void ICACHE_FLASH_ATTR redLEDhandler(void *arg){
	if (red_on==0){
		red_state=true;
	}else if (red_off==0){
		red_state=false;
	}else{
		red_state=!red_state;
	}
	if (red_state){
		GPIO_OUTPUT_SET(2, 0);
		if (red_on>0) os_timer_arm(&red_led_timer, red_on, 0);
	}else{
		GPIO_DIS_OUTPUT(2);
		if (red_off>0) os_timer_arm(&red_led_timer, red_off, 0);
	}
}

void ICACHE_FLASH_ATTR greenLEDhandler(void *arg){
	if (green_on==0){
		green_state=true;
	}else if (green_off==0){
		green_state=false;
	}else{
		green_state=!green_state;
	}
	if (green_state){
		GPIO_OUTPUT_SET(0, 0);
		if (green_on>0) os_timer_arm(&green_led_timer, green_on, 0);
	}else{
		GPIO_DIS_OUTPUT(0);
		if (green_off>0) os_timer_arm(&green_led_timer, green_off, 0);
	}
}

void ICACHE_FLASH_ATTR updateLED(uint16 _red_on,uint16 _red_off,uint16 _green_on,uint16 _green_off){
	red_on=_red_on;
	red_off=_red_off;
	green_on=_green_on;
	green_off=_green_off;
	os_timer_disarm(&red_led_timer);
	os_timer_disarm(&green_led_timer);
	os_timer_arm(&red_led_timer, 0, 0);
	os_timer_arm(&green_led_timer, 0, 0);
}

void ICACHE_FLASH_ATTR change_state(int8_t state){
	os_timer_disarm(&turn_off_timer);
	switch (state){
		case BUTTONSTATE_BOOT:
			os_printf("STATE:BOOT\n");
			last_edge_time=0;
			if (GPIO_INPUT_GET(14)){
				last_key_state=0;
			}else{
				last_key_state=1;
			}
			
			os_timer_disarm(&red_led_timer);
			os_timer_setfn(&red_led_timer, (os_timer_func_t *)redLEDhandler, NULL);
			os_timer_disarm(&green_led_timer);
			os_timer_setfn(&green_led_timer, (os_timer_func_t *)greenLEDhandler, NULL);
			os_timer_disarm(&turn_off_timer);
			os_timer_setfn(&turn_off_timer, (os_timer_func_t *)turnOffhandler, NULL);
			
			updateLED(1,0,0,1);
			
			break;
		case BUTTONSTATE_ERR_SPIDATA_INVALID:
			os_printf("STATE:DATA INVALID\n");
			//SHOW LED and turn off
			updateLED(100,100,100,100);
			os_timer_arm(&turn_off_timer, 200*5, 0);
			break;	
		case BUTTONSTATE_ESPTOUCH:
			os_printf("STATE:ESPTOUCH\n");
			updateLED(1,0,50,950);
			os_timer_arm(&turn_off_timer, 5*60*1000UL, 0);
			break;
		case BUTTONSTATE_ESPTOUCH_SSID_GOT:
			os_printf("STATE:ESPTOUCH\n");
			updateLED(1,0,500,500);
			break;
		case BUTTONSTATE_UDP_URL:
			os_printf("STATE:UDP_URL\n");
			updateLED(1,0,150,50);
			os_timer_arm(&turn_off_timer, 30*1000, 0);
			break;
		case BUTTONSTATE_SETTING_FINISHED:
			os_printf("STATE:SETTING_FINISHED\n");
			updateLED(1,0,480,20);
			os_timer_arm(&turn_off_timer, 2000, 0);
			break;	
		case BUTTONSTATE_WIFI_LOOK_FOR_AP_NORMAL:
			os_printf("STATE:BUTTONSTATE_WIFI_LOOK_FOR_AP_NORMAL\n");
			updateLED(1,0,250,250);
			break;
		case BUTTONSTATE_WIFI_LOOK_FOR_AP_UDP_SERVER:
			os_printf("STATE:BUTTONSTATE_WIFI_LOOK_FOR_AP_UDP_SERVER\n");
			updateLED(1,0,250,250);
			break;
		case BUTTONSTATE_ERR_WIFI_FAILED:
			os_printf("STATE:WIFI_FAILED\n");
			updateLED(0,1,10,190);
			os_timer_arm(&turn_off_timer, 200*5, 0);
			break;
		case BUTTONSTATE_WIFI_RESP_200:
			os_printf("STATE:RESP_200\n");
			updateLED(1,0,480,20);
			os_timer_arm(&turn_off_timer, 200*5, 0);
			break;			
		case BUTTONSTATE_WIFI_RESP_NOT_200:
			os_printf("STATE:RESP_NOT_200\n");
			updateLED(20,480,480,20);
			os_timer_arm(&turn_off_timer, 200*5, 0);
			break;				
	}
	current_state=state;
}
 
void ICACHE_FLASH_ATTR button_intr_handler(int8_t key)
{
	
	uint32 current_time;
	uint32 gpio_status;
	
	gpio_status = GPIO_REG_READ(GPIO_STATUS_ADDRESS);
	//clear interrupt status
	GPIO_REG_WRITE(GPIO_STATUS_W1TC_ADDRESS, gpio_status);
	//os_printf("%04X\n",gpio_input_get());
	
	current_time=system_get_time();
	
	
	
	if (GPIO_INPUT_GET(14)){
		if (last_key_state!=0 && (current_time-last_edge_time)>10000){
			uint16 duration=(current_time-last_edge_time)/1000;
			last_key_state=0;
			os_printf("Released %d\n",duration);
			
			if (duration>5000 && last_edge_time>0){
				os_printf("Long Press!\n",duration);
				//system_restart();	//change to Power down !!!!
				os_timer_arm(&turn_off_timer, 1, 0);
			}else{
				switch (current_state){
					case BUTTONSTATE_ESPTOUCH:
						if (last_edge_time>0){						
							change_state(BUTTONSTATE_WIFI_LOOK_FOR_AP_UDP_SERVER);
							startUDPServer_CB();
						}
						break;
					case BUTTONSTATE_UDP_URL:
						os_printf("oFF\n");	//todo Shutdown
						os_timer_arm(&turn_off_timer, 1, 0);
						break;
				
				}
			}
			last_edge_time=current_time;
		}
	}else{	//pressed
		if (last_key_state!=1 && (current_time-last_edge_time)>10000){
			last_key_state=1;
			os_printf("Pressed\n");
			last_edge_time=current_time;
		}
	}
		
	
}

