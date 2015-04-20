#include "osapi.h"
#include "access_spiflash.h"
#include "user_interface.h"


/*
struct station_config {
    uint8 ssid[32];
    uint8 password[64];
    uint8 bssid_set;	// Note: If bssid_set is 1, station will just connect to the router
                        // with both ssid[] and bssid[] matched. Please check about this.
    uint8 bssid[6];
};*/
struct spi_config spi_config_buffer;
char generic_print_buffer[128];

bool readAndCheckSpiSetting(){	//if there is a valid setting, return ture;
	bool validDataInSpi = false;
	uint8 result;
	
	result = spi_flash_read(0x3D000, (uint32*)&spi_config_buffer, sizeof(spi_config_buffer));
	
	
	
	if (result==SPI_FLASH_RESULT_OK){	//ESP8266 is little-endian
		uint16 i;
		//check checksum
		//os_printf("size %d\n",size(struct station_config)+);
		uint8* dataPtr=(uint8*)&spi_config_buffer;
		uint16 chechsum=0;
		
		/*os_sprintf(spi_config_buffer.config.ssid, "123456");
		os_sprintf(spi_config_buffer.config.password, "654321");
		os_sprintf(spi_config_buffer.target_url, "ABCDEF");
		spi_config_buffer.checksum=0x55aa;
		
		spi_config_buffer.config.bssid_set=0x5a;
		spi_config_buffer.config.bssid[0]=0x5b;*/
		
		
		for (i=0;i<sizeof(spi_config_buffer)-2;i+=1){
			uint8 data=*dataPtr;
			dataPtr++;
			chechsum+=data;
			//os_printf("%04x on %04x\n",data,i);
		}
		if (chechsum+spi_config_buffer.checksum==0xFFFF){
			validDataInSpi=true;
			os_printf("Flash data Valid\n");
		}else{	//clear
			os_printf("Flash data Not valid\n");
			spi_config_buffer.config.ssid[0]='\0';
			spi_config_buffer.config.password[0]='\0';
			spi_config_buffer.config.bssid_set=0;
			spi_config_buffer.target_url[0]='\0';
			spi_config_buffer.misc_data[0]='\0';
		}		
	}else{
		os_printf("Flash read ERR%d\n");
	}
	
	return validDataInSpi;
}

bool writeSpiSetting(){
	uint8 result;
	uint16 i;
	uint8* dataPtr=(uint8*)&spi_config_buffer;
	uint16 chechsum=0;
	for (i=0;i<sizeof(spi_config_buffer)-2;i+=1){
		uint8 data=*dataPtr;
		dataPtr++;
		chechsum+=data;
	}
	spi_config_buffer.checksum=0xFFFF-chechsum;
	
	result=spi_flash_erase_sector(0x3D);
	if (result!=SPI_FLASH_RESULT_OK) return false;
	spi_flash_write(0x3D000, (uint32*)&spi_config_buffer, sizeof(spi_config_buffer));
	if (result!=SPI_FLASH_RESULT_OK) return false;
	return true;
}














