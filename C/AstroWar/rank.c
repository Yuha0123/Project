#include "rank.h"

struct info rank[11];

/*������ ���� �� ����� �̸��� ����,�ð��� ��ŷ�� ����*/
void AddRank(int time, int score)
{
	Flush_buffer();
	gotoxy(33,13);
	printf("�г���:");
	gotoxy(41,13);
	//scanf("%s",rank[10].name);
	fgets(rank[10].name,10,stdin);
	
	rank[10].time = time;
	rank[10].score = score;
	RankSort();
	FileSave();
}


void RankReset(void)
{
	int i;
	for(i=0; i<11; i++)
		rank[i].score=0; //������ 0���� ���·� ������ �����ϸ� �ð�,����,�̸��� ��� �ʱ�ȭ��	
	FileSave();
}

       
/*������ ���� ��ũ ����*/
void RankSort(void)
{
	int i,j,cnt=0;
	struct info temp;
	
	for(i=0; i<10; i++)
	{
		for(j=0; j<11; j++)
		{
			if(rank[j].score < rank[j+1].score)
			{
				temp = rank[j];
				rank[j] = rank[j+1];
				rank[j+1] = temp;
			}
		}
	}
	FileSave();
}


/*������ ���� ����Ǿ��ִ� �����͸� �ҷ���*/
void FileLoad(void)
{
	int i;
	FILE *savefile;
	
	savefile=fopen(".\\savefile\\rank.txt","rt");

	
	if(savefile == NULL){ //���� or ���� ������ ���� ���� 
		savefile=fopen(".\\savefile\\rank.txt","a");
		fclose(savefile);
		return;
	}
	
	
	for(i=0; i<10; i++)
		fscanf(savefile,"%d %d %s\n", &rank[i].time, &rank[i].score, &rank[i].name);
		
	fclose(savefile);
}


/*������ ���� TOP 10�� ��ŷ��� �� ����*/
void FileSave(void)
{	
	int i;
	FILE *savefile;
	savefile=fopen(".\\savefile\\rank.txt","wt");
	
	for(i=0; i<10; i++){
		if(rank[i].score == 0) //������ 0���̸�
			fprintf(savefile,"0 0 ---\n");
		else	
		    fprintf(savefile,"%d %d %s\n", rank[i].time, rank[i].score, rank[i].name);	
	}
	fclose(savefile);
}




void RankDraw(void)
{
	const x=16;
	int i;
	FrameDraw();
	FileLoad();
	RankSort();
	
	gotoxy(10,36);
	SetColor(RED);
	printf("DELETE : ��ŷ �ʱ�ȭ");
	gotoxy(55,36);
	SetColor(WHITE);
	printf("ESC : ������");
	
	for(i=0; i<10; i++){
		if(i==0) SetColor(14);
		gotoxy(x,(i+1)*3+2);
		printf("[%d��]",i+1);
		gotoxy(x+8,(i+1)*3+2);
		printf("�г���: %s",rank[i].name);
		gotoxy(x+26,(i+1)*3+2);
		printf("�ð�: %d��",rank[i].time);
		gotoxy(x+40,(i+1)*3+2);
		printf("����: %d",rank[i].score);
		SetColor(7);
		Sleep(200);
	}
	
	while(1)
	{
		if(GetAsyncKeyState(VK_ESCAPE) & 0x8000) break;
		if(GetAsyncKeyState(VK_DELETE) & 0x8000){
			RankReset();
			break;	
		}
	}
	system("cls");
}

