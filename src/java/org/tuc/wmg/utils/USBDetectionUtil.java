package org.tuc.wmg.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A Utility for detection of all available ttyUSB connection.
 * 
 * @author Chenfeng Zhu
 *
 */
public class USBDetectionUtil {

	public USBDetectionUtil() {
	}

	/**
	 * Get all available ttyUSB connection.
	 * 
	 * @return a list contains all available ttyUSB connection.
	 */
	public static List<String> getAvailableUSB() {
		List<String> list = new ArrayList<String>(0);
		String[] cmd = { "bash", "-c", "ls /dev/ttyU*" };
		// here using /dev to detect.
		// another option is using "motelist", "-c".
		try {
			ProcessBuilder pb = new ProcessBuilder(cmd);
			Process p = pb.start();
			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return list;
	}

}
