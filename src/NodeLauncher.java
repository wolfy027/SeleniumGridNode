import java.awt.Color;
import java.io.File;
import java.net.Socket;
import java.util.Map.Entry;
import java.util.Set;

import org.openqa.grid.common.RegistrationRequest;
import org.openqa.grid.internal.utils.SelfRegisteringRemote;
import org.openqa.grid.internal.utils.configuration.GridNodeConfiguration;
import org.openqa.grid.shared.GridNodeServer;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.server.SeleniumServer;

import ui.NodeConsole;
import util.SystemUtil;

public class NodeLauncher {

	public static void main(String... s) throws Exception {
		NodeConsole.initialize();
		SelfRegisteringRemote remote = null;
		try {
			GridNodeConfiguration gridConfiguration = new GridNodeConfiguration();
			File JSONFile = new File("nodeConfig.json");
			gridConfiguration = GridNodeConfiguration.loadFromJSON(JSONFile.toString());
			String hubUrl = gridConfiguration.hub;

			if (isConnected("localhost:" + gridConfiguration.port.toString())) {
				NodeConsole.nodestatus.setBackground(Color.orange);
				NodeConsole.nodestatus.setText("Node Port already in Use");
				Thread.sleep(5000);
				System.exit(0);
			}

			Thread thread = new Thread() {
				public void run() {

					while (true) {
						if (isConnected(hubUrl)) {
							NodeConsole.nodestatus.setBackground(Color.green);
							NodeConsole.nodestatus.setText("Connected to HUB : " + hubUrl);
						} else {
							NodeConsole.nodestatus.setBackground(Color.red);
							NodeConsole.nodestatus.setText(hubUrl + " IS DOWN");
						}
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};
			thread.setDaemon(true);
			thread.start();

			NodeConsole.log("Hub Url  :  " + gridConfiguration.hub);
			NodeConsole.log("Role  :  " + gridConfiguration.role);
			NodeConsole.log("Node Port  :  " + gridConfiguration.port);

			Set<String> mapKeySet = gridConfiguration.custom.keySet();

			for (String key : mapKeySet) {
				System.setProperty(key, gridConfiguration.custom.get(key));
			}

			for (MutableCapabilities capability : gridConfiguration.capabilities) {
				capability.setCapability("resolution", SystemUtil.getSystemResolution());
			}
			RegistrationRequest request = new RegistrationRequest(gridConfiguration);
			remote = new SelfRegisteringRemote(request);
			GridNodeServer node = new SeleniumServer(request.getConfiguration());

			remote.setRemoteServer(node);
			remote.startRemoteServer();
			remote.startRegistrationProcess();
			for (MutableCapabilities capability : gridConfiguration.capabilities) {
				String pattern = "*************************************************";
				NodeConsole.log(pattern);
				for (Entry<String, Object> map : capability.asMap().entrySet()) {
					if (map.getKey().contains("CONFIG_UUID"))
						continue;
					NodeConsole.log(map.getKey() + "  :   " + map.getValue().toString());
				}
			}
		} catch (Exception e) {
			NodeConsole.log(e.getMessage());
		}

	}

	public static boolean isConnected(String url) {
		boolean flag;
		try {
			Socket socket = new Socket(url.replace("http://", "").split(":")[0],
					Integer.parseInt(url.replace("http://", "").split(":")[1]));
			socket.close();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

}