package top.csaf;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 属性 Function
 *
 * @param <T> 输入类型
 * @param <R> 输出类型
 */
@FunctionalInterface
public interface PropertyFunction<T, R> extends Function<T, R>, Serializable {
}
