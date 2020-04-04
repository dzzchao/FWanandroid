package com.dzzchao.fwanandroid.test.rxjava

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.load.engine.bitmap_recycle.IntegerArrayAdapter
import com.dzzchao.fwanandroid.R
import com.dzzchao.fwanandroid.retrofit.bean.HomeArticleResp
import com.dzzchao.fwanandroid.retrofit.bean.HomeBannerResp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TestRxJavaActivity : AppCompatActivity() {

    private var disposables: CompositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_rx_java)


        //flatMap 对象转换
        val retrofit = Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        val service = retrofit.create(RetrofitServiceForTest::class.java)
        disposables.add(service.register("zhangchao", "123456", "123456")
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .flatMap { resp ->
                var userName = ""
                var password = ""
                resp.let {
                    userName = resp.data.username
                    password = resp.data.password
                }
                service.login(userName, password)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            })
        )

        //zip 联合判断条件
        val zip = service.getBanner()
            .zipWith(
                service.getHomeArticle(),
                object : BiFunction<HomeBannerResp, HomeArticleResp, Boolean> {
                    override fun apply(t1: HomeBannerResp, t2: HomeArticleResp): Boolean {

                        return true
                    }
                })
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({

            }, {

            })

        // merge 合并请求，适用于结果一样的
        val merge = service.getBanner()
            .mergeWith(service.getBanner())
            .subscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}
