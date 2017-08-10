package java_02_Adapter;

/*
 * Adapter �����
 * ����ϰ��� �ϴ� �޼ҵ�� goodMethod�̸� �Ű������� Enumeration�̴�.
 * ������ �츮���� Iterator�� �����Ƿ� Enumeration���� ��ȯ��������Ѵ�. */

// Adapter ������ � ������Ʈ�� ĳ������ �Ұ����� �ٸ� Ŭ������ ���·� ��ȯ�����ִ� ���Դϴ� //

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Test {
	
	public static void goodMethod(Enumeration<String> enu){
		while(enu.hasMoreElements()){
			System.out.println(enu.nextElement()); 
		}
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>(); //ArrayList ����
		
		list.add("AA"); //����Ʈ�� ������ �߰�
		list.add("BB");
		list.add("CC");
		
		Enumeration<String> ite = new IteratorToEnumeration(list.iterator());
		Test.goodMethod(ite);
	}
}
