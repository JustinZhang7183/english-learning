package com.jusitn.newwordtool.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: transform words to txt.
 * <a href="https://github.com/Wei-Xia/most-frequent-technology-english-words">url</a>
 *
 * @author Justin_Zhang
 * @date 12/5/2022 17:12
 */
public class ProgramerWordExtract {
  private static final String source = "src/main/resources/programmer_source";

  private static final String target = "src/main/resources/programmer_target/target.txt";

  /**
   * extract words from file name.
   */
  public static void extractWordsFromFileName() {
    File path = new File(source);
    File[] files = path.listFiles();
    List<String> words = new ArrayList<>();
    for (File file : files) {
      String name = file.getName().replace(".md", "").substring(11);
      if (name.contains("-")) {
        for (String s : name.split("-")) {
          words.add(s);
        }
      } else {
        words.add(name);
      }
    }
    TxtTool.writeOneWordPerLine(target, words);
    System.out.println("number of words is: " + words.size());
  }

  public static void main(String[] args) {
    extractWordsFromFileName();
  }
}
