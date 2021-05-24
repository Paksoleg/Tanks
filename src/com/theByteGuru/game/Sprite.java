package com.theByteGuru.game;

import java.awt.*;
import java.awt.image.BufferedImage;

//хранит в себе 1 изображение
public class Sprite {

    private SpriteSheet sheet;
    private float scale;// насколько большим рисовать изображение

    public Sprite(SpriteSheet sheet, float scale){
        this.scale = scale;
        this.sheet = sheet;
    }

    public void render(Graphics2D g, float x, float y){
        BufferedImage image = sheet.getSprite(0);
        g.drawImage(image, (int)(x), (int)(y),(int)(image.getWidth() * scale), (int)(image.getHeight() * scale), null );
    }

}
