package j03_scanner;

import java.util.Scanner;

public class ScannerTest {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); //ǥ�� �Է�
		
		String str = sc.nextLine(); //���๮��(����)���� ���ڿ� �Է�
		char ch = sc.nextLine().charAt(0); //���๮��(����)���� ���ڿ� �Է¹ް� ù��° ����
		int n = sc.nextInt(); // int(����)���·� �Է�
		
		System.out.printf("%d, %c, %s", n, ch, str);
	}
}





