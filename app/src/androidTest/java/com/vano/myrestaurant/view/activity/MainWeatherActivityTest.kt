package com.vano.myrestaurant.view.activity

import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vano.myrestaurant.R
import com.vano.myweather.model.entity.City
import com.vano.myweather.view.activity.MainWeatherActivity
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainWeatherActivityTest {

    private val testCity = City(
        "Town", 24.4, "clear sky", 50, 25.0
    )

    @Test
    fun test_that_cityIsEmpty() {
        ActivityScenario.launch(MainWeatherActivity::class.java).use { scenario ->
            scenario.onActivity { activity: MainWeatherActivity ->
                val textView = activity.findViewById<TextView>(R.id.t1)

                assertThat(textView, withText(""))
            }
        }
    }

    @Test
    fun test_fillInfo_should_fill_weatherFields() {
        ActivityScenario.launch(MainWeatherActivity::class.java).use { scenario ->
            scenario.onActivity { activity: MainWeatherActivity ->
                val tempField = activity.findViewById<TextView>(R.id.t1)

                activity.fillInfo(testCity)
                Assert.assertTrue(tempField.text.toString().length > 1)
            }
        }
    }
}