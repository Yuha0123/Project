package p2p;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Main extends JFrame {
	private JTextField ip;
	private JTextField port;
	private JTextField serv_port;
	private JButton server_open;
	private JButton connect;
	private JButton disconnect;
	private JTextArea log;
	
	private Reciver reciver = null;
	private DataOutputStream out;
	private boolean connected = false;

	private Socket socket = null;	
	private ServerSocket server = null;
	
	public Main() throws Exception{
		init();
	}
	
	// ������ ���� �� ���̾ƿ� ����, �̺�Ʈ ó��
	private void init() {
		setTitle("P2P Test");
		setSize(300, 400);
		setResizable(false);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ip = new JTextField(9);
		port = new JTextField(4);
		serv_port = new JTextField(5);
		
		server_open = new JButton("Start");
		server_open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					int _port = Integer.parseInt(serv_port.getText()); // �Էµ� �ؽ�Ʈ�� ���ڷ� ��ȯ
					server(_port); // �Է��� ��Ʈ ��ȣ�� ���� ����
				} catch(NumberFormatException e) { // ��ȯ ����
					logger("Port error");
				}
			}
		});
		
		connect = new JButton("Connect");
		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					int _port = Integer.parseInt(port.getText()); // ���� ��ȯ
					String _ip = ip.getText();
					if(!_ip.equals("")) { //������ �ƴҰ�� ��û �õ�
						client(_ip, _port);
					} else {
						logger("You can't connect to server");
					}
				} catch(NumberFormatException e) { // ���� ��ȯ ����
					logger("Error in port");
				}	
			}
		});
		
		disconnect = new JButton("Disconnect");
		disconnect.addActionListener(new ActionListener() { // ���� ���Ӳ���
			@Override
			public void actionPerformed(ActionEvent arg0) { 
				disconnect();
			}
		});
		
		log = new JTextArea();
		JScrollPane scroll = new JScrollPane(log); // ������ �������� ��ũ���� �����ϵ���
		log.setEditable(false);
		
		JPanel client = new JPanel();
		JPanel server = new JPanel();
		JPanel top = new JPanel();
		
		JPanel menu = new JPanel(new GridLayout(2, 1));
		
		server.add(serv_port);
		server.add(server_open);
		server.setBorder(new TitledBorder(new EtchedBorder(), "Server"));
		top.add(disconnect);
		top.add(server);
		menu.add(top);
		
		client.add(ip);
		client.add(new JLabel(":"));
		client.add(port);
		client.add(connect);
		client.setBorder(new TitledBorder(new EtchedBorder(), "Client"));
		menu.add(client);
		menu.setBorder(new TitledBorder(new EtchedBorder(), "Menu"));
		
		log.setBorder(new TitledBorder(new EtchedBorder(), "Log"));
		
		JTextField input = new JTextField(3);
		input.addKeyListener(new KeyAdapter() { // Ű ������
			@Override
			public void keyPressed(KeyEvent e) {
				input.setText("");
				if(connected) { // ����Ǿ������� ��뿡�� ������ ����
					try {
						out.writeUTF(e.getKeyChar() + ""); 
					} catch(Exception e1) {
						disconnect();
					}
				}				
			}
		});
		add(scroll, BorderLayout.CENTER);
		add(menu, BorderLayout.SOUTH);
		add(input, BorderLayout.NORTH);
		setVisible(true);
	}
	
	// Ŭ���̾�Ʈ ���� ���� �� ��û
	private void client(String ip, int port) {
		Thread t = new Thread(new Runnable() { // ������ �����忡�� �����۾� ����
			@Override
			public void run() {
				setAllenabled(false);
				try {
					socket = new Socket(ip, port);
					logger("Connected!");
					
					InputStream in = socket.getInputStream(); // Input ��Ʈ�� �������� (����)
					out = new DataOutputStream(socket.getOutputStream()); // Output ��Ʈ�� �������� (����)

					reciver = new Reciver(new DataInputStream(in));
					reciver.start();
					connected = true; // ���������� true
				} catch(Exception e) {
					disconnect();
				}
			}
		});
		t.setDaemon(true); // ���󽺷��� 
		t.start(); // ���� �����ĵ� ���� �����մϴ�
	}
	
	// ���� ���� ���� �� ��û ���
	private void server(int port) {
		Thread t = new Thread(new Runnable() {	
			@Override
			public void run() {
				setAllenabled(false);
				try {
					server = new ServerSocket(port);
					logger("Waiting client..");
					socket = server.accept();
					logger("Client connected!");
					
					InputStream in = socket.getInputStream();
					out = new DataOutputStream(socket.getOutputStream());

					reciver = new Reciver(new DataInputStream(in));
					reciver.start();
					connected = true; // ���������� true
				} catch(Exception e) {
					disconnect();
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}
	
	// ��� �Է� ������Ʈ Disable, Enable
	private void setAllenabled(boolean enabled) {
		serv_port.setEnabled(enabled);
		server_open.setEnabled(enabled);
		ip.setEnabled(enabled);
		port.setEnabled(enabled);
		connect.setEnabled(enabled);	
	}
	
	// �α� �����
	public void logger(String msg) {
		log.append(msg + System.lineSeparator());
		log.setCaretPosition(log.getDocument().getLength()); // �� �Ʒ��� ��ũ��
	}
	
	// ����, �������� �ݱ�
	private void disconnect() {
		// ����
		try {
			connected = false;
			setAllenabled(true);
			socket.close();
			logger("Disconnected");
		} catch (Exception e) {
			logger("Disconnect failed");
		}
		
		// ��������
		try {
			server.close();
			logger("Server closed");
		} catch (Exception e) {
			logger("Server close error");
		}
	}
	
	// ������ ���� Ŭ����
	class Reciver extends Thread {
		private DataInputStream in = null;
		
		public Reciver(DataInputStream in) {
			this.in= in; // InputStream ����
		}
		
		@Override
		public void run() {
			while(in != null) {
				try {
					String s = in.readUTF(); // �о����
					logger("Received: " + s);
				} catch(Exception e) {
					disconnect();
				}
			}
		}
	}
	
	// Main
	public static void main(String[] args) {
		try {
			new Main(); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
