
package com.ramunepr.entities;

import com.ramunepr.main.Game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Explosion extends Entity{
	
	private int frames = 0;
	private int targetFrames = 4;
	private int maxAnimation = 2;
	private int curAnimation = 0;

	public BufferedImage[] explosionSprites = new BufferedImage[3];
	
	public Explosion(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		explosionSprites[0] = Game.spritesheet.getSprite(0,40,40,40);
		explosionSprites[1] = Game.spritesheet.getSprite(40,40,40,40);
		explosionSprites[2] = Game.spritesheet.getSprite(80,40,40,40);
	}
	
	public void tick() {
		frames++;
		if(frames == targetFrames)
		{
			frames = 0;
			curAnimation++;
			if(curAnimation > maxAnimation)
			{
				Game.entities.remove(this);
				return;
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(explosionSprites[curAnimation],this.getX(),this.getY(),null);
	}

	
	
	
}
