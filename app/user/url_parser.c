#include "url_parser.h"
#include "osapi.h"
#include "c_types.h"
#include "ip_addr.h"


char url_host[64];
char url_abs_path[128];
uint16 url_port=80;
bool host_is_IP=false;
ip_addr_t host_ip;

void ICACHE_FLASH_ATTR parse_URL(char *url_origin)  
{  
	char url_copy[128];
    //char *url_dup = dup_str(URL);  
    char *p_1st_slash = NULL;
    char *p_1st_colon = NULL; 
	
	os_strcpy(url_copy,url_origin);
	p_1st_slash=os_strchr(url_copy,'/');
	if(p_1st_slash != NULL) {  
		os_strcpy(url_abs_path,p_1st_slash);
		*p_1st_slash = '\0'; //remove path
    }else{
		os_strcpy(url_abs_path,"/");
	}
	
	p_1st_colon=os_strchr(url_copy,':');
	if(p_1st_colon != NULL){
		url_port = atoi(p_1st_colon+1);  
		*p_1st_colon = '\0';  
	}else{
		url_port=80;
	}
	
	os_strcpy(url_host,url_copy);
	
	{//check if host is ipaddress
		char *p_number_start = url_copy;
		char *p_number_end = url_copy; 
		uint8 i,j,number_length;
		uint16 recognized_number;
		for (i=0;i<4;i++){
			p_number_start=p_number_end;
			if (i<3){
				p_number_end=os_strchr(p_number_start,'.');
				if (p_number_end==NULL) break;
			}else{
				if (os_strchr(p_number_start,'.')!=NULL) break;	//make sure there is no more dot
				p_number_end=p_number_end+os_strlen(p_number_start);
			}
			*p_number_end='\0';
			number_length=p_number_end-p_number_start;
			if (number_length==0 || number_length>3) break;	//length not correct
			for (j=0;j<number_length;j++){
				if (p_number_start[j]<'0' || p_number_start[j]>'9') break;	//not a 0~9
			}
			if (j<number_length) break;	
			recognized_number=atoi(p_number_start);
			if (recognized_number>255) break;
			
			*(((uint8 *)&(host_ip.addr))+i)=(uint8)recognized_number;		
			//os_printf("ptr: %d %d\n",p_number_start-url_copy,p_number_end-url_copy);
			p_number_end++;
		}
		
		if (i==4){
			host_is_IP=true;
		}else{
			host_is_IP=false;
		}	
	}
	if (host_is_IP){
		//os_printf("IP: %d.%d.%d.%d\n",*(((uint8 *)&host_ip.addr)+0), *(((uint8 *)&host_ip.addr)+1),*(((uint8 *)&host_ip.addr)+2), *(((uint8 *)&host_ip.addr)+3));
	}
	//os_printf("%s  %d  %s\n",url_host,url_port,url_abs_path); 
}
