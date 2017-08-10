#include <stdio.h>
#include <windows.h>
#include <time.h> 
#include <fmod.h>

#include "setting.h"
#include "data.h"


int Menu_YPos = 12;

/*Ÿ��Ʋ �׸���*/
void Title(void)
{
	printf("\n\n");
	printf("      ��         ����   ������  �����       ���                       "); Sleep(80);
	printf("    ��  ��     ��     ��      ��      ��     ��    ��    ��                     "); Sleep(80);
	printf("    ��  ��     ��             ��      ��     ��   ��      ��                    "); Sleep(80);
	printf("   �����     ����        ��      �����    ��      ��                    "); Sleep(80);
	printf("  ��      ��          ��      ��      ��    ��    ��      ��                    "); Sleep(80);
	printf("  ��      ��   ��     ��      ��      ��     ��    ��    ��                     "); Sleep(80);
	printf("  ��      ��    ����        ��      ��      ��     ���                       "); Sleep(80);
	printf("\n\n");
	printf("                                          ��          ��     ��     ����      "); Sleep(80);
	printf("                                          ��          ��   ��  ��   ��   ��     "); Sleep(80);
	printf("                                          ��    ��    ��   ��  ��   ��   ��     "); Sleep(80);
	printf("                                           ��  ���  ��   �����  ����      "); Sleep(80);
	printf("                                            ���  ���   ��      �� ��   ��     "); Sleep(80);
	printf("                                             ��    ��    ��      �� ��    ��    "); Sleep(80);
	
	gotoxy(51,30);
	printf("������[How To PLAY]������"); SetColor(RED);
	gotoxy(56,31);
	printf("Move :    �� ��"); SetColor(YELLOW);
	gotoxy(55,32);
	printf("Shoot :    SPACE"); SetColor(GREEN);
	gotoxy(54,33); 
	printf("Reload :    Ctrl"); SetColor(SKY_BLUE);
	gotoxy(54,34); 
	printf("Select :    ENTER"); SetColor(WHITE);
	gotoxy(51,35);
	printf("������������*������������");
	gotoxy(1,38);
	printf("Dev Option : %d",Dev_Status);
	gotoxy(67,38);
	printf("Version %g",VERSION);
	
	while(1)
	{
		if(GetAsyncKeyState(VK_SPACE) & 0x8000) break;
		
		cnt++;
		
		if(cnt>50){
			gotoxy(15,15);
			printf("               ");
			cnt=0;
		}
		cnt++;
		
		if(cnt>50){
			gotoxy(15,15);
			printf("Press SPACE Key");
			cnt=0;
		}
		Sleep(17);
	}
	gotoxy(15,15);
	printf("               ");
}


/*���θ޴� ���, �����Լ�*/
int MainMenu(void)
{
	int n; 
	Menu_YPos = 30;
	gotoxy(MainMenu_X+2, MainMenu_YMin);
	printf("���ӽ���"); //1
	gotoxy(MainMenu_X+2, MainMenu_YMin+1);
	printf("���� ��ŷ"); //2
	gotoxy(MainMenu_X+2, MainMenu_YMin+2);
	printf("����"); //3
	gotoxy(MainMenu_X+2, MainMenu_YMin+3);
	printf("����"); //4
	
	n=ListArrow(MainMenu_X, MainMenu_YMin, MainMenu_YMin, MainMenu_YMax);
	system("cls");
	
	return n;
}


/*�� ����*/
void LineDraw(int n)
{
	int loop;
	
	SetColor(SKY_BLUE);
	for (loop = 0; loop < 40; loop++) {
		gotoxy(n, loop);
		printf("��");
	}
	
	for(loop=0; loop<5; loop++){
		gotoxy(6,34+loop);
		printf("��  ��");
	}
	
	for(loop=0; loop<10; loop++){
		gotoxy(67,29+loop);
		printf("��  ��");
	}
	gotoxy(6,33);
	printf("������"); gotoxy(6,39);
	printf("������"); gotoxy(67,28);
	printf("������"); gotoxy(67,39);
	printf("������"); gotoxy(65,6);
	printf("��������������"); gotoxy(67,7);
	printf("�̵�: ���"); gotoxy(67,8);
	printf("�߻�: Space"); gotoxy(67,9);
	printf("����: Ctrl"); 
}


/*���ӳ��� �⺻ ���� ����*/
void MainSet(void)
{
	Score=0;
	Life=5;
	SPEED=EASY;
	SPAWN_SPEED=SPN_EASY;
	LineDraw(LEFT_LINE); //���ӳ� ���������� ��� 
	LineDraw(RIGHT_LINE); // " ���������� ��� 
	PlayerReset(); //�÷��̾� ������ġ, ����, �Ѿ� �ʱ�ȭ 
	SetColor(WHITE); //���ڻ� ��� ���� 
	StopSound();//Ÿ��Ʋ ������� ����
}


