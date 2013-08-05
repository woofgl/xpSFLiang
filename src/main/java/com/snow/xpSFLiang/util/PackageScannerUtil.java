package com.snow.xpSFLiang.util;

import javax.annotation.Nullable;

import com.britesnow.snow.util.PackageScanner;
import com.google.common.base.Predicate;

/**
 * Just a wrapper on top of Snow PackageScanner. Should probably be move to Snow PackageScanner
 * @author jeremychone
 *
 */
public class PackageScannerUtil {
    
    static public <E> Class<E>[] findChildrenOf(final Class<E> baseClass){
        Class<E>[] classes = (Class<E>[]) new PackageScanner(baseClass.getPackage().getName()).findClasses(new Predicate<Class>() {
            @Override
            public boolean apply(@Nullable Class cls) {
                if (cls != baseClass && ((java.lang.Class) baseClass).isAssignableFrom(cls)) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        
        return classes;
    }

}