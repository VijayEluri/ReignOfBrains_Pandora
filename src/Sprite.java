import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Sprite {

	protected ArrayList<OneScene> scenes;
	protected float x; //position x
	protected float y; //position y
	protected float vx; //velocity in the x direction
	protected float vy; //velocity y
	protected int sceneIndex;
	protected long movieTime;
	protected long totalTime;
	protected Rectangle2D hitBox;

	public int totalScenes;
	public boolean visible;

	//Private Inner Class
	protected class OneScene{
		BufferedImage pic;
		long endTime;
		long frameTime;
		
		public BufferedImage getPic() {
			return pic;
		}

		public long getEndTime() {
			return endTime;
		}
		
		public long getFrameTime(){
			return frameTime;
		}
		
		public OneScene(BufferedImage pic, long endTime, long frameTime){
			this.pic = pic;
			this.endTime = endTime;
			this.frameTime = frameTime;
		}
				
	}
	
	public Sprite(){
		scenes = new ArrayList<OneScene>();
		sceneIndex = 0;
		totalTime = 0;
		movieTime = 0;
		visible = false;
	}
	
	//copies images only
	public void copy(Sprite from){
		this.clearScenes();
		//this.totalTime = 0;
		//this.movieTime = 0;
		//this.sceneIndex = 0;
		for (int i=0; i<from.scenes.size(); i++){
			this.addScene(from.getScene(i).getPic(), from.getScene(i).getFrameTime());
		}
		//this.totalTime = from.getTotalTime();
	}
	
	public ArrayList<OneScene> getScenes() {
		return scenes;
	}

	protected void clearScenes(){
		scenes.clear();
		scenes = new ArrayList<OneScene>();
		this.totalTime = 0;
		this.totalScenes = 0;
		this.sceneIndex = 0;
	}
	
	public void clear(){
		scenes.clear();
	}
	
	//change position
	public void update(long timePassed){
		x += vx * timePassed;
		y += vy * timePassed;
		if (scenes.size() > 1){
			//System.out.println("update totalTime = " + totalTime);
			
			movieTime += timePassed;
			if (movieTime >= totalTime){
				movieTime = 0;
				sceneIndex = 0;
			}
			while (movieTime > getScene(sceneIndex).endTime){
				sceneIndex++;
			}
			
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getVx() {
		return vx;
	}

	public void setVx(float vx) {
		this.vx = vx;
	}

	public float getVy() {
		return vy;
	}

	public void setVy(float vy) {
		this.vy = vy;
	}
	
	public int getWidth(){
		try {
			return getImage().getWidth(null);
		} catch (Exception ex) {}
		return 0;
	}
	
	public int getHeight(){
		try {
			return getImage().getHeight(null);
		} catch (Exception ex) {}
		return 0;
	}
	
	public synchronized void loadImages(){
		//To be overloaded
	}
	
	public long getTotalTime() {
		return totalTime;
	}
	
	public synchronized void addScene(String si, long t){
		BufferedImage i;
		try {
			// The ClassLoader.getResource() ensures we get the sprite
			// from the appropriate place, this helps with deploying the game
			// with things like webstart. You could equally do a file look
			// up here.
			URL url = this.getClass().getClassLoader().getResource(si);
			
			if (url == null) {
				System.out.println("Can't find ref: "+si);
			}
			
			// use ImageIO to read the image in
			i = ImageIO.read(url);
			totalTime += t;
			//System.out.println("add scene totalTime = " + totalTime);
			scenes.add(new OneScene(i, totalTime, t));
		} catch (IOException e) {
			System.out.println("Failed to load: "+si);
		}

	}

	protected BufferedImage genBufferedImage(String si){
		BufferedImage i;
		try {
			URL url = this.getClass().getClassLoader().getResource(si);
			
			if (url == null) {
				System.out.println("Can't find ref: "+si);
			}
			
			i = ImageIO.read(url);
			return i;
		} catch (IOException e) {
			System.out.println("Failed to load: "+si);
			return null;
		}
		
	}
	
	public synchronized void addScene( BufferedImage i, long t){
		totalTime += t;
		//System.out.println("add scene totalTime = " + totalTime);
		scenes.add(new OneScene(i, totalTime, t));
	}
	
	public synchronized void start(){
		movieTime = 0;
		sceneIndex = 0;
	}
	
	public synchronized Image getImage(){
		if(scenes.size() == 0){
			return null;
		}
		else {
			return getScene(sceneIndex).getPic();
		}
	}
	
	protected OneScene getScene(int x){
		return (OneScene)scenes.get(x);
	}
	
}
