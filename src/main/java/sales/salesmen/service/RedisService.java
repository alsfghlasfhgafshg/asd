package sales.salesmen.service;

public interface RedisService {
    //存储
    void set(String key,String value);
    //获取
    String get(String key);
    //超时
    boolean expire(String key,long expire);
    //删除
    void remove(String key);
    //自增
    Long increment(String key,long delta);
}
