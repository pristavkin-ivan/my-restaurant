package com.vano.myweather.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.vano.myweather.model.api.WeatherApi
import com.vano.myweather.model.entity.City
import com.vano.myweather.model.repository.CityRepository
import com.vano.myweather.model.retrofit.RetrofitModule
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.json.JSONObject
import org.json.JSONTokener
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response
import java.io.File
import java.io.FileReader

@ExperimentalCoroutinesApi
class CityViewModelTest {

    companion object {
        const val TEST_CITY_NAME = "Minsk"
        const val FILE_PATH = "D:\\AndroidStudioProjects\\MyRestaurant"
        const val FILE_NAME = "Minsk.json"
        const val WEATHER_KEY = "weather"
        const val DESCRIPTION = "description"
        const val MAIN = "main"
        const val TEMPERATURE = "temp"
        const val NAME = "name"
        const val FEELS_LIKE = "feels_like"
        const val HUMIDITY = "humidity"
        const val FIRST_ELEMENT = 0
    }

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private val testSchedulers = object : SchedulersWrapper {
        override fun io() = Schedulers.trampoline()
        override fun ui() = Schedulers.trampoline()
    }

    private lateinit var cityViewModel: CityViewModel
    private lateinit var cityViewModelWithRepository: CityViewModel
    private lateinit var repository: CityRepository
    private lateinit var workingRepository: CityRepository
    private lateinit var observer: Observer<City>
    private lateinit var application: Application
    private val testCity = City(
        "Vakanda", 14.5, "clear", 20, 30.2
    )

    @Before
    fun init() {
        repository = mock()
        observer = mock()
        application = mock()
        workingRepository = CityRepository(
            mock(), mock(), RetrofitModule.getRetrofitInstance().create(WeatherApi::class.java)
        )
        cityViewModel = CityViewModel(
            repository, coroutineRule.dispatchersWrapper, testSchedulers,
            application
        )
        cityViewModelWithRepository = CityViewModel(
            workingRepository, coroutineRule.dispatchersWrapper, testSchedulers,
            application
        )
    }

    @Test
    fun saveCityTest() {
        coroutineRule.testDispatcher.runBlockingTest {
            cityViewModel.saveCity(testCity)
            verify(repository).save(testCity)
        }
    }

    @Test
    fun getCityTest() {
        var data: LiveData<Response<City>>? = null
        coroutineRule.testDispatcher.runBlockingTest {
            whenever(repository.getCity(testCity.name)).thenReturn(Response.success(testCity))
            data = cityViewModel.getCity(testCity.name)
        }
        data?.observeForever {
            Assert.assertEquals(it.body(), testCity)
        }
    }

    @Test
    fun getObservableCityTest() {
        whenever(repository.getCityRx(TEST_CITY_NAME)).thenReturn(
            workingRepository.getCityRx(
                TEST_CITY_NAME
            )
        )
        cityViewModel.getObservableCity(TEST_CITY_NAME)
            .test()
            .assertComplete()
    }

    @Test
    fun testJsonDeserializer() {
        cityViewModelWithRepository.getObservableCity(TEST_CITY_NAME).test()
            .assertValue(getCityFromJsonFile())
    }

    private fun getCityFromJsonFile(): City {
        val readText: String

        FileReader(File(FILE_PATH, FILE_NAME)).use {
            readText = it.readText()
        }

        val json = JSONObject(JSONTokener(readText))
        val name = json.getString(NAME)
        val description = json.getJSONArray(WEATHER_KEY).getJSONObject(FIRST_ELEMENT)
            .getString(DESCRIPTION)
        val main = json.getJSONObject(MAIN)
        val temp = main.getDouble(TEMPERATURE)
        val feels = main.getDouble(FEELS_LIKE)
        val humidity = main.getInt(HUMIDITY)

        return City(name, temp, description, humidity, feels)
    }

}

@ExperimentalCoroutinesApi
class CoroutineTestRule(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher() {

    val dispatchersWrapper = object : DispatchersWrapper {
        override val main = testDispatcher
        override val io = testDispatcher
        override val default = testDispatcher
    }

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}
