package com.ader1y.i18n.expend.support;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * @author zhan yan
 * @date 2023/10/24
 **/
@ConfigurationProperties(prefix = "i18n.expend")
public class I18nExpendConfigurationProperties {

    private boolean enable = false;

    private String i18nKey;

    /**
     * 强一致性开关
     * <ul>
     *     <li>关闭时: 定时刷新本地缓存</li>
     *     <li>开启时: I18n服务收到有关当前i18nKey的资源有修改时触发全量更新(通过消息监听器完成)</li>
     * </ul>
     */
    private StrongConsistency strongConsistency;


    public static class StrongConsistency{

        private boolean enable = false;

        private int minRefreshTime = 6;

        private TimeUnit minRefreshTimeUnit = TimeUnit.HOURS;

        public long minRefreshRealTime(){
            return minRefreshTimeUnit.toMillis(minRefreshTime);
        }

        public boolean getEnable() {
            return this.enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public int getMinRefreshTime() {
            return this.minRefreshTime;
        }

        public void setMinRefreshTime(int minRefreshTime) {
            this.minRefreshTime = minRefreshTime;
        }

        public TimeUnit getMinRefreshTimeUnit() {
            return this.minRefreshTimeUnit;
        }

        public void setMinRefreshTimeUnit(TimeUnit minRefreshTimeUnit) {
            this.minRefreshTimeUnit = minRefreshTimeUnit;
        }

        @Override
        public String toString() {
            return "StrongConsistency{" +
                    "enable=" + enable +
                    ", minRefreshTime=" + minRefreshTime +
                    ", minRefreshTimeUnit=" + minRefreshTimeUnit +
                    '}';
        }
    }


    public boolean getEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getI18nKey() {
        return this.i18nKey;
    }

    public void setI18nKey(String i18nKey) {
        this.i18nKey = i18nKey;
    }

    public StrongConsistency getStrongConsistency() {
        return this.strongConsistency;
    }

    public void setStrongConsistency(StrongConsistency strongConsistency) {
        this.strongConsistency = strongConsistency;
    }

    @Override
    public String toString() {
        return "I18nExpendConfigurationProperties{" +
                "enable=" + enable +
                ", i18nKey='" + i18nKey + '\'' +
                ", strongConsistency=" + strongConsistency +
                '}';
    }
}
