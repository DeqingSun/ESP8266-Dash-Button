#ifndef _audio_analyzer_H
#define _audio_analyzer_H 
#include "user_interface.h"

extern char generic_print_buffer[];

struct spi_config {
	struct station_config config;
	uint8 padding;
	uint8 target_url[128];
	uint8 misc_data[128];
	uint16 checksum;
};

bool readAndCheckSpiSetting();
bool writeSpiSetting();

#endif

