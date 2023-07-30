package com.example.game15.viewviewmodel.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.example.game15.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
        private const val SHARED_PREFS = "sharedPreferences"
        private const val LINK_TAG = "link"
    }

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showSplashScreen()
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        if (linkIsSavedInSharedPreferences()) {
            navHostFragment.navController.navigate(R.id.action_startFragment_to_webViewFragment)
            return
        }

        // in case if getData is not successful, we open the game
        if (viewModel.getData()) {
            viewModel.myResponse.observe(this) {
                if (it.isSuccessful && it.body() != null) {
                    if (it.body()?.data.toBoolean()) {
                        navHostFragment.navController.navigate(R.id.action_startFragment_to_webViewFragment)
                    } else {
                        navHostFragment.navController.navigate(R.id.action_startFragment_to_menuFragment)
                    }
                } else {
                    Log.d(TAG, "Response is not successful")
                    navHostFragment.navController.navigate(R.id.action_startFragment_to_menuFragment)
                }
            }
        } else {
            Log.d(TAG, "getData is not successful")
            navHostFragment.navController.navigate(R.id.action_startFragment_to_menuFragment)
        }
    }

    private fun showSplashScreen() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
    }

    private fun linkIsSavedInSharedPreferences(): Boolean {
        val sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        return sharedPreferences.getString(LINK_TAG, null) != null
    }
}