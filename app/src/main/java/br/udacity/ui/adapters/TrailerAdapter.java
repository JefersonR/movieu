package br.udacity.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.udacity.R;
import br.udacity.components.OpenSansBoldTextView;
import br.udacity.models.response.VideosResponse;

/**
 * Created by Jeferson on 07/08/2016.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.CardChangesViewHolder> {

    private List<VideosResponse.Result> cardViewListItems;
    private Context context;
    private OnItemClick onItemClick;


    public TrailerAdapter(List<VideosResponse.Result> palettes, OnItemClick onItemClick) {
        this.cardViewListItems = new ArrayList<VideosResponse.Result>();
        this.cardViewListItems.addAll(palettes);
        this.onItemClick = onItemClick;

    }

    @Override
    public CardChangesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_trailer, viewGroup, false);
        context = viewGroup.getContext();
        return new CardChangesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CardChangesViewHolder holder, int i) {

        if (cardViewListItems.get(holder.getAdapterPosition()) != null) {
            final VideosResponse.Result cardViewListItem = cardViewListItems.get(holder.getAdapterPosition());
            try {
                holder.tvTitle.setText(cardViewListItem.getName());
                holder.tvTitle.setOnClickListener(new View.OnClickListener() {
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


        private OpenSansBoldTextView tvTitle;


        private CardChangesViewHolder(View rootView) {
            super(rootView);
            tvTitle = (OpenSansBoldTextView) rootView.findViewById(R.id.tv_title);
        }
    }

    public interface OnItemClick {
         void onItemClick(View view, VideosResponse.Result item);
    }


}