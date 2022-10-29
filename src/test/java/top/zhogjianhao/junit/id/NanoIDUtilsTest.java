package top.zhogjianhao.junit.id;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.zhogjianhao.id.NanoIDUtils;

/**
 * @author yeliulee
 * @description NanoIDUtils Test
 * @create 2022-10-29 22:34
 */
@DisplayName("NanoIDUtils 测试")
class NanoIDUtilsTest {

    @Test
    @DisplayName("生成随机 ID (默认)")
    void randomNanoId() {
        String id = NanoIDUtils.randomNanoId();
        System.out.println(id);
    }

    @Test
    @DisplayName("生成随机 ID (指定长度)")
    void testRandomNanoId() {
        String id = NanoIDUtils.randomNanoId(24);
        System.out.println(id);
    }

    @Test
    @DisplayName("生成随机 ID (指定长度和字典)")
    void testRandomNanoId1() {
        String id = NanoIDUtils.randomNanoId(24, "0123456789");
        System.out.println(id);
    }

    @Test
    @DisplayName("生成随机 ID (指定长度、字典和随机数生成器)")
    void testRandomNanoId2() {
        String id = NanoIDUtils.randomNanoId(24, "0123456789", NanoIDUtils.DEFAULT_ID_GENERATOR);
        System.out.println(id);
    }
}