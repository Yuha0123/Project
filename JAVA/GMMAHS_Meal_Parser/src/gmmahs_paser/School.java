package gmmahs_paser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*
 * GMMA High School Meal data parser
 * 
 * Version : Beta 0.1
 * Code By lghlove0509@naver.com
 * 
 * */

public class School {
	private static int count = 0;
	private static List<String> list = new ArrayList<>();
	
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK); //1 �Ͽ��� ~ 7 �����
		int day =c.get(Calendar.DAY_OF_MONTH); // ���� ��¥
		System.out.println("����: " + day_of_week + " / ��¥: " + day + "\n\n---�̹��� �޽�---");
		
		try {
			Document doc = Jsoup.connect("http://stu.goe.go.kr/sts_sci_md00_001.do?schulCode=J100000488&schulCrseScCode=4&schulKndScCode=4")
					.timeout(5000).get(); // ����濵ȸ�����б��� �̹��� �޽� ������ ��ȸ��ũ
			
			Elements e = doc.select("tbody"); // HTML�� tbody�±� ���� �ڵ� ���� 
			String str = e.toString().replaceAll("\\s+", ""); // �Ľ��� �������� ����, ���๮�� ��� ����
			
			StringBuffer buf = new StringBuffer();
			
			boolean inDiv = false;
			for(int i=0; i<str.length(); i++) {
				if(str.charAt(i)=='v') { //div �±� ����
					if(inDiv) {
						buf.delete(buf.length() - 4, buf.length());
                        if (buf.length() > 0) 
                        	//���ۿ� �ִ� ������ ���̰� > 0 �̸�
                        	if(++count >= day - (day_of_week-2) && list.size() < 5) {
                        		// ���� ��¥�� ���Ե� ��(Week)�� �޽ĵ����� 5�� �ҷ�����
                        		parseDayMeal(buf.toString() + " ");
                        	}
                        buf.setLength(0);
                    } else {
                        i++;
                    } 
					inDiv = !inDiv;
                } else if (inDiv) {
                	buf.append(str.charAt(i));
                }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Iterator<String> it = list.iterator();
		while(it.hasNext()) {
			System.out.println(it.next() + "\n\n-------------\n"); //����Ʈ�� ����� ������ ���
		}
	}
	
	// ���ڷ� �Ѿ�� data�� �����ϴ� �۾�
	public static void parseDayMeal(String data) {
		String parse = "";
		String day = "";
		
		if(data.charAt(1)==' ' || data.charAt(2)==' ') {
			// �����Ͱ� ���� ���
			parse = "�޽� �����Ͱ� �����ϴ�.";
		} else {
			// br �±׸� ��� ���๮�ڷ� �ٲ�
			parse = data.replaceAll("<br>", "\n");
			
			//�Ʒ� �۾��� ���Ŀ� ����� ��¥ ������ �����۾�
			day += parse.charAt(0);
			if(parse.charAt(1) != ' ') {
				day += parse.charAt(1) + "";
			}
		}
		list.add(parse); //����Ʈ�� ������ �߰�
	}
}
