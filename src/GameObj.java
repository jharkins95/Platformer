import javax.swing.*;

import com.sun.javafx.collections.MappingChange.Map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class GameObj implements Comparable<GameObj> {
	
	// Global object counter
	private static int counter;
	
	// position relative to canvas origin
	private int px;
	private int py;
	private int vx;
	private int vy;
	private int width;
	private int height;
	
	public final int initialVx;
	public final int initialVy;
	
	private int guid;
	
	private boolean isMovingUp;
	private boolean isMovingDown;
	private boolean isMovingLeft;
	private boolean isMovingRight;
	
	private boolean canMoveDown;
	private boolean canMoveUp;
	private boolean canMoveLeft;
	private boolean canMoveRight;
	
	private boolean hasClippedDown;
	private boolean hasClippedUp;
	private boolean hasClippedLeft;
	private boolean hasClippedRight;
	
	private TreeMap<GameObj, ArrayList<Collision>> collisions;
	
	public GameObj(int px, int py, int vx, int vy, int width, int height) {
		this.px = px;
		this.py = py;
		this.vx = vx;
		this.vy = vy;
		this.width = width;
		this.height = height;
		
		initialVx = vx;
		initialVy = vy;
		
		isMovingUp = false;
		isMovingDown = false;
		isMovingLeft = false;
		isMovingRight = false;
		
		resetCollisionFlags();
		
		collisions = new TreeMap<>();
		guid = counter;
		counter++;
	}
	
	public void resetCollisionFlags() {
		canMoveDown = true; 
		canMoveUp = true; 
		canMoveLeft = true; 
		canMoveRight = true;
		
		hasClippedDown = false; 
		hasClippedUp = false;
		hasClippedLeft = false; 
		hasClippedRight = false;
	}
	
	public int getPx() {
		return px;
	}
	
	public int getPy() {
		return py;
	}
	
	public int getVx() {
		return vx;
	}
	
	public int getVy() {
		return vy;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean canMoveDown() {
		return canMoveDown;
	}
	
	public boolean canMoveUp() {
		return canMoveUp;
	}
	
	public boolean canMoveLeft() {
		return canMoveLeft;
	}
	
	public boolean canMoveRight() {
		return canMoveRight;
	}
	
	public boolean hasClippedDown() {
		return hasClippedDown;
	}
	
	public boolean hasClippedUp() {
		return hasClippedUp;
	}
	
	public boolean hasClippedLeft() {
		return hasClippedLeft;
	}
	
	public boolean hasClippedRight() {
		return hasClippedRight;
	}
	
	public void setVx(int vx) {
		this.vx = vx;
		if (vx < 0) {
			isMovingLeft = true;
			isMovingRight = false;
		} else if (vx > 0) {
			isMovingRight = true;
			isMovingLeft = false;
		} else {
			isMovingLeft = false;
			isMovingRight = false;
		}
	}
	
	public void setVy(int vy) {
		this.vy = vy;
		if (vy < 0) {
			isMovingUp = true;
			isMovingDown = false;
		} else if (vy > 0) {
			isMovingDown = true;
			isMovingUp = false;
		} else {
			isMovingUp = false;
			isMovingDown = false;
		}
	}
	
	public int getGuid() {
		return guid;
	}
	
	public void updatePosition() {
		px += vx;
		py += vy;
	}
	
	public boolean isMoving() {
		return isMovingUp || isMovingDown ||
				isMovingLeft || isMovingRight;
	}
	
	public boolean isMovingUp() {
		return isMovingUp;
	}
	
	public boolean isMovingDown() {
		return isMovingDown;
	}
	
	public boolean isMovingLeft() {
		return isMovingLeft;
	}
	
	public boolean isMovingRight() {
		return isMovingRight;
	}
	
	public boolean isBelow(GameObj other) {
		return py > other.getPy() + other.getHeight();
	}
	
	public boolean isWithinHorizontally(GameObj other) {
		return px + width > other.getPx() && px < other.getPx() + other.getWidth();
	}
	
	public boolean isWithinVertically(GameObj other) {
		return py + height > other.getPy() && py < other.getPy() + other.getHeight();
	}
	
	public boolean isTouchingDown(GameObj other) {
		return py + height == other.getPy() && isWithinHorizontally(other);
	}
	
	public boolean isTouchingUp(GameObj other) {
		return py == other.getPy() + other.getHeight() && isWithinHorizontally(other);
	}
	
	public boolean isTouchingLeft(GameObj other) {
		return px == other.getPx() + other.getWidth() && isWithinVertically(other);
	}
	
	public boolean isTouchingRight(GameObj other) {
		return px + width == other.getPx() && isWithinVertically(other);
	}
	
	public void updateMobilityFlags(GameObj obj) {
		if (isTouchingLeft(obj)) canMoveLeft = false;
		if (isTouchingRight(obj)) canMoveRight = false;
		if (isTouchingUp(obj)) canMoveUp = false;
		if (isTouchingDown(obj)) canMoveDown = false;
	}
	
	public boolean isIntersectingDown(GameObj other) {
		return py + height > other.getPy() && isWithinHorizontally(other) &&
			   py + height / 2 < other.getPy() + other.getHeight() / 2;
	}
	
	public boolean isIntersectingUp(GameObj other) {
		return py < other.getPy() + other.getHeight() && isWithinHorizontally(other) &&
			   py + height / 2 > other.getPy() + other.getHeight() / 2;	
	}
	
	public boolean isIntersectingLeft(GameObj other) {
		return px < other.getPx() + other.getWidth() && isWithinVertically(other) &&
			   px + width / 2 > other.getPx() + other.getWidth() / 2;
	}
	
	public boolean isIntersectingRight(GameObj other) {
		return px + width > other.getPx() && isWithinVertically(other) &&
			   px + width / 2 < other.getPx() + other.getWidth() / 2;
	}
	
	public boolean willCollideDown(GameObj other) {
		return py + height + vy > other.getPy() + other.getVy() && 
				   px + width + vx > other.getPx() + other.getVx() && 
				   px + vx < other.getPx() + other.getWidth() + other.getVx() &&
				   py + height / 2 < other.getPy() + other.getHeight() / 2 &&
				   isWithinHorizontally(other);
	}
	
	public boolean willCollideUp(GameObj other) {
		return py + vy < other.getPy() + other.getHeight() + other.getVy() && 
				   px + width + vx > other.getPx() + other.getVx() && 
				   px + vx < other.getPx() + other.getWidth() + other.getVx() &&
				   py + height / 2 > other.getPy() + other.getHeight() / 2 &&
				   isWithinHorizontally(other);
	}
	
	public boolean willCollideLeft(GameObj other) {
		return px + vx < other.getPx() + other.getWidth() + other.getVx() && 
				   py + height + vy > other.getPy() + other.getVy() && 
				   py + vy < other.getPy() + other.getHeight() + other.getVy() &&
				   px + width / 2 > other.getPx() + other.getWidth() / 2 &&
				   isWithinVertically(other);
	}
	
	public boolean willCollideRight(GameObj other) {
		return px + width + vx > other.getPx() + other.getVx() && 
				   py + height + vy > other.getPy() + other.getVy() && 
				   py + vy < other.getPy() + other.getHeight() + other.getVy() &&
				   px + width / 2 < other.getPx() + other.getWidth() / 2 &&
				   isWithinVertically(other);
	}
	
	public void addCollision(GameObj other, Collision collision) {
		if (collisions.get(other) == null) {
			ArrayList<Collision> colList = new ArrayList<>();
			colList.add(collision);
			collisions.put(other, colList);
			switch (collision) {
			case DOWN:
				hasClippedDown = true;
				break;
			case UP:
				hasClippedUp = true;
				break;
			case LEFT:
				hasClippedLeft = true;
				break;
			case RIGHT:
				hasClippedRight = true;
				break;
			}
		} else {
			collisions.get(other).add(collision);
		}
	}
	
	public void processCollisions() {
		for (GameObj obj : collisions.keySet()) {
			for (Collision collision : collisions.get(obj)) {
				switch (collision) {
				case DOWN:
					clipUp(obj);
					break;
				case UP:
					clipDown(obj);
					break;
				case LEFT:
					clipRight(obj);
					break;
				case RIGHT:
					clipLeft(obj);
					break;
				}
			}
		}
		
		collisions = new TreeMap<>();
	}
	
	// Collisions are inelastic; no two blocks
	// will collide since collisions are only checked
	// between players/enemies/items and the blocks.
	
	private void clipDown(GameObj other) {
		py = other.getPy() + other.getHeight();
		vy = other.getVy();
		//System.out.println("Clipped down");
	}
	
	private void clipUp(GameObj other) {
		py = other.getPy() - height;
		vy = other.getVy();
		//System.out.println("Clipped up");
	}
	
	private void clipLeft(GameObj other) {
		px = other.getPx() - width;
		vx = other.getVx();
		//System.out.println("Clipped left");
	}
	
	private void clipRight(GameObj other) {
		px = other.getPx() + other.getWidth();
		vx = other.getVx();
		//System.out.println("Clipped right");
	}
	
	public void fall(int yAccel) {
		vy += yAccel;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(px, py, width, height);
	}
	
	@Override
	public int compareTo(GameObj other) {
		if (other == null) return 1;
		if (guid > other.guid) return 1;
		if (guid < other.guid) return -1;
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((collisions == null) ? 0 : collisions.hashCode());
		result = prime * result + height;
		result = prime * result + (isMovingDown ? 1231 : 1237);
		result = prime * result + (isMovingLeft ? 1231 : 1237);
		result = prime * result + (isMovingRight ? 1231 : 1237);
		result = prime * result + (isMovingUp ? 1231 : 1237);
		result = prime * result + px;
		result = prime * result + py;
		result = prime * result + vx;
		result = prime * result + vy;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameObj other = (GameObj) obj;
		if (collisions == null) {
			if (other.collisions != null)
				return false;
		} else if (!collisions.equals(other.collisions))
			return false;
		if (height != other.height)
			return false;
		if (isMovingDown != other.isMovingDown)
			return false;
		if (isMovingLeft != other.isMovingLeft)
			return false;
		if (isMovingRight != other.isMovingRight)
			return false;
		if (isMovingUp != other.isMovingUp)
			return false;
		if (px != other.px)
			return false;
		if (py != other.py)
			return false;
		if (vx != other.vx)
			return false;
		if (vy != other.vy)
			return false;
		if (width != other.width)
			return false;
		return true;
	}
	
	
}
