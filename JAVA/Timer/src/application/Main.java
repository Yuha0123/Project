package application;

import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application implements Runnable {
	private long sec; // start ~ stop ������ ��(sec) ���庯�� 
	private Thread t;
	private Main mainClass; // �ش� Ŭ������ �ν��Ͻ��� �����ϴ� ���� 
	private Scene main; // Ÿ�̸Ӱ� �ִ� ȭ�� 
	private Font textFont; // ���� ��Ʈ 
	private Font timeFont; // ���� ��Ʈ 
	private boolean start; // ���� ���� 
	@FXML Text flow_time; // �ð� 
	@FXML ImageView background; // ��� 
	@FXML Button start_btn; // ���۹�ư
	@FXML Button stop_btn; // ������ư 

	@Override
	public void start(Stage primaryStage) {
		mainClass = this; // �ڽ� ��ü�� ���� 
		try {
			Scene loading = new Scene(new Pane(), 500, 400); // ũ��� ���� 500, ���� 400 �ȼ� 
			primaryStage.setScene(loading); //�� ȭ������ ȭ�� ���� 
			primaryStage.setResizable(false); // â ũ�� ���� �Ұ��� 
			primaryStage.sizeToScene(); // Scene�� ũ�⿡ �˸°� â ũ�� ������ 
			primaryStage.setTitle("Deemo Timer"); // Ÿ��Ʋ ���� ���� 
			primaryStage.show(); // â ���̱� 

			// �̹��� �ε� �� �۾��� �Ϸ����� ������ ��� ���α׷� ���� 
			if (!Init()) {
				System.exit(0);
			}
			
			// ���������� �Ϸ�Ǿ����� Ÿ�̸� ȭ�� ��� 
			primaryStage.setScene(main);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	// �̹���, ��Ʈ �� �ʱ� �۾� 
	private boolean Init() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml")); // fxml ������ �ε� 
			loader.setController(this); // ��Ʈ�ѷ��� �ڽ����� ���� 
			Parent root = (Parent) loader.load(); 
			main = new Scene(root, 500, 400); // ũ�� 500, 400
			textFont = Font.loadFont(this.getClass().getResource("../font/word.ttf").toExternalForm(), 28); // ��Ʈ �̸�, 28pt
			timeFont = Font.loadFont(this.getClass().getResource("../font/number.ttf").toExternalForm(), 72); // ��Ʈ �̸�, 72pt

			background.setImage(new Image(getClass().getResource("../image/background.png").toString())); // �̹��� �ҷ����� 

			start_btn.setFont(textFont); // ��Ʈ���� 
			start_btn.setOnAction(new EventHandler<ActionEvent>() { // ���۹�ư�� ������ �� 
				@Override
				public void handle(ActionEvent event) {
					start = true; // �ݺ� ���� true 
					start_btn.setDisable(true); // ���۹�ư ��Ȱ��ȭ 
					t = new Thread(mainClass); // Ÿ�̸� ������ ���� 
					t.setDaemon(true); 
					t.start(); // ������ ���� 
				}
			});

			stop_btn.setFont(textFont);
			stop_btn.setOnAction(new EventHandler<ActionEvent>() { // ������ư�� ������ �� 
				@Override
				public void handle(ActionEvent event) {
					if(t != null) {
						t.interrupt(); // �����忡 ���ͷ�Ʈ (����)
					}
				}
			});
			flow_time.setFont(timeFont);
			return true; // ��� �۾��� �������� true 
		} catch (Exception e) {
			return false; // ������ ������ false 
		}
	}

	@Override
	public void run() {
		sec = 0; // ���� �� 0�ʺ��� ī��Ʈ ���� 
		while (start) {
			try {
				TimeUnit.SECONDS.sleep(1); // 1�� ��� 
				sec++; // 1���� 
				flow_time.setText(getTime(sec)); // ������ �ʸ� ��:��:�� �� ��� 
			} catch (InterruptedException e) {
				start_btn.setDisable(false);
				start = false; // ���ͷ�Ʈ �߻� �� �ݺ� ����
			}
		}
	}

	// �ʸ� ��, ��, �ʷ� ��ȯ 
	private String getTime(long time) {
		int hour = (int) (time / 3600);
		int min = (int) (time % 3600 / 60);
		int sec = (int) (time % 60);
		return String.format("%02d : %02d : %02d", hour, min, sec);
	}

	// ���� 
	public static void main(String[] args) {
		launch(args);
	}
}
