package com.ader1y.i18n.expend;

import com.ader1y.i18n.expend.support.I18nExpendConfigurationProperties;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.format.datetime.DateFormatter;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * I18n本地缓存的强一致性实现
 *
 * @author zhan yan
 **/
public class StrongConsistencyI18nCache extends AbstractI18nCache{


    /**
     * 上次刷新缓存的时间, (当前时间-上次刷新时间>最小刷新时间)才需要刷新
     */
    private volatile long lastRefreshTime = 1672502400000L;



    public StrongConsistencyI18nCache(I18nExpendConfigurationProperties properties) {
        super(properties);
    }

    @Override
    protected boolean canRefresh(I18nExpendConfigurationProperties properties) {
        long gap = new Date().getTime() - lastRefreshTime;

        return gap > properties.getStrongConsistency().minRefreshRealTime();
    }


    @Override
    protected void afterRefresh(I18nExpendConfigurationProperties properties) {
        lastRefreshTime = new Date().getTime();
    }
}
