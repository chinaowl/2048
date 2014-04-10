package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.*;

/**
 * Created by chinana523 on 4/4/14.
 */
public class GameWindow extends Application {

    private GameModel model;
    private GridPane grid;

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        model = new GameModel(2048);
        model.printBoard();

        primaryStage.setTitle("2048");
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setAlignment(Pos.CENTER);

        drawBoard(grid);

        Scene scene = new Scene(grid, 500, 500);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.UP) {
                    System.out.println("Up arrow pressed!");
                    model.up();
                    drawBoard(grid);
                } else if (keyEvent.getCode() == KeyCode.DOWN) {
                    System.out.println("Down arrow pressed!");
                    model.down();
                    drawBoard(grid);
                } else if (keyEvent.getCode() == KeyCode.LEFT) {
                    System.out.println("Left arrow pressed!");
                    model.left();
                    drawBoard(grid);
                } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                    System.out.println("Right arrow pressed!");
                    model.right();
                    drawBoard(grid);
                }
            }
        });

        scene.getStylesheets().add("view/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawBoard(GridPane grid) {
        grid.getChildren().clear();
        for (int r = 0; r < model.getBoardLen(); r++) {
            for (int c = 0; c < model.getBoardLen(); c++) {
                Tile tile = model.getTile(r, c);
                Label label;
                if (tile.isAlive()) {
                    label = new Label(String.valueOf(tile.getNumber()));
                } else {
                    label = new Label();
                }
                label.setMinHeight(100);
                label.setMinWidth(100);
                label.setAlignment(Pos.CENTER);
                grid.add(label, c, r);
            }
        }
    }

}
