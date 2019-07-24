package com.ramunepr.main;

import com.ramunepr.entities.*;
import com.ramunepr.graficos.spriteRender;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener{
    public static JFrame frame;
    private boolean isRunning = true;
    private final int WIDTH = 200;
    private final int HEIGHT = 300;
    private final int SCALE = 3;
    private  Thread thread;
    private BufferedImage image;
    public static List<Entity> entities;
    public static spriteRender spritesheet;
    private Player player;
    private int paralax=0, paralax2=-300;
    public SpawnInimigo inimigo;
    public static String GAME_STATE = "NORMAL";
    public BufferedImage GAME_BACKGROUND;
    public Game(){
        Sound.musicBackground.play();
        addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        InitFrame();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        entities = new ArrayList<Entity>();
        spritesheet = new spriteRender("/res/spritesheet.png");
        player = new Player (80,250,40,40,spritesheet.getSprite(0, 0, 40, 40));
        inimigo = new SpawnInimigo();
        try {
            GAME_BACKGROUND = ImageIO.read(getClass().getResource("/res/map.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        entities.add(player);
    }
    
    public void InitFrame(){
        frame = new JFrame("Nave Game");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public synchronized void start(){
        thread = new Thread(this);
        isRunning = true;
        thread.start();
        
    }
    public synchronized void stop(){
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String args[]){
        Game game = new Game();
        game.start();
    }
    public void tick(){
        inimigo.tick();
        paralax+=4;
        paralax2+=4;
        if (paralax == 300){
            paralax=-300;
        }
        if (paralax2== 300){
            paralax2 = -300;
            
        }
        for (int i = 0; i < entities.size(); i++){ 
            Entity e = entities.get(i);
            e.tick();
        }
        
    }
    
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(new Color(0,0,0));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.setColor(Color.white);
        g.drawImage(this.GAME_BACKGROUND, 0, paralax, this);
        g.drawImage(this.GAME_BACKGROUND, 0, paralax2, this);
        g.drawString("Pontos: " + Player.pontos, 20, 30);
        g.drawString("Vida: " + Player.vida, 150, 30);
        if (GAME_STATE == "GAMEOVER"){
        for (int i = 0; i < entities.size(); i++){
                entities.remove(i);
        }
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(13,13,13,100));
            g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.setColor(Color.white);
            g.drawString("Game Over", 60, 150);
            g.setFont(new Font("Arial", Font.BOLD, 10));
            g.drawString(">Aperte enter para jogar novamente<", 10, 160);
        }
        if (GAME_STATE == "NORMAL"){
        for (int i = 0; i < entities.size(); i++){
            Entity e = entities.get(i);
            e.render(g);
        }  
        }

 
        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
        bs.show();
    }
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        while(isRunning){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now; 
            if (delta >= 1){
                tick();
                render();
                frames++;
                delta--;
            }
            if (System.currentTimeMillis() - timer >= 1000){
                System.out.println("FPS: " + frames);
                frames = 0;
                timer += 1000;
            }
        }
        stop();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (GAME_STATE == "NORMAL"){
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
			player.left = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
			player.right = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_Z) {
			player.atirando = true;
                        Sound.hurt.play();
		} 
        }else if(GAME_STATE == "GAMEOVER"){
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    GAME_STATE = "NORMAL";
                    Player.pontos = 0;
                    Player.vida = 10;
                    player = new Player (80,250,40,40,spritesheet.getSprite(0, 0, 40, 40));
                    entities.add(player);
		}
        }

    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }


}
