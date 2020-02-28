package com.wemew.rediscache.utils;

import com.wemew.rediscache.model.MethodNameAndArgs;
import com.wemew.rediscache.service.RedisUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    /**
     * 作者 CG
     * 时间 2020/2/28 14:08
     * 注释 获取参数名字和参数列表
     */
    public MethodNameAndArgs getMethod(Method method) {
        MethodNameAndArgs andArgs = new MethodNameAndArgs();
        String name = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        andArgs.setName(name);
        andArgs.setArgs(parameterTypes);
        return andArgs;
    }
    /**
     * 作者 CG
     * 时间 2020/2/28 15:11
     * 注释 根据method/参数列表 执行方法
     */
    public Object invoke(Object source, Method method, Object... objects) {
        Object invoke = null;
        try {
            method.setAccessible(true);
            invoke = method.invoke(source, objects);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return invoke;
    }

    /**
     * 作者 CG
     * 时间 2020/2/28 14:48
     * 注释 根据类/方法名/参数列表执行方法
     */
    public Object invoke(Object source, String methodName, Object... objects) {
        Object invoke = null;
        try {
            Class<?>[] classes = parameterType(objects);
            Method method = source.getClass().getMethod(methodName, classes);
            invoke = method.invoke(source, objects);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return invoke;
    }

    /**
     * 作者 CG
     * 时间 2020/2/24 14:02
     * 注释 参数转参数类型
     */
    public Class<?>[] parameterType(Object... objects) {
        if (objects == null)
            return null;
        Class[] classes = new Class[objects.length];
        for (int i = 0; i < objects.length; i++) {
            classes[i] = objects[i].getClass();
        }
        return classes;
    }

    /**
     * 作者 CG
     * 时间 2019/12/31 21:54
     * 注释 获取实现类的泛型
     */
    public static Class<?> classGenericity(Object object) {
        Type[] types = object.getClass().getGenericInterfaces();
        ParameterizedType parameterized = (ParameterizedType) types[0];
        Class<?> clazz = (Class<?>) parameterized.getActualTypeArguments()[0];
        return clazz;
    }


    public static Class[] objectToClass(Object... objects) {
        Class[] classes = new Class[objects.length];
        for (int i = 0; i < objects.length; i++) {
            classes[i] = objects[i].getClass();
        }
        return classes;
    }

    /**
     * 作者 CG
     * 时间 2019/12/24 19:26
     * 注释 根据参数判断是否要封装Admin
     */
    public static Object[] checkMethod(Method method, Object... objects) {
        List<Object> resultList = new ArrayList<>();
        Class<?>[] parameterTypes = method.getParameterTypes();
        //判断方法参数列表和传入参数列表是否一致
        if (parameterTypes.length == objects.length)
            return objects;
        List<Class<?>> classes = Arrays.asList(parameterTypes);
        List<Object> objects1 = Arrays.asList(objects);

        return resultList.toArray(new Object[classes.size()]);
    }

    /**
     * 作者 CG
     * 时间 2019/12/24 19:22
     * 注释 通过方法名字获取方法
     */
    public static Method getMethod(Class index, String methodName) {
        Method[] methods = index.getDeclaredMethods();
        for (Method indexFuntion : methods
        ) {
            String indexName = indexFuntion.getName();
            if (indexName.equals(methodName)) {
                return indexFuntion;
            }
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContextParam) throws BeansException {
        applicationContext = applicationContextParam;
    }

    public static Object getObject(String id) {
        Object object = null;
        try {
            object = applicationContext.getBean(id);
        } catch (BeansException e) {
            e.printStackTrace();
            return object;
        }
        return object;
    }

    public static <T> T getObject(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }

    public static Object getBean(String tClass) {
        return applicationContext.getBean(tClass);
    }

    public <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }
}