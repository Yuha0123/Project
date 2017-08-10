#include <stdio.h>
#include <windows.h>
#include <time.h>

#include "setting.h"
#include "data.h"


/*�Ѿ� �߻� �ʱ� ����*/
void BulletSet(void)
{
	if(Bullet_Check == 0){
		Bullet_XPOS=Player_x;
		Bullet_Check = 1;
		Ammo--;	
	}
	Bullet_YPOS=Player_y-1;
	Sound_Play(S_SHOOT);
	VolumeSetSound();
}


/*����Ű�� ������ ��*/
void Reload(void)
{ 
	Sound_Play(S_RELOAD);
	VolumeSetSound();
	Reloading=1; //���� ��... 
}


/*�����ð� ���� reld�� 80�� ������ �����Ϸ�*/
void ReloadAmmo(void)
{
	if(reld > 60){
		Reloading=0; //���� �� 
		reld=0;
		Ammo=10;
	}	
}


/*�Ѿ� ���� ���*/
void AmmoDraw(void)
{
	SetColor(GRAY);
	int i;
	for(i=0; i<Ammo; i++){ //�Ѿ˱׸��� 
		gotoxy(69,38-i);
		printf("��");
	} 
	for(i=10-Ammo; i>0; i--){ //�Ѿ������ 
		gotoxy(69,28+i);
		printf("  ");
	}
	SetColor(WHITE);
} 


/*�߻����� �Ѿ� �׸���*/
void BulletDraw(void)
{
	gotoxy(Bullet_XPOS, Bullet_YPOS);
	SetColor(GREEN);
	printf("|");
	SetColor(WHITE);
}


/*�Ѿ� �����*/
void BulletClear(void)
{
	gotoxy(Bullet_XPOS, Bullet_YPOS);
	printf(" ");
}


/*�Ѿ� �浹üũ*/
void BulletCrashCheck(void)
{
	int i;
	for(i=0; i<MeteoMAX; i++){
		if((Bullet_XPOS >= MeteoX[i] && Bullet_XPOS <= MeteoX[i]+3) && (Bullet_YPOS <= MeteoY[i]+1 || Bullet_YPOS <= MeteoY[i]+2)){
			MeteoDestroy(i);
			break;
		}
	}
}


/*�Ѿ� ���� �����Լ�*/
void BulletCtrl(void)
{
	if (Bullet_YPOS < 1) {
		BulletClear();
		Bullet_Check=0; 
		return;
	}
	
	BulletClear();	
	BulletCrashCheck();
	
	Bullet_YPOS--;
	if(Bullet_Check == 0){
		Bullet_YPOS=0;
		Bullet_XPOS=0;
		return;
	}
	BulletDraw();
}


/*���� ���� ���*/
void LifeDraw(void)
{
	SetColor(RED);
	int i;
	for(i=0; i<Life; i++){ //����׸��� 
		gotoxy(8,38-i);
		printf("��");
	} 
	for(i=5-Life; i>0; i--){ //��������� 
		gotoxy(8,33+i);
		printf("  ");
	}
	SetColor(WHITE);
} 


/*�÷��̾� �ʱ⼳��*/
void PlayerReset(void)
{
	Life = 5; 
	Ammo = 10;
	Player_x= 39;
	Player_y= 37;
	PlayerDraw();
}


/*�÷��̾� ����*/
void PlayerDraw(void)
{
	gotoxy(Player_x, Player_y);
	printf("^");
	gotoxy(Player_x-1, Player_y+1);
	printf("<#>");
}
 
 
/*�÷��̾� �����*/
void PlayerClear(void)
{
	gotoxy(Player_x, Player_y);
	printf(" ");
	gotoxy(Player_x-1, Player_y+1);
	printf("   ");
}


/*Ű���� ����*/
void PlayerCtrl(void)
{
	LifeDraw();
	AmmoDraw();
	if(GetAsyncKeyState(VK_LEFT)<0 && Player_x > X_MIN){
		PlayerClear();
		Player_x--;
		PlayerDraw();
	}
	
	if(GetAsyncKeyState(VK_RIGHT)<0 && Player_x < X_MAX){
		PlayerClear();
		Player_x++;
		PlayerDraw();
	}
		
	if(GetAsyncKeyState(VK_SPACE)<0 && Bullet_Check == 0 && Ammo>0 && Reloading == 0){
		BulletSet();
		BulletCtrl();
	}
	
	if(GetAsyncKeyState(VK_CONTROL)<0 && Bullet_Check == 0 && Ammo !=10){
		Reload();
	}
}

