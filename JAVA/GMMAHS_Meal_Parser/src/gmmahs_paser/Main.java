package gmmahs_paser;

import java.util.Iterator;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		School sc = School.getInstance(); // ��ü �޾ƿ���
		List<DayMeal> list = sc.getMonthData(); // �̹��� ������ ����
		List<DayMeal> week_list = sc.getWeekDataInMonthData(0); // 2°���� �޽� ������

		System.out.println("<<<<< �̹����� �޽ĵ����� >>>>>");
		Iterator<DayMeal> it = list.iterator();
		while (it.hasNext()) { // ���
			DayMeal temp = it.next();
			System.out.printf("[%d�� %d�� %s����]\n", temp.getMONTH(), temp.getDAY(), temp.getDAY_OF_WEEK());
			System.out.println(temp.getMealData() + "\n------------");
		}

		System.out.println("\n\n\n<<<<< �̹����� �޽ĵ����� >>>>>");
		Iterator<DayMeal> it_week = week_list.iterator();
		while (it_week.hasNext()) { // ���
			DayMeal temp = it_week.next();
			System.out.printf("[%d�� %d�� %s����]\n", temp.getMONTH(), temp.getDAY(), temp.getDAY_OF_WEEK());
			System.out.println(temp.getMealData() + "\n------------");
		}
	}
}
