package com.example.xyh.shoppingdemo.net;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;

/**
 * @param <T>
 */
public abstract class ResultCallback<T> {
    Type mType;

    public ResultCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subClass) {
        Type superClass = subClass.getGenericSuperclass();
        //如果superClass是Class的一个实例
        if (superClass instanceof Class) {
            throw new RuntimeException("Miss type parameter");
        }

        ParameterizedType parameterized = (ParameterizedType) superClass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public abstract void onError(Request mRequest, Exception e);

    public abstract void onResponse(T response);

    public abstract void onBeforeRequest(Request request);

    public abstract  void onFailure(Request request, Exception e) ;

    public abstract void onSuccess(T response) ;
}