/*���� ����, �ð� ���*/
void Board(void)
{	 
	gotoxy(68,1);
	printf("            ");
	gotoxy(68,1);
	printf("����: %d",Score);
	gotoxy(68,3);
	printf(" %d ��",(ThisTime-T)/1000); //����ð� - (���ӽ��۽ð� - ���α׷� ����ð�) > (ms) / 1000 = N(sec) 
}


/*�Ͻ����� �޴� ���, ����*/
int PauseGame(void)
{
	int n;
	const int x =34;
	Menu_YPos = 12;
	STime = clock(); //�Ͻ����� ������ �ð����� 
	gotoxy(35,10);
	printf("[�Ͻ�����]");
	
	gotoxy(34,12);
	printf(">");
	
	gotoxy(36,12);
	printf("����ϱ�");
	gotoxy(36,13);
	printf("���θ޴�");
	gotoxy(36,14);
	printf("����");
	
	
	n=ListArrow(x, Menu_YPos, Pause_YMin, Pause_YMax);
	
	ETime=clock();
	T+=ETime-STime;
	
	gotoxy(35,10);
	printf("          ");
	gotoxy(34,12);
	printf("          ");
	gotoxy(34,13);
	printf("          ");
	gotoxy(34,14);
	printf("          ");
	return n;
}


/*�Ͻ����� �޴� �����*/
void ClearPauseMenu(void)
{
	int x=34,y=10,i; 
	for(i=0; i<5; i++){
		gotoxy(x,y+i);
		printf("           ");
	}	
}


/*������ 0�Ͻ� ��������*/
void GameOver(void)
{
	gotoxy(33,9);
	printf("��������������");
	gotoxy(33,10);
	printf("�� ���ӿ��� ��");
	gotoxy(33,11);
	printf("��������������");
	Sleep(2000);
	AddRank((ThisTime-T)/1000,Score);
} 


/*�����Լ�*/ 
int main(void) 
{
	int i,n=0, reStart;
	FileLoad();
	system("mode con: lines=40"); //���α��� 40
	system("title AstroWar"); //���� Ÿ��Ʋ ���� AstroWar�� ���� 
	CursorHide(); // Cmdâ Ŀ�� �����
	Init(); //���� �ʱ⼳�� 

	//���θ޴� �̵�(�����)�� ���� �ݺ��� 
	do
	{
		Sound_Play(S_MAIN); //Ÿ��Ʋ ���� ���� 
		VolumeSetSound();
		
		while(1)
		{
			VolumeSetSound();
			Title();
    		n=MainMenu()+1;
    		
    		
    		if(n==1) break;
			else if(n==2) RankDraw();
			else if(n==3) Option();
			else return 0;
		}
    	
    	reStart=0;
		MainSet();
		PlayerDraw();
		MeteoReset();
		
		ThisTime=clock(); //����ð� ���� 
		T=ThisTime; //����ð� 

		srand((unsigned int)time(NULL)); //���� ���� ���������� ���� Seed�� ���� 
		StartTime = clock(); //���ӽ��۽ð� ���� 
		
		
		while(n == 1) //����� or ���۽� �ݺ����� ���� ���ӽ��� 
		{
			if(GetAsyncKeyState(VK_ESCAPE)<0) { //ESCŰ�� ������ �� 
				n=PauseGame()+1; //�Ͻ����� �Լ� ���� 
				
				if(n==0){ //��ȯ���� 0�Ͻ� �ݺ��� Ż�� (��������)
					FileSave();
					return 0; 
				} 
				else if(n==2){ //��ȯ���� 2�Ͻ� ���θ޴��� 
					reStart=1; 
					break;
				}
			}
			
			
			if(Life == 0){
				GameOver();
				reStart=1;
				break;
			}
			
			if(Dev_Status == 1) Dev_Option();
			if(Reloading == 1){
				reld++;
				ReloadAmmo();
			}
			
			SoundUpdate();
			PlayerCtrl(); //�÷��̾� ���� 
			BulletCtrl(); //�Ѿ� ���� 
			MeteoCtrl(); //� ���� 
			LevelCtrl(); //���� ���� 
			Board(); //������ ���
			
			Sleep(17); //�� 0.017��  
			ThisTime=clock(); //���� �ð� ���� 
			
			cnt++;
			spn++; 
		}
		system("cls");
	}while(reStart == 1); 
	FileSave();
	return 0;
}
