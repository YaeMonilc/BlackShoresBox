package ltd.aliothstar.blackshoresbox

import android.app.Application
import android.content.Intent
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import ltd.aliothstar.blackshoresbox.ui.activity.CrashActivity
import kotlin.system.exitProcess

const val APPLICATION_CRASH_VALUE_KEY = "APPLICATION_CRASH_KEY"

@HiltAndroidApp
class BlackShoresBox : Application() {
    override fun onCreate() {
        super.onCreate()

        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            Log.e("APPLICATION_CRASH", throwable.stackTraceToString())

            Intent(this, CrashActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

                putExtra(APPLICATION_CRASH_VALUE_KEY, throwable.stackTraceToString())
            }.let {
                startActivity(it)
                exitProcess(1)
            }
        }
    }
}