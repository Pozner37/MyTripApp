package com.mytrip

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mytrip.classes.Post
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class CreatePostFragment : Fragment() {
    private lateinit var view: View
    private lateinit var description: TextInputEditText
    private lateinit var attachPictureButton: ImageButton
    private lateinit var submitButton: MaterialButton
    private var attachedPicture: Uri = Uri.EMPTY

    private val args: CountryPageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(
            R.layout.create_post_fragment, container, false
        )

        initViews(view)
        handleSubmitButton()
        handleAttachProductPicture()

        return view
    }

    private fun initViews(view: View) {
        description = view.findViewById(R.id.post_description)
        attachPictureButton = view.findViewById(R.id.upload_picture_button)
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
            showDialogResponse("Must Enter Description")
            return
        }
        if (attachedPicture == Uri.EMPTY) {
            showDialogResponse("Must Pick Picture")
            return
        }

        val newPost = Post(
            "sdsd", args.country.name.common ,description.text.toString(), "testPictureURL",position = LatLng(23.123,234.23)
        )

//        Model.instance.addPost(newPost, attachedPicture) {
//            findNavController().popBackStack()
//        }
    }

    private val pickImageContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                attachedPicture = it
            }
        }

    private fun handleAttachProductPicture() {
        attachPictureButton.setOnClickListener {
            pickImageContract.launch("image/*")
        }
    }

}