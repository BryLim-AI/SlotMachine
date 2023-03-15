package com.example.slotmachine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
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
            intent.putExtra(HEADER,getString(R.string.pressBet))
           startActivityForResult(intent, REQUEST_CODE)

            //startActivity(intent)
            // https://youtu.be/VzUjz9xdVDI?t=663
        }
        // to transfer data to from the game Activity
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            val returnValue = data?.getStringExtra(RETURN_KEY)?:"Welcome to Apple Slot Machine!"
            //to display
            binding.txtMain.text = returnValue
        }
    }
}