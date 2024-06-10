package top.csaf.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.lang.NumberUtil;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisplayName("NUMBER 工具类测试")
class NumberUtilTest {

    @DisplayName("leThanZero：小于 0，数字为 null 时返回 false")
    @Test
    void leThanZero() {
        assertFalse(NumberUtil.leThanZero(null));
        assertTrue(NumberUtil.leThanZero(-1));
        assertFalse(NumberUtil.leThanZero(0));
        assertFalse(NumberUtil.leThanZero(1));
    }

    @DisplayName("leThanEqToZero：小于等于 0，数字为 null 时返回 false")
    @Test
    void leThanEqToZero() {
        assertFalse(NumberUtil.leThanEqToZero(null));
        assertTrue(NumberUtil.leThanEqToZero(0));
        assertTrue(NumberUtil.leThanEqToZero(-10));
        assertFalse(NumberUtil.leThanEqToZero(10));
        assertFalse(NumberUtil.leThanEqToZero(Double.MAX_VALUE));
        assertFalse(NumberUtil.leThanEqToZero(Double.MIN_VALUE));
        assertTrue(NumberUtil.leThanEqToZero(0.0));
        assertTrue(NumberUtil.leThanEqToZero(-0.0));
        assertFalse(NumberUtil.leThanEqToZero(Double.POSITIVE_INFINITY));
        assertTrue(NumberUtil.leThanEqToZero(Double.NEGATIVE_INFINITY));
        assertFalse(NumberUtil.leThanEqToZero(Double.NaN));
    }

    @DisplayName("geThanZero：大于 0，数字为 null 时返回 false")
    @Test
    void geThanZero() {
        assertFalse(NumberUtil.geThanZero(null));
        assertFalse(NumberUtil.geThanZero(-1));
        assertFalse(NumberUtil.geThanZero(0));
        assertTrue(NumberUtil.geThanZero(1));
    }
    @DisplayName("geThanEqToZero：大于等于 0，数字为 null 时返回 false")
    @Test
    void geThanEqToZero(){
        assertFalse(NumberUtil.geThanEqToZero(null));
        assertTrue(NumberUtil.geThanEqToZero(0));
        assertFalse(NumberUtil.geThanEqToZero(-10));
        assertTrue(NumberUtil.geThanEqToZero(10));
        assertTrue(NumberUtil.geThanEqToZero(Double.MAX_VALUE));
        assertTrue(NumberUtil.geThanEqToZero(Double.MIN_VALUE));
        assertTrue(NumberUtil.geThanEqToZero(0.0));
        assertTrue(NumberUtil.geThanEqToZero(-0.0));
        assertTrue(NumberUtil.geThanEqToZero(Double.POSITIVE_INFINITY));
        assertFalse(NumberUtil.geThanEqToZero(Double.NEGATIVE_INFINITY));
        assertFalse(NumberUtil.geThanEqToZero(Double.NaN));
    }
    @DisplayName("isInteger：是否为整数，数字为 null 时返回 false")
    @Test
    void isInteger(){
        assertTrue(NumberUtil.isInteger("123456"));
        assertFalse(NumberUtil.isInteger("1234.56"));
        assertFalse(NumberUtil.isInteger("1234a"));
        assertFalse(NumberUtil.isInteger("a1234"));
        assertFalse(NumberUtil.isInteger("1234.56e-7"));
        assertTrue(NumberUtil.isInteger("0"));
        assertTrue(NumberUtil.isInteger("-123456"));
        assertTrue(NumberUtil.isInteger("0123456"));
        assertFalse(NumberUtil.isInteger("0x123456"));
        assertFalse(NumberUtil.isInteger("1234 56"));
        assertFalse(NumberUtil.isInteger("1234.56 78"));
        assertFalse(NumberUtil.isInteger("1234a56"));
        assertFalse(NumberUtil.isInteger("1234.56a78"));
        assertFalse(NumberUtil.isInteger("a1234.56"));
        assertFalse(NumberUtil.isInteger("1234.56a"));
        assertFalse(NumberUtil.isInteger("a1234.56"));
        assertFalse(NumberUtil.isInteger("1234.56e-7a"));
        assertFalse(NumberUtil.isInteger("1234.56e-7 89"));
        assertFalse(NumberUtil.isInteger("1234.56e-7a89"));
        assertFalse(NumberUtil.isInteger("1234.56e-7 89a"));
        assertFalse(NumberUtil.isInteger(" "));
        assertFalse(NumberUtil.isInteger(""));
        assertFalse(NumberUtil.isInteger(null));
    }
    @DisplayName("parseLong：数字为 null 时返回 0")
    @Test
    void parseLong(){
        assertEquals(0L, NumberUtil.parseLong("0"));
        assertEquals(123456L, NumberUtil.parseLong("123456"));
        assertEquals(0L, NumberUtil.parseLong("1234.56"));
        assertEquals(0L, NumberUtil.parseLong("1234a"));
        assertEquals(0L, NumberUtil.parseLong("a1234"));
        assertEquals(0L, NumberUtil.parseLong("1234.56e-7"));
        assertEquals(0L, NumberUtil.parseLong("0x123456"));
        assertEquals(0L, NumberUtil.parseLong("1234 56"));
        assertEquals(0L, NumberUtil.parseLong("1234.56 78"));
        assertEquals(0L, NumberUtil.parseLong("1234a56"));
        assertEquals(0L, NumberUtil.parseLong("1234.56a78"));
        assertEquals(0L, NumberUtil.parseLong("a1234.56"));
        assertEquals(0L, NumberUtil.parseLong("1234.56a"));
        assertEquals(0L, NumberUtil.parseLong("a1234.56"));
        assertEquals(0L, NumberUtil.parseLong("1234.56e-7a"));
        assertEquals(0L, NumberUtil.parseLong("1234.56e-7 89"));
        assertEquals(0L, NumberUtil.parseLong("1234.56e-7a89"));
        assertEquals(0L, NumberUtil.parseLong("1234.56e-7 89a"));
        assertEquals(0L, NumberUtil.parseLong(" "));
        assertEquals(0L, NumberUtil.parseLong(""));
        assertEquals(0L, NumberUtil.parseLong(null));
    }

}
