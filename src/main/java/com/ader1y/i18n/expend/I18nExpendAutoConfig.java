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
@EnableConfigurationProperties(I18nExpendConfigurationProperties.class)
public class I18nExpendAutoConfig {

    private final I18nExpendConfigurationProperties properties;

    private final I18nCenterClient i18nCenterClient;

    private I18nCacheManager i18nCacheManager;

    public I18nExpendAutoConfig(I18nExpendConfigurationProperties properties, I18nCenterClient i18nCenterClient) {
        this.properties = properties;
        this.i18nCenterClient = i18nCenterClient;
    }

    @Bean
    @ConditionalOnProperty(name = "i18n.expend.enable")
    @ConditionalOnMissingBean
    public I18nCacheManager i18nCacheManager(){
        i18nCacheManager = new I18nCacheManager(properties);
        return i18nCacheManager;
    }

    @Bean
    @ConditionalOnProperty(name = "i18n.expend.enable")
    @ConditionalOnMissingBean
    public I18nRefreshListener i18nRefreshListener(){

        return new I18nRefreshListener(i18nCacheManager, i18nCenterClient, properties);
    }

}
