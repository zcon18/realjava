package dev.stuff.realjava;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import dev.stuff.realjava.display.Display;
import dev.stuff.realjava.gfx.ImageLoader;

public class Game implements Runnable{
	private Display display;
	public int width, height;
	public String title;
	private Thread thread;
	private boolean running=false;
	private BufferStrategy bs;
	private Graphics g;
	private BufferedImage testImage;
	public Game(String title, int width, int height){
		this.width=width;
		this.height=height;
		this.title=title;
	}
	private void init(){
		display= new Display(title, width, height);
		testImage=ImageLoader.loadImage("/textures/test.png");
	}
	private void tick(){
		
	}
	private void render(){
		bs=display.getCanvas().getBufferStrategy();
		if(bs==null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g=bs.getDrawGraphics();
		g.clearRect(0,0,width,height);
		//yeet things here
		g.drawImage(testImage, 20, 20, null);
		//shtap yeeting
		bs.show();
		g.dispose();
	}
	public void run(){
		init();
		while(running){
			tick();
			render();
		}
		stop();
	}
	public synchronized void start(){
		if(running)return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	public synchronized void stop(){
		if(!running)return;
		running=false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
