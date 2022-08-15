package fast.parse.past

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {

    private val url = "https://en.wikipedia.org/"
    private val cssQuery = "#mp-dyk > ul > li:nth-child(1)"
    private val imgQuery = "#mp-dyk > a"

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv = findViewById<TextView>(R.id.tv)
        tv.textSize = 22F

        runBlocking {
            val job: Job = GlobalScope.launch(Dispatchers.IO) {
                tv.text = webData()

            }
            job.join()
            job.cancel()
        }
    }

    private fun webData(): String {
        val doc = Jsoup.connect(url).get()
        val elements = doc.select(cssQuery)
        val imageUrl = url + "wiki/" + doc.select(imgQuery).text()//elements.select("a").text().replace(" ", "_")
        Log.d("************ = ", imageUrl)

        return Jsoup.parse(elements.toString()).wholeText().substring(8)
    }


}