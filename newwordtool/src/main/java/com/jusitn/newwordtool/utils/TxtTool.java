package com.jusitn.newwordtool.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: txt tool.
 *
 * @author Justin_Zhang
 * @date 12/5/2022 17:18
 */
@Slf4j
public class TxtTool {
  /**
   * write one word per line.
   */
  public static void writeOneWordPerLine(String path, List<String> words) {
    try {
      File file = new File(path);
      FileOutputStream outputStream = new FileOutputStream(file);
      for (String word : words) {
        outputStream.write(word.getBytes());
        outputStream.write("\n".getBytes());
      }
      outputStream.flush();
      outputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * append word to existed txt.
   */
  public static void appendWordsToExistedTxt(String path, List<String> words) {
    File file = new File(path);
    List<String> oldWords = readExistedContentByLine(path);
    words.removeAll(oldWords);
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
      log.info("{} word book: start at \"{}\", append {} words", path, words.get(0), words.size());
      for (String word : words) {
        bufferedWriter.write(word);
        bufferedWriter.write("\n");
      }
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }

  /**
   * read existed content by line.
   */
  public static List<String> readExistedContentByLine(String path) {
    List<String> words = new ArrayList<>();
    File file = new File(path);
    if (!file.exists()) {
      return words;
    }
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
      String word = null;
      while ((word = bufferedReader.readLine()) != null) {
        words.add(word);
      }
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    return words;
  }

}
