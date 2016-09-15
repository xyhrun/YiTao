package com.example.xyh.shoppingdemo.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 向阳湖 on 2016/8/24.
 */
public class OkHttpClientManager {

    private static final String TAG = "OkHttpClientManager";
    private static OkHttpClientManager mOkHttpManagerInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDeliver;
    private Gson mGson;

    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();
        mDeliver = new Handler(Looper.getMainLooper());
        mGson = new Gson();
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static OkHttpClientManager getOkHttpManagerInstance() {
        if (mOkHttpManagerInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mOkHttpManagerInstance == null) {
                    mOkHttpManagerInstance = new OkHttpClientManager();
                }
            }
        }
        return mOkHttpManagerInstance;
    }

    /**
     * 同步请求get返回Response
     *
     * @param url
     * @return
     * @throws IOException
     */
    public Response _getAsResponse(String url) throws IOException {
        Request mRequest = new Request.Builder().get().url(url).build();
        return mOkHttpClient.newCall(mRequest).execute();
    }

    /**
     * 同步请求get返回String
     *
     * @param url
     * @return
     * @throws IOException
     */
    public String _getAsString(String url) throws IOException {
        Response mResponse = _getAsResponse(url);
        return mResponse.body().string();
    }

    /**
     * 异步请求get
     *
     * @param url
     */
    private void _getAsyn(String url, ResultCallback mResultCallback) {
        Request mRequest = new Request.Builder().get().url(url).build();
        deliveryResult(mRequest, mResultCallback);
    }

    /**
     * 同步post请求, 返回Response
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    private Response _postAsResponse(String url, Param... params) throws IOException {
        Request request = buildPostRequest(url, params);
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 同步post请求, 返回String
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    private String _postAsString(String url, Param... params) throws IOException {
        Request request = buildPostRequest(url, params);
        return _postAsResponse(url, params).body().string();
    }

    /**
     * 异步Post请求
     *
     * @param url
     * @param resultCallback
     * @param params
     */
    private void _postAsyn(String url, ResultCallback resultCallback, Param... params) {
        Request request = buildPostRequest(url, params);
        deliveryResult(request, resultCallback);
    }

    /**
     * 异步Post请求
     *
     * @param url
     * @param resultCallback
     * @param params
     */
    private void _postAsyn(String url, ResultCallback resultCallback, Map<String, String> params) {
        Param[] paramArr = map2Params(params);
        Request request = buildPostRequest(url, paramArr);
        deliveryResult(request, resultCallback);
    }

    /**
     * 同步的post上传,返回response
     *
     * @param url
     * @param files
     * @param fileKeys
     * @param params
     * @return
     * @throws IOException
     */
    //多文件上传
    private Response _postUploadAsResponse(String url, File[] files, String[] fileKeys, Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        Response response = mOkHttpClient.newCall(request).execute();
        return response;
    }

    //单文件上传
    private Response _postUploadAsResponse(String url, File file, String[] fileKeys, Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, fileKeys, params);
        Response response = mOkHttpClient.newCall(request).execute();
        return response;
    }

    //单文件上传不要参数
    private Response _postUploadAsResponse(String url, File file, String[] fileKeys) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, fileKeys, null);
        Response response = mOkHttpClient.newCall(request).execute();
        return response;
    }

    //多文件上传不要参数
    private Response _postUploadAsResponse(String url, File[] files, String[] fileKeys) throws IOException {
        Request request = buildMultipartFormRequest(url, files, fileKeys, null);
        Response response = mOkHttpClient.newCall(request).execute();
        return response;
    }

    /**
     * 异步post上传
     *
     * @param resultCallback
     * @param url
     * @param files
     * @param fileKeys
     * @param params
     */
    //异步多文件上传
    private void _postAsynUpload(ResultCallback resultCallback, String url, File[] files, String[] fileKeys, Param... params) {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        deliveryResult(request, resultCallback);
    }

    //异步单文件上传
    private void _postAsynUpload(ResultCallback resultCallback, String url, File file, String[] fileKeys, Param... params) {
        Request request = buildMultipartFormRequest(url, new File[]{file}, fileKeys, params);
        deliveryResult(request, resultCallback);
    }

    //异步单文件上传且不要参数
    private void _postAsynUpload(ResultCallback resultCallback, String url, File file, String[] fileKeys) {
        Request request = buildMultipartFormRequest(url, new File[]{file}, fileKeys, null);
        deliveryResult(request, resultCallback);
    }

    //异步多文件上传且不要参数
    private void _postAsynUpload(ResultCallback resultCallback, String url, File[] files, String[] fileKeys) {
        Request request = buildMultipartFormRequest(url, files, fileKeys, null);
        deliveryResult(request, resultCallback);
    }

    /**
     * 异步get下载
     *
     * @param url
     * @param destFileDir
     * @param resultCallback
     */
    private void _downLoadAsyn(final String url, final String destFileDir, final ResultCallback resultCallback) {
        final Request request = new Request.Builder().get().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: 异步get请求失败");
                sendFailedStringCallback(request, e, resultCallback);
            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream is = null;
                int len = 0;
                FileOutputStream fos = null;
                byte[] bytes = new byte[2048];
                try {
//                    Log.i(TAG, "onResponse: response.body().byteStream() = "+response.body().byteStream());
                    is = response.body().byteStream();
                    //file有问题
                    File file = new File(destFileDir, getFileName(url));
                    fos = new FileOutputStream(file);
                    while ((len = is.read()) != -1) {
                        fos.write(bytes, 0, len);
                    }
                    fos.flush();
                    //如果下载成功第一个参数为文件的绝对路径
                    sendSuccessResultCallback(file.getAbsolutePath(), resultCallback);
                } catch (Exception e) {
                    Log.i(TAG, "onResponse: 写入数据错误");
                    sendFailedStringCallback(request, e, resultCallback);
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : (path.substring(separatorIndex + 1, path.length()));
    }

    private void _displayImage(final ImageView imageView, String url, final int errorResId) {
        final Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                setErrorResId(imageView, errorResId);
            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream is = null;
                try {
                    is = response.body().byteStream();
                    BitmapFactory.Options option = new BitmapFactory.Options();
                    option.inJustDecodeBounds = false;
                    final Bitmap bitmap = BitmapFactory.decodeStream(is, null, option);
                    mDeliver.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                } catch (Exception e) {
                    setErrorResId(imageView, errorResId);
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void setErrorResId(final ImageView imageView, final int errorResId) {
        mDeliver.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageResource(errorResId);
            }
        });
    }

    private Request buildMultipartFormRequest(String url, File[] files, String[] fileKeys, Param[] params) {
        //看它是否为空
        params = validateParam(params);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Param param : params) {
            builder.addPart(Headers.of("Content-Disposition", "form-data;name = \"" + param.getKey() + "\""),
                    RequestBody.create(null, param.getValue()));
        }
        if (files != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //TODO 根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().post(requestBody).url(url).build();
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    private Param[] validateParam(Param[] params) {
        if (params == null) {
            return new Param[0];
        } else {
            return params;
        }
    }

    /**
     * 将
     *
     * @param params
     * @return
     */
    private Param[] map2Params(Map<String, String> params) {
        if (params == null) {
            return new Param[0];
        }

        int size = params.size();
        Param[] paramArr = new Param[size];
        int i = 0;
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            paramArr[i++] = new Param(entry.getKey(), entry.getValue());
        }

        return paramArr;
    }

    private void deliveryResult(final Request request, final ResultCallback resultCallback) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedStringCallback(request, e, resultCallback);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String result = response.body().string();
                    if (resultCallback.mType == result.getClass()) {
                        sendSuccessResultCallback(result, resultCallback);
                    } else {
                        Object object = mGson.fromJson(result, resultCallback.mType);
                        Log.i(TAG, "onResponse:传入类型 " + resultCallback.mType);
                        sendSuccessResultCallback(object, resultCallback);
                    }
                } catch (IOException e) {
                    sendFailedStringCallback(request, e, resultCallback);
                } catch (com.google.gson.JsonParseException e) {
                    //json解析异常
                    sendFailedStringCallback(request, e, resultCallback);
                }
            }
        });


    }

    /**
     * 建立post请求
     *
     * @param url
     * @param params
     * @return
     */
    private Request buildPostRequest(String url, Param[] params) {
        if (params == null) {
            params = new Param[0];
        }

        FormBody.Builder builder = new FormBody.Builder();
        for (Param param : params) {
            builder.add(param.getKey(), param.getValue());
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }

    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback) {
        mDeliver.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onError(request, e);
                }
            }
        });
    }

    private void sendSuccessResultCallback(final Object object, final ResultCallback callback) {
        mDeliver.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: object = " + object);
                if (callback != null) {
                    callback.onResponse(object);
                }
            }
        });
    }

    /**
     * @param <T>
     */
    public static abstract class ResultCallback<T> {
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
    }

    /**
     * param类  里面存放的是Post请求 键值对
     */
    class Param {
        private String key;
        private String value;

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 对外暴露的方法
     */

    //同步get,返回Response
    public static Response getAsResponse(String url) throws IOException {
        return getOkHttpManagerInstance()._getAsResponse(url);
    }

    //同步get,返回String
    public static String getAsString(String url) throws IOException {
        return getOkHttpManagerInstance()._getAsString(url);
    }

    //异步get
    public static void getAsyn(String url, ResultCallback resultCallback) {
        getOkHttpManagerInstance()._getAsyn(url, resultCallback);
    }

    ////同步post,返回Response
    public static Response postAsResponse(String url, Param... params) throws IOException {
        return getOkHttpManagerInstance()._postAsResponse(url, params);
    }

    //同步post,返回String
    public static String postAsString(String url, Param... params) throws IOException {
        return getOkHttpManagerInstance()._postAsString(url, params);
    }

    //异步post
    public static void postAsyn(String url, ResultCallback resultCallback, Param... params) {
        getOkHttpManagerInstance()._postAsyn(url, resultCallback, params);
    }

    //异步post
    public static void postAsyn(String url, ResultCallback resultCallback, Map<String, String> params) {
        getOkHttpManagerInstance()._postAsyn(url, resultCallback, params);
    }

//    //同步多文件上传
//    public static Response postUploadAsResponse(String url, File[] files, String[] fileKeys, Param... params) throws IOException {
//        return getOkHttpManagerInstance()._postUploadAsResponse(url, files, fileKeys, params);
//    }
//
//    //同步单文件上传
//    public static Response postUploadAsResponse(String url, File file, String[] fileKeys, Param... params) throws IOException {
//        return getOkHttpManagerInstance()._postUploadAsResponse(url, new File[]{file}, fileKeys, params);
//    }
//
//    //同步多文件上传不带参数
//    public static Response postUploadAsResponse(String url, File[] files, String[] fileKeys) throws IOException {
//        return getOkHttpManagerInstance()._postUploadAsResponse(url, files, fileKeys, null);
//    }
//
//    //同步单文件上传不带参数
//    public static Response postUploadAsResponse(String url, File file, String[] fileKeys) throws IOException {
//        return getOkHttpManagerInstance()._postUploadAsResponse(url, new File[]{file}, fileKeys, null);
//    }
//
//    //异步多文件上传
//    public static void postAsynUpload(ResultCallback resultCallback, String url, File[] files, String[] fileKeys, Param... params) {
//        getOkHttpManagerInstance()._postAsynUpload(resultCallback, url, files, fileKeys, params);
//    }
//
//    //异步单文件上传
//    public static void postAsynUpload(ResultCallback resultCallback, String url, File file, String[] fileKeys, Param... params) {
//        getOkHttpManagerInstance()._postAsynUpload(resultCallback, url, new File[]{file}, fileKeys, params);
//    }
//
//    //异步多文件上传不带参数
//    public static void postAsynUpload(ResultCallback resultCallback, String url, File[] files, String[] fileKeys) {
//        getOkHttpManagerInstance()._postAsynUpload(resultCallback, url, files, fileKeys, null);
//    }
//
//    //异步单文件上传不带参数
//    public static void postAsynUpload(ResultCallback resultCallback, String url, File file, String[] fileKeys) {
//        getOkHttpManagerInstance()._postAsynUpload(resultCallback, url, new File[]{file}, fileKeys, null);
//    }

    //异步get下载
    public static void downLoadAsyn(final String url, final String destFileDir, final ResultCallback resultCallback) {
        getOkHttpManagerInstance()._downLoadAsyn(url, destFileDir, resultCallback);
    }

    //显示图片
    public static void displayImage(final ImageView imageView, String url, final int errorResId) {
        getOkHttpManagerInstance()._displayImage(imageView, url, errorResId);
    }

}
