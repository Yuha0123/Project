package gmmahs_paser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*
 * GMMA High School Meal data parser
 * 
 * Version : Beta 0.2
 * Code By lghlove0509@naver.com
 * 
 * */

public class School {
	private static School instance = null;
	
	private School() {
		//Private
	}
	
	public static School getInstance() {
		if(instance == null) {
			instance = new School();
		}
		return instance;
	}
	
	// �̹����� �޽� �����͸� ������
	public List<String> getWeekData(){
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK); //1 �Ͽ��� ~ 7 �����
		int day =c.get(Calendar.DAY_OF_MONTH); // ���� ��¥
		int day_count = 0; // �̹��� �����͸� ��󳻱� ���� �������� ī��Ʈ
		List<String> list = new ArrayList<>();
		
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
                        	if(++day_count >= day - (day_of_week-2) && list.size() < 5) {
                        		// ���� ��¥�� ���Ե� ��(Week)�� �޽ĵ����� 5�� �ҷ�����
                        		list.add(parseDayMeal(buf.toString() + " ", day_count));
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
			list.clear();
			for(int i=0; i<5; i++) {
				list.add("������ �ҷ����� ����");
			}
			return null;
		}
		return list;
	}
	
	// ���ڷ� �Ѿ�� data�� �����ϴ� �۾�
	public String parseDayMeal(String data, int day) {
		String parse = "";
		if(data.charAt(1)==' ' || data.charAt(2)==' ') {
			// �����Ͱ� ���� ���
			parse = "�޽� �����Ͱ� �����ϴ�.";
		} else {
			// 1, 2��° br �±׸� ������ �������� ��� ���๮�ڷ� �ٲ�
			parse = data.replaceFirst(day + "", "");
			parse = parse.replaceAll("\\[�߽�\\]", ""); // [�߽�] ���� ����
			parse = parse.replaceFirst("<br>", ""); // 1��° br ����
			parse = parse.replaceFirst("<br>", ""); // 2��° br ����
			parse = parse.replaceAll("<br>", "\n"); // ���� br�±׵��� �������� ��ȯ
		}
		return parse; //����Ʈ�� ������ �߰�
	}
}
