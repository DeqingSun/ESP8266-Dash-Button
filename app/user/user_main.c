/******************************************************************************
 * Copyright 2013-2014 Espressif Systems (Wuxi)
 *
 * FileName: user_main.c
 *
 * Description: entry file of user application
 *
 * Modification history:
 *     2014/1/1, v1.0 create this file.
*******************************************************************************/
#include "ets_sys.h"
#include "osapi.h"
#include "gpio.h"

#include "user_interface.h"
#include "smartconfig.h"

#include "access_spiflash.h"
#include "access_web.h"
#include "url_parser.h"
#include "button_n_phase.h"

extern struct spi_config spi_config_buffer;

#define user_procTaskPrio        0
#define user_procTaskQueueLen    1
static volatile os_timer_t some_timer,check_wifi_timer,start_btn_timer;
os_event_t    user_procTaskQueue[user_procTaskQueueLen];
static void user_procTask(os_event_t *events);
bool flash_data_valid;

void ICACHE_FLASH_ATTR some_timerfunc(void *arg)
{
	if (0){
		if (GPIO_INPUT_GET(14)){
			os_printf("P14 HIGH\n");
		}else{
			os_printf("P14 LOW\n");
		}
		//os_printf("%04X\n",gpio_input_get());
	}	
	
   //os_timer_arm(&some_timer, 1000, 0);
}


void ICACHE_FLASH_ATTR check_wifi_timerfunc(void *arg)
{
	switch(wifi_station_get_connect_status()){
		case STATION_IDLE:
			os_printf("STATION_IDLE, STOP\n");
			change_state(BUTTONSTATE_ERR_WIFI_FAILED);
			wifi_station_disconnect();//Stop trying
			break;
		case STATION_CONNECTING:
			//os_printf("STATION_CONNECTING\n");
			os_timer_arm(&check_wifi_timer, 100, 0);
			break;
		case STATION_WRONG_PASSWORD:
			os_printf("STATION_WRONG_PASSWORD, STOP\n");
			change_state(BUTTONSTATE_ERR_WIFI_FAILED);
			wifi_station_disconnect();//Stop trying
			break;	
		case STATION_NO_AP_FOUND:
			os_printf("STATION_NO_AP_FOUND, STOP\n");
			change_state(BUTTONSTATE_ERR_WIFI_FAILED);
			wifi_station_disconnect();//Stop trying
			break;
		case STATION_CONNECT_FAIL:
			os_printf("STATION_CONNECT_FAIL, STOP\n");
			change_state(BUTTONSTATE_ERR_WIFI_FAILED);
			wifi_station_disconnect();//Stop trying
			break;
		case STATION_GOT_IP:
			os_printf("STATION_GOT_IP\n");
			switch (get_state()){
				case BUTTONSTATE_WIFI_LOOK_FOR_AP_NORMAL:{
						struct ip_info ipConfig;
						wifi_get_ip_info(STATION_IF, &ipConfig);
						if (ipConfig.ip.addr != 0){
							///connect_URL("blank.org");
							//connect_URL("173.192.121.250");
							//connect_URL("4.35.21.158/intl/en/about/");
							//connect_URL("retro.hackaday.com/retro.html");
							connect_URL(spi_config_buffer.target_url);
							
						}else{
							os_printf("IP IS ZERO!\n");
							change_state(BUTTONSTATE_ERR_WIFI_FAILED);
							wifi_station_disconnect();//Stop trying
						}
					}
					break;
				case BUTTONSTATE_WIFI_LOOK_FOR_AP_UDP_SERVER:{
						change_state(BUTTONSTATE_UDP_URL);
						init_udp_server();
					}
					break;
			}
			
			
			break;
	}
	//os_printf("CHECK\n");
}

//Do nothing function
static void ICACHE_FLASH_ATTR
user_procTask(os_event_t *events)
{
    os_delay_us(10);
}

void ICACHE_FLASH_ATTR
smartconfig_done(sc_status status, void *pdata)
{
    switch(status) {
        case SC_STATUS_WAIT:
            os_printf("SC_STATUS_WAIT\n");
            break;
        case SC_STATUS_FIND_CHANNEL:
            os_printf("SC_STATUS_FIND_CHANNEL\n");
            break;
        case SC_STATUS_GETTING_SSID_PSWD:
            os_printf("SC_STATUS_GETTING_SSID_PSWD\n");
            break;
        case SC_STATUS_LINK:
            os_printf("SC_STATUS_LINK\n");
            struct station_config *sta_conf = pdata;
			
			change_state(BUTTONSTATE_WIFI_LOOK_FOR_AP_UDP_SERVER);
			os_memcpy(&spi_config_buffer.config, sta_conf, sizeof(struct station_config));
	
			if(!writeSpiSetting()) os_printf("SPI write ERR\n");

			wifi_station_set_config(sta_conf);
			wifi_station_disconnect();
			wifi_station_connect();
			os_timer_arm(&check_wifi_timer, 100, 0);
            break;
        case SC_STATUS_LINK_OVER:
            os_printf("SC_STATUS_LINK_OVER\n");
            smartconfig_stop();
            break;
    }
	if (status==SC_STATUS_WAIT || status==SC_STATUS_FIND_CHANNEL){
		change_state(BUTTONSTATE_ESPTOUCH);
	}else if (status==SC_STATUS_GETTING_SSID_PSWD){
		change_state(BUTTONSTATE_ESPTOUCH_SSID_GOT);
	}		
}

