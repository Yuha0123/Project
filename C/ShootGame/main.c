#include <stdio.h>
#include <stdlib.h>
#include <windows.h>
#include <mmsystem.h>
#include "switch.h"

#define Version 3.00
#define Date "2016.07.22"


/*--------�Ʒ� ��ũ�δ� �ؽ�Ʈ ������� ������. ---------*/
#define COL GetStdHandle(STD_OUTPUT_HANDLE)
#define RED SetConsoleTextAttribute(COL, 0x000c);
#define YELLOW SetConsoleTextAttribute(COL, 0x000e);
#define GREEN SetConsoleTextAttribute(COL, 0x0002);
#define SKY_BLUE SetConsoleTextAttribute(COL, 0x000b);
#define BLUE SetConsoleTextAttribute(COL, 0x0001);
#define PURPLE SetConsoleTextAttribute(COL, 0x000d);
#define WHITE SetConsoleTextAttribute(COL, 0x000f);
/*-------------------------------------------------------*/

#define MAX 10 //��ǥ�� �ִ� �������� 
#define Space 32 //�����̽����� �ƽ�Ű�ڵ�  

#define Easy 5000
#define Normal 3500 //��ǥ �����ð� (v3.0 �⺻�� : Normal) 
#define Hard 1500

#define MAIN_SOUND ".\\res\\main.wav" 
#define SCORE_SOUND ".\\res\\score.wav"
#define SHOOT_SOUND ".\\res\\shoot.wav" 
#define RELOAD_SOUND ".\\res\\reload.wav"
/*--------------------------------------------------*/

//-----��������-----//
int Dev_Option=0;
int cnt=0;
int XPOS[10]={0};
int ammo=0;
//------------------//
unsigned __stdcall Target(void *);
unsigned __stdcall Dev_op(void *);
int Title(void);
int Menu(void);
void Option(void);
void Change_Log(void);
void Game(void);
void Shoot(int);
void Ammo_State(void);
void Reload(void);
void Board(int);
//------------------//

// ������ : �̱��� (�)         //
// E-Mail : lghlove0509@naver.com //
// 2016-07-22 // v3.0 �ϼ�        // 



int main(void)
{
	Title();
	return 0;
}

unsigned __stdcall Target(void *arg) //������1 (��ǥ�� ���� �Լ�) 
{
	int i;
	Sleep(2000);
	while(1)
	{
		if(cnt<MAX)
		{
			int x = rand()%77+1; //1~77 
			int y = rand()%15; //0~15
			Sleep(Normal);
			gotoxy(x,y);
			if(Dev_Option==1) printf("%d",cnt); //������ �ɼ��� ���������� ��ǥ����� �ĺ���ȣ ���
			else printf("*"); 
			for(i=0; i<MAX; i++)
				if(XPOS[i]==0)
				{
					XPOS[i]=x;
					break;
				}
		
			cnt++; //�ִ� ��ǥ�� ���������� �����ϱ� ���� �����ø��� ī��Ʈ 
		}
	}
}



unsigned __stdcall Dev_op(void *arg) //������2 (��ǥ�� x��ǩ���� ����� �迭��� ��� [Dev Option]) 
{
	while(1)
	{
		Sleep(5000); //5�ʿ� �ѹ� ���ΰ�ħ
		int i=0; 
		gotoxy(38,23);
		for(i=0; i<10; i++)
		{
			gotoxy(38+(i*3),23);
			printf("%02d ",XPOS[i]);
		} 
	}
}


int Title(void)  
{
	PlaySound(TEXT(MAIN_SOUND), NULL, SND_FILENAME | SND_ASYNC | SND_LOOP);

	int i;
	int SELECT;
	RED
	printf("��������������������������������������������������������������������������������");
	printf("��       ����    ��    ��     ����     ����    ������      ����   ��"); YELLOW
	printf("��     ��         ��    ��    ��    ��   ��    ��       ��          ��  ��    ��");
	printf("��      ����    �����    ��    ��   ��    ��       ��          �� ��     ��"); GREEN
	printf("��           ��   ��    ��    ��    ��   ��    ��       ��           ��       ��");
	printf("��          ��   ��    ��    ��    ��   ��    ��       ��                     ��"); SKY_BLUE
	printf("��  �����    ��    ��     ����     ����        ��            ��        ��");
	printf("��                                                                            ��"); PURPLE
	printf("�� Ver 3.0                                                  ## Made By LGH ## ��");
	printf("��������������������������������������������������������������������������������"); WHITE
	printf("  Dev Option State: %d",Dev_Option); //�����ڿɼ� On/off ���� 
	gotoxy(0,18);
	Sleep(1000);
	WHITE
	printf("������������ How to Play! ������������\n");
	printf("��#       Move       #    W A S D   ��\n");
	printf("��#  Shoot & Select  #     Space    ��\n");
	printf("��#      Reload      #       R      ��\n");
	printf("����������������  --  ����������������\n");	
	Sleep(1000);

	while(1)
	{ 
		SELECT=Menu(); 
		switch(SELECT)
		{
			case 0:
				for(i=3; i>0; i--)
				{
					gotoxy(65,24);
					printf("Start in..%d",i);
					Sleep(1000);
				}
				system("cls");
				Game();
				break;
		
			case 1:
				gotoxy(55,16);
				printf("������ : �̱���(�)");
				Sleep(3000);
				gotoxy(55,16);
				printf("                     ");
				break;
			
			case 2:
				Option();
				break;
				
			case 3:
				Change_Log();
				break;
		
			case 4:
				return 0;
		}
	}
}





