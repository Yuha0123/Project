package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
	public static final int width = 800;
	public static final int height = 600;
	public GraphicsContext gc = null;
	private boolean running;
	private final int playerSize = 20;
	private final int blockSize = 50;
	private final int SPEED = 1;
	
	private double staticX, staticY;
	private double x, y;
	private double x2, y2;
	private double showPosX, showPosY;
	// ��, ��, ��, ��
	private boolean moveNorth, moveSouth, moveEast, moveWest;
	
	private Scene main, game;
	
	private final char MAP[][] = new char[100][100];
	
	@Override
	public void start(Stage primaryStage) {
		try {
			/*staticX = Main.width/2-playerSize/2;
			staticY = Main.height/2-playerSize/2;
			x=0;
			y=0;
			x2 = 0;
			y2 = 0;*/
           
			main = mainScene();
			game = gameScene();
			primaryStage.setResizable(false);
			primaryStage.setScene(main);
			primaryStage.show();
			
			/*AnimationTimer game = new AnimationTimer() {
				@Override
				public void handle(long now) {
					final int speed = running ? SPEED*2 : SPEED; // �޸��� = �̵��ӵ�*2
					if(moveNorth && y-speed >= 0) y-=speed;
					if(moveSouth && y+speed <= 5000) y+=speed;
					if(moveEast && x+speed <= 5000) x+=speed;
					if(moveWest && x-speed >= 0) x-=speed;
			    	draw();
				}
			};
			game.start();*/
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// ����ȭ���� FXML������ �����Ͽ� �ε�
	private Scene mainScene() throws IOException {
		return new Scene(FXMLLoader.load(getClass().getResource("main.fxml")));
	}
	
	private Scene gameScene() {
		Group root = new Group();
		Canvas canvas = new Canvas(Main.width, Main.height);
		gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		Scene scene = new Scene(root);
		
		// Ű���� Ű�� ������ �� 
		scene.setOnKeyPressed((event)->{
		    switch (event.getCode()) {
		    	case W:    
                    moveNorth = true;
                    break;
                    	
                case A: 
                    moveWest = true;
                    break;
                    	
                case S:
                    moveSouth = true;
                    break;
                    	
                case D: 
                    moveEast = true;
                    break;
                    
                case SHIFT: 
                    running = true;
                    break;
                    
                //case ESCAPE:
            }
		});
		
		// Ű���� Ű�� ������ �� 
		scene.setOnKeyReleased((event)->{
		    switch (event.getCode()) {
		    case W:    
		    	moveNorth = false;
                break;
                    	
            case A: 
                moveWest = false;
                break;
                    	
            case S:
                moveSouth = false;
                break;
                    	
            case D: 
                moveEast = false;
                break;
                
            case SHIFT:
            	running = false;
            	break;
            }
		    draw();
		});
		
		// ���콺 �̵� �̺�Ʈ
		scene.setOnMouseMoved((event)->{
			//System.out.println(event.getX() + "/" + event.getY());
			showPosX = event.getX();
			showPosY = event.getY();
		});
		
		// ���콺 �巡�� �̺�Ʈ(Ŭ�� + �̵�)
		scene.setOnMouseDragged((event)->{
			//System.out.println(event);
			showPosX = event.getX();
			showPosY = event.getY();
			if(MouseButton.PRIMARY == event.getButton()) {
				System.out.println("Shoot"); // ���콺 ù��° Ű: �߻� 
			}
		});
		
		return scene;
	}
	
	// �� �ε�
	private void loadMap(final String name) {
		File target = new File("map/" + name);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(target));
			String s;
			int line = 0;
			while((s = reader.readLine()) != null) {
				for(int i=0; i<100; i++) MAP[line][i] = s.charAt(i);
				line++;
			}
			System.out.println("Map load done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Map load fail");
		}
	}
	
	private void draw() {
		// �÷��̾� ��ġ�������� ���°�ε������� ������� ���
		int x_ = (int)Math.floor(x/800 * 16)-8;
		int y_ = (int)Math.floor(y/600 * 12)-6;
		for(int height=0; height<13; height++) { // height block: 12 +1
			for(int width=0; width<25; width++) { // width block: 16 +1
				try {
					char tempBlock = MAP[y_+height][x_+width];
					if(tempBlock == '0') { // �ܵ� 
						gc.setFill(Color.YELLOWGREEN);
						gc.setStroke(Color.GREEN);
					} else if(tempBlock == '1') { // ����  
						gc.setFill(Color.LIGHTGRAY);
						gc.setStroke(Color.GRAY);
					} else if(tempBlock == '2') { // �� 
						gc.setFill(Color.BEIGE);
						gc.setStroke(Color.SANDYBROWN);
					}
				} catch(ArrayIndexOutOfBoundsException e) {
					gc.setFill(Color.DODGERBLUE);
					gc.setStroke(Color.ALICEBLUE);
				}
				
				double drawX = (width*blockSize-(x%50));
				double drawY = (height*blockSize-(y%50));
				gc.fillRect(drawX, drawY, blockSize, blockSize);
				gc.strokeRect(drawX, drawY, blockSize, blockSize);
			}
		}
		
		// �÷��̾� 
		gc.setFill(Color.BEIGE);
		gc.setStroke(Color.BROWN);
		gc.fillOval(staticX, staticY, playerSize, playerSize);
		gc.strokeOval(staticX, staticY, playerSize, playerSize);
		
		int circleSize = 600; // �� ũ��(����)
		//gc.setFill(Color.RED);
		gc.setStroke(Color.BLUE);
		gc.strokeOval((x2+400-circleSize/2)-x, (y2-300+circleSize/2)-y, circleSize, circleSize);
		
		
		
		// Line (Aim, �þ�)
		gc.setFill(Color.RED);
		gc.setStroke(Color.RED);
		gc.strokeLine(staticX+playerSize/2, staticY+playerSize/2, showPosX, showPosY);
		gc.fillOval(showPosX-3, showPosY-3, 6, 6);
		gc.strokeOval(showPosX-10, showPosY-10, 20, 20);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
