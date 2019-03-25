package com.pgg.yixiannonapp.module.goods_detail.fragment;

import android.view.LayoutInflater;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.base.BaseFragment;

import butterknife.BindView;

public class GoodsInfoWebFragment extends BaseFragment {

    @BindView(R.id.wv_detail)
    public WebView wv_detail;
    private WebSettings webSettings;
    private LayoutInflater inflater;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_item_info_web;
    }

    @Override
    public void initView() {
        String url = "http://m.okhqb.com/item/description/1000334264.html?fromApp=true";
        wv_detail.setFocusable(false);
        wv_detail.loadUrl(url);
        webSettings = wv_detail.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setBlockNetworkImage(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wv_detail.setWebViewClient(new GoodsDetailWebViewClient());
    }

    private class GoodsDetailWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webSettings.setBlockNetworkImage(false);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }

    @Override
    protected void managerArguments() {

    }

    @Override
    public String getUmengFragmentName() {
        return null;
    }
}
