#include "access_web.h"
#include "url_parser.h"
#include "button_n_phase.h"

#include "osapi.h"

#include "ip_addr.h"	//this need to be included before espconn
#include "espconn.h"

struct espconn request_conn;
ip_addr_t request_ip;
esp_tcp tcp;

extern char url_host[];
extern char url_abs_path[];
extern uint16 url_port;
extern bool host_is_IP;
extern ip_addr_t host_ip;

uint16 package_count=0;


static void ICACHE_FLASH_ATTR networkSentCb(void *arg) {
  os_printf("networkSentCb\n");
}
 
static void ICACHE_FLASH_ATTR networkRecvCb(void *arg, char *data, unsigned short len) {
  os_printf("networkRecvCb\n");
   
  struct espconn *conn=(struct espconn *)arg;
  int x;
  
  {
	char buf[32];
	int i=0;
	int ptr=0;
	for (i=0;i<len;i++){
		os_printf("%c",data[i]);
		//buf[ptr++]=data[i++];
		/*if (ptr==31){
			buf[ptr]='\0';
			os_printf("%s",buf);
			ptr=0;
		}*/
	}
	os_printf("\n");
  }
  
  if (package_count==0){
	bool valid_200=false;
	if ((os_memcmp(data,"HTTP",4)==0)){
	  char *space_ptr=memchr(data,' ',10);
	  if (space_ptr!=NULL){
		if ((os_memcmp(space_ptr+1,"200",3)==0)){
		  valid_200=true;
		}
	  }
	}
	if (valid_200){
	  change_state(BUTTONSTATE_WIFI_RESP_200);
	}else{
	  change_state(BUTTONSTATE_WIFI_RESP_NOT_200);
	}
  }
  
  package_count++;
  
  //uart0_tx_buffer(data,len);
  //for (x=0; x<len; x++) networkParseChar(conn, data[x]);
}
 
static void ICACHE_FLASH_ATTR networkConnectedCb(void *arg) {
  char outbuf[192];
  os_printf("networkConnectedCb\n");
  struct espconn *conn=(struct espconn *)arg;
 
  if (host_is_IP){
    os_sprintf(outbuf,"GET %s HTTP/1.0\r\n\r\n\r\n",url_abs_path);
  }else{
    os_sprintf(outbuf,"GET %s HTTP/1.1\r\nHost: %s\r\n\r\n",url_abs_path,url_host);
  }
  
  sint8 d = espconn_sent(conn,outbuf,os_strlen(outbuf));
 
  espconn_regist_recvcb(conn, networkRecvCb);
  os_printf("cend\n");
}
 
static void ICACHE_FLASH_ATTR networkReconCb(void *arg, sint8 err) {
  os_printf("networkReconCb\n");	//this will be called if connection Failed
  os_printf("RECONNECT ERR\n");
//  os_printf("Reconnect\n\r");
//  network_init();
}
 
static void ICACHE_FLASH_ATTR networkDisconCb(void *arg) {
  os_printf("networkDisconCb\n");
//  os_printf("Disconnect\n\r");
//  network_init();
}


static void ICACHE_FLASH_ATTR user_dns_found_CB(const char *name, ip_addr_t *ip, void *arg) {
  struct espconn *conn=(struct espconn *)arg;
  if (ip==NULL) {
    os_printf("Nslookup failed ERR\n");
	change_state(BUTTONSTATE_ERR_WIFI_FAILED);
  }else{
	os_printf("DST: %d.%d.%d.%d\n",
  *((uint8 *)&ip->addr), *((uint8 *)&ip->addr + 1),
  *((uint8 *)&ip->addr + 2), *((uint8 *)&ip->addr + 3));
    conn->type=ESPCONN_TCP;
    conn->state=ESPCONN_NONE;
    conn->proto.tcp=&tcp;
    conn->proto.tcp->local_port=espconn_port();
    conn->proto.tcp->remote_port=url_port;
    os_memcpy(conn->proto.tcp->remote_ip, &ip->addr, 4);
    espconn_regist_connectcb(conn, networkConnectedCb);
    espconn_regist_disconcb(conn, networkDisconCb);
    espconn_regist_reconcb(conn, networkReconCb);
    espconn_regist_recvcb(conn, networkRecvCb);
    espconn_regist_sentcb(conn, networkSentCb);
	package_count=0;
    if (espconn_connect(conn)!=0){
	  os_printf("espconn_connect ERR\n");
	  change_state(BUTTONSTATE_ERR_WIFI_FAILED);
	}
  }

 

}

void ICACHE_FLASH_ATTR connect_URL(char *url){
	uint8 result;
	parse_URL(url);
	
	if (host_is_IP){	//skip DNS, call callback directly
		user_dns_found_CB(url_host, &host_ip, &request_conn);
	}else{
		result = espconn_gethostbyname(&request_conn,url_host, &request_ip, user_dns_found_CB);
		//os_printf("espconn_DNS %d\n",result);
	}
}

