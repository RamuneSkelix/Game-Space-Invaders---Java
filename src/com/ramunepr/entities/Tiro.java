package com.ramunepr.entities;

import com.ramunepr.main.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tiro extends Entity{
    private int speed = 5;
    public Tiro(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }
    public void tick(){
        y -= speed;
        if (y < 0){
            Game.entities.remove(this);
            return;
        }
        

    }
    public void render(Graphics g){
        g.setColor(Color.yellow);
        g.fillRect(x, y, 3, 3);
    }
    
}
