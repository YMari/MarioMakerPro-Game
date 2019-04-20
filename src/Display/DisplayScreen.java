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

public class DisplayScreen {

	public JFrame frame;
	public JFrame frame2;
	public Canvas canvas;
	public Canvas canvas2;

	private URL iconURL;

	private String title;
	private int width, height;

	public DisplayScreen(String title, int width, int height){
		this.title = title;
		this.width = width;
		this.height = height;



		createDisplay();
		createDisplay2();
	}

	private void createDisplay(){
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
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
	
	private void createDisplay2() {
		// multiplayer display
		frame2 = new JFrame(title);
		frame2.setSize(width, height);
		frame2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame2.setResizable(false);
		frame2.setLocation(frame.getX() + frame.getWidth(), frame.getY());
		frame2.setVisible(false);   // only visible when multiplayer is chosen
		frame2.setBackground(Color.black);

		try {
			frame2.setIconImage(ImageIO.read(new File("res/Sheets/item/SL1.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		canvas2 = new Canvas();
		canvas2.setPreferredSize(new Dimension(width, height));
		canvas2.setMaximumSize(new Dimension(width, height));
		canvas2.setMinimumSize(new Dimension(width, height));
		canvas2.setFocusable(false);
		canvas2.setBackground(Color.black);

		frame2.add(canvas2);
		frame2.pack();
	}

	public Canvas getCanvas(){
		return canvas;
	}
	public Canvas getCanvas2(){
		return canvas2;	
	}

	public JFrame getFrame(){
		return frame;
	}

	public JFrame getFrame2(){
		return frame2;
	}

}
