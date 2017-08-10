package java_01_Iterator;
/*
 * Iterator ���ͷ����� 
 * ��� �÷������κ��� ������ ���� �� �ִ� �������̽� */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MagicianList implements Iterable<String>{
	private List<String> list = new ArrayList<String>();
	
	public void add(String name){
		list.add(name);
	}
	
	@Override
	public Iterator<String> iterator() {
		return new Iterator<String>() {
			int seq = 0;
			public boolean hasNext(){
				return seq < list.size();
			}
			
			public String next(){
				return list.get(seq++);
			}
			
			public void remove(){
				throw new UnsupportedOperationException();
			}
		};
	}
	
	
	public static void main(String[] args) {
		MagicianList list = new MagicianList(); //�ν��Ͻ� ����
		list.add("AA"); //������ �߰�
		list.add("BB");
		list.add("CC");
		
		Iterator<String> iterator = list.iterator(); //MagicianList�� iterator�� �޾ƿ�
		
		while(iterator.hasNext()){ //���� �����Ͱ� ������ ����
			String element = iterator.next(); //�ش� ������ �����͸� �ް� �ε��� 1����
			System.out.println(element); //���� ���
		}
	}
}
