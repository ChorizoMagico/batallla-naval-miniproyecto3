package com.example.batallanaval.controller;

import com.example.batallanaval.model.Boat;
import com.example.batallanaval.model.LogicBoard;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class PreparationViewController {

    private Scene mainScene;
    private Group waterSquare[];
    private Group defaultSquares[][];
    private LogicBoard playerBoard;

    @FXML
    private FlowPane showShapes;

    @FXML
    private GridPane boardGrid;

    @FXML
    private Button playButton;

    @FXML
    private Button returnButton;

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    @FXML
    private void initialize(){
        returnButton.setOnAction(this::handleReturn);
        playButton.setOnAction(this::handlePlay);
        playerBoard = new LogicBoard();
        showShapesFlowPane();
        createWaterSquare();
        loadGridPaneWithWater();
    }

    @FXML
    private void handleReturn(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(mainScene);
    }

    @FXML
    private void handlePlay(ActionEvent actionEvent) {
        // Cierra el fxml actual y abre el fxml del juego
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/batallanaval/FXML/game-view.fxml"));
            Parent root = fxmlLoader.load();

            // Cerramos la scene
            GameController gameController = fxmlLoader.getController();
            Stage currentStage = (Stage) playButton.getScene().getWindow();
            gameController.setMainScene(currentStage.getScene());

            // Mostramos la neuva scene
            Scene gameScene = new Scene(root);
            currentStage.setScene(gameScene);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Se termino la excepcion.");
        }
    }

    private void showShapesFlowPane(){
        for(int i=0; i<10; i++){
            showShapes.getChildren().add(playerBoard.getBoats().get(i).getShape());
        }
    }

    private void createWaterSquare() {
        waterSquare = new Group[100];
        for(int i=0; i<100; i++) {
            waterSquare[i] = new Group();
            Rectangle water = new Rectangle(41.0, 41.0);
            water.setArcWidth(5.0);
            water.setArcHeight(5.0);
            water.setFill(Color.DODGERBLUE);
            water.setStroke(Color.BLACK);
            water.setStrokeType(StrokeType.INSIDE);
            water.setStrokeWidth(1.0);

            waterSquare[i].getChildren().add(water);
        }

    }

    private void loadGridPaneWithWater() {
        int counter = 0;
        defaultSquares = new Group[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                defaultSquares[i][j] = waterSquare[counter];
                boardGrid.add(defaultSquares[i][j], j, i);
                counter++;
            }
        }
    }

    public void setSquareGraphic(int row, int column, Group newShape) {
        // Buscar nodos en esa celda
        Node presentNode = null;
        ObservableList<Node> children = boardGrid.getChildren();

        for (int i = 0; i < children.size(); i++) {
            Node node = children.get(i);
            Integer colIndex = GridPane.getColumnIndex(node);
            Integer rowIndex = GridPane.getRowIndex(node);

            if ((colIndex != null && colIndex == column) && (rowIndex != null && rowIndex == row)) {
                presentNode = node;
                break;
            }
        }

        // Si hay un nodo, se elimina
        if (presentNode != null) {
            boardGrid.getChildren().remove(presentNode);
        }

        // Agrega el nuevo nodo
        boardGrid.add(newShape, column, row);
    }
}
