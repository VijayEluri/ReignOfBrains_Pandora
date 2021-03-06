import java.awt.geom.Rectangle2D;

public class Item extends Sprite {
	private int itemType;
	private int value;
	private String name;

	public Item(){
		super();
		this.visible = true;
		this.update(0);
	}
	
	@Override
	public void update(long timePassed){
		super.update(timePassed);
		if (hitBox != null){
			this.hitBox.setRect(this.getX(), this.getY(),this.getWidth(), this.getHeight());
		}
		else {
			this.hitBox = new Rectangle2D.Double(this.getX(), this.getY(),this.getWidth(), this.getHeight());
		}
	}
	
	public void unload(){
		this.visible = false;
		this.setValue(0);
		this.scenes.clear();
	}
	
	public void setItemType(int itemType) {
		this.itemType = itemType;
		try {
			switch (itemType){
			case 0:
				name = "Ammunition";
				value = 1;
				this.clearScenes();
				this.addScene("sprites/items/ammo.gif", 250);
				break;
			case 1:
				name = "Health";
				value = 25;
				this.clearScenes();
				this.addScene("sprites/items/health.png", 250);
				break;
			case 2:
				name = "Sandwich";
				value = 1;
				this.clearScenes();
				this.addScene("sprites/items/sandwich.png", 250);
				break;
			case 3:
				name = "Battery";
				value = 25;
				this.clearScenes();
				this.addScene("sprites/items/battery.png", 250);
				break;
			}
		} catch (Exception ex){}
	}

	public int getItemType() {
		return itemType;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	
}
