package com.example.android_devproj5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.android_devproj5.ui.theme.Android_devproj5Theme
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler
import okhttp3.Headers
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide. request.RequestOptions

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()




        setContentView(R.layout.activity_main)

        var button:Button = findViewById<Button>(R.id.pokemon_button)
        var picture: ImageView = findViewById<ImageView>(R.id.pokemon)
        var search_name:EditText =findViewById<EditText>(R.id.search)
        var name: TextView = findViewById<TextView>(R.id.name)
        var health: TextView = findViewById<TextView>(R.id.health)
        var attack: TextView = findViewById<TextView>(R.id.attack)
        var defense: TextView = findViewById<TextView>(R.id.defense)

        fun get_pokemon(input_name: String){
                val client = AsyncHttpClient()
                val params = RequestParams()
                client.get("https://pokeapi.co/api/v2/pokemon/${input_name}", object : JsonHttpResponseHandler() {
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Headers,
                        json: JsonHttpResponseHandler.JSON
                    ) {
                        Log.d("Main Activity", "It worked!")
                        var sprite = json.jsonObject.getJSONObject("sprites").getString("front_default")
                        var poke_hp = json.jsonObject.getJSONArray("stats").getJSONObject(0).getInt("base_stat")
                        var poke_def = json.jsonObject.getJSONArray("stats").getJSONObject(2).getInt("base_stat")
                        var poke_attack = json.jsonObject.getJSONArray("stats").getJSONObject(1).getInt("base_stat")
                        Glide.with(this@MainActivity)
                            .load(sprite)
                            .fitCenter()
                            .into(picture)
                        var pokemon_name =json.jsonObject.getString("name")
                        name.text = "name: "+ pokemon_name
                        health.text = "health: "+ poke_hp.toString()
                        defense.text = "defense: "+ poke_def.toString()
                        attack.text = "attack: "+ poke_attack.toString()





                    }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        throwable: Throwable?
                    ) {
                        Log.e("MainActivity", "HTTP $statusCode\n$errorResponse", throwable)
                    }


                })
            }
      button.setOnClickListener(){
        val input = search_name.text.toString().trim().lowercase()
        if(input != "" && input !="enter pokemon name"){
        get_pokemon(input)
      }
        else{
            val toast = Toast.makeText(this, "Missing Pokemon Name",Toast.LENGTH_LONG )
            toast.show()

        }}













    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Android_devproj5Theme {
        Greeting("Android")
    }
}