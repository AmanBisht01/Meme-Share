package com.aman.memeshare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;

import static android.content.Intent.createChooser;

public class MainActivity extends AppCompatActivity {

    String Imgurl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMeme();
    }

    private void loadMeme() {

        ProgressBar progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://meme-api.herokuapp.com/gimme";

// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        Imgurl = response.getString("url");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    progressBar.setVisibility(View.VISIBLE);
                    ImageView imageView =findViewById(R.id.memeShare);
                    Glide.with(MainActivity.this).load(Imgurl).into(imageView);
                    progressBar.setVisibility(View.INVISIBLE);

                }, error -> {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                    });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }


    public void shereMeme(View view) {
        Uri imageUri = Uri.parse(Imgurl);
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        Intent intent1 = intent.putExtra(Intent.EXTRA_TEXT, "Hay,Check this " + zimageUri);
        Intent Choose= createChooser(intent1,"Share this meme using");
        startActivity(Choose);
        //

    }

    public void nextMeme(View view) {
        loadMeme();

    }
}