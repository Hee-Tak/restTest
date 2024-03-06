package com.tak.dummyfreeapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MyViewModel : ViewModel() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(JsonPlaceholderApiService::class.java)


    private val _posts = MutableLiveData<List<Post>>()
    val posts : LiveData<List<Post>> get() = _posts

    fun getPosts() {
        viewModelScope.launch {

            try {
                val response = apiService.getPosts()
                if(response.isSuccessful){
                    val posts = response.body()
                    // 결과를 처리
                    //posts?.forEach {
                    //    println("Post Title: ${it.title}")
                    //}
                    _posts.postValue(posts!!)
                } else {
                    // 오류 처리
                    //println("Error: ${response.code()}")
                    Log.e("MyViewModel", "Error: ${response.code()}")
                }

            } catch (e:Exception){
                e.printStackTrace()
            }

        }

    }
}


/**
 * viewModelScope
 * - Android JetPack 의 ViewModel 라이브러리에서 제공하는 코루틴 스코프.
 * - 이것은 뷰 모델 수명주기와 관련이 있으며, 뷰모델이 파괴될 때 해당 스코프에서 실행중인 모든 작업을 자동으로 취소합니다.
 * - viewModelScope 를 사용하면 뷰 모델이 소멸될 때 코루틴에서 발생하는 잠재적인 메모리 누수와 관련된 문제를 방지할 수 있습니다.
 * - 뷰모델의 수명주기와 함께 사용하면, 뷰모델이 활성 상태일 때만 실행 중인 작업이 있도록 보장할 수 있습니다.
 *
 *          //라이브러리 dependency 관계 설정
 *          implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.0")
 *
 *          //이를 통해 viewModelScope 를 사용하여, 뷰모델에서 안전하게 비동기 작업을 수행 가능
 *
 * - viewModelScope.launch{ ... }
 *      이건 코루틴을 시작하고 해당 뷰모델의 수명주기에 맞게 관리하는 데 사용된다.
 *      이를 통해 비동기 작업을 안전하게 수행할 수 있다.
 */