package com.maty.android.bookshelves

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.test.MoreAsserts.assertEquals

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
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.maty.android.bookshelves", appContext.packageName)
    }
}
