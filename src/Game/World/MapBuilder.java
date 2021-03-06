package Game.World;

import Game.Entities.DynamicEntities.BaseDynamicEntity;
import Game.Entities.DynamicEntities.Ghost;
import Game.Entities.DynamicEntities.Goomba;
import Game.Entities.DynamicEntities.Mario;
import Game.Entities.DynamicEntities.Mushroom;
import Game.Entities.DynamicEntities.Wario;
import Game.Entities.StaticEntities.*;
import Main.Handler;
import Resources.Images;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapBuilder {

	public static int pixelMultiplier = 48;
	public static int boundBlock = new Color(0,0,0).getRGB();
	public static int groundBlock = new Color(19,147,36).getRGB(); // new block color green
	public static int grassBlock = new Color(221,31,155).getRGB(); // new block 2 color pink
	public static int mario = new Color(255,0,0).getRGB();
	public static int surfaceBlock = new Color(255,106,0).getRGB();
	public static int breakBlock = new Color(0,38,255).getRGB();
	public static int misteryBlock = new Color(255,216,0).getRGB();
	public static int mushroom = new Color(178,0,255).getRGB();
	public static int ghost = new Color(73,70,70).getRGB(); // new enemy color grey
	public static int goomba = new Color(167,15,1).getRGB();
	public static int flag = new Color(75, 0, 150).getRGB(); // new flag color 
	
	public static int wario = new Color(0,255,255).getRGB(); //player two color cyan
	
	public static boolean mapDone = false;
	

	
	public static Map createMap(BufferedImage mapImage, Handler handler){
		Map mapInCreation = new Map(handler);
		for (int i = 0; i < mapImage.getWidth(); i++) {
			for (int j = 0; j < mapImage.getHeight(); j++) {
				int currentPixel = mapImage.getRGB(i, j);
				int xPos = i*pixelMultiplier;
				int yPos = j*pixelMultiplier;
				if(currentPixel == boundBlock){
					BaseStaticEntity BoundBlock = new BoundBlock(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addBlock(BoundBlock);	
				}else if(currentPixel == groundBlock){
					BaseStaticEntity groundBlock = new GroundBlock(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addBlock(groundBlock);
				}else if(currentPixel == grassBlock){
					BaseStaticEntity grassBlock = new GrassBlock(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addBlock(grassBlock);
				}else if(currentPixel == mario){
					BaseDynamicEntity Mario = new Mario(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addEnemy(Mario);
				}else if(currentPixel == surfaceBlock){
					BaseStaticEntity SurfaceBlock = new SurfaceBlock(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addBlock(SurfaceBlock);
				}else if(currentPixel == breakBlock){
					BaseStaticEntity BreakBlock = new BreakBlock(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addBlock(BreakBlock);
				}else if(currentPixel == misteryBlock){
					BaseStaticEntity MisteryBlock = new MisteryBlock(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addBlock(MisteryBlock);
				}else if(currentPixel == ghost){
					BaseDynamicEntity Ghost = new Ghost(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addEnemy(Ghost);
				}else if(currentPixel == mushroom){
					BaseDynamicEntity Mushroom = new Mushroom(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addEnemy(Mushroom);
				}else if(currentPixel == goomba){
					BaseDynamicEntity Goomba = new Goomba(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addEnemy(Goomba);
				}else if(currentPixel == wario){
					BaseDynamicEntity Wario = new Wario(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addEnemy(Wario);
				}else if(currentPixel == flag){
					BaseStaticEntity Flag = new Flag(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addBlock(Flag);
				}

			}
		}
		if(mapDone) {
			Images.makeMap(50, pixelMultiplier, mapImage.getWidth(), 100, mapInCreation, handler);
			for(int i = 96; i < 101; i++) {
				mapInCreation.addBlock(new BreakBlock(49*pixelMultiplier, i*pixelMultiplier,48,48,handler));
				mapInCreation.addBlock(new BreakBlock(54*pixelMultiplier, i*pixelMultiplier,48,48,handler));
			}
		}
		return mapInCreation;
	}

}
