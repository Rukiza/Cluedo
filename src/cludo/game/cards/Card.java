package cludo.game.cards;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * Playing card that are unique and are used to play with.
 * @author Shane Brewer
 *
 */
public class Card {
	
	// Card type 
	public static enum Type {ROOM , WEAPON, CHARACTER};
	
	public final Type type;
	public final String name;
	// base classes must set this 
	private BufferedImage image;
	
	/**
	 * Constructs a type of card with the name of the card
	 * @param type - type of card 
	 * @param name - name of the card e.g CandleStick
	 */
	public Card(Type type, String name){
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice device = env.getDefaultScreenDevice();
	    GraphicsConfiguration config = device.getDefaultConfiguration();
	    BufferedImage buffy = config.createCompatibleImage(75, 110, Transparency.TRANSLUCENT);
		this.type = type;
		this.name = name;
		image =  loadImage("src/images/"+name+".png");
		Graphics2D g2 = buffy.createGraphics();
		g2.drawImage(image, 0, 0, 75, 110, null);
		image = buffy;
	}
	
	/**
	 * Returns a file link to the image
	 * that represents this card.
	 * @return
	 */
	public Image getImage(){
		return image;
	}

	public boolean checkType(Type type) {
		return this.type.equals(type);
	}
	
	@Override
	public String toString() {
		return  name;
	}

	/**
	 * Reformated from the pacman game made by D.J.P
	 * @param imagePath
	 * @return
	 */
	public BufferedImage loadImage(String imagePath){
		try {
			BufferedImage image  = ImageIO.read(new File("src/images/"+name+".png"));
			return image;
	
		} catch (IOException e) {
			// we've encountered an error loading the image. There's not much we
			// can actually do at this point, except to abort the game.
			throw new RuntimeException("Unable to load image: " + imagePath);
		}
	}
	
	
}
