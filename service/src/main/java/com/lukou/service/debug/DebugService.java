package com.lukou.service.debug;

/**
 * 调试面板工具
 * <p>
 * 域名切换：切换到指定域名<br>
 * 延时请求：每个请求都会延时返回<br>
 * 请求失败：模拟网络失败 50% 请求失败：下N次网络请求模拟失败
 *
 * @author Xu
 */
public interface DebugService {

    String switchDomain();

    void setSwitchDomain(String to);

    long delay();

    void setDelay(long delay);

    boolean failHalf();

    boolean isActivated();

    void setActivated(boolean activated);
}
