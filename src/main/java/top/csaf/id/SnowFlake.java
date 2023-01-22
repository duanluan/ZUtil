package top.csaf.id;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Twitter 雪花算法
 * <p>
 * 二进制：0 - 41 位时间戳 - 5 位数据中心 ID - 5 位机器 ID - 12 位序列号
 * <p>
 * https://github.com/beyondfengyu/SnowFlake
 */
@Deprecated
@Slf4j
public class SnowFlake {

  /**
   * 开始时间戳
   */
  private final static long START_TIME_MILLIS = 1640966400000L;

  /**
   * 默认数据中心 ID 或机器 ID 二进制位数
   */
  private static final long DEFAULT_DATA_CENTER_OR_MACHINE_BIT = 5L;
  /**
   * 最大数据中心 ID 或机器 ID 二进制位数
   */
  private static final long MAX_DATA_CENTER_AND_MACHINE_BIT = 10L;
  /**
   * 序列号二进制位数
   */
  private static final long SEQUENCE_BIT = 12L;
  /**
   * 序列号最大值
   */
  private static final long SEQUENCE_MAX_NUMBER = getMaxNumberByBit(SEQUENCE_BIT);

  /**
   * 机器 ID 左移 = 序列号二进制位数
   */
  private static long MACHINE_LEFT = SEQUENCE_BIT;
  /**
   * 数据中心 ID 左移 = 机器 ID 左移 + 机器 ID 二进制位数
   */
  private static final long DATA_CENTER_LEFT = MACHINE_LEFT + DEFAULT_DATA_CENTER_OR_MACHINE_BIT;
  /**
   * 时间戳左移 = 数据中心 ID 左移 + 数据中心 ID 二进制位数
   */
  private static final long TIME_MILLIS_LEFT = DATA_CENTER_LEFT + DEFAULT_DATA_CENTER_OR_MACHINE_BIT;

  /**
   * 数据中心 ID 二进制位数
   */
  private long dataCenterBit = DEFAULT_DATA_CENTER_OR_MACHINE_BIT;
  /**
   * 机器 ID 二进制位数
   */
  private long machineBit = DEFAULT_DATA_CENTER_OR_MACHINE_BIT;
  /**
   * 数据中心 ID
   */
  @Setter
  private long dataCenterId;
  /**
   * 机器 ID
   */
  @Setter
  private long machineId;

  /**
   * 根据位数计算十进制最大值
   * <p>
   * ~(-1 << 5 = -1 * (2 ^ 5) = -32) = 31，即 11111 的十进制为 31
   *
   * @param bit 位数
   * @return 十进制最大值
   */
  private static long getMaxNumberByBit(long bit) {
    return ~(-1L << bit);
  }

  public SnowFlake(SnowFlakeBuilder snowFlakeBuilder) {
    this.dataCenterBit = snowFlakeBuilder.dataCenterBit;
    this.machineBit = snowFlakeBuilder.machineBit;
  }

  /**
   * 雪花算法类 Builder
   * <p>
   * 默认数据中心 ID 二进制位数和机器 ID 二进制位数为 5
   */
  public static class SnowFlakeBuilder {
    private Long dataCenterBit;
    private Long machineBit;
    private long dataCenterId;
    private long machineId;

    public SnowFlakeBuilder() {
      this.dataCenterBit = DEFAULT_DATA_CENTER_OR_MACHINE_BIT;
      this.machineBit = DEFAULT_DATA_CENTER_OR_MACHINE_BIT;
    }

    public SnowFlakeBuilder(long dataCenterBit, long machineBit) {
      this.dataCenterBit = dataCenterBit;
      this.machineBit = machineBit;
    }

    /**
     * 设置数据中心 ID 二进制位数，和机器 ID 二进制位数同时只需设置一个即可
     *
     * @param dataCenterBit 数据中心 ID 二进制位数
     * @return 雪花算法类 Builder
     */
    public SnowFlakeBuilder dataCenterBit(long dataCenterBit) {
      if (dataCenterBit > MAX_DATA_CENTER_AND_MACHINE_BIT || dataCenterBit < 0) {
        throw new IllegalArgumentException("dataCenterBit cannot be greater than " + MAX_DATA_CENTER_AND_MACHINE_BIT + " or less than 0");
      }
      this.dataCenterBit = dataCenterBit;
      this.machineBit = MAX_DATA_CENTER_AND_MACHINE_BIT - dataCenterBit;
      return this;
    }

