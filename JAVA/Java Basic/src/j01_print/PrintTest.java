package j01_print;

public class PrintTest {

	public static void main(String[] args) {
		System.out.print("1"); //print�� ��¸� ��
		System.out.print("2");
		System.out.print("3\n"); // \n ���๮�ڸ� �����Ͽ� ���Ƿ� ���� ����
		System.out.println("1 �ȳ��ϼ���."); //println�� ����ϰ� �ڵ� ����
		System.out.println("2 �ȳ��ϼ���.");
		System.out.printf("���ڴ� : %d �Դϴ�. \n", 10);
		System.out.printf("2 + 7 = %.2f �Դϴ�. \n", (float)43 / 6);
		int number = 40;
		System.out.println("number ������ �� : " + number); //+ ��ȣ�� ����Ͽ� ���� �Ǵ� ����� ���ڿ��� �Բ� ��°���
	}
}



