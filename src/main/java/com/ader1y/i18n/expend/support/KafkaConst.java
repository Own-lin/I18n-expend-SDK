package com.ader1y.i18n.expend.support;

/**
 * @author zhan yan
 * @date 2023/10/24
 **/
public class KafkaConst {

    private KafkaConst(){}


    public static final String REFRESH_TOPIC = "i18n-resource-refresh";

    public static final String CHANGE_TOPIC = "i18n-resource-change";

    public static final String REFRESH_GID = String.valueOf(SnowflakeHelper.id());

    public static final String CHANGE_GID = String.valueOf(SnowflakeHelper.id());

}
