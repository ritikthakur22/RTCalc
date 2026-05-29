package com.ritikthakur.rtcalc.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ritikthakur.rtcalc.data.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    private val _rates = MutableStateFlow<Map<String, Double>>(emptyMap())
    val rates: StateFlow<Map<String, Double>> = _rates

    private val _sourceCurrency = MutableStateFlow("USD")
    val sourceCurrency: StateFlow<String> = _sourceCurrency

    private val _targetCurrency = MutableStateFlow("EUR")
    val targetCurrency: StateFlow<String> = _targetCurrency

    private val _amount = MutableStateFlow("1.0")
    val amount: StateFlow<String> = _amount

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        fetchRates()
    }

    fun fetchRates() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val fetchedRates = repository.getRates(_sourceCurrency.value)
                _rates.value = fetchedRates
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Failed to fetch rates"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun setSourceCurrency(currency: String) {
        if (_sourceCurrency.value == currency) return
        _sourceCurrency.value = currency
        fetchRates()
    }

    fun setTargetCurrency(currency: String) {
        _targetCurrency.value = currency
    }

    fun setAmount(newAmount: String) {
        if (newAmount.isEmpty() || newAmount.count { it == '.' } > 1) {
            _amount.value = newAmount
            return
        }
        try {
            newAmount.toDouble() // validate
            _amount.value = newAmount
        } catch (e: Exception) {
            if (newAmount == ".") _amount.value = "0."
        }
    }

    fun swapCurrencies() {
        val temp = _sourceCurrency.value
        _sourceCurrency.value = _targetCurrency.value
        _targetCurrency.value = temp
        fetchRates()
    }
}
