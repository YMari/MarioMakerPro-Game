package Main;

import Game.Entities.DynamicEntities.Mario;
import Game.Entities.DynamicEntities.Wario;
import Game.Entities.StaticEntities.Flag;
import Game.World.Map;
import Input.Camera;
import Input.KeyManager;
import Input.MouseManager;

import java.awt.*;


/**
 * Created by AlexVR on 7/1/2018.
 */

public class Handler {

    private static final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    public static final int DEFAULTWIDTH = gd.getDisplayMode().getWidth();
    public static final int DEFAULTHEIGHT = gd.getDisplayMode().getHeight();
    public static boolean multiplayer = false;

    int width,height;

    private GameSetUp game;
    private Mario mario;
    private Wario wario;
    private Map map;
    private boolean marioInMap =false;

    private Camera camera;
    private Camera camera2;


    public Handler(){

        height=2*(DEFAULTHEIGHT/3)  ;
        width =height;

    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public GameSetUp getGame() {
        return game;
    }

    public void setGame(GameSetUp game) {
        this.game = game;
    }

    public KeyManager getKeyManager(){
        return game.getKeyManager();
    }

    public MouseManager getMouseManager(){
        return game.getMouseManager();
    }


    ///TO CHange
    public Mario getMario() {
        return mario;
    }
    
    public Wario getWario() {
        return wario;
    }

    public void setMario(Mario mario) {
        this.mario = mario;
    }
    
    public void setWario(Wario wario) {
    	this.wario = wario;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public boolean isInMap() {
        return marioInMap;
    }

    public void setIsInMap(boolean is) {
        marioInMap = is;
    }
    
    public Camera getCamera() {
        return camera;
    }
    
    public Camera getCamera2() {
    	return camera2;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
    
    public void setCamera2(Camera camera2) {
    	this.camera2 = camera2;
    }
}
