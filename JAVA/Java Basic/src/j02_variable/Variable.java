package j02_variable;

public class Variable {

	public static void main(String[] args) {
		int number;
		char c;
		float f1 = 3.14f; //float ������ ����� �����Ϸ��� f�� �ڿ� �ٿ�����
		double f2 = 3.14;
		String str = "�ȳ�"; //���ڿ��� " �� �����ش�.
		
		number = 123;
		c = 'A'; //���ڴ� ' �� �����ش�
		
		System.out.printf("%d, %c, %f, %f, %s", number, c, f1, f2, str);
	}

}


