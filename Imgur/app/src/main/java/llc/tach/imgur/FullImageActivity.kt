package llc.tach.imgur

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.Picasso

class FullImageActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)

        imageView = findViewById(R.id.fullImageView)

        val url = intent.getStringExtra("Url")
        val picasso = Picasso.get()

        picasso.load(url)
            .into(imageView)

    }
}
