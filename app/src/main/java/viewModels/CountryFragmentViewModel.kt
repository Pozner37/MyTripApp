package viewModels

import androidx.lifecycle.ViewModel
import com.example.mytrip.classes.Country
import com.example.mytrip.classes.CountryFlag
import com.example.mytrip.classes.CountryName

class CountryFragmentViewModel: ViewModel() {
    private var country: Country = Country(CountryName("placeholder"), CountryFlag("placeholder"))

    public fun getCountry(): Country {
        return country
    }

    public fun setCountry(newCountry: Country){
        country = newCountry
    }
}