void ICACHE_FLASH_ATTR
startUDPserver(void){
	smartconfig_stop();	
	wifi_station_connect();
	os_timer_arm(&check_wifi_timer, 100, 0);
}

void ICACHE_FLASH_ATTR start_btn_timerfunc(void *arg){
    static button_hold_counter=0;
	if (GPIO_INPUT_GET(14)){
		os_printf("HOLD BTN: %d\n",button_hold_counter*50);
		if (button_hold_counter*50<2000){
			if (flash_data_valid){
				os_printf("Ready to rock\n");
				os_printf("SSID: %s\n",spi_config_buffer.config.ssid);
				os_printf("PASSWORD: %s\n",spi_config_buffer.config.password);
				os_printf("BSSID_set: %d\n",spi_config_buffer.config.bssid_set);
				wifi_set_opmode(STATION_MODE);
				if (! wifi_station_set_config_current(&spi_config_buffer.config)) os_printf("ERR in config\n");	//this shouldn't happen
				if (! wifi_station_set_auto_connect(1)) os_printf("ERR in auto connect\n");	//this shouldn't happen
				os_timer_arm(&check_wifi_timer, 100, 0);
				os_printf("TRY to connect to WIFI\n");
				change_state(BUTTONSTATE_WIFI_LOOK_FOR_AP_NORMAL);
			}else{
				change_state(BUTTONSTATE_ERR_SPIDATA_INVALID);
				wifi_set_opmode(STATION_MODE);
				wifi_station_set_auto_connect(0);	
			}
		}else{
			wifi_set_opmode(STATION_MODE);
			smartconfig_start(SC_TYPE_ESPTOUCH, smartconfig_done, 1);
			change_state(BUTTONSTATE_ESPTOUCH);
		}
	}else{
		button_hold_counter++;
		os_timer_arm(&start_btn_timer, 50, 0);
	}

}

void user_rf_pre_init(void){
}

void user_init(void){
	bool result;

	GPIO_OUTPUT_SET(12, 1);
	PIN_FUNC_SELECT(PERIPHS_IO_MUX_MTDI_U, FUNC_GPIO12);	//hold power on
	
	system_timer_reinit();
    os_printf("SDK version:%s\n", system_get_sdk_version());
	change_state(BUTTONSTATE_BOOT);
	register_button_Callback(startUDPserver);
	
	PIN_FUNC_SELECT(PERIPHS_IO_MUX_MTMS_U, FUNC_GPIO14);
	GPIO_DIS_OUTPUT(14);	//Set GPIO14 to Input
	PIN_PULLUP_DIS(PERIPHS_IO_MUX_MTMS_U);	//internal pull up will affec circuit
	//PIN_PULLDWN_DIS(PERIPHS_IO_MUX_MTMS_U);
	//PIN_PULLDWN_EN(PERIPHS_IO_MUX_MTMS_U);
	
	ETS_GPIO_INTR_DISABLE(); // Disable gpio interrupts
	ETS_GPIO_INTR_ATTACH(button_intr_handler, 14); // GPIO14 interrupt handler
	GPIO_REG_WRITE(GPIO_STATUS_W1TC_ADDRESS, BIT(12)); // Clear GPIO14 status
	gpio_pin_intr_state_set(GPIO_ID_PIN(14), GPIO_PIN_INTR_ANYEDGE); // Interrupt on any GPIO14 edge
	ETS_GPIO_INTR_ENABLE(); // Enable gpio interrupts
	
	os_timer_disarm(&some_timer);
    os_timer_setfn(&some_timer, (os_timer_func_t *)some_timerfunc, NULL);
	os_timer_disarm(&check_wifi_timer);
    os_timer_setfn(&check_wifi_timer, (os_timer_func_t *)check_wifi_timerfunc, NULL);
	os_timer_disarm(&start_btn_timer);
    os_timer_setfn(&start_btn_timer, (os_timer_func_t *)start_btn_timerfunc, NULL);
	
	flash_data_valid=readAndCheckSpiSetting();
	//check button;
	os_timer_arm(&start_btn_timer, 1, 0);

	
/*	os_timer_disarm(&some_timer);
    os_timer_setfn(&some_timer, (os_timer_func_t *)some_timerfunc, NULL);
	os_timer_arm(&some_timer, 1000, 0);*/
	//system_os_task(user_procTask, user_procTaskPrio,user_procTaskQueue, user_procTaskQueueLen);
//	system_os_post(user_procTaskPrio, 0, 0 );
}
