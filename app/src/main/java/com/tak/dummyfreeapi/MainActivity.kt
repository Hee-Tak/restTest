package com.tak.dummyfreeapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.tak.dummyfreeapi.databinding.ActivityMainBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MainActivity : AppCompatActivity() {


    private val viewModel : MyViewModel by viewModels()

    val binding = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.loadDataButton.setOnClickListener {  }

    }

    fun onLoadDataClick(view: View){
        //버튼 클릭 시 데이터 로딩
        viewModel.getPosts()

        //뷰모델에서 LiveData를 사용하여 데이터를 관찰(observe)하고, 데이터가 변경되었을 때 화면을 갱신할 수 있다.
        viewModel.posts.observe(this, Observer{ posts ->
            //화면에 데이터를 표시
            binding.resultTextView.text = posts.joinToString("\n") { it.title }
        })
    }

}