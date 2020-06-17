package llc.tach.imgur

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerAdapter(private val results: ArrayList<Imgur>, val context: Context) :
    RecyclerView.Adapter<RecyclerAdapter.ResultHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ResultHolder {
        val itemView = LayoutInflater.from(p0.context)
            .inflate(R.layout.result_cell, p0, false)

        return ResultHolder(itemView)
    }

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: ResultHolder, p1: Int) {
        val picasso = Picasso.get()
        val result = results[p1]
        holder.imgurTitle?.text = result.title
        picasso.load(result.imageUrl)
            .into(holder.imgurImageView)
    }

    class ResultHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        var imgurTitle: TextView? = null
        var imgurImageView: ImageView? = null

        init {
            v.setOnClickListener(this)
            this.imgurTitle = v.findViewById(R.id.ImgurName)
            this.imgurImageView = v.findViewById(R.id.ImgurImage)
        }

        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
            //todo make intent into next activity and pass in url
        }
    }

}