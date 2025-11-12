package com.example.cafemobileapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FeedbackActivity : AppCompatActivity() {

    private lateinit var ratingBar: RatingBar
    private lateinit var feedbackInput: EditText
    private lateinit var submitFeedbackButton: Button

    private val database = FirebaseDatabase.getInstance().getReference("Feedback")
    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        ratingBar = findViewById(R.id.ratingBar)
        feedbackInput = findViewById(R.id.feedbackInput)
        submitFeedbackButton = findViewById(R.id.submitFeedbackButton)

        submitFeedbackButton.setOnClickListener {
            submitFeedback()
        }
    }

    private fun submitFeedback() {
        val rating = ratingBar.rating
        val feedbackText = feedbackInput.text.toString().trim()

        if (feedbackText.isEmpty()) {
            Toast.makeText(this, "Please write something!", Toast.LENGTH_SHORT).show()
            return
        }

        val feedbackId = database.push().key ?: return
        val feedbackData = mapOf(
            "userId" to (user?.uid ?: "Anonymous"),
            "rating" to rating,
            "feedback" to feedbackText,
            "timestamp" to System.currentTimeMillis()
        )

        database.child(feedbackId).setValue(feedbackData)
            .addOnSuccessListener {
                Toast.makeText(this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show()
                feedbackInput.text.clear()
                ratingBar.rating = 0f
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to submit feedback. Try again!", Toast.LENGTH_SHORT).show()
            }
    }
}
