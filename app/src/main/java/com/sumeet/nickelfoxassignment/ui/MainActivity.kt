package com.sumeet.nickelfoxassignment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sumeet.nickelfoxassignment.R
import com.sumeet.nickelfoxassignment.databinding.ActivityMainBinding
import com.sumeet.nickelfoxassignment.ui.adapters.VideosAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        someFunc()
        getResultsFromApi()
    }

    private fun getResultsFromApi() {
        viewModel.getAllResults()
    }

    private fun someFunc() {
        lifecycleScope.launchWhenCreated {
            viewModel.result.collect { event ->
                when(event){
                    is MainViewModel.ResultsEvent.Loading -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    is MainViewModel.ResultsEvent.Success -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.recyclerView.apply {
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            adapter = VideosAdapter()
                        }
                    }
                    is MainViewModel.ResultsEvent.Failure -> {
                        Toast.makeText(this@MainActivity,"Failed",Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }
}