package com.sunbinqiang.drible.bean;

/**
 * Created by sunbinqiang on 6/14/16.
 */
public class SingleClassLoader extends ClassLoader {
    private final Class cl;
    private final String className;

    public SingleClassLoader(Class c) {
        cl = c;
        className = c.getName();
    }

    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        if (this.className.equals(className))
            return cl;
        else
            return super.loadClass(className);
    }
}
