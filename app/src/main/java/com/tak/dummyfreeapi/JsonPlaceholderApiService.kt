package com.tak.dummyfreeapi

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonPlaceholderApiService {

    @GET("posts/{id}")                                                   //"posts/{id}"는 엔드포인트의 일부를 나타내며, '{id}' 부분은 동적으로 바뀔 매개변수를 나타낸다.
    suspend fun getPostById(@Path("id") postId: Int): Response<Post>     // getPostById(1) 을 호출 ==> "posts/1" 로 요청이 전송된다.

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>


}

/**
 * suspend 키워드
 * - 코루틴을 지원하기 위한 코틀린의 특별한 키워드. 코루틴은 비동기 코드를 더 쉽게 작성할 수 있도록 하는 기능.
 * - suspend 함수는 일반적인 함수와 마찬가지로 호출되지만, 중간에 일시 중단되고 재개될 수 있다.
 * - 주로 비동기 작업을 수행하는 함수에서 사용된다.
 *
 * - 예를 들어, Retrofit 은 기본적으로 동기적인 코드를 지원하므로, 네트워크 호출 등의 비동기 작업을 처리할 때,
 *      'suspend' 키워드를 사용하여 해당 함수를 코루틴으로 사용할 수 있게 한다.
 *

 */