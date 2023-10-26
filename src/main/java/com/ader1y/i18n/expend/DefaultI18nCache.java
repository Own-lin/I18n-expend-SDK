package com.ader1y.i18n.expend;

import com.ader1y.i18n.expend.support.I18nExpendConfigurationProperties;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;


public class DefaultI18nCache extends AbstractI18nCache{


    public DefaultI18nCache(I18nExpendConfigurationProperties properties) {
        super(properties);
    }

}
