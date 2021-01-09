package com.taghavi.graphqltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {
    private val BASE_URL = "https://api.github.com/graphql"
    private lateinit var client: ApolloClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        client = setupApollo()
    }

    private fun setupApollo(): ApolloClient {
        val okHttp = OkHttpClient
            .Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder().method(
                    original.method(),
                    original.body()
                )
                builder.addHeader(
                    "Authorization", "Bearer "
                )
                chain.proceed(builder.build())
            }
            .build()
        return ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttp)
            .build()
    }
}