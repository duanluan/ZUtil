package top.csaf.coll;

import java.util.Map;

public class MapUtil {

  // region org.apache.commons.collections4.MapUtils

  public static <K> java.lang.Boolean getBoolean(java.util.Map<? super K, ?> map, K key, java.lang.Boolean defaultValue) {
    return org.apache.commons.collections4.MapUtils.getBoolean(map, key, defaultValue);
  }

  public static <K> java.lang.Boolean getBoolean(java.util.Map<? super K, ?> map, K key) {
    return org.apache.commons.collections4.MapUtils.getBoolean(map, key);
  }

  public static <K> java.lang.Byte getByte(java.util.Map<? super K, ?> map, K key) {
    return org.apache.commons.collections4.MapUtils.getByte(map, key);
  }

  public static <K> java.lang.Byte getByte(java.util.Map<? super K, ?> map, K key, java.lang.Byte defaultValue) {
    return org.apache.commons.collections4.MapUtils.getByte(map, key, defaultValue);
  }

  public static <K> java.lang.Short getShort(java.util.Map<? super K, ?> map, K key, java.lang.Short defaultValue) {
    return org.apache.commons.collections4.MapUtils.getShort(map, key, defaultValue);
  }

  public static <K> java.lang.Short getShort(java.util.Map<? super K, ?> map, K key) {
    return org.apache.commons.collections4.MapUtils.getShort(map, key);
  }

  public static <K> java.lang.Long getLong(java.util.Map<? super K, ?> map, K key, java.lang.Long defaultValue) {
    return org.apache.commons.collections4.MapUtils.getLong(map, key, defaultValue);
  }

  public static <K> java.lang.Long getLong(java.util.Map<? super K, ?> map, K key) {
    return org.apache.commons.collections4.MapUtils.getLong(map, key);
  }

  public static <K> java.lang.Float getFloat(java.util.Map<? super K, ?> map, K key) {
    return org.apache.commons.collections4.MapUtils.getFloat(map, key);
  }

  public static <K> java.lang.Float getFloat(java.util.Map<? super K, ?> map, K key, java.lang.Float defaultValue) {
    return org.apache.commons.collections4.MapUtils.getFloat(map, key, defaultValue);
  }

  public static <K> java.lang.Double getDouble(java.util.Map<? super K, ?> map, K key, java.lang.Double defaultValue) {
    return org.apache.commons.collections4.MapUtils.getDouble(map, key, defaultValue);
  }

  public static <K> java.lang.Double getDouble(java.util.Map<? super K, ?> map, K key) {
    return org.apache.commons.collections4.MapUtils.getDouble(map, key);
  }

  public static boolean isEmpty(java.util.Map<?, ?> map) {
    return org.apache.commons.collections4.MapUtils.isEmpty(map);
  }

  public static int size(java.util.Map<?, ?> map) {
    return org.apache.commons.collections4.MapUtils.size(map);
  }

  public static <K,V> java.util.Map<K, V> putAll(java.util.Map<K, V> map, java.lang.Object[] array) {
    return org.apache.commons.collections4.MapUtils.putAll(map, array);
  }

  public static java.util.Map<java.lang.String, java.lang.Object> toMap(java.util.ResourceBundle resourceBundle) {
    return org.apache.commons.collections4.MapUtils.toMap(resourceBundle);
  }

  public static <K,V> V getObject(java.util.Map<? super K, V> map, K key) {
    return org.apache.commons.collections4.MapUtils.getObject(map, key);
  }

  public static <K,V> V getObject(java.util.Map<K, V> map, K key, V defaultValue) {
    return org.apache.commons.collections4.MapUtils.getObject(map, key, defaultValue);
  }

  public static <K> java.lang.Integer getInteger(java.util.Map<? super K, ?> map, K key) {
    return org.apache.commons.collections4.MapUtils.getInteger(map, key);
  }

