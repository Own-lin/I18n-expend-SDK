package com.ader1y.i18n.expend;

import com.ader1y.i18n.expend.support.I18nExpendConfigurationProperties;
import com.ader1y.i18n.expend.support.KafkaConst;
import com.ader1y.i18n.expend.support.SnowflakeHelper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author zhan yan
 * @date 2023/10/24
 **/
@Component
public class I18nListener implements CommandLineRunner {

    private final I18nCacheManager i18nCacheManager;

    private final I18nCenterClient i18nCenterClient;

    private final I18nExpendConfigurationProperties properties;


    public I18nListener(I18nCacheManager i18nCacheManager, I18nCenterClient i18nCenterClient, I18nExpendConfigurationProperties properties) {
        this.i18nCacheManager = i18nCacheManager;
        this.i18nCenterClient = i18nCenterClient;
        this.properties = properties;
    }

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

    private void refreshCache(){
        i18nCacheManager.refresh(i18nCenterClient.pullAll(properties.getI18nKey()));
    }

}
