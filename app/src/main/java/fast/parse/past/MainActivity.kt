package fast.parse.past

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {



    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv = findViewById<TextView>(R.id.tv)
        tv.textSize = 22F
        runBlocking {
            val job: Job = GlobalScope.launch(Dispatchers.IO) {
                val str = webData()
                tv.append(str)
            }
            job.join()
        }
    }

    private fun webData(): String {
        val doc = Jsoup.connect("https://en.wikipedia.org/").get()
        val elements = doc.select("#mp-dyk > ul > li:nth-child(1)")

        return Jsoup.parse(elements.toString()).wholeText().substring(8)
    }

}