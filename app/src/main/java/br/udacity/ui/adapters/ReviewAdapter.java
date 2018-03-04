package br.udacity.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.udacity.R;
import br.udacity.components.OpenSansBoldTextView;
import br.udacity.models.response.ReviewsResponse;

/**
 * Created by Jeferson on 07/08/2016.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.CardChangesViewHolder> {

    private List<ReviewsResponse.Result> cardViewListItems;
    private Context context;



    public ReviewAdapter(List<ReviewsResponse.Result> palettes) {
        this.cardViewListItems = new ArrayList<ReviewsResponse.Result>();
        this.cardViewListItems.addAll(palettes);

    }

    @Override
    public CardChangesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_review, viewGroup, false);
        context = viewGroup.getContext();
        return new CardChangesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CardChangesViewHolder holder, int i) {

        if (cardViewListItems.get(holder.getAdapterPosition()) != null) {
            final ReviewsResponse.Result cardViewListItem = cardViewListItems.get(holder.getAdapterPosition());
            try {
                holder.tvName.setText(String.format(context.getString(R.string.str_author), cardViewListItem.getAuthor()));
                holder.tvReview.setText(cardViewListItem.getContent());
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    @Override
    public int getItemCount() {
        if (cardViewListItems != null)
            return cardViewListItems.size();
        else return 0;

    }

    class CardChangesViewHolder extends RecyclerView.ViewHolder {
        private OpenSansBoldTextView tvName;
        private TextView tvReview;

        private CardChangesViewHolder(View rootView) {
            super(rootView);
            tvName = (OpenSansBoldTextView) rootView.findViewById(R.id.tv_name);
            tvReview = (TextView) rootView.findViewById(R.id.tv_review);
        }
    }


}