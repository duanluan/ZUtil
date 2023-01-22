package top.csaf.junit.id;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.id.NanoIdUtils;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DisplayName("NanoId 工具类测试")
class NanoIdUtilsTest {

  @DisplayName("生成 NanoID")
  @Test
  void randomNanoId() {
    /** {@link top.csaf.id.NanoIdUtils#randomNanoId(int, char[], java.util.Random) } */
    assertThrows(NullPointerException.class, () -> NanoIdUtils.randomNanoId(0, (char[]) null, NanoIdUtils.DEFAULT_ID_GENERATOR));
    assertThrows(NullPointerException.class, () -> NanoIdUtils.randomNanoId(0, new char[0], null));
    assertThrows(IllegalArgumentException.class, () -> NanoIdUtils.randomNanoId(0, new char[0], NanoIdUtils.DEFAULT_ID_GENERATOR));
    assertThrows(IllegalArgumentException.class, () -> NanoIdUtils.randomNanoId(1, new char[0], NanoIdUtils.DEFAULT_ID_GENERATOR));
    assertThrows(IllegalArgumentException.class, () -> NanoIdUtils.randomNanoId(1, new char[256], NanoIdUtils.DEFAULT_ID_GENERATOR));
    assertDoesNotThrow(() -> NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_SIZE, NanoIdUtils.DEFAULT_ALPHABET, NanoIdUtils.DEFAULT_ID_GENERATOR));

    /** {@link top.csaf.id.NanoIdUtils#randomNanoId(int, java.lang.String, java.util.Random) } */
    assertThrows(NullPointerException.class, () -> NanoIdUtils.randomNanoId(0, (String) null, NanoIdUtils.DEFAULT_ID_GENERATOR));
    assertThrows(NullPointerException.class, () -> NanoIdUtils.randomNanoId(0, Arrays.toString(new char[0]), null));
    assertDoesNotThrow(() -> NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_SIZE, Arrays.toString(NanoIdUtils.DEFAULT_ALPHABET), NanoIdUtils.DEFAULT_ID_GENERATOR));

    /** {@link NanoIdUtils#randomNanoId(int, char[]) } */
    assertThrows(NullPointerException.class, () -> NanoIdUtils.randomNanoId(0, (char[]) null));
    assertDoesNotThrow(() -> NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_SIZE, NanoIdUtils.DEFAULT_ALPHABET));

    /** {@link NanoIdUtils#randomNanoId(int, java.lang.String) } */
    assertThrows(NullPointerException.class, () -> NanoIdUtils.randomNanoId(0, (String) null));
    assertDoesNotThrow(() -> NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_SIZE, Arrays.toString(NanoIdUtils.DEFAULT_ALPHABET)));

    /** {@link NanoIdUtils#randomNanoId(int) } */
    assertDoesNotThrow(() -> NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_SIZE));

    /** {@link NanoIdUtils#randomNanoId() } */
    assertDoesNotThrow(() -> NanoIdUtils.randomNanoId());
  }
}
