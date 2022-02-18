package com.hung.exrotrofit

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hung.exrotrofit.adapter.SongAdapter
import com.hung.exrotrofit.databinding.ActivityMainBinding
import com.hung.exrotrofit.model.Song
import com.hung.exrotrofit.network.SongClient
import com.hung.exrotrofit.onclick.ItemClick
import com.hung.exrotrofit.utils.Constants.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), ItemClick {

    private lateinit var binding: ActivityMainBinding
    private lateinit var songAdapter : SongAdapter
    private var mediaPlayer : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecycleView()
    }

    private fun setupRecycleView() {
        songAdapter = SongAdapter(this)
        binding.rvMusic.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)

            adapter = songAdapter
            addItemDecoration(object :
                DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL){})
        }
        data()
    }

    private fun data() {
        SongClient.api.getAllMusic().enqueue(
        object : Callback<Song>{
            override fun onResponse(call: Call<Song>, response: Response<Song>) {
                if (response.isSuccessful){
                    val musics = response.body()?.music
                    songAdapter.differ.submitList(musics)

                }
            }
            override fun onFailure(call: Call<Song>, t: Throwable) {
                Toast.makeText(this@MainActivity, "error", Toast.LENGTH_SHORT).show()
            }

        }
    )
    }

    override fun onItemClick(position: Int) {
        if (mediaPlayer !=null){
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            startMusic(position)
        }else{
            startMusic(position)
        }
    }

    private fun startMusic(mPosition: Int) {
        mediaPlayer = MediaPlayer()
        val url = BASE_URL + songAdapter.differ.currentList[mPosition].source
        mediaPlayer!!.setDataSource(url)
        mediaPlayer!!.prepare()
        mediaPlayer!!.start()
    }
}