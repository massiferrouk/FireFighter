package view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public interface ViewElement {
  Color getColor();
  void paintBox(int x, int y, GraphicsContext graphicsContext, int width, int height);
  Image getImage();
}
