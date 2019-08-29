package ui;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class NodeConsole {

	private static JFrame frame;
	static String message = "";
	public static JTextArea logarea = new JTextArea();
	public static JButton nodestatus = new JButton("HUB STATUS");

	public static JFrame initialize() {

		if (frame == null) {
			frame = new JFrame();
			ImageIcon img = new ImageIcon(System.getProperty("user.dir") + "/ico/node_user.png");
			frame = new JFrame();
			frame.setIconImage(img.getImage());
			frame.setTitle("NODE CONNECTOR");
			frame.getContentPane().setLayout(null);
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			frame.setBounds((int) ((dimension.getWidth() - frame.getWidth()) / 2),
					(int) ((dimension.getHeight() - frame.getHeight()) / 2), 300, 700);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			nodestatus.setBounds(0, 0, 300, 30);
			logarea.setBounds(10, 44, 300, 484);
			frame.getContentPane().add(nodestatus);
			frame.add(logarea);
			frame.setResizable(false);
			Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
					.getDefaultConfiguration().getBounds();
			frame.setLocation((int) rect.getMaxX() - 5 - frame.getWidth(),
					(int) rect.getMaxY() - frame.getHeight() - 40);
			frame.setVisible(true);
		}
		return frame;
	}

	public static void log(String message) {
		logarea.append(message + "\n");
	}

}
