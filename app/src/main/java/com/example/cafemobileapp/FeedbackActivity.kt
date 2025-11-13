package com.example.cafemobileapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FeedbackActivity : AppCompatActivity() {

    private lateinit var ratingBar: RatingBar
    private lateinit var feedbackInput: EditText
    private lateinit var submitFeedbackButton: Button

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        ratingBar = findViewById(R.id.ratingBar)
        feedbackInput = findViewById(R.id.feedbackInput)
        submitFeedbackButton = findViewById(R.id.submitFeedbackButton)

        submitFeedbackButton.setOnClickListener {
            submitFeedback()
        }
        val backButton = findViewById<Button>(R.id.backToMenuButton)
        backButton.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }

    }

    private fun submitFeedback() {
        val rating = ratingBar.rating
        val feedbackText = feedbackInput.text.toString().trim()

        if (feedbackText.isEmpty()) {
            Toast.makeText(this, "Please write something!", Toast.LENGTH_SHORT).show()
            return
        }

        val userEmail = auth.currentUser?.email ?: "Guest"

        // create a map to save
        val data = hashMapOf(
            "user" to userEmail,
            "rating" to rating,
            "feedback" to feedbackText,
            "timestamp" to System.currentTimeMillis()
        )

        // write to collection "feedback"
        db.collection("feedback")
            .add(data)
            .addOnSuccessListener { docRef ->
                Toast.makeText(this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show()
                feedbackInput.text.clear()
                ratingBar.rating = 0f

                // Optional: go to OrderConfirmation or back to menu
                // val intent = Intent(this, MenuActivity::class.java)
                // startActivity(intent)
                // finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to submit feedback: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}

