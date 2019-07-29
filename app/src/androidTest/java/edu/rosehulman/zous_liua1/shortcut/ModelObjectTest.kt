package edu.rosehulman.zous_liua1.shortcut

import android.util.Log
import androidx.test.InstrumentationRegistry
import androidx.test.filters.SdkSuppress
import androidx.test.runner.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class ModelObjectTest {

    @Test
    fun checkEmulatorInfo() {

        try {
            val mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
            mDevice.pressHome()

            val settings = mDevice.findObject(By.desc("Settings"))
            settings.click()

            val scrollDown = UiScrollable(UiSelector().scrollable(true))
            scrollDown.scrollForward()
            scrollDown.scrollTextIntoView("System")

            val systemSettings = mDevice.findObject(UiSelector().text("System"))
            systemSettings.click()

            val aboutEmulator = mDevice.findObject(UiSelector().text("About emulated device"))
            aboutEmulator.click()
        } catch (e: Exception) {
            Log.e(Constants.TAG, "Exception:" + e.toString())
            null
        }
    }

    @Test
    fun calculateOnePlusTwo() {

        try {
            val mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
            mDevice.pressHome()

            val calculator = mDevice.findObject(By.desc("Calculator"))
            calculator.click()

            val pressOne = mDevice.findObject(UiSelector().text("1"))
            pressOne.click()

            val pressAdd = mDevice.findObject(UiSelector().text("+"))
            pressAdd.click()

            val pressTwo = mDevice.findObject(UiSelector().text("2"))
            pressTwo.click()

            val pressEqual = mDevice.findObject(UiSelector().text("="))
            pressEqual.click()
        } catch (e: Exception) {
            Log.e(Constants.TAG, "Exception:" + e.toString())
            null
        }
    }
}