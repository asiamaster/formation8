package com.dili.formation8.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangmi on 2017/5/10.
 */
public class BeanConverter {
    private final static Logger LOG = LoggerFactory.getLogger(BeanConverter.class);
    /**
     * 实例类转换
     * @param source 源对象
     * @param target 目标对象
     * @param <T> 源对象
     * @param <K> 目标对象
     */
    public static<T,K> K copeBean(T source,Class<K> target ){
        if (source == null) {
            return null;
        }
        BeanCopier beanCopier = BeanCopier.create(source.getClass(),target,false);
        K result = null;
        try {
            result = (K)target.newInstance();
        } catch (Exception e) {
            LOG.error("实例转换出错");
        }
        beanCopier.copy(source,result,null);
        return result;
    }

    private static boolean hasMethod(Class clazz, String methodName, Class<?>... parameterTypes ){
        try {
            clazz.getMethod(methodName, parameterTypes);
            return true;
        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
            return false;
        }
    }

    /**
     * page实例类转换
     * @param <T> 源对象
     * @param <K> 目标对象
     * @param source 源对象
     * @param target 目标对象
     * @return
     */
    public static <T,K> List<K> copeList(List<T> source, Class<K> target){
        List<K> list = new ArrayList<K>();
        for(T af : source){

            BeanCopier beanCopier = BeanCopier.create(af.getClass(),target,false);
            K af1 = null;
            try {
                af1 = (K)target.newInstance();
            } catch (Exception e) {
                LOG.error("实例转换出错");
            }
            beanCopier.copy(af,af1,null);
            list.add(af1);
        }
        return list;
    }

}
