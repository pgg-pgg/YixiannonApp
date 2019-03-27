package com.pgg.yixiannonapp.net.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.pgg.yixiannonapp.global.GlobalApplication;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by pgg on 2019/3/26.
 */

public class OkHttp3Utils {
    private static OkHttpClient client;

    //设置缓存目录 100M
    private static File file=new File(GlobalApplication.getInstance().getApplicationContext().getExternalCacheDir(),"MyCache");
    private static Cache cache=new Cache(file,10*1024*1024);

    /**
     * 获取OkHttpClient对象
     */
    public static OkHttpClient getOkHttpClient(){
        if (null==client){
            client=new OkHttpClient.Builder().cache(cache)
                    //添加自定义的request的请求头
                    .addInterceptor(mTokenInterceptor)
                    //添加自定义缓存拦截器
                    .addNetworkInterceptor(mTestInterceptor)
                    //添加日志信息拦截器
                    .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor(mTestInterceptor)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10,TimeUnit.SECONDS)
                    .readTimeout(10,TimeUnit.SECONDS)
                    .cache(cache)
                    .build();
        }

        return client;
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private static final Interceptor mTestInterceptor=new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request=chain.request();
            if (!isNetworkReachable(GlobalApplication.getInstance().getApplicationContext())){
                request=request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response proceed = chain.proceed(request);
            if (isNetworkReachable(GlobalApplication.getInstance().getApplicationContext())){
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return proceed.newBuilder()
                        .header("Cache-Control",cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }else {
                int maxStale=60*60*24*365;//无网络时间
                return proceed.newBuilder()
                        .header("Cache-Control","public, only-if-cached, max-stale="+maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    /**
     * 云端响应头拦截器
     * 用于添加统一请求头  请按照自己的需求添加
     */
    private static final Interceptor mTokenInterceptor=new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request build = request.newBuilder()
                    .header("Pgg_FromSource", "android-2.65")
                    .header("Pgg_IP", getLocalAddress())
                    .header("Content-Type", "application/json; charset=utf-8")
                    .build();
            return chain.proceed(build);
        }
    };

    /**
     * 自动管理Cookies
     * @return
     */
    private static class CookiesManager implements CookieJar {
        private final PersistentCookieStore cookieStore=new PersistentCookieStore(GlobalApplication.getInstance().getApplicationContext());

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies!=null&&cookies.size()>0){
                for (Cookie item:cookies){
                    cookieStore.add(url,item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies=cookieStore.get(url);
            return cookies;
        }
    }

    private static String getLocalAddress() {
        try {
            for (Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces(); enumeration.hasMoreElements();){
                NetworkInterface networkInterface = enumeration.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = networkInterface.getInetAddresses(); enumIpAddr.hasMoreElements();){
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    //加上这个地址获取的一定是IPV4地址  不加的话 有可能是IPV6地址
                    if (!inetAddress.isLoopbackAddress()&&!inetAddress.isLinkLocalAddress()){
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "127.0.0.1(error)";
    }

    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    public static Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }
}
