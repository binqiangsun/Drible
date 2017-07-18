package com.sunbinqiang.drible.manager;

import android.content.Context;
import android.support.annotation.StringDef;
import android.support.v4.util.ArrayMap;



public class ServiceManager {

    @StringDef({ServiceName.ACCOUNT_SERVICE})
    public @interface ServiceName {
        String ACCOUNT_SERVICE = "account";
    }

    private ArrayMap<String, Object> servicesMap = new ArrayMap<>();
    private static ServiceManager serviceManager;

    private ServiceManager(Context context) {
        servicesMap.put(ServiceName.ACCOUNT_SERVICE, new DefaultAccountService(context));
    }

    public static ServiceManager instance(Context context) {
        if (serviceManager == null) {
            serviceManager = new ServiceManager(context);
        }
        return serviceManager;
    }

    public Object getService(@ServiceName String serviceName) {
        return servicesMap.get(serviceName);
    }
}
