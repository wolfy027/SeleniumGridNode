package util;

import java.awt.Dimension;
import java.awt.Toolkit;

public class SystemUtil {

	public static String getSystemResolution() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return (int) screenSize.getWidth() + "x" + (int) screenSize.getHeight();

	}
}
