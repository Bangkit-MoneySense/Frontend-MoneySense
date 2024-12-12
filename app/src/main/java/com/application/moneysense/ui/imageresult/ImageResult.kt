package com.application.moneysense.ui.imageresult

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.application.moneysense.R
import com.application.moneysense.databinding.ActivityImageResultBinding
import com.application.moneysense.ui.moneyscan.MoneyScan
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ImageResult : AppCompatActivity() {

    private lateinit var binding: ActivityImageResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImageResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
//        val imageUri = intent.getParcelableExtra<Uri>(EXTRA_IMAGE_URI)
        try {
            if (imageUri != null) {
                Log.d("Image URI", "showImage: $imageUri")
                binding.previewImageView.setImageURI(null) // Clear the ImageView first
                binding.previewImageView.setImageURI(imageUri)
            } else {
                Log.e("Image URI", "URI is null or invalid")
                showToast("Failed to load image")
            }
        } catch (e: Exception) {
            Log.e("Image URI", "Error loading image", e)
            showToast("Failed to load image: ${e.message}")
        }

        // initialize button
        val backBtn = findViewById<FloatingActionButton>(R.id.backBtn)

        // back to real scanning
        backBtn.setOnClickListener() {
            back()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun back() {
        val intent = Intent(this, MoneyScan::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
    }
}