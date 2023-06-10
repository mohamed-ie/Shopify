package com.example.shopify.feature.navigation_bar.my_account.screens.add_address

import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.feature.navigation_bar.model.repository.ShopifyRepository
import com.example.shopify.feature.navigation_bar.my_account.screens.add_address.view.AddAddressEvent
import com.example.shopify.feature.navigation_bar.my_account.screens.add_address.view.AddAddressState
import com.example.shopify.helpers.Resource
import com.example.shopify.helpers.validator.TextFieldStateValidator
import com.shopify.buy3.Storefront
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAddressViewModel @Inject constructor(
    private val textFieldStateValidator: TextFieldStateValidator,
    private val repository: ShopifyRepository
) : BaseScreenViewModel() {
    private val _state = MutableStateFlow(AddAddressState())
    val state = _state.asStateFlow()

    private val _back = MutableSharedFlow<Boolean>()
    val back = _back.asSharedFlow()

    init {
        toStableScreenState()
    }

    fun onEvent(event: AddAddressEvent) {
        when (event) {
            is AddAddressEvent.ApartmentChanged -> {
                _state.update {
                    val value = it.apartment.updateValue(event.newValue)
                    it.copy(apartment = value)
                }
            }

            is AddAddressEvent.CityChanged -> {
                _state.update {
                    val value = it.city.updateValue(event.newValue)
                    it.copy(city = value)
                }
            }

            is AddAddressEvent.CountryChanged -> {
                _state.update {
                    val value = it.country.updateValue(event.newValue)
                    it.copy(country = value)
                }
            }

            is AddAddressEvent.FirstNameChanged -> {
                _state.update {
                    val value = it.firstName.updateCharactersValue(event.newValue)
                    it.copy(firstName = value)
                }
            }

            is AddAddressEvent.LastNameChanged -> {
                _state.update {
                    val value = it.lastName.updateCharactersValue(event.newValue)
                    it.copy(lastName = value)
                }
            }

            is AddAddressEvent.OrganizationChanged -> {
                _state.update {
                    val value = it.organization.updateValue(event.newValue)
                    it.copy(organization = value)
                }
            }

            is AddAddressEvent.PhoneChanged -> {
                _state.update {
                    val value = it.phone.updateNumbersValue(event.newValue)
                    it.copy(phone = value)
                }
            }

            is AddAddressEvent.StateChanged -> {
                _state.update {
                    val value = it.state.updateValue(event.newValue)
                    it.copy(state = value)
                }
            }

            is AddAddressEvent.StreetChanged -> {
                _state.update {
                    val value = it.street.updateValue(event.newValue)
                    it.copy(street = value)
                }
            }

            is AddAddressEvent.ZIPChanged -> {
                _state.update {
                    val value = it.zip.updateValue(event.newValue)
                    it.copy(zip = value)
                }
            }

            AddAddressEvent.HomeAddressSelected ->
                _state.update { it.copy(isHomeAddress = true) }

            AddAddressEvent.WorkAddressSelected ->
                _state.update { it.copy(isHomeAddress = false) }

            AddAddressEvent.Save -> {
                updateFields()
                if (isFieldsValid())
                    save()
            }

        }

    }

    private fun updateFields() {
        _state.update { oldState ->
            oldState.copy(
                street = textFieldStateValidator.emptyValidation(oldState.street),
                apartment = textFieldStateValidator.emptyValidation(oldState.apartment),
                city = textFieldStateValidator.emptyValidation(oldState.city),
                state = textFieldStateValidator.emptyValidation(oldState.state),
                organization = textFieldStateValidator.emptyValidation(oldState.organization),
                country = textFieldStateValidator.emptyValidation(oldState.country),
                zip = textFieldStateValidator.validateZip(oldState.zip),
                phone = textFieldStateValidator.validatePhone(oldState.phone),
                firstName = textFieldStateValidator.validateName(oldState.firstName),
                lastName = textFieldStateValidator.validateName(oldState.lastName),
            )
        }
    }

    private fun isFieldsValid(): Boolean = _state.value.run {
        !street.isError &&
                !apartment.isError &&
                !city.isError &&
                !state.isError &&
                !country.isError &&
                !zip.isError &&
                !phone.isError &&
                !firstName.isError &&
                !lastName.isError &&
                (isHomeAddress || !organization.isError)

    }

    private fun save() {
        toLoadingScreenState()
        val address = Storefront.MailingAddressInput().apply {
            this.address1 = state.value.street.value
            this.address2 = state.value.apartment.value
            this.province = state.value.state.value
            this.city = state.value.city.value
            this.country = state.value.country.value
            this.firstName = state.value.firstName.value
            this.lastName = state.value.lastName.value
            this.phone = state.value.phone.value
            this.zip = state.value.zip.value
            this.company = state.value.organization.value
        }
        viewModelScope.launch {
            when (repository.saveAddress(address)) {
                is Resource.Error -> {
                    toErrorScreenState()
                }

                is Resource.Success -> {
                    _back.emit(true)
                }
            }
        }
    }

}