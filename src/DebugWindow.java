import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

@SuppressWarnings("serial")
public class DebugWindow extends JFrame {

	private JPanel contentPane;
	private JLabel lblPlayerPx_val;
	private JLabel lblPlayerPy_val;
	private JLabel lblPlayerVx_val;
	private JLabel lblPlayerVy_val;
	private JLabel lblPlayerCollidingDown_val;
	private JLabel lblPlayerCollidingUp_val;
	private JLabel lblPlayerCollidingLeft_val;
	private JLabel lblPlayerCollidingRight_val;
	private JLabel lblYAccel_val;
	private JLabel lblPlayerTouchingDown_val;
	private JLabel lblPlayerTouchingUp_val;
	private JLabel lblPlayerTouchingLeft_val;
	private JLabel lblPlayerTouchingRight_val;

	
	/**
	 * Create the frame.
	 */
	public DebugWindow(GameCourt court) {
		
		setTitle("Debug");
		setType(Type.UTILITY);
		setResizable(false);
		setAutoRequestFocus(false);
		setBounds(100, 100, 250, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPlayerPx = new JLabel("player.px:");
		lblPlayerPx.setBounds(10, 10, 130, 14);
		contentPane.add(lblPlayerPx);
		
		JLabel lblPlayerPy = new JLabel("player.py:");
		lblPlayerPy.setBounds(10, 25, 130, 14);
		contentPane.add(lblPlayerPy);
		
		JLabel lblPlayerVx = new JLabel("player.vx:");
		lblPlayerVx.setBounds(10, 40, 130, 14);
		contentPane.add(lblPlayerVx);
		
		JLabel lblPlayerVy = new JLabel("player.vy:");
		lblPlayerVy.setBounds(10, 55, 130, 14);
		contentPane.add(lblPlayerVy);
		
		JLabel lblPlayerCollidingDown = new JLabel("playerCollidingDown:");
		lblPlayerCollidingDown.setBounds(10, 70, 130, 14);
		contentPane.add(lblPlayerCollidingDown);
		
		JLabel lblPlayerCollidingUp = new JLabel("playerCollidingUp:");
		lblPlayerCollidingUp.setBounds(10, 85, 130, 14);
		contentPane.add(lblPlayerCollidingUp);
		
		JLabel lblPlayerCollidingLeft = new JLabel("playerCollidingLeft:");
		lblPlayerCollidingLeft.setBounds(10, 100, 130, 14);
		contentPane.add(lblPlayerCollidingLeft);
		
		JLabel lblPlayerCollidingRight = new JLabel("playerCollidingRight:");
		lblPlayerCollidingRight.setBounds(10, 115, 130, 14);
		contentPane.add(lblPlayerCollidingRight);
		
		JLabel lblYAccel = new JLabel("yAccel:");
		lblYAccel.setBounds(10, 190, 130, 14);
		contentPane.add(lblYAccel);
		
		lblPlayerPx_val = new JLabel("0");
		lblPlayerPx.setLabelFor(lblPlayerPx_val);
		lblPlayerPx_val.setBounds(170, 10, 40, 14);
		contentPane.add(lblPlayerPx_val);
		
		lblPlayerPy_val = new JLabel("0");
		lblPlayerPy.setLabelFor(lblPlayerPy_val);
		lblPlayerPy_val.setBounds(170, 25, 40, 14);
		contentPane.add(lblPlayerPy_val);
		
		lblPlayerVx_val = new JLabel("0");
		lblPlayerVx.setLabelFor(lblPlayerVx_val);
		lblPlayerVx_val.setBounds(170, 40, 40, 14);
		contentPane.add(lblPlayerVx_val);
		
		lblPlayerVy_val = new JLabel("0");
		lblPlayerVy.setLabelFor(lblPlayerVy_val);
		lblPlayerVy_val.setBounds(170, 55, 40, 14);
		contentPane.add(lblPlayerVy_val);
		
		lblPlayerCollidingDown_val = new JLabel("false");
		lblPlayerCollidingDown.setLabelFor(lblPlayerCollidingDown_val);
		lblPlayerCollidingDown_val.setBounds(170, 70, 40, 14);
		contentPane.add(lblPlayerCollidingDown_val);
		
		lblPlayerCollidingUp_val = new JLabel("false");
		lblPlayerCollidingUp.setLabelFor(lblPlayerCollidingUp_val);
		lblPlayerCollidingUp_val.setBounds(170, 85, 40, 14);
		contentPane.add(lblPlayerCollidingUp_val);
		
		lblPlayerCollidingLeft_val = new JLabel("false");
		lblPlayerCollidingLeft.setLabelFor(lblPlayerCollidingLeft_val);
		lblPlayerCollidingLeft_val.setBounds(170, 100, 40, 14);
		contentPane.add(lblPlayerCollidingLeft_val);
		
		lblPlayerCollidingRight_val = new JLabel("false");
		lblPlayerCollidingRight.setLabelFor(lblPlayerCollidingRight_val);
		lblPlayerCollidingRight_val.setBounds(170, 115, 40, 14);
		contentPane.add(lblPlayerCollidingRight_val);
		
		lblYAccel_val = new JLabel("0");
		lblYAccel.setLabelFor(lblYAccel_val);
		lblYAccel_val.setBounds(170, 190, 40, 14);
		contentPane.add(lblYAccel_val);
		
		JLabel lblPlayerTouchingDown = new JLabel("playerTouchingDown:");
		lblPlayerTouchingDown.setLabelFor(lblPlayerTouchingDown);
		lblPlayerTouchingDown.setBounds(10, 130, 130, 14);
		contentPane.add(lblPlayerTouchingDown);
		
		JLabel lblPlayerTouchingUp = new JLabel("playerTouchingUp:");
		lblPlayerTouchingUp.setBounds(10, 145, 130, 14);
		contentPane.add(lblPlayerTouchingUp);
		
		JLabel lblPlayerTouchingLeft = new JLabel("playerTouchingLeft:");
		lblPlayerTouchingLeft.setBounds(10, 160, 130, 14);
		contentPane.add(lblPlayerTouchingLeft);
		
		JLabel lblPlayerTouchingRight = new JLabel("playerTouchingRight:");
		lblPlayerTouchingRight.setBounds(10, 175, 130, 14);
		contentPane.add(lblPlayerTouchingRight);
		
		lblPlayerTouchingDown_val = new JLabel("false");
		lblPlayerTouchingDown_val.setBounds(170, 130, 40, 14);
		contentPane.add(lblPlayerTouchingDown_val);
		
		lblPlayerTouchingUp_val = new JLabel("false");
		lblPlayerTouchingUp.setLabelFor(lblPlayerTouchingUp_val);
		lblPlayerTouchingUp_val.setBounds(170, 145, 40, 14);
		contentPane.add(lblPlayerTouchingUp_val);
		
		lblPlayerTouchingLeft_val = new JLabel("false");
		lblPlayerTouchingLeft.setLabelFor(lblPlayerTouchingLeft_val);
		lblPlayerTouchingLeft_val.setBounds(170, 160, 40, 14);
		contentPane.add(lblPlayerTouchingLeft_val);
		
		lblPlayerTouchingRight_val = new JLabel("false");
		lblPlayerTouchingRight.setLabelFor(lblPlayerTouchingRight_val);
		lblPlayerTouchingRight_val.setBounds(170, 175, 40, 14);
		contentPane.add(lblPlayerTouchingRight_val);
		
		JSlider speedSlider = new JSlider();
		speedSlider.setMaximum(1000);
		speedSlider.setMinimum(1);
		speedSlider.setBounds(23, 234, 200, 26);
		contentPane.add(speedSlider);
		speedSlider.setValue(getSliderValue(GameCourt.DEFAULT_RENDERING_INTERVAL));
		
		speedSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				court.setRenderingInterval(getGameSpeed(speedSlider.getValue()));
			}
		});
		
		JLabel lblGameSpeed = new JLabel("Game Speed");
		lblGameSpeed.setHorizontalAlignment(SwingConstants.CENTER);
		lblGameSpeed.setBounds(83, 215, 79, 14);
		contentPane.add(lblGameSpeed);
	}
	
	private int getSliderValue(int gameSpeed) {
		return (int) Math.round(142.857 * Math.log(0.973615 * gameSpeed));
	}
	
	private int getGameSpeed(int sliderValue) {
		// Make slider logarithmic
		return (int) Math.round(1.0271 * Math.exp(0.007 * sliderValue));
	}
	
	public void updateLblPlayerPx(int px) {
		lblPlayerPx_val.setText("" + px);
	}
	
	public void updateLblPlayerPy(int py) {
		lblPlayerPy_val.setText("" + py);
	}

	public void updateLblPlayerVx(int vx) {
		lblPlayerVx_val.setText("" + vx);
	}

	public void updateLblPlayerVy(int vy) {
		lblPlayerVy_val.setText("" + vy);
	}
	
	public void updateLblPlayerCollidingDown(boolean collidingDown) {
		lblPlayerCollidingDown_val.setText("" + collidingDown);
	}
	
	public void updateLblPlayerCollidingUp(boolean collidingUp) {
		lblPlayerCollidingUp_val.setText("" + collidingUp);
	}
	
	public void updateLblPlayerCollidingLeft(boolean collidingLeft) {
		lblPlayerCollidingLeft_val.setText("" + collidingLeft);
	}
	
	public void updateLblPlayerCollidingRight(boolean collidingRight) {
		lblPlayerCollidingRight_val.setText("" + collidingRight);
	}
	
	public void updateLblPlayerTouchingDown(boolean touchingDown) {
		lblPlayerTouchingDown_val.setText("" + touchingDown);
	}
	
	public void updateLblPlayerTouchingUp(boolean touchingUp) {
		lblPlayerTouchingUp_val.setText("" + touchingUp);
	}
	
	public void updateLblPlayerTouchingLeft(boolean touchingLeft) {
		lblPlayerTouchingLeft_val.setText("" + touchingLeft);
	}
	
	public void updateLblPlayerTouchingRight(boolean touchingRight) {
		lblPlayerTouchingRight_val.setText("" + touchingRight);
	}
	
	public void updateLblYAccel(int yAccel) {
		lblYAccel_val.setText("" + yAccel);
	}
}
