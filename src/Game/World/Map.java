package Game.World;

import Display.UI.UIPointer;
import Game.Entities.DynamicEntities.*;
import Game.Entities.StaticEntities.BaseStaticEntity;
import Game.Entities.StaticEntities.Wall;
import Main.Handler;
import Resources.Images;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import Display.UI.UIListener;

public class Map {

	ArrayList<BaseStaticEntity> blocksOnMap;
	ArrayList<BaseDynamicEntity> enemiesOnMap;
	Handler handler;
	private double bottomBorder;
	private double bottomBorder2;
	private UIListener listener;
	private Background hand;
	private Random rand;
	private Wall walls;
	private int mapBackground;

	public Map(Handler handler) {
		this.handler=handler;
		this.rand = new Random();
		this.hand = new Background(this.handler);
		this.listener = new UIListener( this.handler);
		this.walls = new Wall(this.handler);
		this.blocksOnMap = new ArrayList<>();
		this.enemiesOnMap = new ArrayList<>();
		bottomBorder = handler.getHeight();
		bottomBorder2 = handler.getHeight(); // added
		this.mapBackground = this.rand.nextInt(6);
	}

	public void addBlock(BaseStaticEntity block){
		blocksOnMap.add(block);
	}
	public void addEnemy(BaseDynamicEntity entity){
		if(entity instanceof Mario){
			handler.setMario((Mario) entity);
			handler.getCamera().setX(handler.getMario().x - (MapBuilder.pixelMultiplier*6));
			handler.getCamera().setY(handler.getMario().y - (MapBuilder.pixelMultiplier*10));
			bottomBorder = handler.getHeight()+handler.getMario().y;
		}
		else if(entity instanceof Wario){
			handler.setWario((Wario) entity);
			handler.getCamera().setX(handler.getMario().x - (MapBuilder.pixelMultiplier*6));  //can't seem to change camera
			handler.getCamera().setY(handler.getMario().y - (MapBuilder.pixelMultiplier*10));
			bottomBorder = handler.getHeight()+handler.getMario().y;

		}
		else {
			enemiesOnMap.add(entity);
		}
	}

	public void drawMap(Graphics2D g2) {
		handler.setIsInMap(true);
		Point camLocation = new Point((int)handler.getCamera().getX(), (int)handler.getCamera().getY());
		g2.translate(-camLocation.x, -camLocation.y);
		g2.drawImage(Images.backgrounds2[this.mapBackground], camLocation.x, camLocation.y, this.handler.getWidth(), this.handler.getHeight(),null);
		for (BaseStaticEntity block:blocksOnMap) {
			g2.drawImage(block.sprite,block.x,block.y,block.width,block.height,null);
		}
		for (BaseDynamicEntity entity:enemiesOnMap) {
			if(entity instanceof Item){
				if(!((Item)entity).used){
					g2.drawImage(entity.sprite, entity.x, entity.y, entity.width, entity.height, null);
				}
			}else if(entity instanceof Goomba && !entity.ded){
				g2.drawImage(((Goomba)entity).anim.getCurrentFrame(), entity.x, entity.y, entity.width, entity.height, null);
			}
			else if(entity instanceof UIPointer ){
				((UIPointer) entity).render(g2);
			}else {
				g2.drawImage(entity.sprite, entity.x, entity.y, entity.width, entity.height, null);
			}
		}
		if (Handler.multiplayer) {
			handler.getMario().drawMario(g2);
			handler.getWario().drawWario(g2);
		}
		else {
			handler.getMario().drawMario(g2);
		}

		if(this.listener != null && MapBuilder.mapDone) {
			this.listener.render(g2);
			this.hand.render(g2);
			this.walls.render(g2);
		}
		g2.translate(camLocation.x, camLocation.y);
	}

	public void drawMap2(Graphics2D g2) {
		//handler.setIsInMap(true); // if we set it to handler.setIsInMap2(true);, mario and wario are drawn at the same time :(
		Point camLocation = new Point((int)handler.getCamera().getX(), (int)handler.getCamera().getY()); //update cam
		g2.translate(-camLocation.x, -camLocation.y);
		g2.drawImage(Images.backgrounds2[this.mapBackground], camLocation.x, camLocation.y, this.handler.getWidth(), this.handler.getHeight(),null);
		for (BaseStaticEntity block:blocksOnMap) {
			g2.drawImage(block.sprite,block.x,block.y,block.width,block.height,null);
		}
		for (BaseDynamicEntity entity:enemiesOnMap) {
			if(entity instanceof Item){
				if(!((Item)entity).used){
					g2.drawImage(entity.sprite, entity.x, entity.y, entity.width, entity.height, null);
				}
			}else if(entity instanceof Goomba && !entity.ded){
				g2.drawImage(((Goomba)entity).anim.getCurrentFrame(), entity.x, entity.y, entity.width, entity.height, null);
			}
			else if(entity instanceof UIPointer ){
				((UIPointer) entity).render(g2);
			}else {
				g2.drawImage(entity.sprite, entity.x, entity.y, entity.width, entity.height, null);
			}
		}

		handler.getMario().drawMario(g2);
		handler.getWario().drawWario(g2);
		if(this.listener != null && MapBuilder.mapDone) {
			this.listener.render(g2);
			this.hand.render(g2);
			this.walls.render(g2);
		}
		g2.translate(camLocation.x, camLocation.y);
	}

	public ArrayList<BaseStaticEntity> getBlocksOnMap() {
		return blocksOnMap;
	}

	public ArrayList<BaseDynamicEntity> getEnemiesOnMap() {
		return enemiesOnMap;
	}

	public double getBottomBorder() {
		return bottomBorder;
	}

	public double getBottomBorder2() {
		return bottomBorder2;
	}

	public UIListener getListener() {
		return this.listener;
	}
	public Background getHand() {
		return this.hand;
	}
	public Wall getWalls() {
		return this.walls;
	}

	public void reset() {
	}
}
