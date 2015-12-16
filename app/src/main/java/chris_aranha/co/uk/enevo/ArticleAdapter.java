package chris_aranha.co.uk.enevo;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import static chris_aranha.co.uk.enevo.Utils.DOMAIN;
import static chris_aranha.co.uk.enevo.Utils.IMAGEFILEFOLDERLOCATION;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<Article> articleList;

    public ArticleAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {

        Article article = articleList.get(position);
        String imageFileName = articleList.get(position).getImageString();
        String imageUrl = DOMAIN + IMAGEFILEFOLDERLOCATION + "/" + imageFileName;
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        holder.vArticleImage.setImageUrl(imageUrl, imageLoader);
        articleList.get(position).setImageUrl(imageUrl);
        holder.vArticleTitle.setText(article.getArticleTitle());
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_card_layout, parent, false);
        return new ArticleViewHolder(itemView);
    }

    // Create the ViewHolder class "pattern"
    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        protected TextView vArticleTitle;
        protected NetworkImageView vArticleImage;

        public ArticleViewHolder(View v) {
            super(v);

            vArticleTitle = (TextView) v.findViewById(R.id.title);
            vArticleImage = (NetworkImageView) v.findViewById(R.id.image);
        }
    }

    /**
     * Method to swap data with new set
     *
     * @param newData
     */
    public void swapDataSet(List<Article> newData) {
        this.articleList = newData;
        notifyDataSetChanged();
    }
}
