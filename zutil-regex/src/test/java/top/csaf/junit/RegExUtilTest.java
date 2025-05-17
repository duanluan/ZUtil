package top.csaf.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.coll.CollUtil;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static top.csaf.regex.RegExUtil.*;

@Slf4j
@DisplayName("正则工具类测试")
class RegExUtilTest {

  @Test
  void testReplaceAllSpecialChar() {
    assertEquals("\\/\\\\\\(\\)\\[\\]\\{\\}\\.\\?\\+\\*\\|\\^\\$", replaceAllSpecialChar("/\\()[]{}.?+*|^$"));
  }

  @Test
  void testGetMatcher() {
    assertEquals(Pattern.compile("", Pattern.DOTALL).matcher("").toString(), getMatcher("", "", Pattern.DOTALL).toString());
    assertEquals(Pattern.compile("").matcher("").toString(), getMatcher("", "").toString());

    assertTrue(isMatch("123\n567", ".*5", Pattern.DOTALL));
    assertTrue(isMatch("123\n567", ".*3"));
  }

  @Test
  void testIndexOf() {
    assertEquals(0, indexOf("123\n567", ".*5", Pattern.DOTALL));
    assertEquals(-1, indexOf("1", "2", Pattern.DOTALL));
    assertEquals(4, indexOf("123\n567", ".*5"));
    assertEquals(-1, indexOf("1", "2"));
  }

  @Test
  void testMatch() {
    assertThrows(IllegalArgumentException.class, () -> match("", "", -1, 1, 0));
    assertThrows(IllegalArgumentException.class, () -> match("", "", 0, -1, 0));
    assertEquals("5", match("123\n567", "(1).*(5)", 0, 2, Pattern.DOTALL));
    assertEquals("5", match("123\n567", ".*(5)", 0, 1));
    assertEquals("7", match("123|567", "(\\d)\\d(\\d)", 1, 2));
    assertNull(match("1", "2", 0, 0));

    assertEquals("5", matchFirstItem("123\n567", "(1).*(5)", 2, Pattern.DOTALL));
    assertEquals("5", matchFirstItem("123\n567", ".*(5)", 1));

    assertEquals("5", matchFirstGroup("123\n567", ".*(5)", 0, Pattern.DOTALL));
    assertEquals("5", matchFirstGroup("123\n567", ".*(5)", 0));

    assertEquals("5", matchFirstItemGroup("123\n567", ".*(5)", Pattern.DOTALL));
    assertEquals("5", matchFirstItemGroup("123\n567", ".*(5)"));


    assertThrows(IllegalArgumentException.class, () -> matchAllItems("", "", -1, 0));
    assertEquals("5", matchAllItems("123\n567", "(1).*(5)", 2, Pattern.DOTALL).get(0));
    assertEquals("1", matchAllItems("123\n567", "(\\d).*", 1).get(0));

    assertEquals("7", matchAllItemsFirstGroup("123\n567", ".*(\\d)", Pattern.DOTALL).get(0));
    assertEquals("3", matchAllItemsFirstGroup("123\n567", ".*(\\d)").get(0));


    assertThrows(IllegalArgumentException.class, () -> matchAllGroups("", "", -1, 0));
    assertEquals("7", matchAllGroups("123\n567", "(\\d).*(\\d)", 0, Pattern.DOTALL).get(1));
    assertEquals("7", matchAllGroups("123\n567", "(\\d).*(\\d)", 1).get(1));
    assertTrue(CollUtil.isEmpty(matchAllGroups("1", "2", 0)));


    assertEquals("7", matchAll("123\n567", ".*(\\d\\d)(\\d)", Pattern.DOTALL).get(1));
    assertEquals("3", matchAll("123\n567", ".*(\\d\\d)(\\d)").get(1));


    assertEquals("3", match("123", "1|(2)(3)", 1, 2));
    assertEquals("2", matchFirstItem("123", "(1)(2)|3", 2));
    assertEquals("2", matchFirstGroup("123", "1|(2)(3)", 1));
    assertEquals("1", matchFirstItemGroup("123", "(1)(2)|3"));
    assertArrayEquals(new String[]{"2", "4"}, matchAllItems("1234", "(1)(2)|(3)(4)", 2).toArray(new String[2]));
    assertArrayEquals(new String[]{"1", "3"}, matchAllItemsFirstGroup("1234", "(1)(2)|(3)(4)").toArray(new String[2]));
    assertArrayEquals(new String[]{"2", "3"}, matchAllGroups("123", "1|(2)(3)", 1).toArray(new String[2]));
    assertArrayEquals(new String[]{"2", "3"}, matchAll("123", "1|(2)(3)").toArray(new String[2]));
    assertArrayEquals(new String[]{"2", "3"}, matchAll("123", "1|(2)(3)", false).toArray(new String[2]));
    assertArrayEquals(new String[]{"1", "23"}, matchAll("123", "1|(2)(3)", true).toArray(new String[2]));
    assertArrayEquals(new String[]{"1", "23"}, matchAllItems("123", "1|(2)(3)", 0).toArray(new String[2]));
  }

