#include "access_web.h"

#include "osapi.h"

#include "ip_addr.h"	//this need to be included before espconn
#include "espconn.h"

struct espconn request_conn;
ip_addr_t request_ip;
esp_tcp tcp;


static void ICACHE_FLASH_ATTR networkSentCb(void *arg) {
  os_printf("sent\n");
}
 
static void ICACHE_FLASH_ATTR networkRecvCb(void *arg, char *data, unsigned short len) {
  os_printf("recv\n");
   
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
  
  
  //uart0_tx_buffer(data,len);
  //for (x=0; x<len; x++) networkParseChar(conn, data[x]);
}
 
static void ICACHE_FLASH_ATTR networkConnectedCb(void *arg) {
 
  os_printf("conn\n");
  struct espconn *conn=(struct espconn *)arg;
 
  char *data = "GET / HTTP/1.0\r\n\r\n\r\n";
  sint8 d = espconn_sent(conn,data,os_strlen(data));
 
  espconn_regist_recvcb(conn, networkRecvCb);
  os_printf("cend\n");
}
 
static void ICACHE_FLASH_ATTR networkReconCb(void *arg, sint8 err) {
  os_printf("rcon\n");
//  os_printf("Reconnect\n\r");
//  network_init();
}
 
static void ICACHE_FLASH_ATTR networkDisconCb(void *arg) {
  os_printf("dcon\n");
//  os_printf("Disconnect\n\r");
//  network_init();
}


static void ICACHE_FLASH_ATTR user_dns_found_CB(const char *name, ip_addr_t *ip, void *arg) {
  struct espconn *conn=(struct espconn *)arg;
  if (ip==NULL) {
    os_printf("Nslookup failed ERR\n");
  }else{
	os_printf("DST: %d.%d.%d.%d",
  *((uint8 *)&ip->addr), *((uint8 *)&ip->addr + 1),
  *((uint8 *)&ip->addr + 2), *((uint8 *)&ip->addr + 3));
    conn->type=ESPCONN_TCP;
    conn->state=ESPCONN_NONE;
    conn->proto.tcp=&tcp;
    conn->proto.tcp->local_port=espconn_port();
    conn->proto.tcp->remote_port=80;
    os_memcpy(conn->proto.tcp->remote_ip, &ip->addr, 4);
    espconn_regist_connectcb(conn, networkConnectedCb);
    espconn_regist_disconcb(conn, networkDisconCb);
    espconn_regist_reconcb(conn, networkReconCb);
    espconn_regist_recvcb(conn, networkRecvCb);
    espconn_regist_sentcb(conn, networkSentCb);
    espconn_connect(conn);
  }

 

}

void ICACHE_FLASH_ATTR connect_URL(char *url){
	espconn_gethostbyname(&request_conn,url, &request_ip, user_dns_found_CB);
}

