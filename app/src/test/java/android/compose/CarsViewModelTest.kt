package android.compose

import android.compose.presentation.viewmodels.cars.CarsViewModel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CarsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `when updateBrandFilters is called, cars list should be filtered correctly`(): Unit = runTest {
        // Arrange
        val fakeCarsRepository = FakeCarsRepository()
        val viewModel = CarsViewModel(fakeCarsRepository)
        val expectedBrand = "Toyota"

        // Act
        viewModel.updateBrandFilters(listOf(expectedBrand))
        val result = viewModel.cars.first()

        // Assert
        result.forEach {
            assertEquals(expectedBrand, it.brand)
        }
    }
}