package application;

import java.net.Socket;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class CreateController {
	@FXML TextField portField;
	@FXML Text portError;
	@FXML Button createBtn;
	@FXML ImageView backBtn;
	
	Socket socket;
	
	@FXML
	private void initialize() {
		backBtn.setOnMouseClicked((e)->{
			Main.program.playClickSound();
			Main.program.changeScene(SceneCode.MAIN);
		});
		
		backBtn.setOnMouseEntered((e)->{
			backBtn.setOpacity(0.5);
		});
		
		backBtn.setOnMouseExited((e)->{
			backBtn.setOpacity(1);
		});
		
		createBtn.setOnAction((event)->{
			String text = portField.getText();
			try {
				int port = Integer.parseInt(text);
				portError.setVisible(false);
				createServer(port);
			} catch(NumberFormatException e) {
				portError.setVisible(true);
			}
		});
	}
	
	// ���� �����۾��� �ٸ� �����忡�� ����
	private void createServer(final int port) {
		socket = null;
		Connection conn = new Connection(true);
		Main.program.changeScene(SceneCode.LOADING);
		Main.program.setLoadingMsg("Ŭ���̾�Ʈ ���� ��� ��..", conn);
		Thread thread = new Thread(()-> {
			socket = conn.createServer(port);
			if(socket != null) {
				Platform.runLater(new Runnable() {
	                @Override 
	                public void run() {
	                	Game game = new Game(socket, true);
	                }
	            });	
			}
		});
		thread.setDaemon(true);
		thread.start();
	}
}
