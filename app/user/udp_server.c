#include "udp_server.h"
#include "button_n_phase.h"
#include "access_spiflash.h"
#include "ip_addr.h"
#include "espconn.h"
#include "osapi.h"

struct espconn udp_server_conn;
esp_udp udp;
extern struct spi_config spi_config_buffer;

static void ICACHE_FLASH_ATTR serverSentCb(void *arg) {
  os_printf("serverSentCb\n");
  espconn_delete(&udp_server_conn);
  change_state(BUTTONSTATE_SETTING_FINISHED);
}


static void ICACHE_FLASH_ATTR serverRecvCb(void *arg, char *data, unsigned short len) {
  //os_printf("ServerRecvCb\n");
   
  struct espconn *conn=(struct espconn *)arg;
  int i;
  uint16 checksum=0;
  for (i=0;i<len-3;i++){
	checksum+=data[i];
  }
  checksum=0xFFFF-(checksum&0xFFFF);
  if (data[len-3]==0 && data[len-2]==(checksum&0xFF) && data[len-1]==(checksum>>8)){
    uint8 out_data=len-3;
	udp_server_conn.proto.udp->remote_port=10000;	//send to 10000 of incoming IP
	sint8 d = espconn_sent(&udp_server_conn,&out_data,1);
	os_printf("OK: %s\n",data);
	os_memcpy(spi_config_buffer.target_url, data, len-2);
	if(!writeSpiSetting()) os_printf("SPI write ERR\n");
  }
  
}
 

void init_udp_server(){
	udp_server_conn.type=ESPCONN_UDP;
    udp_server_conn.state=ESPCONN_NONE;
    udp_server_conn.proto.udp=&udp;
    udp_server_conn.proto.udp->local_port=7001;
    udp_server_conn.proto.udp->remote_port=10000;
    //espconn_regist_connectcb(conn, networkConnectedCb);
    //espconn_regist_disconcb(conn, networkDisconCb);
    //espconn_regist_reconcb(conn, networkReconCb);
    espconn_regist_recvcb(&udp_server_conn, serverRecvCb);
	espconn_regist_sentcb(&udp_server_conn, serverSentCb);
    //espconn_regist_sentcb(conn, networkSentCb);
    if (espconn_create(&udp_server_conn)!=0){
		os_printf("UDP CREATE ERR\n");
	}
	os_printf("UDP! Getting URL\n");

}
