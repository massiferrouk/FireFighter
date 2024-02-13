package controller;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.util.Pair;
import model.*;
import model.ModelElement.*;
import util.Position;
import view.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Controller {

  public static final int PERIOD_IN_MILLISECONDS = 50;
  @FXML
  public Button restartButton;
  @FXML
  public Button oneStepButton;
  @FXML
  public Label generationNumberLabel;
  @FXML
  private ToggleButton pauseToggleButton;
  @FXML
  private ToggleButton playToggleButton;
  @FXML
  private FirefighterGrid grid;
  @FXML
  private Pane imagePane;
  private Timeline timeline;
  private GameBoard board;

  private  int currentImageIndex=0;
  private List<ViewElement> imagePaneElements=new ArrayList<>();
  @FXML
  private void initialize() {
    initializePlayAndPauseToggleButtons();
    initializeTimeline();
  }

  private void initializePlayAndPauseToggleButtons() {
    ToggleGroup toggleGroup = new PersistentToggleGroup();
    toggleGroup.getToggles().addAll(playToggleButton, pauseToggleButton);
    pauseToggleButton.setSelected(true);
  }

  private void setModel(GameBoard firefighterBoard) {
    this.board = requireNonNull(firefighterBoard, "firefighter.model is null");
  }


  private void updateBoard(){

    List<Position> updatedPositions = board.updateToNextGeneration();
    List<Pair<Position, List<ViewElement> >> updatedSquares = new ArrayList<>();
    for(Position updatedPosition : updatedPositions){
      List<ModelElement> squareState = board.getState(updatedPosition);
      List<ViewElement> viewElement = getViewElement(squareState);
      updatedSquares.add(new Pair<>(updatedPosition, viewElement));
    }
    grid.repaint(updatedSquares);
    updateGenerationLabel(board.stepNumber());

  }



  private void repaintGrid(){
    int columnCount = board.columnCount();
    int rowCount = board.rowCount();
    List<ViewElement>[][] viewElements = new ArrayList[rowCount][columnCount];
    for(int column = 0; column < columnCount; column++)
      for(int row = 0; row < rowCount; row++) {
        viewElements[row][column] = getViewElement(board.getState(new Position(row, column)));
      }
    grid.repaint(viewElements);
    updateGenerationLabel(board.stepNumber());
  }

  private List<ViewElement> getViewElement(List<ModelElement> squareState) {
   List<ViewElement> viewElements= new ArrayList<>();
  if(!squareState.isEmpty()) {
    for (ModelElement modelElement : squareState)
      viewElements.add(modelElement.getViewElement());
  }
  else
    viewElements.add(new EmptyElement());

  return viewElements;
  }




  private void initializeTimeline() {
    Duration duration = new Duration(Controller.PERIOD_IN_MILLISECONDS);
    EventHandler<ActionEvent> eventHandler =
            event -> updateBoard();
    KeyFrame keyFrame = new KeyFrame(duration, eventHandler);
    timeline = new Timeline(keyFrame);
    timeline.setCycleCount(Animation.INDEFINITE);
  }



  public void play() {
    timeline.play();
  }

  public void pause() {
    timeline.pause();
  }

  public void pauseToggleButtonAction() {
    this.pause();
  }

  public void playToggleButtonAction() {
    this.play();
  }

  public void restartButtonAction() {
    this.pause();
    board.reset();
    resetPane();
    pauseToggleButton.setSelected(true);
    repaintGrid();
  }

  private void resetPane()
  {
    imagePane.getChildren().clear();
  }



  public void initialize(int squareWidth, int squareHeight, int columnCount,
                                int rowCount, int initialFireCount, int initialFirefighterCount, int initialMountainCount,
                         int initialCloudCount, int initialFireFighterMotorized, int initialRoadCount) {
    grid.setDimensions(columnCount, rowCount, squareWidth, squareHeight);
    GameBoardBuilder gameBoardBuilder= new GameBoardBuilder(columnCount,rowCount,initialFireCount);
    GameBoard gameBoard = gameBoardBuilder
            .setFirefighters(initialFirefighterCount)
            .setFirefightersMotorized(initialFireFighterMotorized)
            .setMountains(initialMountainCount)
            .setRoads(initialRoadCount)
            .setClouds(initialCloudCount)
            .build();


    this.setModel(gameBoard);
    repaintGrid();
  }



  public void oneStepButtonAction() {
    this.pause();
    updateBoard();

  }

  private void showImage(int index) {
    imagePane.getChildren().clear();
    if (index >= 0 && index < imagePaneElements.size()) {
      ViewElement viewElement = imagePaneElements.get(index);
      Image image = new Image(viewElement.getImage().getUrl());
      ImageView imageView = new ImageView(image);
      double paneWidth = imagePane.getWidth();
      double paneHeight = imagePane.getHeight();
      double imageWidth = imageView.getImage().getWidth();
      double imageHeight = imageView.getImage().getHeight();
      double scaleFactor = Math.min(paneWidth / imageWidth, paneHeight / imageHeight);
      imageView.setFitWidth(imageWidth * scaleFactor);
      imageView.setFitHeight(imageHeight * scaleFactor);
      double x = (paneWidth - imageView.getFitWidth()) / 2;
      double y = (paneHeight - imageView.getFitHeight()) / 2;
      imageView.setLayoutX(x);
      imageView.setLayoutY(y);
      imagePane.getChildren().add(imageView);
    }
  }

  @FXML
  private void handleScrollButtonClick() {
    if(!imagePaneElements.isEmpty()) {
      currentImageIndex = (currentImageIndex + 1) % imagePaneElements.size();
      showImage(currentImageIndex);
    }

  }
  @FXML
  private void handleGridClick(MouseEvent event) {

    double xPositionCLicked = event.getX();
    double yPositionClicked = event.getY();
    repaintGrid();
    int column= (int) xPositionCLicked/ grid.boxWidth();
    int row= (int) yPositionClicked/ grid.boxHeight();
    grid.selectedBox(row,column, Color.LIGHTBLUE);
    List<ViewElement> viewElements= getViewElement(board.getState(new Position(row, column)));
    imagePaneElements=viewElements;
    currentImageIndex=0;
   showImage(currentImageIndex);
}



  private void updateGenerationLabel(int value){
    generationNumberLabel.setText(Integer.toString(value));
  }
}