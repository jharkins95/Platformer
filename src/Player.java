import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Player extends GameObj {
	
	private static final int JUMP_VEL = -15;
	
	private int hp;
	// TODO: add some form of ammunition + a limit for it
	
	public Player(int px, int py, int vx, int vy, int width, int height, int hp) {
		super(px, py, vx, vy, width, height);
		this.hp = hp;
	}
	
	public boolean isAlive() {
		return hp > 0;
	}
	
	public void jump() {
		setVy(JUMP_VEL);
	}
	
	public boolean canJump(GameObj other) {
		return getPy() + getVy() + JUMP_VEL > other.getPy() + other.getHeight() + other.getVy() ||
				!isWithinHorizontally(other);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(getPx(), getPy(), getWidth(), getHeight());
	}

}
