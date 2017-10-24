package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateServerController implements Initializable, Runnable {
	private final int SEC = 15; // 15�� ���Ӵ��
	private ServerSocket server = null;
	private int PORT;
	
	@FXML Button create_server_btn;
	@FXML Button back_btn;
	@FXML TextArea server_log_area;
	@FXML TextField port_area;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		create_server_btn.setOnAction((event)->{
			String data = port_area.getText(); // ������ ��������
			try {
				PORT = Integer.parseInt(data); // ���ڿ��� ������ ��ȯ
				RunApp.logger.printlnLog("Server port: " + PORT); 
				create_server_btn.setDisable(true); // ��ư ��Ȱ��ȭ
				server_log_area.setText("");
				port_area.setDisable(true); 
				Thread t = new Thread(this); // ��Ʈ��ũ �۾� ������
				t.setDaemon(true);
				t.start();
			} catch(NumberFormatException e) {
				RunApp.logger.printlnLog("Number parse error!");
			}
		});
		
		back_btn.setOnAction((event)->{
			try {
				// ������ �����ְų�, ������ �����Ǿ������� ���� �ݱ�
				if(!server.isClosed() || !(server==null)) {
					server.close();
				}
			} catch (IOException e) {
				RunApp.logger.printlnLog("Server close Error!");
				e.printStackTrace();
			}
			server_log_area.setText("");
			Stage n = (Stage)((Node)event.getSource()).getScene().getWindow();
			n.setScene(RunApp.mainScene);
		});
	}
	
	// �α� �߰��ϱ� 
	private void addLog(String msg) {
		String str = server_log_area.getText();
		str += msg + "\n";
		server_log_area.setText(str);
	}

	@Override
	public void run() {
		server = null;
		try {
			// ���� ���� 
			server = new ServerSocket(PORT);
			server.setSoTimeout(SEC * 1000);
		} catch (IOException e) {
			addLog("Create server failed.");
			RunApp.logger.printlnLog("Create server failed.");
			return;
		}
		
		RunApp.socket = null;
		boolean wait = true;
		while(wait) {
			if(!server.isClosed()) {
				try {
					addLog("Starting server.. waiting client");
					addLog("Connection timeout : 15 sec");
					RunApp.logger.printlnLog("Starting server.. waiting client");
					RunApp.socket = server.accept();
					RunApp.logger.printlnLog(RunApp.socket.getInetAddress().toString());
				} catch (SocketTimeoutException timeout) {
					wait = false;
					try {
						server.close();
					} catch (IOException e) {}
					addLog("Server timeout.");
					RunApp.logger.printlnLog("Server timeout.");
				} catch (SocketException e1) {
					RunApp.logger.printlnLog("Server close");
				} catch (IOException e2) {
					addLog("Connect error.");
					RunApp.logger.printlnLog("Connect error.");
					e2.printStackTrace();
				}
			} else {
				wait = false;
			}
		}
		
		// �ٽ� Ȱ��ȭ
		create_server_btn.setDisable(false);
		port_area.setDisable(false);
	}
}
