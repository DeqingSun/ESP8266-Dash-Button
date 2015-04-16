#ifndef _audio_analyzer_H
#define _audio_analyzer_H

extern char generic_print_buffer[];

#define printf( ... ) os_sprintf( generic_print_buffer, __VA_ARGS__ );  uart0_sendStr( generic_print_buffer );

unsigned char checkMicrophone( unsigned long duration );


#endif
