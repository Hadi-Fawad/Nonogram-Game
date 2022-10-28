package edu.ou.cs2334.project5;

import edu.ou.cs2334.project5.presenters.NonogramPresenter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	private final static int IDX_CELL_SIZE = 0;
	private final static int DEFAULT_CELL_SIZE = 30;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		var cellSize = getParameters().getUnnamed();
		NonogramPresenter presenter;
		if(cellSize != null) {
			int length = Integer.parseInt(cellSize.get(IDX_CELL_SIZE));
			presenter = new NonogramPresenter(length);
		}
		else {
			presenter = new NonogramPresenter(DEFAULT_CELL_SIZE);
		}
		Scene scene = new Scene(presenter.getPane());
		scene.getStylesheets().add("/style.css");
		primaryStage.setScene(scene);
		primaryStage.setTitle("Nonogram");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}
