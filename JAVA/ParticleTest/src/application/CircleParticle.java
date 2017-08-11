package application;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Paint;

public class CircleParticle extends Particle {
	private double radius; // ������

	public CircleParticle(double x, double y, Point2D velociry, double radius, double expireTime, Paint color,
			BlendMode blendMode) {
		this.x = x;
		this.y = y;
		this.velocity = velociry;
		this.radius = radius;
		this.color = color;
		this.blendMode = blendMode;
		this.decay = 0.1 / expireTime;
	}

	@Override
	public boolean isAlive() {
		return life > 0;
	}

	@Override
	public void update() {
		x += velocity.getX(); // X��ŭ �̵� (��, ��)
		y -= velocity.getY(); // Y��ŭ ����
		life -= decay;
	}

	@Override
	public void render(GraphicsContext g) {
		g.setGlobalAlpha(life); // ���� life ��ŭ ���İ�(����) ����
		g.setGlobalBlendMode(blendMode);
		g.setFill(color); // ����
		g.fillOval(x, y, radius, radius); // �� �׸���
	}
}
