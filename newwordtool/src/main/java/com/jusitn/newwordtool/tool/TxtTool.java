package com.jusitn.newwordtool.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Description: txt tool.
 *
 * @author Justin_Zhang
 * @date 12/5/2022 17:18
 */
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
}
