package java_03_Factory;
/*
 * Factory ���丮
 * new �� ���� ���� ��ü�� �������� �ʰ�, Factory Ŭ��������
 * �ش��ϴ� ��ü�� ������ �� �����޴� ����̴�. */


public class Test {

	public static void main(String[] args) {
		Animal a1 = AnimalFactory.create("��");
		a1.printDescription();
		Animal a2 = AnimalFactory.create("�����");
		a2.printDescription();
		Animal a3 = AnimalFactory.create("������");
		a3.printDescription();
	}
}
