package com.jusitn.newwordtool.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Description: transform for bu bei word app.
 *
 * @author Justin_Zhang
 * @date 12/5/2022 17:07
 */
public class BuBeiWordTransform {

  private static final String source = "src/main/resources/source";

  private static final String target = "src/main/resources/target";

  public static void main(String[] args) {
    transformXmlsToTxt(new File(source));
  }

  /**
   * transform xml to txt for a directory.
   *
   */
  public static void transformXmlsToTxt(File path) {
    try {
      File[] files = path.listFiles();
      for (File file : files) {
        transformXmlToTxt(file);
      }
    } catch (ParserConfigurationException | IOException | SAXException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * transform xml to txt.
   *
   */
  public static void transformXmlToTxt(File sourceFile) throws ParserConfigurationException,
      IOException, SAXException {
    List<String> list = new ArrayList<>();
    String sourceName = sourceFile.getName().replace(".xml", "");
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    Document document = documentBuilder.parse(sourceFile);
    NodeList rootNodeList = document.getElementsByTagName("wordbook");
    Node rootNode = rootNodeList.item(0);
    Document ownerDocument = rootNode.getOwnerDocument();
    NodeList items = ownerDocument.getElementsByTagName("item");
    List<String> words = new ArrayList<>();
    for (int i = 0; i < items.getLength(); i++) {
      Node node = document.getElementsByTagName("word").item(i);
      String word = node.getFirstChild().getNodeValue();
      words.add(word);
      if (word.contains(" ")) {
        list.add(word);
      }
    }
    TxtTool.writeOneWordPerLine(target + "/" + sourceName + ".txt", words);
    if (list.size() > 0) {
      System.out.println("There are some phrases in " + sourceName + " :");
      for (String s : list) {
        System.out.println(s);
      }
    }
  }
}
