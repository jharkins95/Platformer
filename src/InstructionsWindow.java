import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Window.Type;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;

public class InstructionsWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public InstructionsWindow() {
		setType(Type.UTILITY);
		setTitle("Instructions");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JTextArea txtrInstructions = new JTextArea();
		txtrInstructions.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		String instructions = "";
		Scanner sc = null;
		
		try {
			sc = new Scanner(new BufferedReader(new FileReader("instructions.txt")));
			while (sc.hasNext()) {
				instructions += sc.nextLine() + '\n';
			}
		} catch (IOException e) {
			instructions = "File not found: instructions.txt";
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
		
		txtrInstructions.setText(instructions);
		scrollPane.setViewportView(txtrInstructions);
		setVisible(false);
	}

}
