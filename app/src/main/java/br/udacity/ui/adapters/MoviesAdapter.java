package br.udacity.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.udacity.R;
import br.udacity.components.OpenSansBoldTextView;
import br.udacity.models.response.ResultResponse;

/**
 * Created by Jeferson on 07/08/2016.
 */
//Adapter de Evetos
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.CardChangesViewHolder> {

    private List<ResultResponse> cardViewListItems;
    private Context context;
    private OnItemClick onItemClick;


    public MoviesAdapter(List<ResultResponse> palettes, OnItemClick onItemClick) {
        this.cardViewListItems = new ArrayList<ResultResponse>();
        this.cardViewListItems.addAll(palettes);
        this.onItemClick = onItemClick;

    }

    @Override
    public CardChangesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_movie, viewGroup, false);
        context = viewGroup.getContext();
        return new CardChangesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CardChangesViewHolder holder, int i) {

        if (cardViewListItems.get(holder.getAdapterPosition()) != null) {
            final ResultResponse cardViewListItem = cardViewListItems.get(holder.getAdapterPosition());
            try {

                if (cardViewListItem.getPosterPath() != null) {
                    holder.shimmer.startShimmerAnimation();
                    Picasso.with(context).load(String.format(context.getString(R.string.str_base_img), cardViewListItem.getPosterPath())).error(R.drawable.img_placeholder_loading_error)
                            .into(holder.imgBackground, new Callback() {
                                @Override
                                public void onSuccess() {
                                    holder.shimmer.stopShimmerAnimation();
                                }

                                @Override
                                public void onError() {
                                    holder.shimmer.stopShimmerAnimation();
                                }
                            });
                } else {
                    holder.shimmer.stopShimmerAnimation();
                }
                holder.tvTitle.setText(cardViewListItem.getTitle());
                holder.cardItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClick.onItemClick(v, cardViewListItem);
                    }
                });


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

        private CardView cardItem;
        private ShimmerFrameLayout shimmer;
        private ImageView imgBackground;
        private OpenSansBoldTextView tvTitle;


        private CardChangesViewHolder(View rootView) {
            super(rootView);
            imgBackground = (ImageView) rootView.findViewById(R.id.img_background);
            shimmer = (ShimmerFrameLayout) rootView.findViewById(R.id.shimmer_view_container);
            cardItem = (CardView) rootView.findViewById(R.id.card_item);
            tvTitle = (OpenSansBoldTextView) rootView.findViewById(R.id.tv_title);


        }
    }

    public interface OnItemClick {
         void onItemClick(View view, ResultResponse item);
    }


}