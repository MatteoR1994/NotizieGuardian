package com.example.notizieguardian

import android.app.VoiceInteractor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private var pageNumber = 1
    private var resultsList = mutableListOf<Data>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        higherPageButton.setOnClickListener {
            pageNumber += 1
            resultsList.clear()
            sendRequest()
        }

        lowerPageButton.setOnClickListener {
            pageNumber -= 1
            resultsList.clear()
            sendRequest()
        }
    }
    fun startSearch(v: View) {
        resultsList = mutableListOf()
        sendRequest()
    }
    fun getUrl(): String {
        val word = inputKeyword.text
        val pageSize = 10
        val apiKey = "16e5cdc7-7521-43a6-877d-60b72fceec90"
        //val url = "https://content.guardianapis.com/$word?page=$pageNumber&page-size=$pageSize&api-key=$apiKey"
        //val url = "https://content.guardianapis.com/search?$word&api-key=$apiKey"
        //val url = "https://content.guardianapis.com/search?q=$word&api-key=$apiKey"
        val url = "https://content.guardianapis.com/search?order-by=newest&page=$pageNumber&page-size=$pageSize&q=$word&api-key=$apiKey"
        return url
    }
    fun sendRequest() {
        val queue = Volley.newRequestQueue(this)
        val url = getUrl()
        val stringRequest = StringRequest(Request.Method.GET,url,
            { response ->
                try {
                    extractResultsJSON(response)
                } catch(e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(this,error.message.toString(),Toast.LENGTH_SHORT).show()
            })
        queue.add(stringRequest)
    }
    fun extractResultsJSON(s: String) {
        val oggetto = JSONObject(s)
        val corpo = oggetto.getJSONObject("response")
        val indirizzi = corpo.getJSONArray("results")
        for (i in 0..9){
            val item = indirizzi.getJSONObject(i)
            val webTitle = item.getString("webTitle")
            val webUrl = item.getString("webUrl")
            val data = Data(webTitle, webUrl)
            resultsList.add(data)
        }
        val adapter = NewsAdapter(resultsList)
        risultati.adapter = adapter
    }
}