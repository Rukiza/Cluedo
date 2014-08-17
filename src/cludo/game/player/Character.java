package cludo.game.player;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cludo.gui.CludoCanvas;

public class Character {
	String name;
	BufferedImage image;

	public Character(String name) {
		super();
		this.name = name;
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice device = env.getDefaultScreenDevice();
	    GraphicsConfiguration config = device.getDefaultConfiguration();
	    BufferedImage buffy = config.createCompatibleImage(CludoCanvas.cardWidth*3 ,14*CludoCanvas.squareSize , Transparency.TRANSLUCENT);
		image =  loadImage("../../../portraits/"+name+".jpg");
		Graphics2D g2 = buffy.createGraphics();
		g2.drawImage(image, 0, 0, CludoCanvas.cardWidth*3, 14*CludoCanvas.squareSize, null);
		image = buffy;
	}
	
	public BufferedImage loadImage(String imagePath){

		java.net.URL imageURL = Character.class.getResource(imagePath);
		try {
			BufferedImage image  = ImageIO.read(imageURL);
			return image;
	
		} catch (IOException e) {
			// we've encountered an error loading the image. There's not much we
			// can actually do at this point, except to abort the game.
			throw new RuntimeException("Unable to load image: " + imagePath);
		}
		
		
	}
}
