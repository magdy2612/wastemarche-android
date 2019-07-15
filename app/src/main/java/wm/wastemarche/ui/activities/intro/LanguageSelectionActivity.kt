package wm.wastemarche.ui.activities.intro

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import wm.wastemarche.R
import wm.wastemarche.services.datacenter.DataCenter
import java.util.*

class LanguageSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_selection)
    }

    fun onArabicButtonClicks(view: View) {
        DataCenter.lang = "ar"
        startNextActivity()
    }

    fun onEnglishButtonClicks(view: View) {
        DataCenter.lang = "en"
        startNextActivity()
    }

    fun startNextActivity() {
        changeLang(applicationContext, DataCenter.lang)
        val intent = Intent(this, LoginRegisterActivity::class.java)
        startActivity(intent)
    }

    fun changeLang(context: Context, lang: String) {
        val myLocale = Locale(lang)
        Locale.setDefault(myLocale)
        val config = Configuration()
        config.locale = myLocale
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}
