package llc.tach.imgur

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerAdapter
    private var recyclerView: RecyclerView? = null

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
                //getImagesBasedOnQuery(query)
                return false
            }
        })

        results.add(Imgur("dummy", "https://static01.nyt.com/images/2019/03/25/arts/25batman-DTC1000B/25batman-DTC1000B-videoSixteenByNineJumbo1600.jpg"))

        recyclerView = findViewById(R.id.recyclerView)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = linearLayoutManager

        adapter = RecyclerAdapter(results, this)
        adapter.onItemClick = { image ->
            val intent = Intent(this, FullImageActivity::class.java).apply {
                putExtra("Url", image.imageUrl)
            }
            startActivity(intent)
        }

        recyclerView?.adapter = adapter
        adapter.notifyDataSetChanged()

    }
}
