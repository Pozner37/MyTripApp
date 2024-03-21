package com.mytrip

import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.mytrip.viewModels.LocationViewModel
import java.util.Locale

class CreatePostFragment : Fragment() {
    private lateinit var view: View
    private lateinit var description: TextInputEditText
    private lateinit var attachPictureButton: ImageButton
    private lateinit var submitButton: MaterialButton
    private lateinit var deviceLocation: Location
    private var attachedPicture: Uri = Uri.EMPTY
    private var imageView: ImageView? = null

    private val args: CountryPageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(
            R.layout.create_post_fragment, container, false
        )
        val locationViewModel = ViewModelProvider(requireActivity()).get(LocationViewModel::class.java)
        val location = locationViewModel.location

        deviceLocation = location

        val geocoder = context?.let { Geocoder(it, Locale.getDefault()) }
        val addresses = geocoder?.getFromLocation(location.latitude, location.longitude, 1)
        val countryCode = addresses?.get(0)?.countryCode

        initViews(view)
        handleSubmitButton()
        handleAttachProductPicture()

        return view
    }

    private fun initViews(view: View) {
        description = view.findViewById(R.id.post_description)
        attachPictureButton = view.findViewById(R.id.upload_picture_button)
        imageView = view.findViewById(R.id.selected_image)
        submitButton = view.findViewById(R.id.post_submit)
    }

    private fun handleSubmitButton() {
        submitButton.setOnClickListener {
            createNewPost()
        }
    }

    private fun showDialogResponse(message: String) {
        val rootView: View = requireView()
        val snackBar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)
        val snackBarView: View = snackBar.view
        snackBarView.setBackgroundColor(resources.getColor(R.color.black))
        val textView: TextView =
            snackBarView.findViewById(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(resources.getColor(R.color.white))
        snackBar.show()
    }

    private fun createNewPost() {
        val descriptionValue = description.text.toString()

        if (descriptionValue.isEmpty()) {
            showDialogResponse("Please enter a description")
            return
        }
        if (attachedPicture == Uri.EMPTY) {
            showDialogResponse("Please select a picture")
            return
        }

//        val newPost = Post(
//            "sdsd", args.country.name.common ,description.text.toString() ,position = LatLng(23.123,234.23)
//        )

//        Model.instance.addPost(newPost, attachedPicture) {
//            findNavController().popBackStack()
//        }
    }

    private val pickImageContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            try {
                uri?.let {
                    attachedPicture = it
                }
            } catch (e: Exception) {
                Log.d("CreatePost", "${e.message}")
            }
        }

    private fun handleAttachProductPicture() {
        attachPictureButton.setOnClickListener {
            pickImageContract.launch("image/*")
        }
    }
}
