package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;
import javafx.util.Pair;
import util.Position;

import java.util.List;

public class FirefighterGrid extends Canvas  implements Grid<List<ViewElement>>{

    private void paintElementAtPosition(List<ViewElement> elements, Position position) {
        paintBox(position.row(), position.column(), elements);
    }


    private void paintBox(int row, int column, List<ViewElement> elements){
        int sectionX = column * boxWidth;
        int sectionY = row * boxHeight;
        elements.get(0).paintBox(sectionX,sectionY,getGraphicsContext2D(),boxWidth, boxHeight);
    }

    private int boxWidth;
    private int boxHeight;
    private int columnCount;
    private int rowCount;


    @Override
    public void repaint(List<Pair<Position, List<ViewElement> > > positionedElements) {
        clear(positionedElements);
        paint(positionedElements);
        paintLines();
    }

    private void clear(List<Pair<Position, List<ViewElement> >> positionedElements)
    {
        for (Pair<Position, List<ViewElement> > positionElement : positionedElements)
        {
            Position position = positionElement.getKey();
            clearBox(position.row(), position.column());
        }
    }

    private void paint(List<Pair<Position, List<ViewElement> >> positionedElements) {
        for(Pair<Position, List<ViewElement> > pair : positionedElements){
            paintElementAtPosition(pair.getValue(), pair.getKey());
        }
    }

    @Override
    public void repaint(List<ViewElement>[][] elements) {
        clear();
        paint(elements);
        paintLines();
    }

    private void clear() {
        getGraphicsContext2D().clearRect(0,0,getWidth(), getHeight());
    }

    private void paint(List<ViewElement>[][] elements) {
        for(int column = 0; column < columnCount; column++)
            for(int row = 0; row < rowCount; row++){
                paintElementAtPosition(elements[row][column], new Position(row, column));
            }
    }

    public int columnCount() {
        return columnCount;
    }

    public int rowCount() {
        return rowCount;
    }

    public int boxWidth() {
        return boxWidth;
    }

    public int boxHeight() {
        return boxHeight;
    }

    @Override
    public void setDimensions(int columnCount, int rowCount, int boxWidth, int boxHeight) {
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        super.setWidth(boxWidth * columnCount);
        super.setHeight(boxHeight * rowCount);
    }

    private void paintLines()
    {
        paintHorizontalLines();
        paintVerticalLines();
    }

    private void paintVerticalLines() {
        for(int column = 0; column < columnCount; column++) {
            getGraphicsContext2D().setStroke(Color.BLACK);
            getGraphicsContext2D().setLineWidth(3.0);
            getGraphicsContext2D().strokeLine(column * boxWidth, 0, column * boxWidth, getHeight());
        }
    }

    private void paintHorizontalLines() {
        for(int row = 0; row < rowCount; row++) {
            getGraphicsContext2D().setStroke(Color.BLACK);
            getGraphicsContext2D().setLineWidth(3.0);
            getGraphicsContext2D().strokeLine(0, row * boxHeight, getWidth(), row * boxHeight);
        }
    }

    private void clearBox(int row, int column){
        getGraphicsContext2D().clearRect(column * boxWidth,row * boxHeight, boxWidth, boxHeight);
    }

    public void selectedBox(int row, int column, Color color) {
        GraphicsContext gc = getGraphicsContext2D();
        int sectionX = column * boxWidth;
        int sectionY = row * boxHeight;
        double oldAlpha = gc.getGlobalAlpha();
        gc.setGlobalAlpha(0.5);
        gc.setFill(color);
        gc.fillRect(sectionX, sectionY, boxWidth, boxHeight);
        gc.setGlobalAlpha(oldAlpha);
    }


}