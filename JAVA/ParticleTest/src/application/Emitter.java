package application;

import java.util.List;

import javafx.scene.paint.Color;

// Emit : �����ϴ�
public abstract class Emitter {
	public abstract List<CircleParticle> emit(double x, double y, int count, double rotate, Color c);
}
