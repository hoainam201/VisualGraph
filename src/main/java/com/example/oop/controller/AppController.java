package com.example.oop.controller;

import com.example.oop.algorithm.DFS;
import com.example.oop.algorithm.Dijkstra;
import com.example.oop.model.Edge;
import com.example.oop.model.Graph;
import com.example.oop.model.Vertex;
import com.example.oop.step.PseudoStep;
import com.example.oop.step.soloStep;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    @FXML
    public Label textOfShowPanel;
    @FXML
    public Button showPanel;
    @FXML
    public Button codeTraceButton;
    @FXML
    public Button statusButton;
    @FXML
    public VBox panel;
    @FXML
    public Label textOfShowStatus;
    @FXML
    public Label textOfShowCodeTrace;
    @FXML
    public TextFlow status;
    @FXML
    public TextFlow codeTrace;
    @FXML
    public AnchorPane main;
    @FXML
    public Button newGraph;
    @FXML
    public Button dfs;
    @FXML
    public Button db;
    @FXML
    public Button djs;
    @FXML
    public Button exitButton;
    @FXML
    public ToolBar DFSBox;
    @FXML
    public ToolBar DPBox;
    @FXML
    public ToolBar djsBox;
    @FXML
    public TextArea DFSGo;
    @FXML
    public TextArea DPGo;
    @FXML
    public TextArea djsGo;
    @FXML
    public TextFlow message;

    private boolean panelVisable = false;

    Graph graph = new Graph();
    private boolean isDrag = false;
    private boolean creatButton = false;
    Robot robot = new Robot();
    Edge currentLine;


    static class InitMenu {
        TranslateTransition translateTransition;
        FadeTransition fadeTransition;
        RotateTransition rotateTransition;

        Node container;
        boolean isShow = false;

        public InitMenu(Node container, Label label) {
            this.container = container;
            translateTransition = new TranslateTransition(Duration.millis(100), container);
            fadeTransition = new FadeTransition(Duration.millis(100), container);
            rotateTransition = new RotateTransition(Duration.millis(100), label);
        }

        public InitMenu(Node container) {
            this.container = container;
            translateTransition = new TranslateTransition(Duration.millis(100), container);
            fadeTransition = new FadeTransition(Duration.millis(100), container);
        }

        public void play(int byX) {
            if (!isShow) {
                container.setVisible(true);
                translateTransition.setByX(byX);
                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1);
                rotateTransition.setByAngle(180);
                translateTransition.play();
                fadeTransition.play();
                rotateTransition.play();
                isShow = true;
            } else {
                translateTransition.setByX(-byX);
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0);
                rotateTransition.setByAngle(-180);
                translateTransition.play();
                fadeTransition.play();
                rotateTransition.play();
                PauseTransition p = new PauseTransition(Duration.millis(100));
                p.setOnFinished(actionEvent -> container.setVisible(false));
                p.play();
                isShow = false;
            }
        }
    }

    InitMenu initPanel, initStatus, initCodeTrace;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initPanel = new InitMenu(panel, textOfShowPanel);
        initStatus = new InitMenu(status, textOfShowStatus);
        initCodeTrace = new InitMenu(codeTrace, textOfShowCodeTrace);
        main.setFocusTraversable(true);
    }

    public void showPanel() {
        initPanel.play(20);
        panelVisable = !panelVisable;
        if (!panelVisable) {
            DFSBox.setVisible(false);
            DPBox.setVisible(false);
            djsBox.setVisible(false);
        }

    }

    public void showStatus() {
        initStatus.play(-50);
    }

    public void showCodeTrace() {
        initCodeTrace.play(-50);
    }

    public void showDfsBox() {
        if (isEmpty())
            return;
        newGraph.setText("On Draw");
        creatButton = false;
        DFSBox.setVisible(!DFSBox.isVisible());
        DPBox.setVisible(false);
        djsBox.setVisible(false);
    }

    public void showDPBox() {
        if (isEmpty())
            return;
        newGraph.setText("On Draw");
        creatButton = false;
        DPBox.setVisible(!DPBox.isVisible());
        DFSBox.setVisible(false);
        djsBox.setVisible(false);
    }

    public void showDijBox() {
        if (isEmpty())
            return;
        newGraph.setText("On Draw");
        creatButton = false;
        djsBox.setVisible(!djsBox.isVisible());
        DFSBox.setVisible(false);
        DPBox.setVisible(false);
    }


    public void addOrLink(MouseEvent mouseEvent) {
        if (!creatButton)
            return;
        Node cur = mouseEvent.getPickResult().getIntersectedNode();
        System.out.println(cur);
        if (cur == main && !isDrag) {
            Vertex node = new Vertex();
            double x = robot.getMouseX() - main.localToScreen(main.getBoundsInLocal()).getMinX() - 22;
            double y = robot.getMouseY() - main.localToScreen(main.getBoundsInLocal()).getMinY() - 22;
            x = Math.min(x, main.getPrefWidth() - 44);
            y = Math.min(y, main.getPrefHeight() - 44);
            node.setLayoutX(x);
            node.setLayoutY(y);
            node.setId(graph.getVertices().size());
            main.getChildren().add(node);
            graph.addVertex(node);

            node.setOnMouseEntered(mouseEvent1 -> node.requestFocus());
            node.setOnKeyPressed(keyEvent -> {
                        if (keyEvent.getCode() == KeyCode.DELETE && creatButton && !isDrag) {
                            graph.removeVertex(node, main);
                            refreshNode();
                        }
                    }
            );
        } else {
            Vertex currentNode;
            if (!isDrag && (cur instanceof Circle || cur instanceof Text)) {
                isDrag = true;
                currentNode = checkNode(mouseEvent);
                currentLine = new Edge();
                currentLine.setVisible(true);
                currentLine.setStartX(currentNode.getLayoutX() + 22);
                currentLine.setStartY(currentNode.getLayoutY() + 22);
                currentLine.setEndX(currentLine.getStartX());
                currentLine.setEndY(currentLine.getStartY());
                currentLine.setFrom(currentNode);
                main.getChildren().add(currentLine);

            } else if (isDrag && (cur instanceof Circle || cur instanceof Text)) {
                isDrag = false;
                currentNode = checkNode(mouseEvent);
                currentLine.setEndX(currentNode.getLayoutX() + 22);
                currentLine.setEndY(currentNode.getLayoutY() + 22);
                graph.addEdge(currentLine);
                currentLine.setTo(currentNode);
                int fromX = (int) currentLine.getFrom().getLayoutX();
                int fromY = (int) currentLine.getFrom().getLayoutY();
                int toX = (int) currentLine.getTo().getLayoutX();
                int toY = (int) currentLine.getTo().getLayoutY();
                int length = (int) Math.sqrt(Math.pow(fromX - toX, 2) + Math.pow(fromY - toY, 2)) / 50;
                currentLine.setLength(length, main);
                graph.addEdge(currentLine);
                System.out.println(graph.getEdges().isEmpty());
                System.out.println("from " + currentLine.getFrom().getID() + " to " + currentLine.getTo().getID() + " length " + length);
            }
        }
    }

    private void refreshNode() {
        graph.getVertices().forEach(stackPane -> {
                    for (Edge e : graph.getEdges()) {
                        if (e.getFrom().getID() == stackPane.getID())
                            e.getFrom().setId(graph.getVertices().indexOf(stackPane));
                        if (e.getTo().getID() == stackPane.getID())
                            e.getTo().setId(graph.getVertices().indexOf(stackPane));
                    }
                    stackPane.setId(graph.getVertices().indexOf(stackPane));

                }
        );
        for (Edge e : graph.getEdges()) {
            System.out.println("form " + e.getFrom().getID() + " to " + e.getTo().getID() + " lenght " + e.getLabel());
        }
    }

    private int checkIdNode(MouseEvent mouseEvent) {
        Parent parent = mouseEvent.getPickResult().getIntersectedNode().getParent();
        return Integer.parseInt(parent instanceof Label ? ((Label) parent).getText() :
                ((Label) ((StackPane) parent).getChildren().get(1)).getText());
    }

    private Vertex checkNode(MouseEvent mouseEvent) {
        int currId = checkIdNode(mouseEvent);
        return graph.getVertices().get(currId);
    }

    public void drawLine() {
        if (isDrag) {
            double x = robot.getMouseX() - main.getLayoutX() - main.getParent().getScene().getWindow().getX();
            double y = robot.getMouseY() - main.getLayoutY() - main.getParent().getScene().getWindow().getY();
            x = Math.min(x, main.getPrefWidth() - 1);
            y = Math.min(y, main.getPrefHeight() - 1);
            currentLine.setEndX(x);
            currentLine.setEndY(y);
        }
    }

    public boolean isEmpty(){
        int size = graph.getVertices().size();
        Text text;
        if (size == 0){
            text = new Text("Graph cannot be empty.");
            text.setStyle("-fx-font-size: 16px; -fx-fill: gold;");
            message.getChildren().clear();
            message.getChildren().add(text);
            return true;
        }
        return false;
    }


    @FXML
    private void CreateGraph() {
        if(isDrag)
            return;
        if (isEmpty() && creatButton)
            return;
        message.getChildren().clear();
        graph.clearHighlight();
        newGraph.setText(creatButton ? "On Draw" : "Off Draw");
        creatButton = !creatButton;
        DFSBox.setVisible(false);
        DPBox.setVisible(false);
        djsBox.setVisible(false);
    }



    public void executeDFS()
    {
        graph.clearHighlight();
    	int getSourceDfs = Integer.parseInt(DFSGo.getText());
        int size = graph.getVertices().size();
        Text text;
        if(getSourceDfs >= size || getSourceDfs < 0){
            text = new Text("This vertex does not exist in the graph. Please select another source vertex.");
            text.setStyle("-fx-font-size: 16px; -fx-fill: gold;");
            message.getChildren().add(text);
            return;
        }
        message.getChildren().clear();
    	Vertex temp = new Vertex();
    	temp.setId(getSourceDfs);
    	exploreDFS(temp);
    }


    public void exploreDFS(Vertex vertex) {
    	DFS dfs = new DFS();
    	dfs.setData(graph);
        dfs.falseAll();
    	dfs.explore(vertex);
    	Task<Void> task = new Task<>() {
            @Override
            public Void call() throws Exception {
                Platform.runLater(() -> codeTrace.getChildren().clear());
                if (!initCodeTrace.isShow)
                    showCodeTrace();
                if (!initStatus.isShow)
                    showStatus();
                for (int i = 0; i < dfs.getPseudoCodes().size(); i++) {
                    Text text = new Text(dfs.getPseudoCodes().get(i));
                    text.setStyle("-fx-font-size: 16px;");
                    Platform.runLater(() -> codeTrace.getChildren().add(text));
                }
                for (PseudoStep step : dfs.getPseudoSteps()) {
                    Platform.runLater(() -> {
                        codeTrace.getChildren().forEach(node -> node.setStyle("-fx-font-weight: normal"));
                        int idPseudo = Integer.parseInt(step.getText());
                        if (idPseudo != -1)
                            codeTrace.getChildren().get(idPseudo).setStyle("-fx-font-weight: bold");
                    });

                    for (soloStep detail : step.getDetail()) {
                        Platform.runLater(() -> {
                            if (detail.getText().length() > 0) {
                                status.getChildren().clear();
                                status.getChildren().add(new Text(detail.getText()));
                            }
                            Platform.runLater(detail::run);
                        });
                    }
                    Thread.sleep(1000);
                }
                return null;
            }
        };
         new Thread(task).start();

	}

    public void executeDP(){
        graph.clearHighlight();
        int getSourceDP = Integer.parseInt(DPGo.getText());
        Vertex temp = new Vertex();
        temp.setId(getSourceDP);
        exploreDP(temp);
    }

    public void exploreDP(Vertex vertex){
        System.out.println("Hello words");
    }

    public void executeDijkstra()
    {
        graph.clearHighlight();
        int getSourceDij = Integer.parseInt(djsGo.getText());
        Vertex temp = new Vertex();
        temp.setId(getSourceDij);
        exploreDij(temp);
    }

    public void exploreDij(Vertex vertex) {
        Dijkstra dijkstra = new Dijkstra();
//        graph.highlightSource(vertex);
        dijkstra.setData(graph);
        dijkstra.explore(vertex);
        Task<Void> task = new Task<>() {
            @Override
            public Void call() throws Exception {
                Platform.runLater(() -> codeTrace.getChildren().clear());
                if (!initCodeTrace.isShow)
                    showCodeTrace();
                if (!initStatus.isShow)
                    showStatus();
                for (int i = 0; i < dijkstra.getPseudoCodes().size(); i++) {
                    Text text = new Text(dijkstra.getPseudoCodes().get(i));
                    text.setStyle("-fx-font-size: 16px;");
                    Platform.runLater(() -> codeTrace.getChildren().add(text));
                }
                for (PseudoStep step : dijkstra.getPseudoSteps()) {
                    Platform.runLater(() -> {
                        codeTrace.getChildren().forEach(node -> node.setStyle("-fx-font-weight: normal"));
                        int idPseudo = Integer.parseInt(step.getText());
                        if (idPseudo != -1)
                            codeTrace.getChildren().get(idPseudo).setStyle("-fx-font-weight: bold");
                    });

                    for (soloStep detail : step.getDetail()) {
                        Platform.runLater(() -> {
                            if (detail.getText().length() > 0) {
                                status.getChildren().clear();
                                status.getChildren().add(new Text(detail.getText()));
                            }
                            Platform.runLater(detail::run);
                        });
                    }
                    Thread.sleep(1000);
                }
                return null;
            }
        };
        new Thread(task).start();

    }

    public void exitEvent(MouseEvent mouseEvent) {
        Node cur = mouseEvent.getPickResult().getIntersectedNode();
        if (cur == exitButton)
            exitButton.setStyle("-fx-background-radius: 10px; -fx-background-color: #ff0000;");
        else
            exitButton.setStyle("-fx-background-radius: 10px; -fx-background-color: #222426");
    }

    public void exit() {
        System.exit(0);
    }

}
