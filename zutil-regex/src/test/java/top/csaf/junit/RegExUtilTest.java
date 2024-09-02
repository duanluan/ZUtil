package top.csaf.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.coll.CollUtil;
import top.csaf.regex.RegExUtil;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
@DisplayName("正则工具类测试")
class RegExUtilTest {

  @Test
  void replaceAllItemsSpecialChar() {
    assertEquals("\\/\\\\\\(\\)\\[\\]\\{\\}\\.\\?\\+\\*\\|\\^\\$", RegExUtil.replaceAllSpecialChar("/\\()[]{}.?+*|^$"));
  }

  @Test
  void getMatcher() {
    assertEquals(Pattern.compile("", Pattern.DOTALL).matcher("").toString(), RegExUtil.getMatcher("", "", Pattern.DOTALL).toString());
    assertEquals(Pattern.compile("").matcher("").toString(), RegExUtil.getMatcher("", "").toString());

    assertTrue(RegExUtil.isMatch("123\n567", ".*5", Pattern.DOTALL));
    assertTrue(RegExUtil.isMatch("123\n567", ".*3"));
  }

  @Test
  void indexOf() {
    assertEquals(0, RegExUtil.indexOf("123\n567", ".*5", Pattern.DOTALL));
    assertEquals(-1, RegExUtil.indexOf("1", "2", Pattern.DOTALL));
    assertEquals(4, RegExUtil.indexOf("123\n567", ".*5"));
    assertEquals(-1, RegExUtil.indexOf("1", "2"));
  }

  @Test
  void match() {
    assertThrows(IllegalArgumentException.class, () -> RegExUtil.match("", "", -1, 1, 0));
    assertThrows(IllegalArgumentException.class, () -> RegExUtil.match("", "", 0, -1, 0));
    assertEquals("5", RegExUtil.match("123\n567", "(1).*(5)", 0, 2, Pattern.DOTALL));
    assertEquals("5", RegExUtil.match("123\n567", ".*(5)", 0, 1));
    assertEquals("7", RegExUtil.match("123|567", "(\\d)\\d(\\d)", 1, 2));
    assertNull(RegExUtil.match("1", "2", 0, 0));

    assertEquals("5", RegExUtil.matchFirstItem("123\n567", "(1).*(5)", 2, Pattern.DOTALL));
    assertEquals("5", RegExUtil.matchFirstItem("123\n567", ".*(5)", 1));

    assertEquals("5", RegExUtil.matchFirstGroup("123\n567", ".*(5)", 0, Pattern.DOTALL));
    assertEquals("5", RegExUtil.matchFirstGroup("123\n567", ".*(5)", 0));

    assertEquals("5", RegExUtil.matchFirstItemGroup("123\n567", ".*(5)", Pattern.DOTALL));
    assertEquals("5", RegExUtil.matchFirstItemGroup("123\n567", ".*(5)"));


    assertThrows(IllegalArgumentException.class, () -> RegExUtil.matchAllItems("", "", -1, 0));
    assertEquals("5", RegExUtil.matchAllItems("123\n567", "(1).*(5)", 2, Pattern.DOTALL).get(0));
    assertEquals("1", RegExUtil.matchAllItems("123\n567", "(\\d).*", 1).get(0));

    assertEquals("7", RegExUtil.matchAllItemsFirstGroup("123\n567", ".*(\\d)", Pattern.DOTALL).get(0));
    assertEquals("3", RegExUtil.matchAllItemsFirstGroup("123\n567", ".*(\\d)").get(0));


    assertThrows(IllegalArgumentException.class, () -> RegExUtil.matchAllGroups("", "", -1, 0));
    assertEquals("7", RegExUtil.matchAllGroups("123\n567", "(\\d).*(\\d)", 0, Pattern.DOTALL).get(1));
    assertEquals("7", RegExUtil.matchAllGroups("123\n567", "(\\d).*(\\d)", 1).get(1));
    assertTrue(CollUtil.isEmpty(RegExUtil.matchAllGroups("1", "2", 0)));


    assertEquals("7", RegExUtil.matchAll("123\n567", ".*(\\d\\d)(\\d)", Pattern.DOTALL).get(1));
    assertEquals("3", RegExUtil.matchAll("123\n567", ".*(\\d\\d)(\\d)").get(1));
  }

  @Test
  void replace() {
    assertThrows(IllegalArgumentException.class, () -> RegExUtil.replace("", "", "", -1, 0, 0));
    assertThrows(IllegalArgumentException.class, () -> RegExUtil.replace("", "", "", 0, -1, 0));
    assertEquals("123\n867", RegExUtil.replace("123\n567", "(1).*(5)", "8", 0, 2, Pattern.DOTALL));
    assertEquals("123\n867", RegExUtil.replace("123\n567", ".*(5)", "8", 0, 1));
    assertEquals("123|568", RegExUtil.replace("123|567", "(\\d)\\d(\\d)", "8", 1, 2));
    assertNull(RegExUtil.replace("1", "2", "3", 0, 0));

    assertEquals("123\n867", RegExUtil.replaceFirstItem("123\n567", "(1).*(5)", "8", 2, Pattern.DOTALL));
    assertEquals("123\n867", RegExUtil.replaceFirstItem("123\n567", ".*(5)", "8", 1));

    assertEquals("123\n867", RegExUtil.replaceFirstGroup("123\n567", ".*(5)", "8", 0, Pattern.DOTALL));
    assertEquals("123\n867", RegExUtil.replaceFirstGroup("123\n567", ".*(5)", "8", 0));

    assertEquals("123\n867", RegExUtil.replaceFirstItemGroup("123\n567", ".*(5)", "8", Pattern.DOTALL));
    assertEquals("123\n867", RegExUtil.replaceFirstItemGroup("123\n567", ".*(5)", "8"));


    assertThrows(IllegalArgumentException.class, () -> RegExUtil.replaceAllItems("", "", "", -1, 0));
    assertEquals("123\n867", RegExUtil.replaceAllItems("123\n567", "(1).*(5)", "8", 2, Pattern.DOTALL));
    assertEquals("823\n867", RegExUtil.replaceAllItems("123\n567", "(\\d).*", "8", 1));

    assertEquals("123\n568", RegExUtil.replaceAllItemsFirstGroup("123\n567", ".*(\\d)", "8", Pattern.DOTALL));
    assertEquals("128\n568", RegExUtil.replaceAllItemsFirstGroup("123\n567", ".*(\\d)", "8"));


    assertThrows(IllegalArgumentException.class, () -> RegExUtil.replaceAllGroups("", "", "", -1, 0));
    assertEquals("823\n568", RegExUtil.replaceAllGroups("123\n567", "(\\d).*(\\d)", "8", 0, Pattern.DOTALL));
    assertEquals("123\n868", RegExUtil.replaceAllGroups("123\n567", "(\\d).*(\\d)", "8", 1));


    assertEquals("123\n88", RegExUtil.replaceAll("123\n567", ".*(\\d\\d)(\\d)", "8", Pattern.DOTALL));
    assertEquals("88\n88", RegExUtil.replaceAll("123\n567", ".*(\\d\\d)(\\d)", "8"));
    assertEquals("{\"b\":[\"123\"],\"ab\":123,\"b\":\"123\"}", RegExUtil.replaceAllItems("{\"a\":[\"123\"],\"az\":123,\"d\":\"123\"}", "\\\"(((?!\\\").)*)\\\":", "b", 2));
  }

  @Test
  void containsHanZi() {
    assertTrue(RegExUtil.containsHanZi("1中国"));
    assertFalse(RegExUtil.containsHanZi("1"));
  }
}