int Menu(void)
{
	const x=60;  
	int y=18;
	char input;
	
	
	gotoxy(x,y); 
	printf(">");
	printf(" PLAY");
	gotoxy(x,y+1);
	printf("  Developer");
	gotoxy(x,y+2);
	printf("  Option");
	gotoxy(x,y+3);
	printf("  Change Log");
	gotoxy(x,y+4);
	printf("  Exit");
	
	
	
	while(1)
	{
		gotoxy(x,y);
		printf("");  
		input=getch(); 
		printf(" ");
		
		switch(input)
		{
			case 'w':
				if(y>18) y--; 
				break;
				
			case 's':
				if(y<22) y++;  
				break;
				
			case Space:
				return y-18;
		}
		gotoxy(x,y);
		printf(">");
	}
}

void Option(void)
{
	system("cls");
	printf("Developer Option Switch\n(Use SpaceBar)\nWait 3 sec..");
	Sleep(3000);
	system("cls");
	
	
	Dev_Option=use_switch();
	system("cls");
	Title();
}


void Change_Log(void)
{
	gotoxy(0,12);
	printf(" Version: %.1f.%s\n",Version,Date);
	printf(" 1. Add Sound!\n 2. Reload function");
	Sleep(3000);
	gotoxy(0,12);
	printf("                        \n");
	printf("               \n                   ");
} 


void Game(void)
{
	SKY_BLUE //���ڻ��� �ϴû����� ���� 
	char ch;
	int x=39,y=21,loc; //�÷��̾� ���� ��ġ x:39 y:21
	int score=0;
	int random; 
	int i;
	
	PlaySound(NULL, 0, 0);
	
	
	_beginthreadex(NULL, 0, Target, 0, 0,NULL); //������1 ���� 
	
	if(Dev_Option==1) _beginthreadex(NULL, 0, Dev_op, 0, 0,NULL); //������2 ���� 
	
	ammo=10;
	
	while(1)
	{
		gotoxy(36,23);
		printf("��");
		
		gotoxy(0,24);	
		printf("��������������������������������������������������������������������������������");
		
		Board(score); //���� ǥ�� �Լ� 
		Ammo_State();
		
		gotoxy(x,y);
		printf("��"); //�÷��̾� ǥ�� 
		
		if(Dev_Option==1)
		{
			gotoxy(70,23);
			printf("Pos: %02d",x); //������ �ɼ� (�÷��̾� ��ǩ�� ���) 
		}
		

		ch=getch(); 
		printf(" ");
		
		switch(ch)
		{
			case 'a':
				if(x>1) x--; //x�� �ּ�: 1 
				break;
				
			case 'd':
				if(x<77) x++; //x�� �ִ� 76 
				break;
				
			case 'r':
				Reload();
				break;
			
			case Space:
				if(ammo>0)
				{
					PlaySound(TEXT(SHOOT_SOUND), NULL, SND_FILENAME | SND_ASYNC);
					Shoot(x);
					ammo--;
				}
				else
				{
					Reload();
					break;
				}
				for(i=0; i<10; i++)
				{
					if(XPOS[i]==x)
					{
						PlaySound(TEXT(SCORE_SOUND), NULL, SND_FILENAME | SND_ASYNC);
						score+=10;
						XPOS[i]=0;
						cnt--;
						break;
					}
				}
				break;
		}
		gotoxy(x,y);
		printf("��");
	}
}


void Shoot(int x)
{
	int y;
	for(y=20; y>0; y--)
	{
		gotoxy(x,y);
		printf("."); //ȭ���� x��ǥ���� ���� ���ư� 
		Sleep(20);
		gotoxy(x,y);
		printf(" "); //ȭ���� ������ ��ġ�� �������� ������ 
	}
}

void Ammo_State(void)
{
	int i;
	gotoxy(16,22);
	printf("����������������������������������������������������������������") ;
	gotoxy(16,23);
	for(i=0; i<10; i++)
	{
		if(i<ammo)
			printf("��");
		else
			printf("  "); 
	}
}

void Reload(void)
{
	int i=0;
	if(ammo!=10)
	{
		gotoxy(16,23);
		printf("                    ");
		gotoxy(16,23);
		printf("Reloading");
		PlaySound(TEXT(RELOAD_SOUND), NULL, SND_FILENAME | SND_ASYNC);
		for(i=0; i<3; i++)
		{
			gotoxy(25+i,23);
			printf(".");
			Sleep(500);
		}
		ammo=10;
	} 
}

void Board(int score)
{
	gotoxy(0,22);
	printf("����������������\n"); 
	printf("������:  %04d ��",score); 
}

