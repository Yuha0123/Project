#include "meteo.h"

void MeteoSpawn(void)
{
	int i;
	spn=0;
	for(i=0; i<MeteoMAX; i++){
		if(MeteoX[i] == 0){
			MeteoX[i] = (rand() % 45)+15;
			MeteoY[i] = 0;
			MeteoCount++;
			break; 
		}
	}
}


void MeteoReset(void)
{
	int i;
	MeteoCount=0;
	for(i=0; i<MeteoMAX; i++){
		MeteoX[i]=0;
		MeteoY[i]=0;
	}
}

/*�Ѿ��̶� �ε����� �� �����
��� ����� ����� -1*/
void MeteoDestroy(int n) //� �ı� 
{
	Sound_Play(S_DESTROY);
	VolumeSetSound();
	MeteoCount--;
	
	Score+=10; //���� +10 
	
	gotoxy(MeteoX[n], MeteoY[n]);
	printf("    ");
	gotoxy(MeteoX[n], MeteoY[n]+1);
	printf("    ");
		
	Bullet_Check = 0; //�Ѿ� ���� 
	
	MeteoX[n]=0; //�  ��ǥ �ʱ�ȭ 
	MeteoY[n]=0;
}


/*� �����*/
void MeteoClear(void)
{
	int i;
	for(i=0; i<MeteoMAX; i++){
		gotoxy(MeteoX[i], MeteoY[i]);
		printf("    "); 
		gotoxy(MeteoX[i], MeteoY[i]+1);
		printf("    ");
	}
}


/*��� �������� �ϴ� �Լ�*/
void MeteoFall(void)
{
	int i;
	cnt=0; 
	MeteoClear();
	for(i=0; i<MeteoMAX; i++){
		if(MeteoX[i] != 0)
			MeteoY[i]++;
			
		if(MeteoY[i] == 38 || (MeteoY[i] == Player_y-2 && Player_x-1 >= MeteoX[i]-1 && Player_x+1 <= MeteoX[i]+4)){
			VolumeSetSound();
			Sound_Play(S_DESTROY);
			VolumeSetSound();
			MeteoX[i] = 0;
			MeteoY[i] = 0; //��� �ٴڿ� ��ų�, �÷��̾�� �ε����� �� �ش� ��� ����� ���� -5
			Life--;
			MeteoCount--;
		}
	}
	MeteoDraw();	
}



/*� �׸���*/
void MeteoDraw(void)
{
	SetColor(YELLOW);
	int i;
	for(i=0; i<MeteoMAX; i++){
		if(MeteoX[i] != 0){
			gotoxy(MeteoX[i], MeteoY[i]);
			printf("�ɢ�");
			gotoxy(MeteoX[i], MeteoY[i]+1);
			printf("�ʢ�");
		}
	}
	SetColor(WHITE); 
}


/*� ���� �����Լ�*/
void MeteoCtrl(void)
{
	if(cnt>SPEED) MeteoFall();

	if(MeteoCount == MeteoMAX){
		spn = 0;
		return;	
	}
	
	if(spn > SPAWN_SPEED && (MeteoCount >= 0 && MeteoCount <=MeteoMAX)){
		MeteoSpawn();
	}
}


/*���� ���ӷ��� ���*/
void Level_Draw(int lv)
{
	gotoxy(67,5);
	if(lv==EASY) printf("����: EASY  ");
	else if(lv==NORMAL) printf("����: NORMAL");
	else if(lv==HARD) printf("����: HARD  ");
	else if(lv==CRAZY) printf("����: CRAZY  ");
	else if(lv==HELL) printf("����: HELL  ");
	else printf("����: ERROR");
} 


/*���� ������ �Ǹ� ������(�ӵ�����)*/
void LevelCtrl(void)
{
	Level_Draw(SPEED);
	if(Score==50){
		if(Life < 5) Life++;
		SPEED=NORMAL;
		SPAWN_SPEED=SPN_NORMAL;
		Sound_Play(S_LEVEL);
		VolumeSetSound();
		Score+=30;
	}
	else if(Score==200){
		if(Life < 5) Life++;
		SPEED=HARD;
		SPAWN_SPEED=SPN_HARD;
		Sound_Play(S_LEVEL);
		VolumeSetSound();
		Score+=50;
	}
	else if(Score==400){
		if(Life < 5) Life++;
		SPEED=CRAZY;
		SPAWN_SPEED=SPN_CRAZY;
		Sound_Play(S_LEVEL);
		VolumeSetSound();
		Score+=70;
	}
	else if(Score==800){
		if(Life < 5) Life++;
		SPEED=HELL;
		SPAWN_SPEED=SPN_HELL;
		Sound_Play(S_LEVEL);
		VolumeSetSound();
		Score+=100;
	}  
}
