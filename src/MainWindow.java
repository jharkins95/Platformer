import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBoxMenuItem;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private GameCourt court;
	private static final int COURT_WIDTH = 640;
	private static final int COURT_HEIGHT = 480;

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setResizable(false);
		setVisible(false);
		setTitle("Game");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		
		// Close operations
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				court.pause();
				court.displayExitDialog(0);
				court.unpause();
			}
		});
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.pause();
				court.displayExitDialog(0);
				court.unpause();
			}
		});
		
		JMenuItem mntmDebugWindow = new JMenuItem("Debug Window");
		mntmDebugWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.showDebugWindow();
			}
		});
		mnFile.add(mntmDebugWindow);

		mnFile.add(mntmQuit);
		court = new GameCourt(COURT_WIDTH, COURT_HEIGHT);
		court.setBorder(new EmptyBorder(5, 5, 5, 5));
		court.setLayout(new BorderLayout(0, 0));
		setContentPane(court);
		pack();
		
		// Center window in middle of screen
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int locX = gd.getDisplayMode().getWidth() / 2 - getWidth() / 2;
		int locY = gd.getDisplayMode().getHeight() / 2 - getHeight() / 2;
		setLocation(locX, locY);
		
		setVisible(true);
	}
	
	public void startGame() {
		court.start();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
					frame.startGame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
