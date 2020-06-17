package llc.tach.imgur

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerAdapter
    private var recyclerView: RecyclerView? = null

    private lateinit var httpClient: OkHttpClient

    var results = ArrayList<Imgur>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val search = findViewById<SearchView>(R.id.searchView)

        search.queryHint = "Search Here"
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {

                runOnUiThread { getImagesBasedOnQuery(query) }

                return false
            }
        })

        recyclerView = findViewById(R.id.recyclerView)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = linearLayoutManager

        results.add(Imgur("title", "https://imgur.com/a/MD2VQwc.jpg"))
        adapter = RecyclerAdapter(results, this)
        recyclerView?.adapter = adapter
        adapter.onItemClick = { image ->
            val intent = Intent(this, FullImageActivity::class.java).apply {
                putExtra("Url", image.imageUrl)
            }
            startActivity(intent)
        }
    }

    private fun getImagesBasedOnQuery(query: String) {
        results.clear()

        httpClient = OkHttpClient.Builder().build()

        val request: Request = Request.Builder()
            .url("https://api.imgur.com/3/gallery/search/{{sort}}/{{window}}/{{page}}?q=$query")
            .header("Authorization", "Client-ID eb6206e6b72a898")
            .header("User-Agent", "Imgur")
            .build()

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException) {
                Log.e("Error", "An error has occurred $e")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call?, response: Response?) {
                val data = JSONObject(response!!.body()!!.string())
                val items = data.getJSONArray("data")

                for (i in 0 until items.length()) {
                    val item = items.getJSONObject(i)

                    val title = item.getString("title")
                    var url = item.getString("link")
                    url += ".jpg"
                    val imgur = Imgur(title, url)

                    Log.v(imgur.title, imgur.imageUrl)

                    results.add(imgur)
                }
            }
        })
        adapter.notifyDataSetChanged()
    }
}