  public static <K> java.lang.Integer getInteger(java.util.Map<? super K, ?> map, K key, java.lang.Integer defaultValue) {
    return org.apache.commons.collections4.MapUtils.getInteger(map, key, defaultValue);
  }

  public static <K,V> java.util.Map<K, V> unmodifiableMap(java.util.Map<? extends K, ? extends V> map) {
    return org.apache.commons.collections4.MapUtils.unmodifiableMap(map);
  }

  public static <K> java.util.Map<?, ?> getMap(java.util.Map<? super K, ?> map, K key, java.util.Map<?, ?> defaultValue) {
    return org.apache.commons.collections4.MapUtils.getMap(map, key, defaultValue);
  }

  public static <K> java.util.Map<?, ?> getMap(java.util.Map<? super K, ?> map, K key) {
    return org.apache.commons.collections4.MapUtils.getMap(map, key);
  }

  public static <K> java.lang.Number getNumber(java.util.Map<? super K, ?> map, K key) {
    return org.apache.commons.collections4.MapUtils.getNumber(map, key);
  }

  public static <K> java.lang.Number getNumber(java.util.Map<? super K, ?> map, K key, java.lang.Number defaultValue) {
    return org.apache.commons.collections4.MapUtils.getNumber(map, key, defaultValue);
  }

  public static <K,V> java.util.SortedMap<K, V> unmodifiableSortedMap(java.util.SortedMap<K, ? extends V> map) {
    return org.apache.commons.collections4.MapUtils.unmodifiableSortedMap(map);
  }

  public static <K,V> java.util.Map<K, V> synchronizedMap(java.util.Map<K, V> map) {
    return org.apache.commons.collections4.MapUtils.synchronizedMap(map);
  }

  public static <K,V> java.util.SortedMap<K, V> synchronizedSortedMap(java.util.SortedMap<K, V> map) {
    return org.apache.commons.collections4.MapUtils.synchronizedSortedMap(map);
  }

  public static <K> java.lang.String getString(java.util.Map<? super K, ?> map, K key) {
    return org.apache.commons.collections4.MapUtils.getString(map, key);
  }

  public static <K> java.lang.String getString(java.util.Map<? super K, ?> map, K key, java.lang.String defaultValue) {
    return org.apache.commons.collections4.MapUtils.getString(map, key, defaultValue);
  }

  public static boolean isNotEmpty(java.util.Map<?, ?> map) {
    return org.apache.commons.collections4.MapUtils.isNotEmpty(map);
  }

  public static <K,V> java.util.SortedMap<K, V> predicatedSortedMap(java.util.SortedMap<K, V> map, org.apache.commons.collections4.Predicate<? super K> keyPred, org.apache.commons.collections4.Predicate<? super V> valuePred) {
    return org.apache.commons.collections4.MapUtils.predicatedSortedMap(map, keyPred, valuePred);
  }

  public static <K,V> java.util.SortedMap<K, V> transformedSortedMap(java.util.SortedMap<K, V> map, org.apache.commons.collections4.Transformer<? super K, ? extends K> keyTransformer, org.apache.commons.collections4.Transformer<? super V, ? extends V> valueTransformer) {
    return org.apache.commons.collections4.MapUtils.transformedSortedMap(map, keyTransformer, valueTransformer);
  }

  public static <K> boolean getBooleanValue(java.util.Map<? super K, ?> map, K key) {
    return org.apache.commons.collections4.MapUtils.getBooleanValue(map, key);
  }

  public static <K> boolean getBooleanValue(java.util.Map<? super K, ?> map, K key, boolean defaultValue) {
    return org.apache.commons.collections4.MapUtils.getBooleanValue(map, key, defaultValue);
  }

  public static <K> byte getByteValue(java.util.Map<? super K, ?> map, K key, byte defaultValue) {
    return org.apache.commons.collections4.MapUtils.getByteValue(map, key, defaultValue);
  }

