package com.ader1y.i18n.expend;

import com.ader1y.i18n.expend.support.I18nExpendConfigurationProperties;
import com.ader1y.i18n.expend.support.KafkaConst;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author zhan yan
 **/
@Component
public class I18nRefreshListener {

    private final AbstractI18nCache i18nCache;

    private final I18nCenterClient i18nCenterClient;

    private final I18nExpendConfigurationProperties properties;


    public I18nRefreshListener(AbstractI18nCache i18nCache, I18nCenterClient i18nCenterClient, I18nExpendConfigurationProperties properties) {
        this.i18nCache = i18nCache;
        this.i18nCenterClient = i18nCenterClient;
        this.properties = properties;
    }

    @KafkaListener(topics = {KafkaConst.REFRESH_TOPIC})
    public void listenRefreshEvent(){
        refreshCache();
    }

    /**
     * 监听资源修改消息,
     */
    @ConditionalOnProperty(name = "i18n.expend.strong-consistency.enable")
    @KafkaListener(topics = {KafkaConst.CHANGE_TOPIC})
    public void listenChangeEvent(String i18nKey){
        if (properties.getI18nKey().equals(i18nKey))
            refreshCache();
    }


    /**
     * 刷新I18n的本地缓存资源
     */
    private void refreshCache(){
        i18nCache.refresh(i18nCenterClient.pullAll(properties.getI18nKey()));
    }

}
