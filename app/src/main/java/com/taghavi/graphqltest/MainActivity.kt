package com.taghavi.graphqltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scope = MainScope()

        val apolloClient = ApolloClient.builder()
            .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
            .build()

        scope.launch {
            val response = try {
                apolloClient.query(LaunchDetails(id = "83")).toDeferred().await()
            } catch (e: ApolloException) {
                // handle protocol errors
                return@launch
            }

            val launch = response.data?.launch
            if (launch == null || response.hasErrors()) {
                // handle application errors
                return@launch
            }

            // launch now contains a typesafe model of your data
            println("Launch site: ${launch.site}")
        }
    }
}