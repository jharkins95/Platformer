
public class Block extends GameObj {

	public Block(int px, int py, int vx, int vy, int width, int height) {
		super(px, py, vx, vy, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public void checkVerticalMovement(int canvasHeight) {
		if (initialVy != 0 && getPy() + initialVy < 50) {
			setVy(1 + getVy());
		} else if (initialVy != 0 && getPy() + initialVy > canvasHeight - 50) {
			setVy(-1 + getVy());
		}
	}
	
}
