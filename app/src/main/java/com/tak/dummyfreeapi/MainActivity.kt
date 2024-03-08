package com.tak.dummyfreeapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.tak.dummyfreeapi.databinding.ActivityMainBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MainActivity : AppCompatActivity() {




    private lateinit var binding : ActivityMainBinding //xml 파일 명이 CamelCase 표기로 바뀌고 Binding 이 붙는다.
    private val viewModel : MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this   //LiveData 의 관찰을 활성화하기 위해 lifecycleOwner 설정

        binding.viewModel = viewModel   //뷰 모델을 레이아웃과 연결

        binding.loadDataButton.setOnClickListener {
            //액티비티가 종료되지 않은 상태에서만 데이터를 가져오도록 확인
            if(!isFinishing && !isDestroyed) {
                viewModel.getPosts()
            }
        }

        observeViewModel()
    }

    override fun onStop() {
        super.onStop()
        viewModel.posts.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy called")
    }

    private fun observeViewModel() {
        viewModel.posts.observe(this, Observer{posts ->
            //데이터가 변경될 때마다 호출되는 부분
            binding.resultTextView.text = posts.joinToString("\n") { it.title }
            SystemClock.sleep(1000)

        })
    }

}


/**
 * 데이터 바인딩
 * - XML 레이아웃 파일에서 뷰와 데이터를 바인딩하여 뷰를 업데이트하거나, 이벤트를 처리할 수 있도록 해주는 라이브러리.
 * - 기존의 방식은 findViewById 메소드를 이용해서 뷰를 찾아야 했지만, 데이터 바인딩을 사용하면 XML 에서 바로 뷰를 참조할 수 있다.
 *
 */