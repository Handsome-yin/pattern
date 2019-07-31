package com.algorithm.bloomfilter;

import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.IOException;
import java.nio.charset.Charset;

public class BloomFilter_Test {

    private JedisPool jedisPool = null;
    private Jedis jedis = null;
    //要存储的数据量
    private  long expectedInsertions = 20000;
    //所能容忍错误率
    private double fpp = 0.0000000001F;

    //bit数组长度
    /**
     * 根据预估量n 以及误判率fpp, bit数组的大小的m的计算方式 : m = n*ln*fpp/(ln)2
     *
     * 由预估量n 以及bit数组长度M,可以得到一个hash函数的个数 : k = (m/n)*(ln)2
     */
    private long numBits = optimalNumOfBits(expectedInsertions, fpp);
    //hash函数个数
    private int numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, numBits);


    public static void main(String[] args) {

//        long[] indexs = new BloomFilter_Test().getIndexs("hello");
        BloomFilter_Test filterTest = new BloomFilter_Test();
        filterTest.init();

        int ex_count = 0;
        int ne_count = 0;
        /**
         * 存在： 不一定存在
         * 不存在：一定不存在
         */
        for (int i = 0; i < 20000; i++) {
//            filterTest.put("bf",100 + i + "");
            boolean exist = filterTest.isExist("bf", 100 + i + "");
            if(exist){
                ex_count++;
            }else{
                ne_count++;
            }
        }
        //ex_count:6729	ne_count 3271
        System.out.println("ex_count:" + ex_count + "\t" + "ne_count " + ne_count);
    }

    public void init(){
        //测试连接redis
        jedisPool = new JedisPool("192.168.150.111", 6379);
        jedis = jedisPool.getResource();
    }

    private long getCount(){
        Pipeline pipeline = jedis.pipelined();
        Response<Long> bf = pipeline.bitcount("bf");
        pipeline.sync();
        Long count = bf.get();
        try {
            pipeline.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 判断keys是否存在于集合where中
     */
    public boolean isExist(String where, String key) {
        long[] indexs = getIndexs(key);
        boolean result;
        //这里使用了Redis管道来降低过滤器运行当中访问Redis次数 降低Redis并发量
        Pipeline pipeline = jedis.pipelined();
        try {
            for (long index : indexs) {
                pipeline.getbit(where, index);
            }
            result = !pipeline.syncAndReturnAll().contains(false);
        } finally {
            try {
                pipeline.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
//        if (!result) {
//            put(where, key);
//        }
        return result;
    }


    /**
     * 将key存入redis bitmap
     */
    private void put(String where, String key) {
        long[] indexs = getIndexs(key);
        //这里使用了Redis管道来降低过滤器运行当中访问Redis次数 降低Redis并发量
        Pipeline pipeline = jedis.pipelined();
        try {
            for (long index : indexs) {
                pipeline.setbit(where, index, true);
            }
            pipeline.sync();
            /**
             * 把数据存储到mysql中
             */
        } finally {
            try {
                pipeline.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    /**
     * 根据key获取bitmap下标 方法来自guava
     */
    public long[] getIndexs(String key) {
        long hash1 = hash(key);
        long hash2 = hash1 >>> 16;
        long[] result = new long[numHashFunctions];
        for (int i = 0; i < numHashFunctions; i++) {
            long combinedHash = hash1 + i * hash2;
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            result[i] = combinedHash % numBits;
        }
        return result;
    }

    /**
     * 获取一个hash值 方法来自guava
     */
    private long hash(String key) {
        Charset charset = Charset.forName("UTF-8");
        return Hashing.murmur3_128().hashObject(key, Funnels.stringFunnel(charset)).asLong();
    }


    private static int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }


    private static long optimalNumOfBits(long n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        return (long) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }



}
