import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameCourt extends JPanel {
	
	private boolean gravityEnabled;
	private boolean fastQuitEnabled;
	
	private final int width;
	private final int height;
	
	private Player player; // test player object
	private int debugObjIndex;
	
	private List<Block> gameObjs;
	
	private GameState state;
	private Set<Integer> keysPressed;
	
	private DebugWindow debugWindow;
	
	private Timer renderingTimer;
	private Timer keyPressTimer;
	
	public static final int DEFAULT_RENDERING_INTERVAL = 16;
	public static final int DEFAULT_KEYPRESS_INTERVAL = 1;
	public static final int Y_ACCEL = 1; // TODO: might change this to be variable (e.g. for water)
	
	public GameCourt(int width, int height) {
		this.width = width;
		this.height = height;
		
		renderingTimer = new Timer(DEFAULT_RENDERING_INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
				repaint();
			}
		});
		
		keyPressTimer = new Timer(DEFAULT_KEYPRESS_INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleKeyInput();
			}
		});
		
		state = GameState.PLAYING;
		keysPressed = new TreeSet<Integer>();
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (!keysPressed.contains(e.getKeyCode())) keysPressed.add(e.getKeyCode());
			}
			
			public void keyReleased(KeyEvent e) {
				keysPressed.remove(e.getKeyCode());
			}
		});
		
		debugWindow = new DebugWindow(this);
		debugWindow.setVisible(true);
		
		setFocusable(true);
		requestFocusInWindow();
	}
	
	public void start() {
		player = new Player(320, 100, 0, 0, 50, 50, 100);
		gameObjs = new ArrayList<>();
		gameObjs.add(new Block(50, 400, 0, 2, 75, 20));
		gameObjs.add(new Block(200, 200, 0, -10, 75, 20));
		gameObjs.add(new Block(350, 300, 0, -2, 75, 20));
		gameObjs.add(new Block(500, 100, 0, 3, 75, 20));
		
		gameObjs.add(new Block(0, 460, 0, 0, 640, 20));
		gameObjs.add(new Block(0, 0, 0, 0, 20, 480));
		gameObjs.add(new Block(620, 0, 0, 0, 20, 480));
		
		gravityEnabled = true;
		fastQuitEnabled = true;
		renderingTimer.start();
		keyPressTimer.start();
	}
	
	private void handleKeyInput() {
		boolean keyLeft, keyRight, keyUp, keyDown, keyQuit, keyJump, keyToggleGravity,
				keyMoveDebugBlockForward, keyMoveDebugBlockBackward;
		keyLeft = keysPressed.contains(KeyEvent.VK_LEFT);
		keyRight = keysPressed.contains(KeyEvent.VK_RIGHT);
		keyUp = keysPressed.contains(KeyEvent.VK_UP);
		keyDown = keysPressed.contains(KeyEvent.VK_DOWN);
		keyQuit = keysPressed.contains(KeyEvent.VK_Q);
		keyJump = keysPressed.contains(KeyEvent.VK_J);
		keyToggleGravity = keysPressed.contains(KeyEvent.VK_F);
		keyMoveDebugBlockForward = keysPressed.contains(KeyEvent.VK_PERIOD);
		keyMoveDebugBlockBackward = keysPressed.contains(KeyEvent.VK_COMMA);
		
		if (keyMoveDebugBlockForward) {
			if (debugObjIndex < gameObjs.size() - 1) {
				debugObjIndex++;
			}
			keysPressed.remove(KeyEvent.VK_PERIOD);
		} else if (keyMoveDebugBlockBackward) {
			if (debugObjIndex > 0) {
				debugObjIndex--;
			}
			keysPressed.remove(KeyEvent.VK_COMMA);
		}

		if (keyToggleGravity) {
			gravityEnabled = !gravityEnabled;
			keysPressed.remove(KeyEvent.VK_F);
		}
		
		if (keyQuit) {
			if (fastQuitEnabled) {
				exit(0);
			} else {
				pause();
				displayExitDialog(0);
				unpause();
				keysPressed.remove(KeyEvent.VK_Q);
			}
		}
		
		// both left and right keys could be pressed at the same time
		if ((!keyLeft && !keyRight) || (keyLeft && keyRight)) {
			player.setVx(0);
		} else if (keyLeft && player.canMoveLeft()) {
			player.setVx(-5);
		} else if (keyRight && player.canMoveRight()) {
			player.setVx(5);
		}
		
		if (!gravityEnabled) {
			
			if ((!keyUp && !keyDown) || (keyUp && keyDown)) {
				player.setVy(0);
			} else if (keyUp && player.canMoveUp()) {
				player.setVy(-5);
			} else if (keyDown && player.canMoveDown()) {
				player.setVy(5);
			}
			
		} else {
			if (keyJump && !player.hasClippedDown() && player.canMoveUp() && !player.canMoveDown()) {
				player.jump();
			}
		}
	}
	
	public void setRenderingInterval(int ms) {
		renderingTimer.setDelay(ms);
	}
	
	public void pause() {
		renderingTimer.stop();
		keyPressTimer.stop();
	}
	
	public void unpause() {
		renderingTimer.start();
		keyPressTimer.start();
	}
	
	// TODO: implement a custom timer independent of Swing GUI
	private void tick() {
		switch (state) {
		case MENU:
			// TODO: implement menu
			break;
		case PLAYING:
			
			player.resetCollisionFlags();
			
			// TODO: fix cases when player is "crushed" by moving blocks
			
			for (Block obj : gameObjs) {
				if (player.willCollideDown(obj)) {
					player.addCollision(obj, Collision.DOWN);
				} else if (player.willCollideUp(obj)) {
					player.addCollision(obj, Collision.UP);
				} else if (player.willCollideLeft(obj)) {
					player.addCollision(obj, Collision.LEFT);
				} else if (player.willCollideRight(obj)) {
					player.addCollision(obj, Collision.RIGHT);
				}
			}
			
			player.processCollisions();
			
			for (Block obj : gameObjs) {
				obj.checkVerticalMovement(height);
				obj.updatePosition();
			}
			
			player.updatePosition();
			
			if (!player.hasClippedDown() && !player.hasClippedUp() && gravityEnabled && 
					player.canMoveDown()) {
				player.fall(Y_ACCEL);
			}
			
			for (Block obj : gameObjs) {
				player.updateMobilityFlags(obj);
			}
			
			for (Block obj : gameObjs) {
				if (obj == gameObjs.get(debugObjIndex)) {
					updateDebugPI(obj);
					updateDebugPT(obj);
				}
			}
			break;
		case GAME_OVER:
			// TODO: implement game over screen
			break;
		}
		updateDebugPPV();
		updateDebugGlobals();
	}
	
	// Update player's position and velocity in debug window
	public void updateDebugPPV() {
		debugWindow.updateLblPlayerPx(player.getPx());
		debugWindow.updateLblPlayerPy(player.getPy());
		debugWindow.updateLblPlayerVx(player.getVx());
		debugWindow.updateLblPlayerVy(player.getVy());
	}
	
	// Update player's intersection information in debug window
	public void updateDebugPI(GameObj other) {
		debugWindow.updateLblPlayerCollidingDown(player.isIntersectingDown(other));
		debugWindow.updateLblPlayerCollidingUp(player.isIntersectingUp(other));
		debugWindow.updateLblPlayerCollidingLeft(player.isIntersectingLeft(other));
		debugWindow.updateLblPlayerCollidingRight(player.isIntersectingRight(other));
	}
	
	// Update player's touching information in debug window
	public void updateDebugPT(GameObj other) {
		debugWindow.updateLblPlayerTouchingDown(player.isTouchingDown(other));
		debugWindow.updateLblPlayerTouchingUp(player.isTouchingUp(other));
		debugWindow.updateLblPlayerTouchingLeft(player.isTouchingLeft(other));
		debugWindow.updateLblPlayerTouchingRight(player.isTouchingRight(other));
	}
	
	// Update global game state in debug window
	public void updateDebugGlobals() {
		debugWindow.updateLblYAccel(Y_ACCEL);
	}
	
	public void showDebugWindow() {
		debugWindow.setVisible(true);
	}
	
	private void exit(int statusCode) {
		System.exit(statusCode);;
	}
	
	public void displayExitDialog(int statusCode) {
		// TODO: clean up resources
		int quit = JOptionPane.showConfirmDialog(this, "Are you sure?", "Quit?", 
				JOptionPane.YES_NO_OPTION);
		if (quit == 0) {
			exit(statusCode);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if (!renderingTimer.isRunning()) return; // prevent crashes in Swing Design window
		super.paintComponent(g);
		switch (state) {
		case MENU:
			break;
		case PLAYING:
			Graphics2D g2D = (Graphics2D) g;
			for (GameObj obj : gameObjs) {
				obj.draw(g2D);
			}
			player.draw(g2D);
			break;
		case GAME_OVER:
			break;
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	
	
}
