package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class MainController implements Initializable {
	@FXML Text ownCoinText, multipleText, payText, ownCoinTextInfo, totalGame, winRate;
	@FXML LineChart<String, Double> graph;
	@FXML CategoryAxis xAxis;
	@FXML NumberAxis yAxis;
	@FXML TextField multipleInput, betInput;
	@FXML Button startBtn, resetBtn;
	
	private int coin, betCoin, ownCoin, win, lose;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		readFile();
		graph.setAnimated(false); // �ִϸ��̼� ȿ�� X
		graph.getXAxis().setTickLabelsVisible(false); // X�� �� ����� 
		
		multipleInput.setOnKeyReleased((event)-> {
			computeCoin(); // Ű�� ������ ���� ���� ��� 
		});
		
		betInput.setOnKeyReleased((event)-> {
			computeCoin(); 
		});
		
		startBtn.setOnAction((event)-> {		
			start();
		});
		
		resetBtn.setOnAction((event)-> { // ���¹�ư 
			ownCoin = 1000; // �⺻������ ���� �� 
			win = 0;
			lose = 0;
			saveFile(); // ���� ���� 
			readFile(); // �ҷ��ͼ� �ݿ� 
		});
	}
	
	// ���� ���� 
	private void start() {
		double betMultiple = Double.parseDouble(multipleInput.getText()); // ���� ������ double Ÿ������ ��ȯ
		final double computedMultiple = getMultiple(); // ���� ��� �������� 
		disableControl(true); // ��ư, �Է�â �� ��� ��Ȱ�� 
		
		XYChart.Series<String, Double> series = new Series<String, Double>();
		graph.getData().clear(); // �׷����� ������ ����� 
		graph.getData().add(series); // ���ο� ������ ��� 
		
		// ������ ���� 
		Thread t = new Thread(() -> {
			int i = 0;
			double num = 0; // ��� (0���� ����)
			DecimalFormat df = new DecimalFormat("0.00"); // ������ �׻� �Ҽ��� 2�ڸ����� 
			while(true) {
				try {
					Thread.sleep(80);
					series.getData().add(new XYChart.Data(i++ + "", num));
					num += ((num+1)*0.01);
					multipleText.setText(df.format(num) + "x");	
					System.out.println(num);
					if(num > computedMultiple) break; // ������ �������� ���� ����
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// ���� ���� ������ �� ������� ���� ���� (����)
			if(Double.parseDouble(df.format(num)) >= betMultiple) {
				System.out.println("Win!: +" + coin);
				ownCoin += coin;
				win++;
			} else { // ���� ������ �� ũ�� ���� (������ ���� �����)
				System.out.println("Lose!: -" + betCoin);
				ownCoin -= betCoin;
				lose++;
			}
			saveFile(); // ���� ���� �� �ҷ��ͼ� �ݿ�
			readFile();
			clearControl(); // �Է�â�� ������ �������� ���� 
			disableControl(false); // ��ư, �Է�â ��Ȱ��ȭ ���� 
		});
		t.setDaemon(true); // ���󽺷��� ���� 
		t.start(); // ������ ���� 
	}
	
	// ��ư, �Է�â ��Ȱ��ȭ ���� (true: ��Ȱ��, false: Ȱ��)
	private void disableControl(final boolean disable) {
		multipleInput.setDisable(disable);
		betInput.setDisable(disable);
		startBtn.setDisable(disable);
	}
	
	// ���� �ʱ���·� ���� 
	private void clearControl() {
		startBtn.setDisable(true);
		multipleInput.setText(null);
		betInput.setText(null);
		payText.setText("0");
	}
	
	// ���� ���� �������� 
	private double getMultiple() {
		int n = (int)Math.round(Math.random() * 10); // 0 ~ 9
		if(n > 8) {
			return Math.random() * 100; 
		} else if(n > 6) {
			return Math.random() * 15; 
		} else {
			return Math.random() * 3; 
		}
	}
	
	// ���� ���� ��� 
	private void computeCoin() { 
		try {
			double multiple = Double.parseDouble(multipleInput.getText());
			int coin = Integer.parseInt(betInput.getText());
			betCoin = coin;
			if(ownCoin-betCoin >= 0) {
				this.coin = (int)Math.round(multiple * coin);
				payText.setText(this.coin + "");
				startBtn.setDisable(false);
				System.out.println("���� ����: " + this.coin + " (" + multiple + " * " + coin + ")");
			} else {
				payText.setText("���� �ʰ�");
			}
		} catch(NumberFormatException e) {
			startBtn.setDisable(true);
		}
	}
	
	// ���� �ҷ����� 
	private void readFile() {
		System.out.print("���� �ҷ����� ��.. ");
		try {
			BufferedReader in = new BufferedReader(new FileReader("user.dat"));
			ownCoin = Integer.parseInt(in.readLine());
			win = Integer.parseInt(in.readLine());
			lose = Integer.parseInt(in.readLine());
			ownCoinText.setText(ownCoin + "");
			ownCoinTextInfo.setText(ownCoin + "");
			
			int total = win + lose;
			totalGame.setText(total + "");
			if(total > 0) {
				winRate.setText(Math.round((double)win/total*100) + " %");
			} else {
				winRate.setText("0 %");
			}
			in.close();
			System.out.println("����");
		} catch(FileNotFoundException e) {
			System.out.println("������ �����ϴ�");
			createNewFile();
		} catch(IOException e) {
			System.out.println("����");
			ownCoin = 1000;
			win = 0;
			lose = 0;
		}
	}
	
	// ���� �����ϱ� 
	private void saveFile() {
		System.out.print("���� �����ϴ� ��.. ");
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("user.dat"));
			out.write("" + ownCoin);
			out.newLine();
			out.write("" + win);
			out.newLine();
			out.write("" + lose);
			out.close();
			System.out.println("����");
		} catch(FileNotFoundException e) {
			System.out.println("������ �����ϴ�");
			createNewFile();
		} catch(IOException e) {
			System.out.println("����");
		}
	}
	
	// �� ���� ���� 
	private void createNewFile() {
		System.out.print("�� ���� �����ϴ� ��.. ");
		ownCoin = 1000;
		ownCoinText.setText("1000");
		ownCoinTextInfo.setText("1000");
		try {
			BufferedWriter newfile = new BufferedWriter(new FileWriter("user.dat"));
			newfile.write("1000");
			newfile.newLine();
			newfile.write("0");
			newfile.newLine();
			newfile.write("0");
			newfile.close();
			System.out.println("����");
		} catch (IOException e1) {
			System.out.println("����");
		}
	}
}
