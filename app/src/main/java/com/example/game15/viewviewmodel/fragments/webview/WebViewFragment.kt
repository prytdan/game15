package com.example.game15.viewviewmodel.fragments.webview

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.game15.databinding.FragmentWebViewBinding
import com.example.game15.viewviewmodel.activities.MainActivity
import com.example.game15.viewviewmodel.fragments.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class WebViewFragment : BaseFragment<FragmentWebViewBinding, WebViewViewModel>() {

    companion object {
        private const val SHARED_PREFS = "sharedPreferences"
        private const val LINK_TAG = "link"
        private const val LINK = "https://www.youtube.com/"
    }

    override val viewModel: WebViewViewModel by viewModel()

    override fun getViewBinding() = FragmentWebViewBinding.inflate(layoutInflater)

    override fun initViews() {
        super.initViews()
        setupViews()
        overrideOnBackPressed()
        saveLinkToSharedPreferences()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupViews() {
        binding.webView.webViewClient = WebViewClient()
        binding.webView.webChromeClient = WebChromeClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(LINK)
    }

    private fun overrideOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack()
            }
        }
    }

    private fun saveLinkToSharedPreferences() {
        val sharedPreferences = this.activity?.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        sharedPreferences?.edit()?.putString(LINK_TAG, "https://www.youtube.com/")?.apply()
    }
}