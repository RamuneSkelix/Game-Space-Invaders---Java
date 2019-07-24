package com.ramunepr.entities;

import com.ramunepr.main.Game;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    
    protected int x,y,width,height;
    private BufferedImage sprite;
    
    public Entity(int x, int y, int width, int height, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void tick(){
        
    }

    public void render(Graphics g){
        g.drawImage(sprite, this.getX(), this.getY(), null);
    }
    
	public static boolean isColidding(Entity e1,Entity e2){
		Rectangle e1Mask = new Rectangle(e1.getX(),e1.getY(),e1.getWidth(),e1.getHeight());
		Rectangle e2Mask = new Rectangle(e2.getX(),e2.getY(),e2.getWidth(),e2.getHeight());
		
		return e1Mask.intersects(e2Mask);
	}
    }
