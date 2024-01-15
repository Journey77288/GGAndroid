<<<<<<< HEAD:GGAndroid_4.0/core/widget/widget-layout/src/androidTest/java/io/ganguo/layout/ExampleInstrumentedTest.kt
package io.ganguo.layout
=======
package io.ganguo.image.coil
>>>>>>> version4.0/feature/coil:GGAndroid_4.0/core/service/service-image/image-coil/src/androidTest/java/io/ganguo/image/coil/ExampleInstrumentedTest.kt

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
<<<<<<< HEAD:GGAndroid_4.0/core/widget/widget-layout/src/androidTest/java/io/ganguo/layout/ExampleInstrumentedTest.kt
	@Test
	fun useAppContext() {
		// Context of the app under test.
		val appContext = InstrumentationRegistry.getInstrumentation().targetContext
		assertEquals("io.ganguo.layout.test", appContext.packageName)
	}
}
=======
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("io.ganguo.image.coil.test", appContext.packageName)
    }
}
>>>>>>> version4.0/feature/coil:GGAndroid_4.0/core/service/service-image/image-coil/src/androidTest/java/io/ganguo/image/coil/ExampleInstrumentedTest.kt
