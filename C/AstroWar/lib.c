#include <stdio.h>
#include <windows.h>
#include "data.h"


/*�Է¹��� ����� �Լ�*/
void Flush_buffer(void)
{
	FlushConsoleInputBuffer(GetStdHandle(STD_INPUT_HANDLE));
}

/*Ŀ�� ��ġ���� �Լ�*/
void gotoxy(int x, int y)
{
	COORD CursorPosition = {x,y};
	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), CursorPosition);
}


/*���ڻ� ���� �Լ�*/
void SetColor(int num)
{
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), num);
}


/*Ŀ�� ����� �Լ�*/
void CursorHide(void)
{
    CONSOLE_CURSOR_INFO cursorInfo = { 0, };
    cursorInfo.dwSize = 1;
    cursorInfo.bVisible = FALSE;
    SetConsoleCursorInfo(GetStdHandle(STD_OUTPUT_HANDLE), &cursorInfo);
    return 0;
}


/*ȭ��ǥŰ�� �����ϴ� ����� ���� �Լ�*/
int ListArrow(int x, int y, int MIN, int MAX)
{
	SetColor(CYAN);
	gotoxy(x,y);
	printf(">");
	
	while(1)
	{
		if(GetAsyncKeyState(VK_UP)<0 && MIN < y){// ȭ��ǥ ���� Ű�� ������ �� 
			gotoxy(x,y--);
			printf(" ");
			gotoxy(x,y);
			printf(">");
		}
	
		
		if(GetAsyncKeyState(VK_DOWN)<0 && MAX > y){// ȭ��ǥ �Ʒ��� Ű�� ������ �� 
			gotoxy(x,y++);
			printf(" ");
			gotoxy(x,y);
			printf(">");
		}
		
		if(GetAsyncKeyState(VK_RETURN) & 0x8000){
			SetColor(WHITE);
			return y-MIN;
		}
		Sleep(80);
	}
}

/*�׵θ� �׸��� �Լ�*/
void FrameDraw(void)
{
	int i;
	const int right=0,left=78; 
	system("cls");
	gotoxy(0,0);
	printf("�ݦ�����������������������������������������������������������������������������");
	
	for(i=1; i<39; i++){
		gotoxy(right,i);
		printf("��");
	}
	
	for(i=1; i<39; i++){
		gotoxy(left,i);
		printf("��");
	}
	
	gotoxy(0,38);
	printf("�ݦ�����������������������������������������������������������������������������");
}

