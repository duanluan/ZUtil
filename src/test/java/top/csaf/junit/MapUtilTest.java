package top.csaf.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.coll.MapUtil;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description MapUtil 工具类测试
 * @Author Rick
 * @Date 2024/5/30 09:21
 **/
@Slf4j
@DisplayName("MapUtil 工具类测试")
public class MapUtilTest {

    @DisplayName("isEmpty：是否为空Map")
    @Test
    void isEmpty() {
        assertTrue(MapUtil.isEmpty(null));
        Map<Object, Object> map = new HashMap<>();
        assertTrue(MapUtil.isEmpty(map));
        map.put("key", "value");
        assertFalse(MapUtil.isEmpty(map));
    }

    @DisplayName("isNotEmpty：是否不为空Map")
    @Test
    void isNotEmpty() {
        assertFalse(MapUtil.isNotEmpty(null));
        Map<Object, Object> map = new HashMap<>();
        assertFalse(MapUtil.isNotEmpty(map));
        map.put("key", "value");
        assertTrue(MapUtil.isNotEmpty(map));
    }


}
