package com.lukou.service.config;

import com.lukou.service.bean.Config;
import com.lukou.service.bean.Version;

/**
 * Created by wangzhicheng on 2017/3/23.
 */

public interface ConfigService {

    /**
     * 获取config信息
     * @return
     */
    Config config();

    /**
     * 更新config信息
     * @param config
     */
    void update(Config config);

    /**
     * 获取当前版本信息
     * @return
     */
    Version version();

    /**
     * 获取最低版本号
     * @return
     */
    int minVersion();

    /**
     * 重置config信息
     * 子类实现，从服务端更新
     */
    void reset();
}
