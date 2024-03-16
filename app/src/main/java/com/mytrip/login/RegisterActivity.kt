package com.mytrip.login

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresExtension
import androidx.fragment.app.Fragment
import com.mytrip.MainActivity
import com.mytrip.db.user.User
import com.mytrip.db.user.UserModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mytrip.R
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult


class RegisterActivity : Fragment() {

    private lateinit var imageSelectionCallBack: ActivityResultLauncher<Intent>
    private var selectedImageURI: Uri? = null
    private lateinit var firstNameInputLayout: TextInputLayout
    private lateinit var firstNameEditText: TextInputEditText
    private lateinit var lastNameInputLayout: TextInputLayout
    private lateinit var lastNameEditText: TextInputEditText
    private lateinit var emailAddressInputLayout: TextInputLayout
    private lateinit var emailAddressEditText: TextInputEditText
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var confirmPasswordInputLayout: TextInputLayout
    private lateinit var confirmPasswordEditText: TextInputEditText
    private val auth = Firebase.auth

    @RequiresExtension(extension = Build.VERSION_CODES.R, version = 2)
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_register, container, false)

        defineImageSelectionCallBack(view)
        openGallery(view)
        toLoginActivity(view)
        createNewUser(view)

        return view
    }

    private fun createNewUser(view: View) {
        // Initialize views
        firstNameInputLayout = view.findViewById(R.id.layoutFirstName)
        firstNameEditText = view.findViewById(R.id.editTextFirstName)
        lastNameInputLayout = view.findViewById(R.id.layoutLastName)
        lastNameEditText = view.findViewById(R.id.editTextLastName)
        emailAddressInputLayout = view.findViewById(R.id.layoutEmailAddress)
        emailAddressEditText = view.findViewById(R.id.editTextEmailAddress)
        passwordInputLayout = view.findViewById(R.id.layoutPassword)
        passwordEditText = view.findViewById(R.id.editTextPassword)
        confirmPasswordInputLayout = view.findViewById(R.id.layoutConfirmPassword)
        confirmPasswordEditText = view.findViewById(R.id.editTextConfirmPassword)

        view.findViewById<Button>(R.id.SignUpButton).setOnClickListener {
            // Retrieve user input
            val firstName = firstNameEditText.text.toString().trim()
            val lastName = lastNameEditText.text.toString().trim()
            val email = emailAddressEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            // Validate user input
            val syntaxChecksResult =
                validateUserRegistration(firstName, lastName, email, password, confirmPassword)

            if (syntaxChecksResult) {
                auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                    val authenticatedUser = it.user!!

                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setPhotoUri(selectedImageURI)
                        .setDisplayName("$firstName $lastName")
                        .build()

                    authenticatedUser.updateProfile(profileUpdates)

                    UserModel.instance.addUser(
                        User(authenticatedUser.uid, firstName, lastName),
                        selectedImageURI!!
                    ) {
                        Toast.makeText(
                            requireContext(),
                            "Register Successful",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                }.addOnFailureListener {
                    Toast.makeText(
                        requireContext(),
                        "Register Failed, " + it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun toLoginActivity(view: View) {
        view.findViewById<TextView>(R.id.LogInTextView).setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun validateUserRegistration(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (firstName.isEmpty()) {
            firstNameInputLayout.error = "First name cannot be empty"
            return false
        } else {
            firstNameInputLayout.error = null
        }
        if (lastName.isEmpty()) {
            lastNameInputLayout.error = "Last name cannot be empty"
            return false
        } else {
            lastNameInputLayout.error = null
        }
        if (email.isEmpty()) {
            emailAddressInputLayout.error = "Email cannot be empty"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailAddressInputLayout.error = "Invalid email format"
            return false
        } else {
            emailAddressInputLayout.error = null
        }
        if (password.isEmpty()) {
            passwordInputLayout.error = "Password cannot be empty"
            return false
        } else if (password.length < 6) {
            passwordInputLayout.error = "Password must be at least 6 characters"
            return false
        } else if (!password.any { it.isDigit() }) {
            passwordInputLayout.error = "Password must contain at least one digit"
            return false
        } else {
            passwordInputLayout.error = null
        }
        if (confirmPassword.isEmpty()) {
            confirmPasswordInputLayout.error = "Confirm password cannot be empty"
            return false
        } else if (password != confirmPassword) {
            confirmPasswordInputLayout.error = "Passwords do not match."
            return false
        } else {
            confirmPasswordInputLayout.error = null
        }
        if (selectedImageURI == null) {
//            Toast.makeText(
//                this@RegisterActivity,
//                "You must select a Profile Image",
//                Toast.LENGTH_SHORT
//            ).show()
            return false
        }
        return true
    }

    @RequiresExtension(extension = Build.VERSION_CODES.R, version = 2)
    private fun openGallery(view: View) {
        view.findViewById<Button>(R.id.btnPickImage).setOnClickListener {
            val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
            imageSelectionCallBack.launch(intent)
        }
    }

    @SuppressLint("Recycle")
    private fun getImageSize(uri: Uri?): Long {
//        val inputStream = contentResolver.openInputStream(uri!!)
//        return inputStream?.available()?.toLong() ?: 0
        return 0;
    }

    private fun defineImageSelectionCallBack(view: View) {
        imageSelectionCallBack = registerForActivityResult(
            StartActivityForResult()
        ) { result: ActivityResult ->
            // Result handling logic
        }
    }
}
