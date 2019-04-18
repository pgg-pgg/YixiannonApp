package com.pgg.yixiannonapp.net.httpData;

import com.pgg.yixiannonapp.domain.CartGoods.CartGoods;
import com.pgg.yixiannonapp.domain.Classify.ClassifyItemEntity;
import com.pgg.yixiannonapp.domain.Classify.ClassifyTypeEntity;
import com.pgg.yixiannonapp.domain.MainEntity;
import com.pgg.yixiannonapp.domain.Results;
import com.pgg.yixiannonapp.domain.User;
import com.pgg.yixiannonapp.domain.order.Order;
import com.pgg.yixiannonapp.domain.order.ShipAddress;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.net.api.CartGoodsService;
import com.pgg.yixiannonapp.net.api.ClassifyService;
import com.pgg.yixiannonapp.net.api.GoodsDetailService;
import com.pgg.yixiannonapp.net.api.MainService;
import com.pgg.yixiannonapp.net.api.OrderService;
import com.pgg.yixiannonapp.net.api.ShipAddressService;
import com.pgg.yixiannonapp.net.api.UserService;
import com.pgg.yixiannonapp.net.retrofit.RetrofitUtils;
import com.pgg.yixiannonapp.utils.FileUtils;

import java.io.File;
import java.util.List;

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
    protected MainService mainService = RetrofitUtils.getRetrofit(Constant.BASE_URL).create(MainService.class);
    protected ClassifyService classifyService = RetrofitUtils.getRetrofit(Constant.BASE_URL).create(ClassifyService.class);
    protected GoodsDetailService goodsDetailService = RetrofitUtils.getRetrofit(Constant.BASE_URL).create(GoodsDetailService.class);
    protected CartGoodsService cartGoodsService = RetrofitUtils.getRetrofit(Constant.BASE_URL).create(CartGoodsService.class);
    protected ShipAddressService shipAddressService = RetrofitUtils.getRetrofit(Constant.BASE_URL).create(ShipAddressService.class);
    protected OrderService orderService = RetrofitUtils.getRetrofit(Constant.BASE_URL).create(OrderService.class);

    private static class SingletonHolder {
        private static final HttpData INSTANCE = new HttpData();
    }

    public void register(Observer<Results<User>> observable, final User user) {
        Observable<Results<User>> data = userService.register(user);
        setSubscribe(data, observable);
    }

    public void login(Observer<Results<User>> observable, String id, String password) {
        Observable<Results<User>> dataResults = userService.login(id, password);
        setSubscribe(dataResults, observable);
    }

    public void updateUserInfo(Observer<Results<User>> observable, User user) {
        Observable<Results<User>> dataResults = userService.updateUserInfo(user);
        setSubscribe(dataResults, observable);
    }

    public void logout(Observer<Results<User>> observable, User user) {
        Observable<Results<User>> dataResults = userService.logout(user);
        setSubscribe(dataResults, observable);
    }

    public void getHomeData(Observer<Results<MainEntity>> observable,int curPage,int pageNum){
        Observable<Results<MainEntity>> homeData = mainService.getHomeData(curPage,pageNum);
        setSubscribe(homeData, observable);
    }

    public void getRecommendData(Observer<Results<List<MainEntity.RecommendEntity>>> observable, int curPage, int pageNum){
        Observable<Results<List<MainEntity.RecommendEntity>>> homeData = mainService.getRecommendData(curPage,pageNum);
        setSubscribe(homeData, observable);
    }

    public void getAllClassifyData(Observer<Results<List<ClassifyTypeEntity>>> observable){
        Observable<Results<List<ClassifyTypeEntity>>> homeData = classifyService.getAllClassifyData();
        setSubscribe(homeData, observable);
    }

    public void getClassifyItemEntities(Observer<Results<List<ClassifyItemEntity>>> observable,int classifyDescId){
        Observable<Results<List<ClassifyItemEntity>>> classifyData = classifyService.getClassifyItemEntities(classifyDescId);
        setSubscribe(classifyData, observable);
    }

    public void getGoodsDetail(Observer<Results<MainEntity.RecommendEntity>> observable, String goodsName){
        Observable<Results<MainEntity.RecommendEntity>> classifyData = goodsDetailService.getGoodsDetail(goodsName);
        setSubscribe(classifyData, observable);
    }

    public void addCartGoods(Observer<Results<CartGoods>> observer,CartGoods cartGoods){
        Observable<Results<CartGoods>> cartGoodsData = cartGoodsService.addCartGoods(cartGoods);
        setSubscribe(cartGoodsData,observer);
    }

    public void getCartGoodsList(Observer<Results<List<CartGoods>>> observer,String user_name){
        Observable<Results<List<CartGoods>>> cartGoodsData = cartGoodsService.getCartGoodsList(user_name);
        setSubscribe(cartGoodsData,observer);
    }

    public void deleteCartGoodsList(Observer<Results<CartGoods>> observer,List<Integer> cartGoodsIds){
        Observable<Results<CartGoods>> cartGoodsData = cartGoodsService.deleteCartGoodsList(cartGoodsIds);
        setSubscribe(cartGoodsData,observer);
    }

    public void addShipAddress(Observer<Results<ShipAddress>> observer, ShipAddress shipAddress){
        Observable<Results<ShipAddress>> resultsObservable = shipAddressService.addShipAddress(shipAddress);
        setSubscribe(resultsObservable,observer);
    }

    public void getShipAddress(Observer<Results<List<ShipAddress>>> observer, String userName){
        Observable<Results<List<ShipAddress>>> resultsObservable = shipAddressService.getShipAddress(userName);
        setSubscribe(resultsObservable,observer);
    }


    public void modifyShipAddress(Observer<Results<ShipAddress>> observer, ShipAddress shipAddress){
        Observable<Results<ShipAddress>> resultsObservable = shipAddressService.modifyShipAddress(shipAddress);
        setSubscribe(resultsObservable,observer);
    }

    public void deleteShipAddress(Observer<Results<ShipAddress>> observer, int id){
        Observable<Results<ShipAddress>> resultsObservable = shipAddressService.deleteShipAddress(id);
        setSubscribe(resultsObservable,observer);
    }

    public void getShipAddressById(Observer<Results<ShipAddress>> observer, int id){
        Observable<Results<ShipAddress>> resultsObservable = shipAddressService.getShipAddressById(id);
        setSubscribe(resultsObservable,observer);
    }


    public void addOrder(Observer<Results<Order>> observer, Order order){
        Observable<Results<Order>> resultsObservable = orderService.addOrder(order);
        setSubscribe(resultsObservable,observer);
    }

    public void getOrderList(Observer<Results<List<Order>>> observer, String userName){
        Observable<Results<List<Order>>> resultsObservable = orderService.getOrderList(userName);
        setSubscribe(resultsObservable,observer);
    }

    public void getOrderListByStatus(Observer<Results<List<Order>>> observer,String userName,int orderStatus){
        Observable<Results<List<Order>>> resultsObservable = orderService.getOrderListByStatus(userName,orderStatus);
        setSubscribe(resultsObservable,observer);
    }

    public void updateOrderStatus(Observer<Results<Order>> observer,int orderStatus,int id){
        Observable<Results<Order>> resultsObservable = orderService.updateOrderStatus(orderStatus,id);
        setSubscribe(resultsObservable,observer);
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
