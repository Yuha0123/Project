package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Main extends Application {
	public static final int width = 800;
	public static final int height = 600;
	public GraphicsContext gc = null;
	public static Main program;
	private Stage stage;
	
	protected LoadingController loadingController;
	protected ErrorController errorController;
	
	private Scene main, create_server, join, loading, error;
	
	@SuppressWarnings("static-access")
	@Override
	public void start(Stage primaryStage) {
		this.program = this;
		this.stage = primaryStage;
		loadScenes();
		primaryStage.setScene(main);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	protected void changeScene(final SceneCode code) {
		Scene tempScene = null;
		switch(code) {
			case MAIN: {
				tempScene = main;
				break;
			}
			
			case CREATE_SERVER: {
				tempScene = create_server;
				break;
			}
			
			case JOIN: {
				tempScene = join;
				break;
			}
			
			case LOADING: {
				tempScene = loading;
				break;
			}
			
			case ERROR: {
				tempScene = error;
				break;
			}
		}
		stage.setScene(tempScene);
	}
	
	protected void gameStart(Scene scene) {
		stage.setScene(scene);
	}
	
	protected void setLoadingMsg(final String msg, Connection conn) {
		loadingController.setMsg(msg);
		loadingController.setConnector(conn);
	}
	
	protected void setErrorMsg(final String msg) {
		errorController.setMsg(msg);
	}
	
	protected void playClickSound() {
		Thread sound = new Thread(()->{
			Media media = new Media(getClass().getResource("../sound/hover.mp3").toString());
			MediaPlayer player = new MediaPlayer(media);
			player.play();
		});
		sound.setDaemon(true);
		sound.start();
	}
	
	// ����ȭ���� FXML������ �����Ͽ� �ε�
	private void loadScenes() {
		try {
			main = new Scene(FXMLLoader.load(getClass().getResource("Main.fxml")));
			create_server = new Scene(FXMLLoader.load(getClass().getResource("Create_server.fxml")));
			join = new Scene(FXMLLoader.load(getClass().getResource("Join.fxml")));
			
			FXMLLoader loader1 = new FXMLLoader();
			loader1.setLocation(getClass().getResource("Loading.fxml"));
			loader1.load();
			loadingController = loader1.getController();
			loading = new Scene(loader1.getRoot());
			
			FXMLLoader loader2 = new FXMLLoader();
			loader2.setLocation(getClass().getResource("Error.fxml"));
			loader2.load();
			errorController = loader2.getController();
			error = new Scene(loader2.getRoot());
		} catch(IOException e) {
			System.out.println("Load scene error");
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
