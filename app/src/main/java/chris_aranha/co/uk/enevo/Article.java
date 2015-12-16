package chris_aranha.co.uk.enevo;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Article extends AppCompatActivity implements View.OnClickListener {

    private String articleId;
    private String articleTitle;
    private String imageString;
    private String imageUrl;

    public Article(String articleId, String articleTitle, String imageString) {
        this.articleId = articleId;
        this.articleTitle = articleTitle;
        this.imageString = imageString;
    }

    @Override
    public void onClick(View v) {
    }

    // Unused setters bellow will be used for future development
    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
