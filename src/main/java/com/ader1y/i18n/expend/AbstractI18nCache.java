package com.ader1y.i18n.expend;

import com.ader1y.i18n.expend.support.I18nExpendConfigurationProperties;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * I18n缓存的抽象类, 提供了一些回调方法拓展refresh操作
 */
public abstract class AbstractI18nCache {

    private static final Logger LOG = getLogger(AbstractI18nCache.class);

    private final I18nExpendConfigurationProperties properties;

    protected AbstractI18nCache(I18nExpendConfigurationProperties properties) {
        this.properties = properties;
    }

    /**
     * Red节点缓存
     */
    private final Cache<String, String> red = Caffeine
            .from(CaffeineSpec.parse("RedI18nResource"))
            .build();

    /**
     * Black节点缓存
     */
    private final Cache<String, String> black = Caffeine
            .from(CaffeineSpec.parse("BlackI18nResource"))
            .build();


    /**
     * 有效缓存的引用, 永远指向未在refresh的节点(Black/Red)
     */
    private Cache<String, String> master = red;

    /**
     *  子类可通过重写该方法判断是否允许刷新
     */
    protected boolean canRefresh(I18nExpendConfigurationProperties properties){
        return true;
    }

    /**
     * 如果允许刷新, 则在刷新前做一次处理
     */
    protected void beforeRefresh(I18nExpendConfigurationProperties properties){

    }

    /**
     * 如果允许刷新, 则在刷新后做一次处理
     */
    protected void afterRefresh(I18nExpendConfigurationProperties properties){

    }

    /**
     * 刷新cache中的资源, 该操作应该是同步的(防止极端的并发refresh操作带来不可控的影响).<p></p>
     *
     * refresh通过切换缓存节点保证获取缓存资源的可靠性, 避免invalidateAll与putAll操作间隙引起的数据不一致问题
     */
    protected final synchronized void refresh(Map<String, String> resource){
        if (canRefresh(properties)){
            beforeRefresh(properties);
            if (master.equals(red)){
                black.invalidateAll();
                black.putAll(resource);
                master = black;
                red.invalidateAll();
            }else if (master.equals(black)){
                red.invalidateAll();
                red.putAll(resource);
                master = red;
                black.invalidateAll();
            }
            afterRefresh(properties);
            LOG.debug("I18nLocalCache has refresh.");
        }
    }

    /**
     * 获取master指向的缓存节点中的值, 在取值前将服务的i18nKey与缓存key进行拼接, 使前端可无感知获取资源
     */
    public final Map<String, String> getCache(List<String> keys){
        keys = keys.stream()
                .map(i -> i = properties.getI18nKey() + i)
                .toList();

        return master.getAllPresent(keys);
    }

}
