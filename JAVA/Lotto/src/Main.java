import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main extends JFrame implements Runnable{
	final String base_url = "http://www.nlotto.co.kr/gameResult.do?method=byWin&drwNo=";
	JLabel label_1, label_2, label_3, label_4, label_5, label_6, label_7, label_b;
	String[] lotto_num = new String[8]; //�ζ� ��ȣ ����迭
	String ep_num; //ȸ�� ��ȣ
	JPanel mov_btn_layout; //��� ��ư�� ��ġ�� �г�
	JPanel number_layout; //���� �̹����� ��ġ�� �г�
	JPanel input_layout; //�ϴܿ� �ؽ�Ʈ�ʵ�, ��ư�� ��ġ�� �г�
	JButton prev_btn = new JButton("����");
	JButton next_btn = new JButton("����");
	JButton refresh_btn = new JButton("�ش� ȸ����");
	JLabel print_ep; //ȸ����ȣ�� ������ ��
	ImageIcon img_1, img_2, img_3, img_4, img_5, img_6, img_7;
	int Max_ep = 0;
	
	//������
	public Main(){
		Init();
		refresh("");
	}
	
	//Gui �ʱ⼳��
	public void Init(){
		super.setTitle("Lotto Number");
		label_1 = new JLabel();
		label_2 = new JLabel();
		label_3 = new JLabel();
		label_4 = new JLabel();
		label_5 = new JLabel();
		label_6 = new JLabel();
		label_7 = new JLabel();
		label_b = new JLabel("+");
		
		number_layout = new JPanel();
		number_layout.setLayout(new FlowLayout());
		
		mov_btn_layout = new JPanel();
		mov_btn_layout.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 5));
		
		input_layout = new JPanel();
		input_layout.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		print_ep = new JLabel("0");
		
		//�ؽ�Ʈ �Է�ĭ�� ũ��: �ִ� 5���ڱ��� ����
		JTextField text = new JTextField(5);
		
		
		//�ش� ��ư�� ������ �ۼ�
		prev_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = StringToInt(lotto_num[7]);
				if(i-1 > 0){
					i-=1;
					lotto_num[7] = Integer.toString(i);
					refresh(lotto_num[7]); //���� ȸ���� ���ΰ�ħ
				}
			}
		});
		
		next_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = StringToInt(lotto_num[7]);
				if(i+1 <= Max_ep){
					i+=1;
					lotto_num[7] = Integer.toString(i);
					refresh(lotto_num[7]); //���� ȸ���� ���ΰ�ħ
				}
			}
		});

		refresh_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int n = StringToInt(text.getText());
				
				//����ڰ� �Է��� ȸ���� MAX ȸ������ ũ�� ���� ��� ������ �ҷ���.
				if(n <= Max_ep){
					lotto_num[7] = getNumberOnly(text.getText());
					text.setText(null);
					refresh(getNumberOnly(lotto_num[7]));
				}
			}
		});
		
		
		//�гο� �� ��ġ
		number_layout.add(label_1);
		number_layout.add(label_2);
		number_layout.add(label_3);
		number_layout.add(label_4);
		number_layout.add(label_5);
		number_layout.add(label_6);
		number_layout.add(label_b);
		number_layout.add(label_7);
        //�гο� ��ư ��ġ
		mov_btn_layout.add(prev_btn);
		mov_btn_layout.add(print_ep);
		mov_btn_layout.add(next_btn);
		
		
		input_layout.add(text);
		input_layout.add(refresh_btn);
		
		//���� �гε��� �����̳ʿ� ��ġ
		add("North", mov_btn_layout);
		add("Center", number_layout);
		add("South", input_layout);
		
		
		setSize(450, 200); //�����̳� ũ�� ����
		setResizable(false); //ũ�� ���� �Ұ�	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //X��ư�� ������ ����
		setVisible(true); //ȭ�鿡 ǥ��
	}
	
	//�ش� Url�� �̹��� ������ �ҷ��ͼ� BufferedImage�� ��ȯ
	public BufferedImage getUrlImage(String num){ 
		URL url;
		try {
			url = new URL("http://www.nlotto.co.kr/img/common_new/ball_" + num + ".png");
			BufferedImage img = null;
			try {
				img = ImageIO.read(url);
			} catch (IOException e) {
				System.out.println("E: Image Load failed!");
			}		
			return img;
		} catch (MalformedURLException e) {
			System.out.println("E: Connection Error!");
			return null;
		}
	}
	
	
	//���ڿ��� ���ڸ� �����ؼ� ��ȯ
	public String getNumberOnly(String data){
		return data.replaceAll("\\D","");
	}
	
	//���ڿ��� ������ ��ȯ�ؼ� ��ȯ
	public int StringToInt(String data){
		return Integer.parseInt(data);
	}
	
	
	//�ٸ� ȸ���� �̵� ��, �ؽ�Ʈ ������Ʈ
	public void update(){
		if(Max_ep == 0){
			//ù ����� ���� �ֱ��� ȸ����ȣ�� ������. 
			int i = Integer.parseInt(lotto_num[7]);
			Max_ep = i;
		}
		
		//lotto_num �迭�� 7�� �ε������� ����� ȸ����ȣ�� ��ϵǾ�����
		print_ep.setText(lotto_num[7]+" ȸ��");
	}
	
	
	//���ͳݿ� �����Ͽ� �����͸� �޾ƿ��� Thread ����
	public void refresh(String num){
		Thread thread = new Thread(this);
		thread.start();
	}
	
	
	//���α׷��� �߽��� �����ϰ��ִ� ������(�����͸� �޾ƿ�)
	@Override
	public void run() {
		//�����忡�� �����͸� �޾ƿ��µ��� ��ư�� ��Ȱ��ȭ ��Ŵ
		prev_btn.setEnabled(false);
		next_btn.setEnabled(false);
		refresh_btn.setEnabled(false);
		print_ep.setText("Loading..");
		System.out.print("Connecting..");
		try{
			//�ش� �ּҷ� �����Ͽ� HTML ������ �о��
			Document doc = Jsoup.connect(base_url+lotto_num[7]).get();
			System.out.println(" OK!");
			
			//�޾ƿ� �������� �ʿ��� �κи� �Ľ�
			Elements contents = doc.select("h3.result_title strong");
			lotto_num[7] = contents.toString().replaceAll("\\D","");
			System.out.print(lotto_num[7] + "ȸ Data Loading.. ");
			
			contents = doc.select("p.number img");
			int i=0;
			
			//�Ľ��� ������ ��, �ζ� ��ȣ�� �ٽ� �Ľ��Ͽ� �迭�� ���ʷ� ���� 
			for(Element e : contents){
				lotto_num[i++] = e.attr("alt");
				//System.out.println(lotto_num[i++]);
			}
			System.out.println("DONE!");
			
			//getUrlImage() �޼ҵ带 ���Ͽ� ���ͳݿ��� ���� �ҷ���
			img_1 = new ImageIcon(getUrlImage(lotto_num[0]));
			img_2 = new ImageIcon(getUrlImage(lotto_num[1]));
			img_3 = new ImageIcon(getUrlImage(lotto_num[2]));
			img_4 = new ImageIcon(getUrlImage(lotto_num[3]));
			img_5 = new ImageIcon(getUrlImage(lotto_num[4]));
			img_6 = new ImageIcon(getUrlImage(lotto_num[5]));
			img_7 = new ImageIcon(getUrlImage(lotto_num[6]));
			System.out.println("Image load DONE!");
			//�ҷ��� �̹����� �󺧿� ����
			label_1.setIcon(img_1);
			label_2.setIcon(img_2);
			label_3.setIcon(img_3);
			label_4.setIcon(img_4);
			label_5.setIcon(img_5);
			label_6.setIcon(img_6);
			label_7.setIcon(img_7);
			update();
		}
		catch(Exception e){
			//���� �߻� �� ������ ����
			System.out.println("E: Connection Error!");
			e.getStackTrace();
		}
		finally {
			/* ���� ���ο� ������� ���� 
			 * ��ư�� �ٽ� Ȱ��ȭ ��Ŵ */
			prev_btn.setEnabled(true);
			next_btn.setEnabled(true);
			refresh_btn.setEnabled(true);
		}
	}
	
	public static void main(String[] args) {
		new Main(); //���� �޼ҵ忡�� Gui�� �����ϰ� ����
	}
}