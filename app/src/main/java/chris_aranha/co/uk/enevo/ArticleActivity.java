package chris_aranha.co.uk.enevo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import static chris_aranha.co.uk.enevo.Utils.DOMAIN;
import static chris_aranha.co.uk.enevo.Utils.ENDPOINT;
import static chris_aranha.co.uk.enevo.Utils.ITEM;

public class ArticleActivity extends AppCompatActivity {

    // Tag for logging possible errors onto the Log
    private static String TAG = ArticleActivity.class.getSimpleName();

    public String articleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        // Get info from bundle
        Bundle bundle = getIntent().getExtras();
        articleId = bundle.getString("article-id");
        String articleTitle = bundle.getString("article-title");
        String articleUrl = bundle.getString("article-image-url");

        // Get widget references
        TextView mArticleTitleTextView = (TextView) findViewById(R.id.article_title);
        NetworkImageView mArticleImageNetworkImageView = (NetworkImageView) findViewById(R.id.article_image);
        Button mDeleteButton = (Button) findViewById(R.id.delete_button);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        mArticleImageNetworkImageView.setImageUrl(articleUrl, imageLoader);
        mArticleTitleTextView.setText(articleTitle);

        // Set button listener
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent intent = new Intent(ArticleActivity.this, MainActivity.class);
                                                 deletePhotoRequest();
                                                 startActivity(intent);
                                             }
                                         }
        );
    }

    /**
     * Method to delete via JSON
     */
    private void deletePhotoRequest() {

        // Endpoint for single article
        String URL = DOMAIN + ENDPOINT + ITEM + articleId;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // response
                        if (response.equals("[true]")) {
                            Toast.makeText(ArticleActivity.this, "Image Deleted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ArticleActivity.this, "Unable to Delete", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Warn user
                Toast.makeText(getApplicationContext(),
                        "Unable to delete image", Toast.LENGTH_LONG).show();
                Log.e(TAG, error.toString());

            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
