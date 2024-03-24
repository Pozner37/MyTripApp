package com.mytrip.viewModels

import androidx.lifecycle.ViewModel
import com.mytrip.db.user.User

class UserViewModel : ViewModel() {
    var user: User = User("1","Omri", "Alster");
}