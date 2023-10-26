package com.ader1y.i18n.expend.support;

import com.github.yitter.contract.IIdGenerator;
import com.github.yitter.contract.IdGeneratorException;
import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.DefaultIdGenerator;

/**
 * 雪花算法ID生成类
 *
 * @author zhan yan
 * @apiNote 简化于 {@link com.github.yitter.idgen.YitIdHelper}的工具类
 * @date 2023/08/03
 **/
public class SnowflakeHelper {

    private SnowflakeHelper() {
    }

    private static IIdGenerator idGenInstance = null;

    //  初始化配置信息
    static {
        short initWorkerId = (short) (Math.random() * 1000);
        IdGeneratorOptions options = new IdGeneratorOptions(initWorkerId);
        idGenInstance = new DefaultIdGenerator(options);
    }


    public static long id() throws IdGeneratorException {
        return idGenInstance.newLong();
    }

}
