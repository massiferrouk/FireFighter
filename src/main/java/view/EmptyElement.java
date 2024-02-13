package view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class EmptyElement implements ViewElement{
    @Override
    public Color getColor() {
        return Color.WHITE;
    }

    @Override
    public void paintBox(int x, int y, GraphicsContext graphicsContext, int width, int height) {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(x,y, width, height);
    }

    @Override
    public Image getImage() {
        return ImageCache.getImage("file:src\\main\\resources\\view\\symbole-mathematique-de-jeu-vide.png");
    }


}


