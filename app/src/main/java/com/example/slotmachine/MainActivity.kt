package com.example.slotmachine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.slotmachine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // to navigate to game act.
        binding.btnStart.setOnClickListener{
            val intent = Intent(this, GameActivity::class.java)//launching of activity.
            startActivity(intent)
            // https://youtu.be/VzUjz9xdVDI?t=663
        }
    }
}