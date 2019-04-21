package Main;

import Display.DisplayScreen;
import Display.UI.UIPointer;
import Game.Entities.DynamicEntities.Mario;
import Game.Entities.DynamicEntities.Player;
import Game.Entities.DynamicEntities.Wario;
import Game.Entities.StaticEntities.BreakBlock;
import Game.GameStates.GameOverState;
import Game.GameStates.GameState;
import Game.GameStates.MenuState;
import Game.GameStates.PauseState;
import Game.GameStates.State;
import Game.World.Map;
import Game.World.MapBuilder;
import Input.Camera;
import Input.KeyManager;
import Input.MouseManager;
import Resources.Images;
import Resources.MusicHandler;

import java.awt.*;
import java.awt.image.BufferStrategy;


/**
 * Created by AlexVR on 7/1/2018.
 */

public class GameSetUp implements Runnable {
	public DisplayScreen display;
	public DisplayScreen display2;   // second screen
	public String title;

	private boolean running = false;
	private Thread thread;
	public static boolean threadB;

	private BufferStrategy bs;
	private BufferStrategy bs2;
	private Graphics g;
	private Graphics gw;
	public UIPointer pointer;

	//Input
	public KeyManager keyManager;
	public MouseManager mouseManager;
	public MouseManager initialmouseManager;

	//Handler
	private Handler handler;

	//States
	public State gameState;
	public State menuState;
	public State pauseState;
	public State gameOverState; // added

	boolean displayOn = false;

	//Res.music
	private MusicHandler musicHandler;

	public GameSetUp(String title,Handler handler) {
		this.handler = handler;
		this.title = title;
		threadB=false;

		keyManager = new KeyManager();
		mouseManager = new MouseManager();
		initialmouseManager = mouseManager;
		musicHandler = new MusicHandler(handler);
		handler.setCamera(new Camera());
	}

	private void init(){
		display = new DisplayScreen(title, handler.width, handler.height);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);

		Images img = new Images();

		musicHandler.restartBackground();

		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		pauseState = new PauseState(handler);
		gameOverState = new GameOverState(handler);

