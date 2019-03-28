package com.pgg.yixiannonapp.net.httpData;

import com.pgg.yixiannonapp.domain.Results;
import com.pgg.yixiannonapp.domain.User;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.net.api.UserService;
import com.pgg.yixiannonapp.net.retrofit.RetrofitUtils;
import com.pgg.yixiannonapp.utils.FileUtils;

import java.io.File;

import io.rx_cache.Reply;
import io.rx_cache.internal.RxCache;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by pgg on 2019/3/26.
 */

public class HttpData {

    public static HttpData getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static File cacheDirectory = FileUtils.getChaheDirectory();

    private static final CacheProviders providers = new RxCache.Builder()
            .persistence(cacheDirectory)
            .using(CacheProviders.class);

    protected UserService userService = RetrofitUtils.getRetrofit(Constant.BASE_URL).create(UserService.class);

    private static class SingletonHolder {
        private static final HttpData INSTANCE = new HttpData();
    }

    public void register(Observer<Results<User>> observable, final User user) {
        Observable<Results<User>> data = userService.register(user);
        setSubscribe(data, observable);
    }

    private static <T> void setSubscribe(Observable<T> listObservable, Observer<T> observable) {
        listObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observable);
    }

    private class HttpCacheHandler<T> implements Func1<Reply<T>, T> {
        @Override
        public T call(Reply<T> tReply) {
            return tReply.getData();
        }
    }

}
