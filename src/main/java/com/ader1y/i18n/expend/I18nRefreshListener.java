package com.ader1y.i18n.expend;

import com.ader1y.i18n.expend.support.I18nExpendConfigurationProperties;
import com.ader1y.i18n.expend.support.KafkaConst;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author zhan yan
 * @date 2023/10/24
 **/
@Component
public class I18nRefreshListener implements CommandLineRunner {

    private final I18nCacheManager i18nCacheManager;

    private final I18nCenterClient i18nCenterClient;

    private final I18nExpendConfigurationProperties properties;


    public I18nRefreshListener(I18nCacheManager i18nCacheManager, I18nCenterClient i18nCenterClient, I18nExpendConfigurationProperties properties) {
        this.i18nCacheManager = i18nCacheManager;
        this.i18nCenterClient = i18nCenterClient;
        this.properties = properties;
    }

    @ConditionalOnProperty(name = "i18n.expend.strongConsistency")
    @KafkaListener(topics = {KafkaConst.REFRESH_TOPIC})
    public void listenRefreshEvent(){
        refreshCache();
    }

    @KafkaListener(topics = {KafkaConst.CHANGE_TOPIC})
    public void listenChangeEvent(){
        refreshCache();
    }

    @Override
    public void run(String... args) {
        refreshCache();
    }

    /**
     * 刷新I18n的本地缓存资源
     */
    private void refreshCache(){
        i18nCacheManager.refresh(i18nCenterClient.pullAll(properties.getI18nKey()));
    }

}
