package com.jusitn.newwordtool.utils;

import com.jusitn.newwordtool.constants.FilePathConstants;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Description: txt tool test case.
 *
 * @author Justin_Zhang
 * @date 12/9/2022 13:29
 */
public class TxtToolTest {
  @Test
  public void append_content_to_txt() {
    TxtTool.appendWordsToExistedTxt(FilePathConstants.test + "test.txt",
        List.of("banana", "apple"));
  }

  @Test
  public void read_existed_content_by_line() {
    TxtTool.readExistedContentByLine(FilePathConstants.test + "test.txt");
  }
}
