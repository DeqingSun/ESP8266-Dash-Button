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

extern struct spi_config spi_config_buffer;

#define user_procTaskPrio        0
#define user_procTaskQueueLen    1
static volatile os_timer_t some_timer;
os_event_t    user_procTaskQueue[user_procTaskQueueLen];
static void user_procTask(os_event_t *events);

void some_timerfunc(void *arg)
{

		if (GPIO_INPUT_GET(14)){
			os_printf("P14 HIGH\n");
		}else{
			os_printf("P14 LOW\n");
		}
		//os_printf("%04X\n",gpio_input_get());
		
   //os_timer_arm(&some_timer, 1000, 0);
}

//Do nothing function
static void ICACHE_FLASH_ATTR
user_procTask(os_event_t *events)
{
    os_delay_us(10);
}

void ICACHE_FLASH_ATTR
smartconfig_done(void *data)
{
	struct station_config *sta_conf = data;
	
	os_memcpy(&spi_config_buffer.config, sta_conf, sizeof(struct station_config));
	
	if(!writeSpiSetting()) os_printf("SPI write ERR\n");

	wifi_station_set_config(sta_conf);
	wifi_station_disconnect();
	wifi_station_connect();
}

void user_init(void)
{
    os_printf("SDK version:%s\n", system_get_sdk_version());

    
    //smartconfig_start(SC_TYPE_ESPTOUCH, smartconfig_done, 1);
	
	PIN_FUNC_SELECT(PERIPHS_IO_MUX_MTMS_U, FUNC_GPIO14);
	
	GPIO_DIS_OUTPUT(14);	//Set GPIO2 to Input
	
	//PIN_PULLUP_DIS(PERIPHS_IO_MUX_MTMS_U);	//functional on P14
	//PIN_PULLDWN_DIS(PERIPHS_IO_MUX_MTMS_U);
	//PIN_PULLDWN_EN(PERIPHS_IO_MUX_MTMS_U);
	
	os_timer_disarm(&some_timer);
    os_timer_setfn(&some_timer, (os_timer_func_t *)some_timerfunc, NULL);
	
	
	if (readAndCheckSpiSetting()){
		os_printf("Ready to rock\n");
		os_printf("SSID: %s\n",spi_config_buffer.config.ssid);
		os_printf("PASSWORD: %s\n",spi_config_buffer.config.password);
		os_printf("BSSID_set: %d\n",spi_config_buffer.config.bssid_set);
		if (GPIO_INPUT_GET(14)){
			os_printf("14 HIGH\n");
			wifi_set_opmode(STATION_MODE);
			if (! wifi_station_set_config_current(&spi_config_buffer.config)) os_printf("ERR in config\n");
			if (! wifi_station_set_auto_connect(1)) os_printf("ERR in auto connect\n");
			//os_timer_arm(&some_timer, 1000, 0);
			
			os_printf("START\n");
		}else{
			os_printf("14 LOW\n");
			wifi_set_opmode(STATION_MODE);
			smartconfig_start(SC_TYPE_ESPTOUCH, smartconfig_done, 1);
		}
		
		
	}else{
		wifi_set_opmode(STATION_MODE);
		smartconfig_start(SC_TYPE_ESPTOUCH, smartconfig_done, 1);
	}
	
/*	os_timer_disarm(&some_timer);
    os_timer_setfn(&some_timer, (os_timer_func_t *)some_timerfunc, NULL);
	os_timer_arm(&some_timer, 1000, 0);*/
	//system_os_task(user_procTask, user_procTaskPrio,user_procTaskQueue, user_procTaskQueueLen);
//	system_os_post(user_procTaskPrio, 0, 0 );
}
