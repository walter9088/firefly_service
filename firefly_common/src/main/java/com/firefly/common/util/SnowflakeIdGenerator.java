package com.firefly.common.util;

/**
 * 雪花算法ID生成器
 */
public class SnowflakeIdGenerator {
    // 开始时间戳 (2024-01-01)
    private final static long START_TIMESTAMP = 1704038400000L;

    // 每部分占用的位数
    private final static long SEQUENCE_BIT = 12;   // 序列号占用的位数
    private final static long MACHINE_BIT = 5;     // 机器标识占用的位数
    private final static long DATACENTER_BIT = 5;  // 数据中心占用的位数

    // 每部分的最大值
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private final static long MAX_DATACENTER_NUM = ~(-1L << DATACENTER_BIT);

    // 每部分向左的位移
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;  // 数据中心ID
    private long machineId;     // 机器标识ID
    private long sequence = 0L; // 序列号
    private long lastTimestamp = -1L; // 上一次时间戳

    public SnowflakeIdGenerator(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("数据中心ID不能大于" + MAX_DATACENTER_NUM + "或小于0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("机器ID不能大于" + MAX_MACHINE_NUM + "或小于0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     */
    public synchronized long nextId() {
        long currTimestamp = getNewTimestamp();
        if (currTimestamp < lastTimestamp) {
            throw new RuntimeException("时钟向后移动，拒绝生成ID");
        }

        if (currTimestamp == lastTimestamp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currTimestamp = getNextMill();
            }
        } else {
            // 不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastTimestamp = currTimestamp;

        return (currTimestamp - START_TIMESTAMP) << TIMESTAMP_LEFT // 时间戳部分
                | datacenterId << DATACENTER_LEFT                  // 数据中心部分
                | machineId << MACHINE_LEFT                        // 机器标识部分
                | sequence;                                        // 序列号部分
    }

    private long getNextMill() {
        long mill = getNewTimestamp();
        while (mill <= lastTimestamp) {
            mill = getNewTimestamp();
        }
        return mill;
    }

    private long getNewTimestamp() {
        return System.currentTimeMillis();
    }
} 