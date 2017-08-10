import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Main extends JFrame {
	private final int WIDTH = 500;
	private final int HEIGHT = 600;

	private boolean DbufferOn = false;
	private boolean move_down = true;

	private final int x = 100;
	private final int y_MAX = 200;
	private final int y_MIN = 50;
	private int y1, y2;

	private Image iconImage = new ImageIcon(Main.class.getResource("images/test.png")).getImage();
	private Image img;
	private Graphics img_g;

	private ImageIcon button_normal = new ImageIcon(Main.class.getResource("images/button_start.png")); //���ҽ� �̹��� ���
	private ImageIcon button_entered = new ImageIcon(Main.class.getResource("images/button_start_entered.png"));

	public Main() {
		setTitle("Double Buffering");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLayout(null);
		
		y1 = y_MIN;
		y2 = y_MAX;

		JButton btn = new JButton(button_normal); //button_normal�̹����� ��ư����
		btn.setBounds(100, 300, 300, 100); //x, y �������� �ʺ� 300, ���� 100 ��ư
		btn.setBorderPainted(false); //�׵θ� X
		btn.setContentAreaFilled(false); //�̹������� ������ ���� ��ä��� ��Ȱ��
		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				//���콺�� ��ư���� �ö��� �� (����)
				
				btn.setIcon(button_entered);
				btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				//���콺�� ��ư ������ ���� ��
				
				btn.setIcon(button_normal);
				btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				DbufferOn = !DbufferOn;
			}
		});
		add(btn); //��ư�� �����̳����� ��ġ
	}

	public void paint(Graphics g) {
		//������۸� Off
		if (DbufferOn == false) {
			paintComponents(g);
			g.drawLine(x, y1, x + 300, y2);
			g.drawImage(iconImage, 400, 500, null);
			g.drawString("Double Buffer : Off", 100, 500);
		} else { 
			//������۸� On
			//-�Ʒ� ������ ȭ�鿡 �ٷ� �׸��� �ʰ� �޸� ������ �̷����-//
			img = createImage(WIDTH, HEIGHT);
			img_g = img.getGraphics();
			paintComponents(img_g);
			img_g.drawLine(x, y1, x + 300, y2);
			img_g.drawImage(iconImage, 400, 500, null);
			img_g.drawString("Double Buffer : On ", 100, 500);
			//----------------------------------------------------//
			g.drawImage(img, 0, 0, null); //���� ������ ��ģ �̹����� �ѹ��� ȭ�鿡 �׸�
		}
		
		YposChange(); //������ ��ǥ�� �����ϴ� �޼ҵ�
		repaint(); //������ paint�Լ� ȣ�� 
	}

	public void YposChange() {
		if (move_down) {
			y1++;
			y2--;
			if (y1 >= y_MAX) {
				move_down = !move_down;
			}
		}

		if (!move_down) {
			y1--;
			y2++;
			if (y1 <= y_MIN) {
				move_down = !move_down;
			}
		}
	}

	public static void main(String[] args) {
		new Main();
	}
}