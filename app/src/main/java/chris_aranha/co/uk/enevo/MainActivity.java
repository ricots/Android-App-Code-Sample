package chris_aranha.co.uk.enevo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static chris_aranha.co.uk.enevo.Utils.DOMAIN;
import static chris_aranha.co.uk.enevo.Utils.ENDPOINT;
import static chris_aranha.co.uk.enevo.Utils.PHOTOUNITLISTING;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    // Tag for logging possible errors onto the Log
    private static String TAG = MainActivity.class.getSimpleName();
    // Progress dialog
    private ProgressDialog pDialog;
    // JSON Object containing the article fields such as title and image-id
    static JSONObject article;
    // RecyclerView Adapter
    static ArticleAdapter articleAdapter;
    // List containing articles
    List<Article> articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set layout
        setContentView(R.layout.activity_main);

        // Initialize ArrayList containing articles
        articles = new ArrayList<>();

        // Reference variable with layout view
        mRecyclerView = (RecyclerView) findViewById(R.id.cardList);

        // Reference the SwipeRefreshLayout
        SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // Display under one column
        mGridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        // Set orientation
        mGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        // Set Adapter
        articleAdapter = new ArticleAdapter(articles);
        mRecyclerView.setAdapter(articleAdapter);

        // Create click listener for each item on the list
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("article-id", articles.get(position).getArticleId());
                        bundle.putString("article-title", articles.get(position).getArticleTitle());
                        bundle.putString("article-image-url", articles.get(position).getImageUrl());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                })
        );

        // Allows for refresh of data
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent refresh = new Intent(MainActivity.this, MainActivity.class);
                articleAdapter.swapDataSet(articles);
                startActivity(refresh);
                MainActivity.this.finish();
            }
        });

        // Shows message to user while makeJsonObjectRequest() is still running
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Getting photo gallery...");
        pDialog.setCancelable(false);

        // Make JSON request
        makeJsonArrayRequest();
    }

    /**
     * Method to make JSON Array request
     */
    private void makeJsonArrayRequest() {

        // Show dialog while the request is made
        showpDialog();

        // Article listing endpoint
        String URL = DOMAIN + ENDPOINT + PHOTOUNITLISTING;

        final JsonArrayRequest jsonArrayReq = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        // Log response
                        Log.d(TAG, response.toString());

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                article = response.getJSONObject(i);
                                String articleId = article.getString("nid");
                                String title = article.getString("title");
                                String imageString = article.getJSONObject("field_photo").getString("filename");

                                articles.add(new Article(articleId, title, imageString));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        // Update adapter with new fetched JSON information
                        articleAdapter.swapDataSet(articles);
                        // Hide dialog after information has been requested
                        hidepDialog();
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Warn user
                Toast.makeText(getApplicationContext(),
                        "No internet connection", Toast.LENGTH_LONG).show();
                Log.d(TAG, error.toString());
                // hide the progress dialog
                hidepDialog();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonArrayReq);
    }

    /**
     * Method for showing dialog
     */
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    /**
     * Method for hiding dialog
     */
    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
