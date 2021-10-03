package com.vano.myrestaurant.controller.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vano.myrestaurant.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CardViewHolder> {

    private final List<Integer> photos;

    private final List<String> captions;

    private final Listener listener;

    public RecyclerAdapter(List<Integer> photos, List<String> captions, Listener listener) {
        this.photos = photos;
        this.captions = captions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        final CardView cardView = (CardView) layoutInflater.inflate(R.layout.card, parent
                , false);

        return new CardViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, final int position) {
        final CardView cardView = holder.getCardView();
        final TextView name = cardView.findViewById(R.id.name);
        final ImageView image = cardView.findViewById(R.id.photo);

        name.setText(captions.get(position));
        image.setImageResource(photos.get(position));
        image.setContentDescription(captions.get(position));

        if (listener != null) {
            cardView.setOnClickListener(view -> listener.onItemClick(position));
        }
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        public CardViewHolder(@NonNull CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

        public CardView getCardView() {
            return cardView;
        }
    }

    public interface Listener {
        void onItemClick(int position);
    }
    //

}
