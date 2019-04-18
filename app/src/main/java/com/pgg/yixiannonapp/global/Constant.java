package com.pgg.yixiannonapp.global;

public interface Constant {

    String FIRST_OPEN = "first_open";
    String OPENNEWS = "OPENNEWS";

    String QBOX_NEW_VERSION = "QBOX_NEW_VERSION";

    int NEWSFRAGMENT_CATEGORYACTIVITY_REQUESTCODE = 0x0001;
    int NEWSFRAGMENT_CATEGORYACTIVITY_RESULTCODE = 0x0010;
    String TAG_EXIT = "TAG_EXIT";

    /*
     * 首页Banner
     */
    String HOME_BANNER_ONE = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502876108368&di=cd9725c81901f6d7499edd76cf2e68e5&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F17%2F37%2F20%2F80Q58PICe3W_1024.jpg";

    String HOME_BANNER_TWO = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502882008108&di=d0cf4a8536aefa5df791716c1053ca66&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01e9495812c7f9a84a0d304fbc135b.jpg";


    String HOME_BANNER_THREE = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502876281925&di=f33e7ef8be268e90ffbffd315f5fb0a3&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F013e1b57d2731c0000018c1beeca11.jpg%40900w_1l_2o_100sh.jpg";


    String HOME_BANNER_FOUR = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503471047&di=679d7a6c499f59d1b0dcd56b62a9aa6c&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.90sheji.com%2Fdianpu_cover%2F11%2F14%2F64%2F55%2F94ibannercsn_1200.jpg";

    String BASE_URL = "http://10.0.2.2:8080/";
//    String BASE_URL = "http://192.168.71.124:8080/";
//    String BASE_URL = "http://172.20.10.14:8080/";
    /**
     * 支付宝支付业务：入参app_id
     */
    String APPID = "2016091200494687";

    String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCM9iHjB4rO4AnPgqNdmQ6Lk8e0SQ4kBwQ99tSXgm3a3EZ4f7VO7Zum1MhaOZTJrZ5hFwOIsSDdho8U91NVMrdopmg9AJjzOGYU7ivCr7O9Q4apeUukeSdeKlVsOyGbM8jOqgJ9M4URKLqoA8Hw/t12ziE4G79VXieZaI2lvQi3RSH3q0ah68PO6M/ehri0cVCYrMSNLo+I94Jm7XhghRt+B0rHVLh/Qz9BsRw2ENgT6kre4tuOaCMLVH9RztOjsPh/skZfOzffSPLjCXzISPQ6QJo1/764wBlr2bpvasT1I8JCXM4DxP7LBWwBraATXW/3zq67sBvcOgH1M8t+YNc/AgMBAAECggEAbjpKZW4QbZVkFpDhjt+RMDRvhdta43FZL9tEgvXLUcnOkYvMC9xiSyM5SNSeNNc/cky8xrc8K/59BLgiwgzWv07hVFKTSB5iNRvJvIQI2YdkVkIzlHno/wm7otbagLMN5A2txJn1BGYFED1f+0RRigXGlEmeZPMrpBrOKTyZBDYwybW2u27d5lLhjNUQM1d+yCDXeyjsBy8Nh01r57KBRzQfaV+DLpD1l+2wYOO6ZwCtgWjKIitd+uFmOP/YEcdoBKG3CC9TICIILf+i7XOXvKiJJr8n2fi98ppamF/qc4QrdN2UZh5pghzhGP5zKTc+csEXJk0TW7c2huC8Ik/kAQKBgQDRWSGe4Y1DQmTpVmUnUN+lERIsxILA51oEm8xM3bb7YyuTFdYUHZJuaphatg4LqdOdIkdiaUMaN34NOEQ6Q0YBOiEQi1+hTeCSBBwWy0lYWFxcd+BOp6h79kw/Lbpt9WNejkdIFkcRDhDHdvUcb3r5qgwxg+RNrdSRfeooHrDu3wKBgQCsX68jT1O+3VDJ//xWcTKg5svom6vZ5nSL1Bs9TwWMb0/lQ10CyF+ryl9Jx4X6oy3+h9NIfvP1sbZ4XKXtnAtoENkVpfLzH7p/9iWATFTc8K0kz0Pj/hEud23s0QWbE/q9agdADgHjBojjQPPCBfsKb1SfY5LuL/EjG6hBIXIDoQKBgGn4KjKYjb8cHqSWnQF3yJ1vtLmAh4dKFKD8lD/cg0FhA6+igAkSpwrJXnGcciOpRXrm3SeNjO/6Yw4iCzdK0mbQm+gaBMBMAX8oIWfQY0IbCDWuYJav+3M0S4g3h4Udj71PW/m7fSG+8vNk3dIVBDTYxO9Tif9gHJfBGwRP9NQVAoGBAIoIwZY0EysXzXdFT8vlMBbbW1S1PIPs4U2MGBgbbKUOClvceuIclUhbcb0CUw7nl5BpMk+uoiwRGL7/6jwBpry6KS0FzCzXLqED+vnnplmbPfvpIkO0FAwyxUtMAwIxmKwP+ShZTzd+5yWcwNaRtXugtZJBycqFh8OGhVFKuCOhAoGBAIOb2Xvw3Yjwo2j4OJ/Y8zglkt7EcNq5jt1LRAizfPQr2xnSRk307FGkzakdcGPTnjsbOqjrPXkkG7npyMjuMWoMPleIKdxNKIixJr6PLfEIxWezghpD/9YtrovPevnvHAjWTOUZUlFFLncjKp5WfGxpksz1vGu+iA3orv9QFprq";
    String RSA_PRIVATE = "";
    int SDK_PAY_FLAG = 1;
    String USER_ID = "user_id";
    String USER_NAGE = "user_name";
    String USER_NICK = "user_nick";
    String USER_SIGN = "user_sign";
    String USER_STATE = "user_state";
    String USER_MOBILE = "user_mobile";
    String USER_REAL_NAME = "user_real_name";
    String USER_IDENTITY_CARD = "user_identity_card";


    String CLICK_URL = "clickUrl";
    String CLICK_TITLE = "mTitle";

    String GOODS_TITLE = "goods_title";

    String KEY_ORDER_ID = "order_id";

    String SHIP_USERNAME = "shipUserName";
    String SHIP_ID="shipId";
    String SHIP_MOBILE = "shipUserMobile";
    String SHIP_ADDRESS = "shipAddress";
    String SHIP_DEFAULT = "shipIsDefault";

    String ISCHOOSE_ADDRESS = "isChooseAddress";

}
