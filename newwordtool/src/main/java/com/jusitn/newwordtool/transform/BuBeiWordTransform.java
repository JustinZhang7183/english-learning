package com.jusitn.newwordtool.transform;

import com.jusitn.newwordtool.constants.FilePathConstants;
import com.jusitn.newwordtool.utils.TxtTool;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Description: transform for bu bei word app.
 *
 * @author Justin_Zhang
 * @date 12/5/2022 17:07
 */
@Slf4j
public class BuBeiWordTransform {
  public static void main(String[] args) {
    transformXmlsToTxt(new File(FilePathConstants.source));
  }

  /**
   * transform file of xml to txt in a directory.
   */
  public static void transformXmlsToTxt(File path) {
    File[] files = path.listFiles();
    assert files != null;
    for (File file : files) {
      transformXmlToTxt(file);
    }
  }

  /**
   * transform xml to txt.
   */
  private static void transformXmlToTxt(File sourceFile) {
    Map<String, List<String>> wordBookMap = transformXmlToWordBook(parseFileToDocument(sourceFile));
    assert wordBookMap != null;
    wordBookMap.forEach((key, value) -> {
      TxtTool.appendWordsToExistedTxt(FilePathConstants.target + key + ".txt", value);
    });
  }

  private static Map<String, List<String>> transformXmlToWordBook(Document document) {
    if (document == null) {
      return null;
    }
    NodeList itemNodeList = document.getElementsByTagName("item");
    NodeList wordNodeList = document.getElementsByTagName("word");
    NodeList tagNodeList = document.getElementsByTagName("tags");
    Map<String, List<String>> wordBookMap = new HashMap<>();
    for (int i = 0; i < itemNodeList.getLength(); i++) {
      String word = wordNodeList.item(i).getFirstChild().getNodeValue();
      String tag = tagNodeList.item(i).getFirstChild().getNodeValue();
      if (wordBookMap.containsKey(tag)) {
        wordBookMap.get(tag).add(word);
      } else {
        List<String> newList = new ArrayList<>();
        newList.add(word);
        wordBookMap.put(tag, newList);
      }
    }
    return wordBookMap;
  }

  private static Document parseFileToDocument(File sourceFile) {
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      return documentBuilder.parse(sourceFile);
    } catch (ParserConfigurationException | SAXException | IOException e) {
      log.info("parse file to document failed");
      return null;
    }
  }
}
