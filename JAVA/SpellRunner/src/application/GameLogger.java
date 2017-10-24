package application;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GameLogger {
	
	public GameLogger() {
		System.out.println(getTime() + "Game Logger init.");
	}
	
	/* ���� �ִ� �α׳���� */
	public void printlnLog(String msg) {
		System.out.println(getTime() + msg);
	}
	
	/* ���� ���� �α׳���� */
	public void printLog(String msg) {
		System.out.print(getTime() + msg);
	}
	
	/* �Ϸ� */
	public void printDoneLog() {
		System.out.println(" done!");
	}
	
	/* ���� */
	public void printErrorLog() {
		System.out.println(" error!");
	}
	
	/* ���� �ð� */
	private String getTime() {
		SimpleDateFormat time = new SimpleDateFormat("[hh:mm:ss.SSS] ");
		return time.format(new Date());
	}
}
