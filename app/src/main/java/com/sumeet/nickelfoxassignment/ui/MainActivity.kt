package com.sumeet.nickelfoxassignment.ui

import android.R.id
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sumeet.nickelfoxassignment.databinding.ActivityMainBinding
import com.sumeet.nickelfoxassignment.ui.adapters.VideosAdapter
import com.sumeet.nickelfoxassignment.util.OnItemClickListener
import com.sumeet.nickelfoxassignment.util.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() , OnItemClickListener{

    private lateinit var binding : ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()
    private lateinit var videosAdapter: VideosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        someFunc()
        setUpRecyclerView()
        getResultsFromApi()
    }

    private fun getResultsFromApi() {
        viewModel.getAllResults()
    }

    private fun someFunc() {
        viewModel.videoList.observe(this,{
            when(it){
                is Resource.Success -> {
                    hideProgressBar()
                    it.data?.let { response ->
                        videosAdapter.differ.submitList(response.items?.toList())
                        if(response.nextPageToken == null){
                            isLastPage = true
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    it.message?.let { error ->
                        Log.d("MainActivity","Error : $error")
                    }
                }
                else -> Unit
            }
        })
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager

            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastPage = (firstVisibleItemPosition + visibleItemCount) >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= 10
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastPage && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling

            if(shouldPaginate) {
                viewModel.getAllResults()
                isScrolling = false
            }
        }
    }

    private fun hideProgressBar() {
        binding.progressCircular.visibility = View.GONE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.progressCircular.visibility = View.VISIBLE
        isLoading = true
    }

    private fun setUpRecyclerView() {
        videosAdapter = VideosAdapter(this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = videosAdapter
            addOnScrollListener(this@MainActivity.scrollListener)
        }
    }

    override fun onItemClicked(url: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$url"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=$url")
        )
        try {
            startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            startActivity(webIntent)
        }
    }
}