  public static <K> byte getByteValue(java.util.Map<? super K, ?> map, K key) {
    return org.apache.commons.collections4.MapUtils.getByteValue(map, key);
  }

  public static <K> short getShortValue(java.util.Map<? super K, ?> map, K key, short defaultValue) {
    return org.apache.commons.collections4.MapUtils.getShortValue(map, key, defaultValue);
  }

  public static <K> short getShortValue(java.util.Map<? super K, ?> map, K key) {
    return org.apache.commons.collections4.MapUtils.getShortValue(map, key);
  }

  public static void verbosePrint(java.io.PrintStream out, java.lang.Object label, java.util.Map<?, ?> map) {
    org.apache.commons.collections4.MapUtils.verbosePrint(out, label, map);
  }

  public static void debugPrint(java.io.PrintStream out, java.lang.Object label, java.util.Map<?, ?> map) {
    org.apache.commons.collections4.MapUtils.debugPrint(out, label, map);
  }

  public static <K> int getIntValue(java.util.Map<? super K, ?> map, K key, int defaultValue) {
    return org.apache.commons.collections4.MapUtils.getIntValue(map, key, defaultValue);
  }

  public static <K> int getIntValue(java.util.Map<? super K, ?> map, K key) {
    return org.apache.commons.collections4.MapUtils.getIntValue(map, key);
  }

  public static <K> float getFloatValue(java.util.Map<? super K, ?> map, K key) {
    return org.apache.commons.collections4.MapUtils.getFloatValue(map, key);
  }

  public static <K> float getFloatValue(java.util.Map<? super K, ?> map, K key, float defaultValue) {
    return org.apache.commons.collections4.MapUtils.getFloatValue(map, key, defaultValue);
  }

  public static <K> double getDoubleValue(java.util.Map<? super K, ?> map, K key, double defaultValue) {
    return org.apache.commons.collections4.MapUtils.getDoubleValue(map, key, defaultValue);
  }

  public static <K> double getDoubleValue(java.util.Map<? super K, ?> map, K key) {
    return org.apache.commons.collections4.MapUtils.getDoubleValue(map, key);
  }

  public static <K> long getLongValue(java.util.Map<? super K, ?> map, K key) {
    return org.apache.commons.collections4.MapUtils.getLongValue(map, key);
  }

  public static <K> long getLongValue(java.util.Map<? super K, ?> map, K key, long defaultValue) {
    return org.apache.commons.collections4.MapUtils.getLongValue(map, key, defaultValue);
  }

  public static <K,V> java.util.Properties toProperties(java.util.Map<K, V> map) {
    return org.apache.commons.collections4.MapUtils.toProperties(map);
  }

  public static <K,V> org.apache.commons.collections4.IterableMap<K, V> predicatedMap(java.util.Map<K, V> map, org.apache.commons.collections4.Predicate<? super K> keyPred, org.apache.commons.collections4.Predicate<? super V> valuePred) {
    return org.apache.commons.collections4.MapUtils.predicatedMap(map, keyPred, valuePred);
  }

  public static <K,V> org.apache.commons.collections4.IterableMap<K, V> fixedSizeMap(java.util.Map<K, V> map) {
    return org.apache.commons.collections4.MapUtils.fixedSizeMap(map);
  }

  public static <K,V> java.util.Map<V, K> invertMap(java.util.Map<K, V> map) {
    return org.apache.commons.collections4.MapUtils.invertMap(map);
  }

  public static <K> void safeAddToMap(java.util.Map<? super K, java.lang.Object> map, K key, java.lang.Object value) throws java.lang.NullPointerException {
    org.apache.commons.collections4.MapUtils.safeAddToMap(map, key, value);
  }

  public static <K,V> org.apache.commons.collections4.IterableMap<K, V> transformedMap(java.util.Map<K, V> map, org.apache.commons.collections4.Transformer<? super K, ? extends K> keyTransformer, org.apache.commons.collections4.Transformer<? super V, ? extends V> valueTransformer) {
    return org.apache.commons.collections4.MapUtils.transformedMap(map, keyTransformer, valueTransformer);
  }

