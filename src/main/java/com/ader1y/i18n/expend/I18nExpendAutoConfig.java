package com.ader1y.i18n.expend;

import com.ader1y.i18n.expend.support.I18nExpendConfigurationProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author zhan yan
 * @date 2023/10/24
 **/
@ConditionalOnProperty(name = "i18n.expend.enable")
@EnableConfigurationProperties(I18nExpendConfigurationProperties.class)
public class I18nExpendAutoConfig {

    private final I18nExpendConfigurationProperties properties;

    private final I18nCenterClient i18nCenterClient;

    private AbstractI18nCache i18nCache;

    public I18nExpendAutoConfig(I18nExpendConfigurationProperties properties, I18nCenterClient i18nCenterClient) {
        this.properties = properties;
        this.i18nCenterClient = i18nCenterClient;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "i18n.expend.strong-consistency.enable")
    public AbstractI18nCache i18nStrongConsistencyCache(){
        i18nCache = new StrongConsistencyI18nCache(properties);
        return i18nCache;
    }

    @Bean
    @ConditionalOnMissingBean
    public AbstractI18nCache i18nCacheManager(){
        i18nCache = new DefaultI18nCache(properties);
        return i18nCache;
    }

    @Bean
    @ConditionalOnProperty(name = "i18n.expend.enable")
    @ConditionalOnMissingBean
    public I18nRefreshListener i18nRefreshListener(){

        return new I18nRefreshListener(i18nCache, i18nCenterClient, properties);
    }

}
