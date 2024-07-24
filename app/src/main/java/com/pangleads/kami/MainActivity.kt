package com.pangleads.kami

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.sdk.openadsdk.api.init.PAGConfig
import com.bytedance.sdk.openadsdk.api.init.PAGSdk
import com.bytedance.sdk.openadsdk.api.init.PAGSdk.PAGInitCallback
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAd
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAdInteractionListener
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAdLoadListener
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialRequest


class MainActivity : AppCompatActivity() {

    private var interstitialAd: PAGInterstitialAd? = null
    var request = PAGInterstitialRequest()
    var LOG_FOR_AD = "logForAd"

    //For testing Use Only
    //Interstitial Ad ID: 980088188
    //Native Ad ID: 980088216
    //AppID: 8025677

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pAGInitConfig = buildNewConfig()
        PAGSdk.init(this, pAGInitConfig, object : PAGInitCallback {
            override fun success() {
                Log.i(LOG_FOR_AD, "pangle init success: ")
                loadAd()
            }

            override fun fail(code: Int, msg: String) {
                Log.i(LOG_FOR_AD, "pangle init fail: $code")
            }
        })

        findViewById<Button>(R.id.showAd).setOnClickListener {
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            startActivity(intent)
            showInterstitialAd()

        }
    }

    private fun showInterstitialAd() {
        if (interstitialAd != null) {
            interstitialAd?.show(this)
            interstitialAd?.setAdInteractionListener(object :
                PAGInterstitialAdInteractionListener {
                override fun onAdShowed() {}
                override fun onAdClicked() {}
                override fun onAdDismissed() {}
            })
            interstitialAd = null
        } else {
            Log.i(LOG_FOR_AD, "interstitial not load")
        }
    }

    private fun loadAd() {
        PAGInterstitialAd.loadAd("980088188",
            request,
            object : PAGInterstitialAdLoadListener {
                override fun onError(code: Int, message: String) {
                    Log.i(LOG_FOR_AD, "onErrorKami$message ")
                }

                override fun onAdLoaded(interstitialAd: PAGInterstitialAd) {
                    Log.i(LOG_FOR_AD, "onAdLoadedKami ")
                    findViewById<TextView>(R.id.adTxt).text="Ad is Loaded Now"
                    this@MainActivity.interstitialAd = interstitialAd
                }
            })
    }

    private fun buildNewConfig(): PAGConfig? {
        return PAGConfig.Builder()
            .appId("8025677")
            .appIcon(R.mipmap.ic_launcher)
            .debugLog(true)
            .build()
    }
}