    /**
     * 设置机器 ID 二进制位数，和数据中心 ID 二进制位数同时只需设置一个即可
     *
     * @param machineBit 机器 ID 二进制位数
     * @return 雪花算法类 Builder
     */
    public SnowFlakeBuilder machineBit(long machineBit) {
      if (machineBit > MAX_DATA_CENTER_AND_MACHINE_BIT || machineBit < 0) {
        throw new IllegalArgumentException("machineBit cannot be greater than " + MAX_DATA_CENTER_AND_MACHINE_BIT + " or less than 0");
      }
      this.dataCenterBit = MAX_DATA_CENTER_AND_MACHINE_BIT - machineBit;
      this.machineBit = machineBit;
      return this;
    }

    public SnowFlakeBuilder dataCenterId(long dataCenterId) {
      this.dataCenterId = dataCenterId;
      return this;
    }

    public SnowFlakeBuilder machineId(long machineId) {
      this.machineId = machineId;
      return this;
    }

    public SnowFlake build() {
      return new SnowFlake(this);
    }
  }

  public static SnowFlakeBuilder builder() {
    return new SnowFlakeBuilder();
  }

  /**
   * 序列号
   */
  private long sequence = 0L;
  /**
   * 最后时间戳
   */
  private long lastTimeMillis = -1L;

  /**
   * @param dataCenterId 数据中心 ID
   * @param machineId    机器 ID
   */
  public SnowFlake(long dataCenterId, long machineId) {
    // 数据中心 ID 判断
    long dataCenterMaxNumber = getMaxNumberByBit(dataCenterBit);
    if (dataCenterId > dataCenterMaxNumber || dataCenterId < 0) {
      throw new IllegalArgumentException("dataCenterId cannot be greater than " + dataCenterMaxNumber + " or less than 0");
    }
    // 机器 ID 判断
    long machineMaxNumber = getMaxNumberByBit(machineBit);
    if (machineId > machineMaxNumber || machineId < 0) {
      throw new IllegalArgumentException("machineId cannot be greater than " + machineMaxNumber + " or less than 0");
    }
    this.dataCenterId = dataCenterId;
    this.machineId = machineId;
  }

  public synchronized long next() {
    long currentTimeMillis = System.currentTimeMillis();
    // 机器时间被前拨，导致当前时间小于最后时间
    if (currentTimeMillis < lastTimeMillis) {
      throw new RuntimeException("The clock was moved forward and refused to generate ID.");
    }
    // 当前毫秒 == 最后毫秒，即同一毫秒内
    if (currentTimeMillis == lastTimeMillis) {
      // 同一毫秒，序列号自增 & 序列号最大值
      sequence = (sequence + 1) & SEQUENCE_MAX_NUMBER;
      // 如果同一毫秒序列号达到最大（上面 & 了序列号最大值，所以此处最大值为 0）
      if (sequence == 0L) {
        long l = System.currentTimeMillis();
        while (l <= lastTimeMillis) {
          currentTimeMillis = System.currentTimeMillis();
        }
      }
    } else {
      // 不同毫秒内序列号置 0
      sequence = 0;
    }
    lastTimeMillis = currentTimeMillis;

    long dataCenterLeft = DATA_CENTER_LEFT;
    // 如果机器 ID 二进制位数非默认
    if (this.machineBit != DEFAULT_DATA_CENTER_OR_MACHINE_BIT) {
      dataCenterLeft = MACHINE_LEFT + this.machineBit;
    }
    long timeMillisLeft = TIME_MILLIS_LEFT;
    // 如果数据中心 ID 二进制位数非默认
    if (this.dataCenterBit != DEFAULT_DATA_CENTER_OR_MACHINE_BIT) {
      timeMillisLeft = dataCenterLeft + this.dataCenterBit;
    }

    // 时间戳 | 数据中心 ID | 机器 ID | 序列号
    return (currentTimeMillis - START_TIME_MILLIS) << dataCenterLeft | this.dataCenterId << dataCenterLeft | this.machineId << MACHINE_LEFT | sequence;
  }
}
