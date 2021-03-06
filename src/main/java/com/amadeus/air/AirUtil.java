package com.amadeus.air;

import com.amadeus.reader.ResponseHandler;
import com.ets.fe.util.DirectoryHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import org.apache.log4j.Logger;

/**
 *
 * @author Yusuf
 */
public class AirUtil {

    static final Logger logger = Logger.getLogger(ResponseHandler.class.getName());

    public static boolean isValidFile(File f) {
        String firstLine = "";
        String lastLine = "";
        try {
            Scanner scanner = new Scanner(new FileReader(f));

            int linePosition = 0;
            try {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    if (linePosition == 0) {
                        firstLine = line;
                    } else {
                        lastLine = line;
                    }
                    linePosition++;
                }
            } finally {
                scanner.close();
            }

        } catch (FileNotFoundException ex) {

            logger.fatal(ex);
        } catch (Exception e) {

            logger.fatal(e);
        }
        if (firstLine.startsWith("AIR") && lastLine.startsWith("END")) {
            return true;
        } else {
            return false;
        }
    }

    public static void backupAir(File airFile) {
        /*
         File dir = new File("C://BKUP_AIR");
         if (!dir.exists()) {
         dir.mkdir();
         }
         */
        try {
            boolean backedUp = airFile.renameTo(new File(DirectoryHandler.getBackup_air_dir(), getAirNo(airFile) + ".txt"));
            if (backedUp) {
                System.out.println("BKUP Completed");
            } else {
                System.out.println("Could not BKUP");
                boolean deleted = airFile.delete();
                if (deleted) {
                    System.out.println("Deleted");
                } else {
                    System.out.println("Could not delete");
                }
            }
        } catch (Exception ex) {
            logger.fatal(ex);
        }
    }

    public static void sendAirToErrorDirectory(File airFile) {
        /*
         File dir = new File("C://ERROR_AIR");
         if (!dir.exists()) {
         dir.mkdir();
         }
         */
        try {
            System.out.println("Sending air to error folder");
            boolean sent = airFile.renameTo(new File("C://ERROR_AIR", getAirNo(airFile) + ".txt"));
            if (sent) {
                System.out.println("Sent air to error folder");
            } else {
                System.out.println("Could not sent");
                boolean deleted = airFile.delete();
                if (deleted) {
                    System.out.println("Deleted");
                } else {
                    System.out.println("Could not delete");
                }
            }
        } catch (Exception e) {
            logger.fatal(e);
            System.out.println("Could not send...trying to delete");
            airFile.delete();
            System.out.println("Deleted");
        }
    }

    public static String getAirNo(File air) {
        Scanner s = null;
        String airNo = "";
        try {
            s = new Scanner(air);
            fileReading:
            while (s.hasNextLine()) {
                String l = s.nextLine();
                if (l.startsWith("AIR")) {
                    String temp = "";
                    String[] data = l.split(";");
                    temp = data[0].substring(0, 3);
                    airNo = data[4];
                    break fileReading;
                }
            }

        } catch (FileNotFoundException ex) {
            logger.fatal(ex);
        } finally {
            s.close();
        }
        return airNo;
    }
}
