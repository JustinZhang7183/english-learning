package com.jusitn.newwordtool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * bootstrap class.
 */
@SpringBootApplication
public class NewwordtoolApplication {
  private static final String source = "src/main/resources/source";

  private static final String target = "src/main/resources/target";

  /**
   * runner method.
   */
  public static void main(String[] args) {
    // SpringApplication.run(NewwordtoolApplication.class, args);
    try {
      File path = new File(source);
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
    File outFile = new File(target + "/" + sourceName + ".txt");
    FileOutputStream fileOutputStream = new FileOutputStream(outFile);
    for (int i = 0; i < items.getLength(); i++) {
      Node node = document.getElementsByTagName("word").item(i);
      String word = node.getFirstChild().getNodeValue();
      if (word.contains(" ")) {
        list.add(word);
      }
      fileOutputStream.write(word.getBytes());
      fileOutputStream.write("\n".getBytes());
    }
    fileOutputStream.flush();
    fileOutputStream.close();
    if (list.size() > 0) {
      System.out.println("There are some phrases in " + sourceName + " :");
      for (String s : list) {
        System.out.println(s);
      }
    }
  }

}
