package com.ebc.tomate

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ebc.tomate.databinding.ActivityMainBinding
import androidx.compose.ui.res.stringResource
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var isRunning = false
    private var timerSeconds = 0

    private val Handler = Handler(Looper.getMainLooper())
    private val timerRunnable = object : Runnable {
        override fun run() {
            timerSeconds++
            val hours = timerSeconds / 3600
            val minutes = (timerSeconds % 3600) / 60
            val seconds = timerSeconds % 60

            val timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            binding.timerText.text = timeString
            Handler.postDelayed(this, 1000)
        }
    }
// fake version
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            startTimer()
        }
        binding.stopButton.setOnClickListener {
            stopTimer()
        }
        binding.resetButton.setOnClickListener {
            resetTimer()
        }
    }
    private fun startTimer() {
        if (!isRunning) {
            Handler.postDelayed(timerRunnable, 1000)
            isRunning = true

            binding.startButton.isEnabled = false
            binding.stopButton.isEnabled = true
            binding.resetButton.isEnabled =true
        }
    }
    private fun stopTimer() {
        if (isRunning) {
                Handler.removeCallbacks(timerRunnable)
                isRunning = false

                binding.startButton.isEnabled = true
                binding.startButton.text = resources.getString(R.string.resume)
                binding.stopButton.isEnabled = false
                binding.resetButton.isEnabled = true
        }
    }
    private fun resetTimer() {
        stopTimer()
        timerSeconds = 0
        binding.timerText.text = resources.getString(R.string._00_00_00)
        binding.startButton.isEnabled = true
        binding.startButton.text = resources.getString(R.string.start)
        binding.resetButton.isEnabled = false
    }

}