#include "osapi.h"
#include "audio_analyzer.h"
#include "c_types.h"
#include "driver/adc.h"

char generic_print_buffer[64];
extern void wdt_feed (void);

unsigned char ICACHE_FLASH_ATTR checkMicrophone( unsigned long duration ){
  uint32_t startTime = system_get_time();
  uint32_t timeLapsed = 0;
  uint16_t speedCounter = 0;
  
  uint16_t maxAdc=0;
  uint16_t minAdc=65535;
  uint16_t maxPower=0;
  uint16_t minPower=65535;
  
  
  
  do{
    uint32_t currentTime = system_get_time();
    timeLapsed = currentTime - startTime;
	wdt_feed();
	uint16_t adcValue=adc_read();
	speedCounter++;
	
	if (adcValue>maxAdc) maxAdc=adcValue;
	if (adcValue<minAdc) minAdc=adcValue;
	
	
	
	
  }while(timeLapsed<=duration);
  
  uint16_t adcValue=adc_read();
  printf("adc %d\n",adcValue);
  printf("min %d\n",minAdc);
  printf("max %d\n",maxAdc);
  uint16_t voltage=readvdd33();
  printf("power %d\n",voltage);
  printf("speed %d\n",speedCounter);
  return 1;
}
