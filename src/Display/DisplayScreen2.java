package Display;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

import Game.GameStates.MenuState;
import Main.Handler;

/**
 * Created by AlexVR on 7/1/2018.
 */

public class DisplayScreen2 {

	public JFrame frame;
	public Canvas canvas;

	private URL iconURL;

	private String title;
	private int width, height;

	public DisplayScreen2(String title, int width, int height){
		this.title = title;
		this.width = width;
		this.height = height;



		createDisplay();
	}

	public void createDisplay(){
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(false);   // only visible in multiplayer
		frame.setBackground(Color.black);

		try {
			frame.setIconImage(ImageIO.read(new File("res/Sheets/item/SL1.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		canvas.setBackground(Color.black);

		frame.add(canvas);
		frame.pack();


	}

	public Canvas getCanvas(){
		return canvas;
	}

	public JFrame getFrame(){
		return frame;
	}

}