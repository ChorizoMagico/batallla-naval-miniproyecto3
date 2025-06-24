package com.example.batallanaval.model;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.List;

public class DrawBoard {



    /**
     * Fills the board of the game with blue stackpanes that behave like water
     */
    public void loadGridPaneWithWater(GridPane boardGrid, StackPane[][] boatsStack) {
        boardGrid.getChildren().clear();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                boatsStack[i][j] = new StackPane();
                boatsStack[i][j].setStyle("-fx-background-color: dodgerblue; -fx-border-color: black; -fx-border-width: 1;");
                boatsStack[i][j].setAlignment(Pos.TOP_LEFT);
                boardGrid.add(boatsStack[i][j], j, i);
            }
        }
    }

    public void loadShootsInGridPane(StackPane[][] boatsStack, LogicBoard playerBoard, boolean clear) {

        if(clear) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    boatsStack[i][j].getChildren().clear();
                }
            }
        }

        int[][] board= playerBoard.getBoard();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(board[i][j]==2){
                    ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource("/com/example/batallanaval/miss.png"))));
                    boatsStack[i][j].getChildren().add(imageView);
                } else if (board[i][j]==3) {
                    ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource("/com/example/batallanaval/hit.png"))));
                    boatsStack[i][j].getChildren().add(imageView);
                } else if (board[i][j]==4) {
                    ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource("/com/example/batallanaval/sinl.png"))));
                    boatsStack[i][j].getChildren().add(imageView);
                }
            }
        }
    }

    /**
     * Reloads the board with the boats placed
     */
    public void loadGridPane(StackPane[][] boatsStack, LogicBoard playerBoard) {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                boatsStack[i][j].getChildren().clear();
            }
        }

        List<Boat> boats = playerBoard.getBoats();
        for (int b = 0; b < boats.size(); b++) {
            Boat boat = boats.get(b);
            int[] position = boat.getPosition();
            int row = position[0];
            int col = position[1];

            for (int i = 0; i < boat.getSize(); i++) {
                int r;
                int c;

                if (boat.getIsHorizontal()) {
                    r = row;
                    c = col + i;
                } else {
                    r = row + i;
                    c = col;
                }

                Node segment = boat.createSegment(i);
                boatsStack[r][c].getChildren().add(segment);
            }
        }

        loadShootsInGridPane(boatsStack, playerBoard, false);
    }
}
