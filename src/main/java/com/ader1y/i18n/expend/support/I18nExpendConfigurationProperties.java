package com.ader1y.i18n.expend.support;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhan yan
 * @date 2023/10/24
 **/
@ConfigurationProperties(prefix = "i18n.expend")
public class I18nExpendConfigurationProperties {

    /**
     * 强一致性开关
     * <ul>
     *     <li>关闭时: 定时刷新本地缓存</li>
     *     <li>开启时: I18n服务收到有关当前i18nKey的资源有修改时触发全量更新(通过消息监听器完成)</li>
     * </ul>
     */
    private boolean strongConsistency = false;

    private String i18nKey;

    private boolean enable = false;


    @Override
    public String toString() {
        return "I18nExpendConfigurationProperties{" +
                "strongConsistency=" + strongConsistency +
                ", i18nKey='" + i18nKey + '\'' +
                ", enable=" + enable +
                '}';
    }

    public boolean getStrongConsistency() {
        return this.strongConsistency;
    }

    public void setStrongConsistency(boolean strongConsistency) {
        this.strongConsistency = strongConsistency;
    }

    public String getI18nKey() {
        return this.i18nKey;
    }

    public void setI18nKey(String i18nKey) {
        this.i18nKey = i18nKey;
    }

    public boolean getEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
