/*
 Copyright (c) 2020, My Name. All rights reserved. No usage without permission.
*/

package pokemans.utilities.read;

import java.io.*;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.DAYS;

public class GameMasterCheck {

  // set date format pattern
  private static final DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("yyyyMMdd");

  public static String checkFile() {
    String existingFileName = "src\\dataFiles\\GAME_MASTER_V2_20200624.json";
    try {
      File tmpDir = new File(existingFileName);
      boolean fileExists = tmpDir.exists();

      // check # of days between current date and date from game master
      long noOfDaysBetween = calculateDaysBetween(existingFileName);
      if (noOfDaysBetween > 1) {
        ZoneId americaNY = ZoneId.of("America/New_York");
        String newFileName = (timeStampPattern.format(java.time.LocalDateTime.now(americaNY)));
        newFileName = "src\\dataFiles\\GAME_MASTER_V2_" + newFileName + ".json";

        MessageDigest md = MessageDigest.getInstance("MD5");

        String hexFile = "", hexURL = "";
        String gameMasterUrl =
            "https://raw.githubusercontent.com/pokemongo-dev-contrib/pokemongo-game-master/"
                + "master/versions/latest/GAME_MASTER.json";

        // if no game master currently exists, create file
        if (!fileExists) {
          writeNewFile(fileExists, gameMasterUrl, existingFileName, newFileName);
        } else {
          hexFile = checksumFile(existingFileName, md);
          hexURL = checksumURL(gameMasterUrl, md);

          // if checksums are different, overwrite file
          if (!hexFile.equals(hexURL)) {
            writeNewFile(fileExists, gameMasterUrl, existingFileName, newFileName);
            return newFileName;
          }
        }
      }
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return existingFileName;
  }

  private static String checksumFile(String fileName, MessageDigest md) {
    StringBuilder result = new StringBuilder();
    int nread;
    byte[] buffer = new byte[1024];

    try (InputStream fis = new FileInputStream(fileName)) {
      while ((nread = fis.read(buffer)) != -1) {
        md.update(buffer, 0, nread);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    // bytes to hex
    for (byte b : md.digest()) {
      result.append(String.format("%02x", b));
    }
    return result.toString();
  }

  private static String checksumURL(String fileName, MessageDigest md) {
    StringBuilder result = new StringBuilder();
    int nread;
    byte[] buffer = new byte[1024];

    try (InputStream urlis = new URL(fileName).openStream()) {
      while ((nread = urlis.read(buffer)) != -1) {
        md.update(buffer, 0, nread);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    // bytes to hex
    for (byte b : md.digest()) {
      result.append(String.format("%02x", b));
    }
    return result.toString();
  }

  private static long calculateDaysBetween(String fileName) {
    // extract date string from current game master file name
    String dateFromFileName;
    dateFromFileName = fileName.substring(29, 37);
    LocalDate currentDate = LocalDate.now();
    // set LocalDate from "dateFromFileName"
    LocalDate dateFromCurrentGameMaster = LocalDate.parse(dateFromFileName, timeStampPattern);

    // calculate days between now and date from game master
    return DAYS.between(dateFromCurrentGameMaster, currentDate);
  }

  private static void writeNewFile(
      boolean fileExists, String gameMasterUrl, String existingFileName, String newFileName) {
    try (BufferedInputStream in = new BufferedInputStream(new URL(gameMasterUrl).openStream());
        // new file name
        FileOutputStream fileOutputStream = new FileOutputStream(newFileName)) {
      byte[] dataBuffer = new byte[1024];
      int bytesRead;
      while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
        fileOutputStream.write(dataBuffer, 0, bytesRead);
      }

      // delete old/existing file
      if (fileExists) {
        File file = new File(existingFileName);
        if (file.delete()) {
          System.out.println("\n\t\t**Old Game Master deleted successfully**\n");
        }
      }
    } catch (IOException e) {
      System.out.println("\nGame Master Check IO Exception!\n");
      e.printStackTrace();
    }
  }
}
