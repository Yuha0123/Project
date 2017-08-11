package application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
	private int red = 255, green = 70, blue = 20;
	private double rotate = 0;

	private Emitter emitter = new FireEmitter(); // ��ƼŬ �����ϴ� ��ü

	private List<CircleParticle> particles = new ArrayList<>();

	private GraphicsContext g;

	// �����Ӹ��� ȣ��Ǵ� �޼ҵ�
	private void onUpdate() {
		g.setGlobalAlpha(1.0); // ������, 0.0 ���� ~ 1.0 ������
		g.setGlobalBlendMode(BlendMode.SRC_OVER); // ���� ���� ������
		g.setFill(Color.BLACK); // ���������� ����
		g.fillRect(0, 0, 600, 600); // �簢�� �׸���

		// x, y, ����, ����, ����
		particles.addAll(emitter.emit(290, 300, 10, rotate, Color.rgb(red, green, blue))); // Emit �� ��ƼŬ ����Ʈ�� ��� �߰�

		for (Iterator<CircleParticle> it = particles.iterator(); it.hasNext();) {
			CircleParticle p = it.next();
			p.update();

			if (!p.isAlive()) { // ��ƼŬ �����ð��� ������
				it.remove(); // �ش� ��ƼŬ ����
				continue;
			}
			p.render(g);
		}
	}

	// Context ���� (�ر׸�)
	private Parent createContext() {
		Pane root = new Pane();
		root.setPrefSize(600, 600); // Pane�� ��, ���̸� 600px�� ����

		Canvas canvas = new Canvas(600, 600); // ĵ���� ����
		g = canvas.getGraphicsContext2D();

		Slider r = new Slider(0, 255, 255); // red �����̴�
		r.relocate(10, 10);
		r.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				red = newValue.intValue();
			}
		});

		Slider g = new Slider(0, 255, 70); // green �����̴�
		g.relocate(10, 30);
		g.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				green = newValue.intValue();
			}
		});

		Slider b = new Slider(0, 255, 20); // blue �����̴�
		b.relocate(10, 50);
		b.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				blue = newValue.intValue();
			}
		});

		Slider ro = new Slider(-5, 5, 0); // ���� �����̴�
		ro.relocate(10, 80);
		ro.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				rotate = newValue.doubleValue();
			}
		});

		root.getChildren().add(canvas);
		root.getChildren().addAll(r, g, b, ro);
		return root;
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setScene(new Scene(createContext()));
		primaryStage.setTitle("Paticles Effect");
		primaryStage.show();

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				// �� �����Ӹ��� ȣ���
				onUpdate();
			}
		};
		timer.start();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
