package io.ganguo.log.gg

import android.os.Build
import io.ganguo.log.core.LoggerConfig
import io.ganguo.log.core.Logger
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class ExampleUnitTest {

    @Before
    fun initLogger() {
        LoggerConfig.initialize(LoggerPrinter())
    }

    @Test
    fun onTestLogger() {
        ShadowLog.stream = System.out
        Logger.e(javaClass.simpleName + ": Logger.e")
        Logger.d(javaClass.simpleName + ": Logger.d")
        Logger.i(javaClass.simpleName + ": Logger.i")
        Logger.v(javaClass.simpleName + ": Logger.v")
    }
}
