package com.pangleads.kami

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeAd
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeAdLoadListener
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeRequest


class SecondActivity : AppCompatActivity() {
    var request = PAGNativeRequest()
    var LOG_FOR_AD = "logForAd"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        loadNativeAd()
    }

    private fun loadNativeAd() {
        PAGNativeAd.loadAd("980088216", request, object : PAGNativeAdLoadListener {
            override fun onError(code: Int, message: String) {
                Log.i(LOG_FOR_AD, "onError")

            }

            override fun onAdLoaded(pagNativeAd: PAGNativeAd) {
                Log.i(LOG_FOR_AD, "NativeOnAdLoaded")
                findViewById<TextView>(R.id.loading).visibility = View.GONE
                findADView(this@SecondActivity, pagNativeAd)
            }
        })
    }

    private fun findADView(mContext: Context, nativeAd: PAGNativeAd) {
        val nativeAdView: View = LayoutInflater.from(mContext).inflate(R.layout.native_ad, null)
        val mTitle = nativeAdView.findViewById<View>(R.id.ad_title) as TextView
        val mDescription = nativeAdView.findViewById<View>(R.id.ad_desc) as TextView
        val mIcon = nativeAdView.findViewById<View>(R.id.ad_icon) as ImageView
        val mCreativeButton = nativeAdView.findViewById<View>(R.id.creative_btn) as Button
        val mImageOrVideoView = nativeAdView.findViewById<View>(R.id.ad_video) as FrameLayout

        val adData = nativeAd.nativeAdData
        mTitle.text = adData.title
        mDescription.text = adData.description


        val icon = adData.icon
        if (icon != null && icon.imageUrl != null) {
            Glide.with(mContext).load(icon.imageUrl).into(mIcon)
        }
        // Load and display Btn text
        mCreativeButton.text =
            if (TextUtils.isEmpty(adData.buttonText)) mContext.getString(R.string.install) else adData.buttonText

        // Load and display the video content
        val videoContent = adData.mediaView
        if (videoContent != null) {

            mImageOrVideoView.removeAllViews()
            mImageOrVideoView.addView(videoContent)
        } else {

            mImageOrVideoView.visibility = View.GONE
        }

        val adContainer = findViewById<LinearLayout>(R.id.nativeAdViewCon)
        adContainer.addView(nativeAdView)

    }
}