  public static <K,V> org.apache.commons.collections4.IterableMap<K, V> lazyMap(java.util.Map<K, V> map, org.apache.commons.collections4.Factory<? extends V> factory) {
    return org.apache.commons.collections4.MapUtils.lazyMap(map, factory);
  }

  public static <K,V> org.apache.commons.collections4.IterableMap<K, V> lazyMap(java.util.Map<K, V> map, org.apache.commons.collections4.Transformer<? super K, ? extends V> transformerFactory) {
    return org.apache.commons.collections4.MapUtils.lazyMap(map, transformerFactory);
  }

  public static <K,V> org.apache.commons.collections4.OrderedMap<K, V> orderedMap(java.util.Map<K, V> map) {
    return org.apache.commons.collections4.MapUtils.orderedMap(map);
  }

  public static <K,V> java.util.Map<K, V> emptyIfNull(java.util.Map<K, V> map) {
    return org.apache.commons.collections4.MapUtils.emptyIfNull(map);
  }

  public static <K,V> java.util.SortedMap<K, V> lazySortedMap(java.util.SortedMap<K, V> map, org.apache.commons.collections4.Transformer<? super K, ? extends V> transformerFactory) {
    return org.apache.commons.collections4.MapUtils.lazySortedMap(map, transformerFactory);
  }

  public static <K,V> java.util.SortedMap<K, V> lazySortedMap(java.util.SortedMap<K, V> map, org.apache.commons.collections4.Factory<? extends V> factory) {
    return org.apache.commons.collections4.MapUtils.lazySortedMap(map, factory);
  }

  public static <K,V> org.apache.commons.collections4.IterableSortedMap<K, V> iterableSortedMap(java.util.SortedMap<K, V> sortedMap) {
    return org.apache.commons.collections4.MapUtils.iterableSortedMap(sortedMap);
  }

  public static <K,V,E> void populateMap(org.apache.commons.collections4.MultiMap<K, V> map, java.lang.Iterable<? extends E> elements, org.apache.commons.collections4.Transformer<E, K> keyTransformer, org.apache.commons.collections4.Transformer<E, V> valueTransformer) {
    org.apache.commons.collections4.MapUtils.populateMap(map, elements, keyTransformer, valueTransformer);
  }

  public static <K,V> void populateMap(java.util.Map<K, V> map, java.lang.Iterable<? extends V> elements, org.apache.commons.collections4.Transformer<V, K> keyTransformer) {
    org.apache.commons.collections4.MapUtils.populateMap(map, elements, keyTransformer);
  }

  public static <K,V,E> void populateMap(java.util.Map<K, V> map, java.lang.Iterable<? extends E> elements, org.apache.commons.collections4.Transformer<E, K> keyTransformer, org.apache.commons.collections4.Transformer<E, V> valueTransformer) {
    org.apache.commons.collections4.MapUtils.populateMap(map, elements, keyTransformer, valueTransformer);
  }

  public static <K,V> void populateMap(org.apache.commons.collections4.MultiMap<K, V> map, java.lang.Iterable<? extends V> elements, org.apache.commons.collections4.Transformer<V, K> keyTransformer) {
    org.apache.commons.collections4.MapUtils.populateMap(map, elements, keyTransformer);
  }

  public static <K,V> java.util.SortedMap<K, V> fixedSizeSortedMap(java.util.SortedMap<K, V> map) {
    return org.apache.commons.collections4.MapUtils.fixedSizeSortedMap(map);
  }

  public static <K,V> org.apache.commons.collections4.IterableMap<K, V> iterableMap(java.util.Map<K, V> map) {
    return org.apache.commons.collections4.MapUtils.iterableMap(map);
  }

  // endregion
}
