package java_05_Singleton;
/*
 * Signleton �̱���
 * �ν��Ͻ��� �� �Ѱ��� �����ϱ� ���� �����̴�. 
 * */

public class Test {

	public static void main(String[] args) {
		Test t = new Test();
		t.A_method();
		t.B_method();
	}
	
	public void A_method(){
		Counter1 c1 = Counter1.getInstance();
		System.out.println("Call A : " + c1.getNextInt());
	}
	
	public void B_method(){
		Counter1 c1 = Counter1.getInstance();
		System.out.println("Call B : " + c1.getNextInt());
	}

}
