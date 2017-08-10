#include "dev.h"

/*�����ڿɼ� On / Off ����*/
void Option(void)
{
	int n,x=31,y=6;
	FrameDraw();
	gotoxy(32,4);
	printf("[-----����-----]"); gotoxy(33,6);
	printf("�Ҹ� ���� ���� "); gotoxy(33,7);
	printf("�÷��� ����"); gotoxy(33,8);
	printf("��� ����"); gotoxy(33,9);
	printf("���θ޴�");
	
	n=ListArrow(x,y,6,9);
	
	switch(n)
	{
		case 0:
			VolumeCtrl();
			break;
			
		case 1:
			Help();
			break;
			
		case 2:
			Dev_OP();
			break;
			
		case 3:
			break; 
	}
	system("cls");
}



void Dev_OP(void)
{
	int n;
	FrameDraw();
	gotoxy(32,4);
	printf("�� ��޼��� ��");
	gotoxy(36,5);
	printf("[ OFF ]");
	gotoxy(36,6);
	printf("[ O N ]");
	gotoxy(11,20);
	printf("�� ��޼����� ���ӳ��� �����͸� ȭ�鿡 ����ϴ� ����Դϴ�.");
	gotoxy(13,21);
	printf("�� �Ѿ� ��ǥ��");
	gotoxy(13,22);
	printf("�� � ��ǥ��");
	gotoxy(13,23);
	printf("�� �÷��̾� ��ǥ��");
	gotoxy(13,24);
	printf("�� ����, ������ �����̸� ����ϴ� ������");
	gotoxy(13,25);
	printf("�� �ð�, ���� ��, �Ѿ� �� ���");
	gotoxy(14,34); SetColor(RED);
	printf("���� ����� ����ϸ� ���Ӽӵ��� ���ϵ� �� �ֽ��ϴ١�");
	SetColor(WHITE);
	
	n=ListArrow(34,5,5,6);
	
	if(n==0){
		Dev_Status = Dev_OFF;
	}
	else if(n==1){
		Dev_Status = Dev_ON;
	}
	else Dev_Status = Dev_OFF; 
}


/*�÷��� ���� ���*/
void Help(void)
{
	FrameDraw();
	gotoxy(2,3); 
	printf("�� ����Ű ��");
	gotoxy(3,4);
	printf("���̵� : �� ��");
	gotoxy(3,5);
	printf("���߻� : Space");
	gotoxy(3,6);
	printf("������ : Ctrl");
	gotoxy(2,8);
	printf("�� ��Ģ ��");
	gotoxy(3,9); SetColor(RED); 
	printf("�� ������ �������� ��� ���缭 ������ �ø��� �˴ϴ�."); 
	gotoxy(3,10); SetColor(YELLOW); 
	printf("�� ��� �ε����ų� ��� �ٴڿ� ������ ���� 1���� �پ��ϴ�."); 
	gotoxy(3,11); SetColor(GREEN); 
	printf("�� ����(���̵�)�� ���� 50, 200, 400, 800�� �޼��ϸ� �ڵ����� �ö󰩴ϴ�.");
	gotoxy(3,12); SetColor(SKY_BLUE); 
	printf("�� ����(���̵�)�� ���� �� ������ ���� 1���� ȸ���˴ϴ� (���� �ִ� 5��)"); 
	gotoxy(3,13); SetColor(PURPLE); 
	printf("�� �Ѿ��� �� ��ٸ� �������ؼ� �ٽ� ä�� �� �ֽ��ϴ�."); 
	gotoxy(2,15); SetColor(WHITE);
	printf("�� ������ ��");
	gotoxy(3,16);
	printf("�̱���");
	
	gotoxy(33,25);
	printf("ESC : �׸�����");
	
	while(1){
		if(GetAsyncKeyState(VK_ESCAPE)& 0x8000) return;
	} 
}


/*������ �ɼ��� �������� �� ����*/
void Dev_Option(void)
{
	gotoxy(0,1);
	printf("Dev: %d(ON)",Dev_Status);
	gotoxy(0,2); 
	
	SetColor(GREEN);
	printf("Bullet X: %02d",Bullet_XPOS);
	gotoxy(0,3);
	printf("Bullet Y: %02d",Bullet_YPOS);
	SetColor(WHITE);
	
	gotoxy(0,5); 
	printf("[Meteo Data]");
	gotoxy(0,6);
	printf("    X  Y");
	gotoxy(0,7); 
	
	SetColor(YELLOW);
	printf("1) %02d %02d",MeteoX[0],MeteoY[0]);
	gotoxy(0,8);
	printf("2) %02d %02d",MeteoX[1],MeteoY[1]);
	gotoxy(0,9);
	printf("3) %02d %02d",MeteoX[2],MeteoY[2]);
	gotoxy(0,10);
	printf("4) %02d %02d",MeteoX[3],MeteoY[3]);
	gotoxy(0,11);
	printf("5) %02d %02d",MeteoX[4],MeteoY[4]); 
	SetColor(WHITE);
	
	gotoxy(0,13);
	printf("cnt %03d",cnt);
	gotoxy(0,14);
	printf("spn %03d",spn);
	gotoxy(0,15);
	printf("reld %03d",reld);
	gotoxy(0,16);
	printf("Time:%d",ThisTime-T);
	gotoxy(0,18);
	printf("T %d",T);
	gotoxy(0,20);
	SetColor(RED);
	printf("Life:%d",Life); 
	SetColor(WHITE);
	gotoxy(0,21);
	printf("Ammo:%02d",Ammo);
	gotoxy(0,23);
	printf("F_Speed: %d",SPEED); //�������� �ӵ� 
	gotoxy(0,24);
	printf("S_Speed: %d",SPAWN_SPEED); //���� �ӵ� 
	gotoxy(69,25);
	printf("X Pos: %02d",Player_x);
}
