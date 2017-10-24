package application;

import java.io.IOException;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RunApp extends Application {
	/* Scene ȭ�� �ڵ� */
	protected static final int MAIN_SCENE = 0;
	protected static final int CREATE_SERVER_SCENE = 1;
	protected static final int JOIN_SCENE = 2;
	protected static final int IN_GAME_SCENE = 3;

	/* Scene �������� */
	protected static Scene mainScene;
	protected static Scene createScene;
	protected static Scene joinScene;
	protected static Scene infoScene;
	protected static Scene inGameScene;
	
	/* Ŭ���̾�Ʈ ���� */
	protected static Socket socket;
	
	/* ���α׷� �α� ���� Ŭ���� */
	protected static GameLogger logger;
	
	/* ������ Scene �ӽ� ���� ���� */
	private Scene tempScene;
	
	@Override
	public void start(Stage primaryStage) {		
		init(primaryStage);
	}
	
	/* ���α׷� ���� �ʱ��۾� */
	private void init(Stage stage) {
		logger = new GameLogger();
		try {
			logger.printlnLog("Resources loading start.");
			Parent root = FXMLLoader.load(getClass().getResource("../layout/loading.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("Spell Runner");
			stage.setScene(scene);
			stage.sizeToScene();
			stage.setResizable(false);
			stage.show();
			
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					initMainScene();
					initCreateServerScene();
					initJoinScene();
					initInfoScene();
					setScene(stage, MAIN_SCENE);
				}
			});
			t.setDaemon(true);
			t.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/* ���� Scene */
	private void initMainScene() {
		logger.printLog("init main scene..");
		try {
			mainScene = new Scene(FXMLLoader.load(getClass().getResource("../layout/main.fxml")));
		} catch (IOException e) {
			logger.printErrorLog();
			e.printStackTrace();
		}
		logger.printDoneLog();
	}
	
	/* �������� Scene */
	protected void initCreateServerScene() {
		logger.printLog("init create server scene..");
		try {
			createScene = new Scene(FXMLLoader.load(getClass().getResource("../layout/create.fxml")));
		} catch (IOException e) {
			logger.printErrorLog();
			e.printStackTrace();
		}
		logger.printDoneLog();
	}
	
	/* �������� Scene */
	private void initJoinScene() {
		logger.printLog("init join scene..");
		try {
			joinScene = new Scene(FXMLLoader.load(getClass().getResource("../layout/join.fxml")));
		} catch (IOException e) {
			logger.printErrorLog();
			e.printStackTrace();
		}
		logger.printDoneLog();
	}
	
	/* ���� Scene */
	private void initInfoScene() {
		logger.printLog("init info scene..");
		try {
			joinScene = new Scene(FXMLLoader.load(getClass().getResource("../layout/info.fxml")));
		} catch (IOException e) {
			logger.printErrorLog();
			e.printStackTrace();
		}
		logger.printDoneLog();
	}
	
	/* Scene ���� */
	protected void setScene(Stage stage, final int code) {
		switch(code)
		{
		case MAIN_SCENE:
			tempScene = mainScene;
			break;
			
		case CREATE_SERVER_SCENE:
			tempScene = null;
			break;
			
		case JOIN_SCENE:
			tempScene = null;
			break;
			
		case IN_GAME_SCENE:
			tempScene = null;
			break;
			
		default:
			tempScene = mainScene;
			break;
		}
		
		
		Platform.runLater(()->{
			stage.setScene(tempScene);
		});
	}
}
