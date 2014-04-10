package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;

/**
 * Created by chinana523 on 4/4/14.
 */
public class GameWindow extends Application {

    private GameModel model;
    private GridPane grid;
    private double initialX, initialY;

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        model = new GameModel(2048);
        model.printBoard();

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("2048");
        grid = new GridPane();
        addDraggableNode(grid);
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
                    model.up();
                    drawBoard(grid);
                } else if (keyEvent.getCode() == KeyCode.DOWN) {
                    model.down();
                    drawBoard(grid);
                } else if (keyEvent.getCode() == KeyCode.LEFT) {
                    model.left();
                    drawBoard(grid);
                } else if (keyEvent.getCode() == KeyCode.RIGHT) {
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
        if (model.getHasWon()) {
            Label winLabel = new Label("Congratulations! You won!");
            winLabel.setAlignment(Pos.CENTER);
            grid.add(winLabel, 0, 0);
        } else if (model.getHasLost()) {
            Label lossLabel = new Label("Too bad! You lost!");
            lossLabel.setAlignment(Pos.CENTER);
            grid.add(lossLabel, 0, 0);
        } else {
            for (int r = 0; r < model.getBoardLen(); r++) {
                for (int c = 0; c < model.getBoardLen(); c++) {
                    Tile tile = model.getTile(r, c);
                    Label label;
                    if (tile.isAlive()) {
                        label = new Label(String.valueOf(tile.getNumber()));
                        label.setId("label-" + String.valueOf(tile.getNumber()));
                    } else {
                        label = new Label();
                        label.setId("label-empty");
                    }
                    label.setMinHeight(100);
                    label.setMinWidth(100);
                    label.setAlignment(Pos.CENTER);
                    grid.add(label, c, r);
                }
            }
        }
    }

    // Code source: http://stackoverflow.com/questions/11780115/moving-an-undecorated-stage-in-javafx-2
    private void addDraggableNode(final Node node) {
        node.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (me.getButton() != MouseButton.MIDDLE) {
                    initialX = me.getSceneX();
                    initialY = me.getSceneY();
                }
            }
        });

        node.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (me.getButton() != MouseButton.MIDDLE) {
                    node.getScene().getWindow().setX(me.getScreenX() - initialX);
                    node.getScene().getWindow().setY(me.getScreenY() - initialY);
                }
            }
        });
    }
}
