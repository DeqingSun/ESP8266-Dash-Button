#ifndef _button_n_phase_H
#define _button_n_phase_H 
#include "c_types.h"

typedef enum {
    BUTTONSTATE_BOOT,
    BUTTONSTATE_ERR_SPIDATA_INVALID,
	
    BUTTONSTATE_ESPTOUCH,
    BUTTONSTATE_UDP_URL,
	
	BUTTONSTATE_WIFI_LOOK_FOR_AP_NORMAL,
	BUTTONSTATE_WIFI_LOOK_FOR_AP_UDP_SERVER,
	BUTTONSTATE_ERR_WIFI_FAILED,
	
} ButtonState;

void button_intr_handler(int8_t key);
void change_state(int8_t state);
uint8 get_state(void);
void register_button_Callback(void (* _startUDPServer_CB)(void));

#endif

