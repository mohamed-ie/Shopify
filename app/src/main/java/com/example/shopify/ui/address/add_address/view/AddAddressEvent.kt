package com.example.shopify.ui.address.add_address.view

sealed interface AddAddressEvent {
    object HomeAddressSelected : AddAddressEvent
    object WorkAddressSelected : AddAddressEvent
    object Save : AddAddressEvent

    class StreetChanged(val newValue: String) : AddAddressEvent
    class ApartmentChanged(val newValue: String) : AddAddressEvent
    class CityChanged(val newValue: String) : AddAddressEvent
    class CountryChanged(val newValue: String) : AddAddressEvent
    class StateChanged(val newValue: String) : AddAddressEvent
    class ZIPChanged(val newValue: String) : AddAddressEvent
    class FirstNameChanged(val newValue: String) : AddAddressEvent
    class LastNameChanged(val newValue: String) : AddAddressEvent
    class PhoneChanged(val newValue: String) : AddAddressEvent
    class OrganizationChanged(val newValue: String) : AddAddressEvent
}
