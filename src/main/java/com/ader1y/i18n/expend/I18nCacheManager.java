package com.ader1y.i18n.expend;

import com.ader1y.i18n.expend.support.I18nExpendConfigurationProperties;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;


@Component
public class I18nCacheManager {

    private static final Logger LOG = getLogger(I18nCacheManager.class);

    private final I18nExpendConfigurationProperties properties;

    public I18nCacheManager(I18nExpendConfigurationProperties properties) {
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
     * 刷新cache中的资源, 该操作应该是同步的(防止极端的并发refresh操作带来不可控的影响).<p></p>
     *
     * refresh通过切换缓存节点保证获取缓存资源的可靠性, 避免invalidateAll与putAll操作间隙引起的数据不一致问题
     */
    protected synchronized void refresh(Map<String, String> resource){
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
        LOG.debug("I18nLocalCache has refresh.");
    }

    public Map<String, String> getCache(List<String> keys){
        //  与服务在I18n管理页面中注册的key关联, 前端可无感知获取多语言资源
        keys = keys.stream()
                .map(i -> i = properties.getI18nKey() + i)
                .toList();

        return master.getAllPresent(keys);
    }


}
