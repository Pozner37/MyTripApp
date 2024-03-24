package com.mytrip.viewModels

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {
    var location: MutableLiveData<Location> = MutableLiveData();
}
