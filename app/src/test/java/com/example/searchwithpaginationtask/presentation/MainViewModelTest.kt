package com.example.searchwithpaginationtask.presentation

import androidx.paging.PagingData
import com.example.searchwithpaginationtask.domain.models.Product
import com.example.searchwithpaginationtask.domain.repositories.ProductsRepository
import com.example.searchwithpaginationtask.presentation.MainViewModel.Companion.SEARCH_DEBOUNCE_ML
import com.example.searchwithpaginationtask.utils.MainDispatcherRule
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val stubPagingData = PagingData.from(listOf(createProduct(productId = 1)))
    private val productsRepository: ProductsRepository = mockk(relaxed = true) {
        coEvery { getSearchResults(any()) } returns flowOf(stubPagingData)
    }
    private val viewModel by lazy { MainViewModel(productsRepository) }

    @Test
    fun `when query is changed should show reload the data`() = runTest {
        val updatedQuery = "newValue"

        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.products.collect()
        }
        viewModel.updateQuery(updatedQuery)
        advanceTimeBy(SEARCH_DEBOUNCE_ML + 1)

        coVerify { productsRepository.getSearchResults(updatedQuery) }
        job.cancel()
    }

    @Test
    fun `when clear query is clicked should clear the search`() = runTest {
        val values = mutableListOf<String>()
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.searchQuery.collect {
                values.add(it)
            }
        }
        viewModel.updateQuery("newValue")
        viewModel.onClearButtonClick()

        Truth.assertThat(values.last()).isEmpty()
        job.cancel()
    }

    private fun createProduct(
        productId: Int = 1,
        productName: String = "name",
        usp: List<String> = listOf("feature1"),
        imageUrl: String = "",
        price: Double = 10.0
    ): Product = Product(productId, productName, usp, imageUrl, price)
}