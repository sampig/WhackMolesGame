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
public abstract class USBDetectionUtil {

    public enum OSType {
        WINDOWS, LINUX, MAC, SOLARIS
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
        OSType type = getOSType();
        switch (type) {
        case LINUX:
            cmd = new String[] { "bash", "-c", "ls /dev/ttyU*" };
            break;
        case WINDOWS:
            cmd = new String[] { "wmic", "logicaldisk", "where", "drivetype=2", "get", "deviceid,", "volumename,",
                    "description" };
            break;
        case MAC:
            cmd = new String[] { "bash", "-c", "ls /dev/ttyU*" };
            break;
        default:
            cmd = new String[] { "bash", "-c", "ls /dev/ttyU*" };
            break;
        }
        try {
            ProcessBuilder pb = new ProcessBuilder(cmd);
            Process p = pb.start();
            InputStream is = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                list.add(line);
                System.out.println(line);
            }
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Get the type of OS.
     * 
     * @return OS type
     */
    private static OSType getOSType() {
        String os = System.getProperty("os.name");
        String osName = os.toLowerCase();
        OSType type = OSType.LINUX;
        if (osName.contains("linux")) {
            type = OSType.LINUX;
        } else if (osName.contains("windows")) {
            type = OSType.WINDOWS;
        } else if (osName.contains("solaris") || osName.contains("sunos")) {
            type = OSType.SOLARIS;
        } else if (osName.contains("mac os") || osName.contains("macos") || osName.contains("darwin")) {
            type = OSType.MAC;
        }
        return type;
    }

}
