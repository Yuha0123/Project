package gmmahs_paser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*
 * GMMA High School Meal data parser
 * 
 * Version : Beta 0.4
 * Code By lghlove0509@naver.com
 * 
 * */

public class School {
	private final String url = "http://stu.goe.go.kr/sts_sci_md00_001.do?schulCode=J100000488&schulCrseScCode=4&schulKndScCode=4";
	private List<DayMeal> month_list = new ArrayList<>();
	private int year, month, last_day, week_of_month;
	private int first_day_of_week;
	private static School instance = null;

	private School() {
		// Private ������
	}

	// getInstance �޼ҵ�θ� ��ü�� ���� �� ����
	public static School getInstance() {
		if (instance == null) {
			instance = new School();
		}
		return instance;
	}

	// �̹����� �޽� �����͸� ������
	public List<DayMeal> getMonthData() {
		RefreshCalendar(); // ���� ������ �޷� �����ͷ� ����
		int day_count = 1; // 1�� ~ �̹����� ��������
		int day_week = first_day_of_week;
		month_list.clear();
		
		try {
			String str = getDataFromUrl(new URL(url), "tbody").replaceAll("\\s+", "");
			// tbody �±� ���� �����͸� �ҷ��� �� ����, ���๮�� ��� ����

			StringBuffer buf = new StringBuffer();

			boolean inDiv = false;
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) == 'v') { // div �±� ����
					if (inDiv) {
						buf.delete(buf.length() - 4, buf.length());
						if (buf.length() > 0 && day_count < last_day) {
							if(day_week > 7) day_week = 1;
							month_list.add(new DayMeal(month, day_count, DayWeek_toString(day_week), parseDayMeal(buf.toString() + " ", day_count)));
							day_count++;
							day_week++;
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
		} catch (Exception e) {
			e.printStackTrace();
			month_list.clear();
			for (int i = 0; i < last_day; i++) {
				month_list.add(new DayMeal(month, i + 1, "", "������ �ҷ����� ����"));
			}
		}
		return month_list;
	}
	
	
	// ���ڷ� ���޵� ��(Week)�� �޽ĵ����� ����Ʈ�� ��ȯ
	public List<DayMeal> getWeekDataInMonthData(int week){
		/* 
		 * ���ڷ� 0�� �����ϸ� �̹����� �����͸� ��ȯ�մϴ�.
		 * */
		if(month_list.size() == 0) return null;
		
		if(week == 0) week = week_of_month;
		
		List<DayMeal> week_list = new ArrayList<>();
		int temp_week = 1;
		boolean inWeek = false;
		
		for(int i = 0; i<month_list.size(); i++) {
			DayMeal temp = month_list.get(i);
			String d_week = temp.getDAY_OF_WEEK();
			
			if(inWeek) {
				week_list.add(new DayMeal(temp.getMONTH(), temp.getDAY(), d_week, temp.getMealData()));
			}	
			
			if(d_week.equals("��")) {
				temp_week++;
				if(inWeek) break;
				else if(temp_week == week) inWeek = true; 
			}	
		}
		return week_list;
	}
	

	// URL�� �����Ͽ� tag���� ������ �Ľ�
	public String getDataFromUrl(URL url, String tag) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
		StringBuffer buffer = new StringBuffer();
		String line;

		boolean inTable = false;

		while ((line = reader.readLine()) != null) {
			if (inTable) {
				if (line.contains("</" + tag + ">")) {
					break;
				}
				buffer.append(line);
			} else if (line.contains("<" + tag + ">")) {
				inTable = true;
			}
		}
		reader.close();
		return buffer.toString();
	}

	// ���ڷ� �Ѿ�� �Ϸ� �޽� �����͸� �����ϴ� �۾�
	public String parseDayMeal(String data, int day) {
		String parse = "";
		if (data.charAt(1) == ' ' || data.charAt(2) == ' ') {
			// �����Ͱ� ���� ���
			parse = "�޽� �����Ͱ� �����ϴ�.";
		} else {
			// 1, 2��° br �±׸� ������ �������� ��� ���๮�ڷ� �ٲ�
			parse = data.replaceFirst(day + "", "");
			parse = parse.replaceAll("\\[�߽�\\]", ""); // [�߽�] ���� ����
			parse = parse.replaceFirst("<br/>", ""); // 1��° br ����
			parse = parse.replaceFirst("<br/>", ""); // 2��° br ����
			parse = parse.replaceAll("<br/>", "\n"); // ���� br�±׵��� �������� ��ȯ

			// ����9.13.5 �̷� �������� �޽� �޴��� �˷����� ������ ������ ����.
			// �޴��� �˷����� ���� ���̿� ������ ����ִ� �۾�
			StringBuffer sb = new StringBuffer();
			sb.append(parse.charAt(0));
			boolean first = true;
			for (int i = 0; i < parse.length() - 1; i++) {
				char temp = parse.charAt(i + 1);
				if (temp == '\n') {
					sb.append(temp);
					first = true;
				} else if (temp >= 48 && temp <= 57 && first) {
					sb.append(" ");
					sb.append(temp);
					first = false;
				} else {
					sb.append(temp);
				}
			}
			parse = sb.toString();
		}
		return parse; // ����Ʈ�� ������
	}
	
	// Calendar�� ��(Week) �����͸� ���ڿ��� ��ȯ (1�� ~ 7��)
	public String DayWeek_toString(int day_week) {
		String s = "";
		switch (day_week) {
		case 1:
			s = "��";
			break;
			
		case 2:
			s = "��";
			break;
			
		case 3:
			s = "ȭ";
			break;
			
		case 4:
			s = "��";
			break;
			
		case 5:
			s = "��";
			break;
			
		case 6:
			s = "��";
			break;
			
		case 7:
			s = "��";
			break;
		}
		return s;
	}

	// ���� ������ �޷µ����� ����
	public void RefreshCalendar() {
		Calendar c = Calendar.getInstance();
		week_of_month = c.get(Calendar.WEEK_OF_MONTH); // �̹����� ���° ��
		year = c.get(Calendar.YEAR); // �⵵
		month = c.get(Calendar.MONTH) + 1; // �̹� ��
		last_day = c.getMaximum(Calendar.DAY_OF_MONTH); // �̹����� ������ ��

		c.set(year, month - 1, 1); // ��¥�� �̹��� 1�Ϸ� ����
		first_day_of_week = c.get(Calendar.DAY_OF_WEEK); // �̹����� ù°�� ���� (�Ͽ��� ~ 7 �����)
		
//		System.out.printf("%d�� %d�� %d�� / %d ���� / %d��° �� / �̹����� ������ �� : %d\n\n", year, month, day, day_of_week,
//				week_of_month, last_day);
	}
}
