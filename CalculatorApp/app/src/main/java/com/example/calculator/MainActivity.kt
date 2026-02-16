package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.calculationsapi.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurate buttons
        val simplify = findViewById<Button>(R.id.simplify);
        simplify.setOnClickListener() {
            val input = getUserInput();
            val encodedExpr = URLEncoder.encode(input, "UTF-8")

            val url = "https://newton.now.sh/api/v2/simplify/";
            val finalRequest = url + encodedExpr;
            fetchResponse(finalRequest)
        }

        val integrate = findViewById<Button>(R.id.integrate);
        integrate.setOnClickListener() {
            val input = getUserInput();
            val encodedExpr = URLEncoder.encode(input, "UTF-8")

            val url = "https://newton.now.sh/api/v2/integrate/";
            val finalRequest = url + encodedExpr;
            fetchResponse(finalRequest)
        }

        val find0 = findViewById<Button>(R.id.findZeros);
        find0.setOnClickListener() {
            val input = getUserInput();
            val encodedExpr = URLEncoder.encode(input, "UTF-8")

            val url = "https://newton.now.sh/api/v2/zeroes/";
            val finalRequest = url + encodedExpr;
            fetchZeroes(finalRequest)
        }

        val findTangent = findViewById<Button>(R.id.getTangent);
        findTangent.setOnClickListener() {
            val input = getUserInput();
            val encodedExpr = URLEncoder.encode(input, "UTF-8")

            val url = "https://newton.now.sh/api/v2/tangent/";
            val finalRequest = url + encodedExpr;
            fetchResponse(finalRequest)
        }

        val cos = findViewById<Button>(R.id.cos);
        cos.setOnClickListener() {
            val input = getUserInput();
            val encodedExpr = URLEncoder.encode(input, "UTF-8")

            val url = "https://newton.now.sh/api/v2/cos/";
            val finalRequest = url + encodedExpr;
            fetchResponse(finalRequest)
        }

        val sin = findViewById<Button>(R.id.sin);
        sin.setOnClickListener() {
            val input = getUserInput();
            val encodedExpr = URLEncoder.encode(input, "UTF-8")

            val url = "https://newton.now.sh/api/v2/sin/";
            val finalRequest = url + encodedExpr;
            fetchResponse(finalRequest)
        }

        val tangent = findViewById<Button>(R.id.tanges);
        tangent.setOnClickListener() {
            val input = getUserInput();
            val encodedExpr = URLEncoder.encode(input, "UTF-8")

            val url = "https://newton.now.sh/api/v2/tan/";
            val finalRequest = url + encodedExpr;
            fetchResponse(finalRequest)
        }

        val arccos = findViewById<Button>(R.id.arccos);
        arccos.setOnClickListener() {
            val input = getUserInput();
            val encodedExpr = URLEncoder.encode(input, "UTF-8")

            val url = "https://newton.now.sh/api/v2/arccos/";
            val finalRequest = url + encodedExpr;
            fetchResponse(finalRequest)
        }

        val arcsin = findViewById<Button>(R.id.arcsin);
        arcsin.setOnClickListener() {
            val input = getUserInput();
            val encodedExpr = URLEncoder.encode(input, "UTF-8")

            val url = "https://newton.now.sh/api/v2/arcsin/";
            val finalRequest = url + encodedExpr;
            fetchResponse(finalRequest)
        }

        val arctan = findViewById<Button>(R.id.arctan);
        arctan.setOnClickListener() {
            val input = getUserInput();
            val encodedExpr = URLEncoder.encode(input, "UTF-8")

            val url = "https://newton.now.sh/api/v2/arctan/";
            val finalRequest = url + encodedExpr;
            fetchResponse(finalRequest)
        }

        val abs = findViewById<Button>(R.id.abs);
        abs.setOnClickListener() {
            val input = getUserInput();
            val encodedExpr = URLEncoder.encode(input, "UTF-8")

            val url = "https://newton.now.sh/api/v2/abs/";
            val finalRequest = url + encodedExpr;
            fetchResponse(finalRequest)
        }

        val log = findViewById<Button>(R.id.log);
        log.setOnClickListener() {
            val input = getUserInput();
            val encodedExpr = URLEncoder.encode(input, "UTF-8")

            val url = "https://newton.now.sh/api/v2/log/";
            val finalRequest = url + encodedExpr;
            fetchResponse(finalRequest)
        }

        val factor = findViewById<Button>(R.id.factor);
        factor.setOnClickListener() {
            val input = getUserInput();
            val encodedExpr = URLEncoder.encode(input, "UTF-8")

            val url = "https://newton.now.sh/api/v2/factor/";
            val finalRequest = url + encodedExpr;
            fetchResponse(finalRequest)
        }

        val derive = findViewById<Button>(R.id.derive);
        derive.setOnClickListener() {
            val input = getUserInput();
            val encodedExpr = URLEncoder.encode(input, "UTF-8")

            val url = "https://newton.now.sh/api/v2/derive/";
            val finalRequest = url + encodedExpr;
            fetchResponse(finalRequest)
        }

        val area = findViewById<Button>(R.id.areaUnderCurve)
        area.setOnClickListener()
        {
            val input = getUserInput();
            val encodedExpr = URLEncoder.encode(input, "UTF-8")

            val url = "https://newton.now.sh/api/v2/area/"
            val finalRequest = url + encodedExpr
            fetchResponse(finalRequest)
        }
    }

    private fun getUserInput(): String {
        val editText = findViewById<EditText>(R.id.input).text.toString();
        return editText;
    }

    private fun setUserInput(out: String) {
        val output : TextView = findViewById(R.id.output)
        runOnUiThread { output.text = out }
    }

    private fun setUserInput(out: List<Int>) {
        val output : TextView = findViewById(R.id.output)
        var result = ""

        for(num in out) {
            result += (num.toString() + " ")
        }

        runOnUiThread { output.text = result }
    }

    private fun fetchResponse(url: String) {
        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(url + "/").build().create(APIService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val retrofitData = retrofitBuilder.getResponse(url)

            if (retrofitData != null) {
                retrofitData.body()?.let { setUserInput(it.result) }
            }
        }
    }

    private fun fetchZeroes(url: String) {
        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(url + "/").build().create(APIService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val retrofitData = retrofitBuilder.getZeroes(url)

            if (retrofitData != null) {
                retrofitData.body()?.let { setUserInput(it.result) }
            }
        }
    }
}