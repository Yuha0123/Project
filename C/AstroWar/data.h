#include <time.h>
#include "astro.h"

/*------ �� �ڵ尪 ������ ------*/
enum 
{
	BLACK=0, 
	BLUE, 
	CYAN=3, 
	WHITE=7, 
	GRAY, 
	GREEN=10,
	SKY_BLUE, 
	RED, 
	PURPLE=13,
	YELLOW
};
/*------���� �ڵ� ������------*/
enum 
{
	S_MAIN=0,
	S_SHOOT,
	S_RELOAD,
	S_DESTROY,
	S_LEVEL
};
/*------------------------------*/ 

int Playing;

int Life;
int Ammo;
int Reloading; 

int cnt; //� �������� ����� ���� ����, �ð��� ���� 
int spn; //� �����ð��� ���� ���� 
int reld; //������ �ð��� ���� ����
 
int SPEED; //� �������� �ӵ� 
int SPAWN_SPEED; //���� �ӵ� 

int MeteoX[MeteoMAX]; //� X��ǥ 
int MeteoY[MeteoMAX]; //� Y��ǥ
int MeteoCount; //������ � ���� 


int Player_x; //�÷��̾� X ��ǥ 
int Player_y; //�÷��̾� Y ��ǥ

int Bullet_YPOS; //�Ѿ� Y��ǥ 
int Bullet_XPOS; //�Ѿ� X��ǥ  

int Dev_Status;

int Bullet_Check; //�߻����� �Ѿ� ����üũ 0=����  1=����  

int Score; //���� ���� ���庯�� 


clock_t T;
clock_t STime;
clock_t ETime;
clock_t StartTime; //���ӽ��� �ð� 
clock_t ThisTime; //����ð� ���庯�� 


