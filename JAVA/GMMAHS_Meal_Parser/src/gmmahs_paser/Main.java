package gmmahs_paser;

import java.util.Iterator;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		School sc = School.getInstance(); // ��ü �޾ƿ���
		List<String> list = sc.getWeekData(); // �̹��� ������ ����
		
		Iterator<String> it = list.iterator();
		while(it.hasNext()) { //���
			System.out.println(it.next() + "\n-----------");
		}
	}
}