		State.setState(menuState);
	}

	public void init2() {
		display2 = new DisplayScreen(title, handler.width, handler.height);
		//display2.getFrame2().setVisible(false);
		display2.getFrame().addKeyListener(keyManager);
		display2.getFrame().addMouseListener(mouseManager);
		display2.getFrame().addMouseMotionListener(mouseManager);
		display2.getCanvas().addMouseListener(mouseManager);
		display2.getCanvas().addMouseMotionListener(mouseManager);

		Images img = new Images();

		musicHandler.restartBackground();

		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		pauseState = new PauseState(handler);
		gameOverState = new GameOverState(handler);

		State.setState(menuState);
	}

	public void reStart(){
		gameState = new GameState(handler);
	}

	public synchronized void start(){
		if(running)
			return;
		running = true;
		//this runs the run method in this  class
		thread = new Thread(this);
		thread.start();
	}

	public void run(){

		//initiallizes everything in order to run without breaking
		init();
		//init2();   // not working
		

		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;

		while(running){
			//makes sure the games runs smoothly at 60 FPS
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;

			if(delta >= 1){
				//re-renders and ticks the game around 60 times per second
				tick();
				render();
				ticks++;
				delta--;
			}
			if(timer >= 1000000000){
				ticks = 0;
				timer = 0;
			}
		}

		stop();

	}

	private void tick(){
		//checks for key types and manages them

		keyManager.tick();

		if(musicHandler.ended()){
			musicHandler.restartBackground();
		}

		//game states are the menus
		if(State.getState() != null)
			State.getState().tick();
		if (handler.isInMap()) {
			updateCamera();
			//updateCamera2();
		}

	}

	private void updateCamera() {

		// player 1
		Player mario = handler.getMario();
		double marioVelocityX = mario.getVelX();
		double marioVelocityY = mario.getVelY();
		double shiftAmount = 0;
		double shiftAmountY = 0;

		if (marioVelocityX > 0 && mario.getX() - 2*(handler.getWidth()/3) > handler.getCamera().getX()) {
			shiftAmount = marioVelocityX;
		}
		if (marioVelocityX < 0 && mario.getX() +  2*(handler.getWidth()/3) < handler.getCamera().getX()+handler.width) {
			shiftAmount = marioVelocityX;
		}
		if (marioVelocityY > 0 && mario.getY() - 2*(handler.getHeight()/3) > handler.getCamera().getY()) {
			shiftAmountY = marioVelocityY;
		}
		if (marioVelocityX < 0 && mario.getY() +  2*(handler.getHeight()/3) < handler.getCamera().getY()+handler.height) {
			shiftAmountY = -marioVelocityY;
		}
		handler.getCamera().moveCam(shiftAmount,shiftAmountY);
	}
	
	private void updateCamera2() {
		// player 2
		if (Handler.multiplayer) {
			Player wario = handler.getWario();
			double warioVelocityX = wario.getVelX();
			double warioVelocityY = wario.getVelY();
			double shiftAmount2 = 0;
			double shiftAmountY2 = 0;

			if (warioVelocityX > 0 && wario.getX() - 2*(handler.getWidth()/3) > handler.getCamera().getX()) {
				shiftAmount2 = warioVelocityX;
			}
			if (warioVelocityX < 0 && wario.getX() +  2*(handler.getWidth()/3) < handler.getCamera().getX()+handler.width) {
				shiftAmount2 = warioVelocityX;
			}
			if (warioVelocityY > 0 && wario.getY() - 2*(handler.getHeight()/3) > handler.getCamera().getY()) {
				shiftAmountY2 = warioVelocityY;
			}
			if (warioVelocityX < 0 && wario.getY() +  2*(handler.getHeight()/3) < handler.getCamera().getY()+handler.height) {
				shiftAmountY2 = -warioVelocityY;
			}
			handler.getCamera().moveCam(shiftAmount2,shiftAmountY2);
		}
	}


	private void render(){
		bs = display.getCanvas().getBufferStrategy();

		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//Clear Screen
		g.clearRect(0, 0,  handler.width, handler.height);

		//Draw Here!
		Graphics2D g2 = (Graphics2D) g.create();

		if(State.getState() != null)
			State.getState().render(g);

		//End Drawing!
		bs.show();
		g.dispose();
		
//		if (Handler.multiplayer) {
//			bs2 = display2.getCanvas2().getBufferStrategy();
//			if(bs2 == null){
//				display2.getCanvas2().createBufferStrategy(3);
//				return;
//			}
//			gw = bs2.getDrawGraphics();
//			gw.clearRect(0, 0,  handler.width, handler.height);
//			Graphics2D gw2 = (Graphics2D) gw.create();
//			if(State.getState() != null)
//				State.getState().render(gw);
//			bs2.show();
//			gw.dispose();
//		}
		
//		bs = display.getCanvas().getBufferStrategy();
//		bs2 = display2.getCanvas2().getBufferStrategy();
//
//		if(bs == null || bs2 == null){
//			display.getCanvas().createBufferStrategy(3);
//			display2.getCanvas2().createBufferStrategy(3);
//			return;
//		}
//		g = bs.getDrawGraphics();
//		gw = bs2.getDrawGraphics();
//		//Clear Screen
//		g.clearRect(0, 0,  handler.width, handler.height);
//		gw.clearRect(0, 0,  handler.width, handler.height);
//
//		//Draw Here!
//		Graphics2D g2 = (Graphics2D) g.create();
//		Graphics2D gw2 = (Graphics2D) gw.create();
//
//		if(State.getState() != null)
//			State.getState().render(g);
//		if(State.getState() != null)
//			State.getState().render(gw);
//
//		//End Drawing!
//		bs.show();
//		g.dispose();
//		bs2.show();
//		gw.dispose();
		
	}
	public Map getMap() {
		Map map = new Map(this.handler);
		Images.makeMap(0, MapBuilder.pixelMultiplier, 31, 200, map, this.handler);
		for(int i = 195; i < 200; i++) {
			map.addBlock(new BreakBlock(0, i*MapBuilder.pixelMultiplier, 48,48, this.handler));
			map.addBlock(new BreakBlock(30*MapBuilder.pixelMultiplier, i*MapBuilder.pixelMultiplier, 48,48, this.handler));
		}
		Mario mario = new Mario(24 * MapBuilder.pixelMultiplier, 196 * MapBuilder.pixelMultiplier, 48,48, this.handler);
		map.addEnemy(mario);
		map.addEnemy(pointer);
		if (Handler.multiplayer) {
			Wario wario = new Wario(24 * MapBuilder.pixelMultiplier, 196 * MapBuilder.pixelMultiplier, 48,48, this.handler);
			map.addEnemy(wario);
			map.addEnemy(pointer);
		}
		threadB=true;
		return map;
	}

	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public KeyManager getKeyManager(){
		return keyManager;
	}

	public MusicHandler getMusicHandler() {
		return musicHandler;
	}


	public MouseManager getMouseManager(){
		return mouseManager;
	}

}

