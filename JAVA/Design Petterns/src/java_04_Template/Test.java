package java_04_Template;
/*
 * Template ���ø�
 * �б⸦ ����ϴ� �κ��� ����Ŭ�������� ��ü������ �����ϰ�,
 * �б�� ������ ������ ��������ü���� �ѱ�� ��� */

public class Test {

	public static void main(String[] args) {
		Worker designer = new Designer();
		designer.work();
		
		Worker gamer = new Gamer();
		gamer.work();
	}
}