  @Test
  void testReplace() {
    assertThrows(IllegalArgumentException.class, () -> replace("", "", "", -1, 0, 0));
    assertThrows(IllegalArgumentException.class, () -> replace("", "", "", 0, -1, 0));
    assertEquals("123\n867", replace("123\n567", "(1).*(5)", "8", 0, 2, Pattern.DOTALL));
    assertEquals("123\n867", replace("123\n567", ".*(5)", "8", 0, 1));
    assertEquals("123|568", replace("123|567", "(\\d)\\d(\\d)", "8", 1, 2));
    assertNull(replace("1", "2", "3", 0, 0));

    assertEquals("123\n867", replaceFirstItem("123\n567", "(1).*(5)", "8", 2, Pattern.DOTALL));
    assertEquals("123\n867", replaceFirstItem("123\n567", ".*(5)", "8", 1));

    assertEquals("123\n867", replaceFirstGroup("123\n567", ".*(5)", "8", 0, Pattern.DOTALL));
    assertEquals("123\n867", replaceFirstGroup("123\n567", ".*(5)", "8", 0));

    assertEquals("123\n867", replaceFirstItemGroup("123\n567", ".*(5)", "8", Pattern.DOTALL));
    assertEquals("123\n867", replaceFirstItemGroup("123\n567", ".*(5)", "8"));


    assertThrows(IllegalArgumentException.class, () -> replaceAllItems("", "", "", -1, 0));
    assertEquals("123\n867", replaceAllItems("123\n567", "(1).*(5)", "8", 2, Pattern.DOTALL));
    assertEquals("823\n867", replaceAllItems("123\n567", "(\\d).*", "8", 1));

    assertEquals("123\n568", replaceAllItemsFirstGroup("123\n567", ".*(\\d)", "8", Pattern.DOTALL));
    assertEquals("128\n568", replaceAllItemsFirstGroup("123\n567", ".*(\\d)", "8"));


    assertThrows(IllegalArgumentException.class, () -> replaceAllGroups("", "", "", -1, 0));
    assertEquals("823\n568", replaceAllGroups("123\n567", "(\\d).*(\\d)", "8", 0, Pattern.DOTALL));
    assertEquals("123\n868", replaceAllGroups("123\n567", "(\\d).*(\\d)", "8", 1));


    assertEquals("123\n88", replaceAll("123\n567", ".*(\\d\\d)(\\d)", "8", Pattern.DOTALL));
    assertEquals("88\n88", replaceAll("123\n567", ".*(\\d\\d)(\\d)", "8"));
    assertEquals("{\"b\":[\"123\"],\"ab\":123,\"b\":\"123\"}", replaceAllItems("{\"a\":[\"123\"],\"az\":123,\"d\":\"123\"}", "\\\"(((?!\\\").)*)\\\":", "b", 2));


    assertEquals("12", replace("123", "1|(2)(3)", "", 1, 2));
    assertEquals("13", replaceFirstItem("123", "(1)(2)|3", "", 2));
    assertEquals("13", replaceFirstGroup("123", "1|(2)(3)", "", 1));
    assertEquals("23", replaceFirstItemGroup("123", "(1)(2)|3", ""));
    assertEquals("13", replaceAllItems("1234", "(1)(2)|(3)(4)", "", 2));
    assertEquals("24", replaceAllItemsFirstGroup("1234", "(1)(2)|(3)(4)", ""));
    assertEquals("1", replaceAllGroups("123", "1|(2)(3)", "", 1));
    assertEquals("1", replaceAll("123", "1|(2)(3)", ""));
    assertEquals("1", replaceAll("123", "1|(2)(3)", "", false));
    assertEquals("", replaceAll("123", "1|(2)(3)", "", true));
    assertEquals("", replaceAllItems("123", "1|(2)(3)", "", 0));
  }

  @Test
  void testRemove() {
    assertEquals("12", remove("123", "1|(2)(3)", 1, 2));
    assertEquals("13", removeFirstItem("123", "(1)(2)|3", 2));
    assertEquals("13", removeFirstGroup("123", "1|(2)(3)", 1));
    assertEquals("23", removeFirstItemGroup("123", "(1)(2)|3"));
    assertEquals("13", removeAllItems("1234", "(1)(2)|(3)(4)", 2));
    assertEquals("24", removeAllItemsFirstGroup("1234", "(1)(2)|(3)(4)"));
    assertEquals("1", removeAllGroups("123", "1|(2)(3)", 1));
    assertEquals("1", removeAll("123", "1|(2)(3)"));
    assertEquals("1", removeAll("123", "1|(2)(3)", false));
    assertEquals("", removeAll("123", "1|(2)(3)", true));
    assertEquals("", removeAllItems("123", "1|(2)(3)", 0));
  }

  @Test
  void testContainsHanZi() {
    assertTrue(containsHanZi("1中国"));
    assertFalse(containsHanZi("1"));
  }
}
