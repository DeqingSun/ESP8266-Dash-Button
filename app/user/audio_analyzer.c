#include "osapi.h"
#include "audio_analyzer.h"
#include "driver/adc.h"
#include "c_types.h"

char generic_print_buffer[64];
char bell202Buffer[64];
extern void wdt_feed (void);

#define toHex(i) (((i) <= 9)?('0' +(i)):((i)+'@'-9))

char* ICACHE_FLASH_ATTR ucharToHex2(unsigned char data, char *s) { 
  unsigned char d;
  d = data >> 4;
  *s++ = toHex(d);
  d = data & 0x0f;
  *s++ = toHex(d);
  *s = '\0';
  return s;
}

char* ICACHE_FLASH_ATTR uintToHex4(unsigned int data, char *s) {
  char d = data >> 8;
  s = ucharToHex2(d, s);
  d = data & 0xff;
  return ucharToHex2(d, s);
}

unsigned char ICACHE_FLASH_ATTR checkMicrophone( unsigned long duration ){
  uint32_t startTime = system_get_time();
  uint32_t timeLapsed = 0;
  uint16_t speedCounter = 0;
  
  uint16_t maxAdc=0;
  uint16_t minAdc=65535;
  uint16_t maxPower=0;
  uint16_t minPower=65535;
  
  uint16_t samples[1024];
  uint16_t samplesPtr=0;
  
  uint16_t i;
  
  uint16_t averageLevel=545, threshold=25;
  
  uint8_t posOutput=0;
  uint32_t risingTime=0;
  uint16_t bell202BufferPtr=0;
  char charBuffer=0;
  uint8_t uartPhase=0;
  
  do{
    uint32_t currentTime = system_get_time();
    timeLapsed = currentTime - startTime;
	wdt_feed();
	uint16_t adcValue=adc_read();	//system_adc_read can only do 25K samples per second
	
	speedCounter++;
	
	if (adcValue>maxAdc) maxAdc=adcValue;
	if (adcValue<minAdc) minAdc=adcValue;
	
	if (samplesPtr<1024){
	  samples[samplesPtr]=adcValue;
	  samplesPtr++;
	}
	
	if (posOutput){
	  if (adcValue<(averageLevel-threshold)){
	    posOutput=0;
	  }
	}else{
	  if (adcValue>(averageLevel+threshold)){
	    uint32_t period=currentTime-risingTime;
	    posOutput=1;
		if (period>(454-144) && period<(833+144)){
		  //testShifter=testShifter<<1;
		  if (period>(833-144)){	//bit 0
		    //testShifter|=1;
			if (uartPhase==0){
			  uartPhase=1;
			  charBuffer==0;
			}else if(uartPhase<=7){	//7bit data
			  charBuffer=charBuffer>>1;
			  uartPhase++;	  
			}
		  }else{	//bit 1
		    if(uartPhase>0 && uartPhase<=7){
			  charBuffer=charBuffer>>1;
			  charBuffer|=(1<<6);
			  uartPhase++;
			}
		  }
		  if (uartPhase>=8) {
		    uartPhase=0;
			if (bell202BufferPtr<15){
			  bell202Buffer[bell202BufferPtr++]=charBuffer;
			  if (charBuffer=='\n'){
			    bell202Buffer[bell202BufferPtr]='\0';
				uart0_sendStr(bell202Buffer);
				bell202BufferPtr=0;
			  }
			  //uart0_sendStr("!\n");
			}else{
			  bell202BufferPtr=0;
			}
		  }
		}else{
		  uartPhase=0;	
		}
		
		
		
		risingTime=currentTime;
	  }
	}
	
	
  }while(timeLapsed<=duration);
  
  /*bell202Buffer[bell202BufferPtr]='\0';
  uart0_sendStr("BELL202\n");
  uart0_sendStr(bell202Buffer);
  uart0_sendStr("\nBOVER\n");*/
  //printf("DEBUG_STR %d %d %d %d %d %d\n",bell202Buffer[0],bell202Buffer[1],bell202Buffer[2],bell202Buffer[3],bell202Buffer[4],bell202Buffer[5]);
  //uint16_t adcValue=system_adc_read();
  //printf("adc %d\n",adcValue);
  //printf("min %d\n",minAdc);
  //printf("max %d\n",maxAdc);
  //printf("speed %d\n",speedCounter);
  printf("Samples:");
  for (i=0;i<1024;i++){
    wdt_feed();
	printf("%04X,",samples[i]);
  }
  printf("\nOVER\n\n");
  return 1;
}
