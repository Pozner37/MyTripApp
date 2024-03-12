package viewModels

import androidx.lifecycle.ViewModel
import com.mytrip.classes.Country
import com.mytrip.classes.CountryFlag
import com.mytrip.classes.CountryName

class CountryFragmentViewModel: ViewModel() {
    private var country: Country = Country(CountryName("placeholder"), CountryFlag("placeholder"))

    public fun getCountry(): Country {
        return country
    }

    public fun setCountry(newCountry: Country){
        country = newCountry
    }
}