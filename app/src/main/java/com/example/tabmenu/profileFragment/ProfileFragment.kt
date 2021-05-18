package com.example.tabmenu.profileFragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.tabmenu.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {
    private val storageFirebase: StorageReference = FirebaseStorage.getInstance().reference



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?


    ): View? {
        val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        val imageView = view.findViewById(R.id.profile_image) as ImageView
        val profileName = view.findViewById(R.id.profile_name) as TextView
        profileName.text= currentUser?.displayName

        val avatarRef: StorageReference = storageFirebase.child("users/"+mAuth.currentUser.uid+"profile.jpg")
        avatarRef.downloadUrl.addOnSuccessListener {
           Picasso.get().load(it).resize(100,100).noFade().noPlaceholder().into(imageView)

        }

        return view
    }


}