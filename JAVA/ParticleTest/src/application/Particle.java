package application;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Paint;

public abstract class Particle {
	protected double x;
	protected double y;

	protected Point2D velocity; // �ӵ�
	protected double life = 1.0; // ���� (�ð�)
	protected double decay; // Life ���Ұ� ���� (expiteTime: �������� ��ƼŬ ���� ���ӵ�)

	protected Paint color; // ����
	protected BlendMode blendMode; //

	protected abstract boolean isAlive(); // ��ƼŬ ��������

	protected abstract void update(); // ��ƼŬ ������Ʈ

	protected abstract void render(GraphicsContext g); // ��ƼŬ �׸���
}
