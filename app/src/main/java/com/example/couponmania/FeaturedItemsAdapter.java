package com.example.couponmania;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeaturedItemsAdapter extends RecyclerView.Adapter<FeaturedItemsAdapter.FeaturedItemViewHolder> {

    private Cursor cursor;

    public FeaturedItemsAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeaturedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_featured_ad, parent, false);
        return new FeaturedItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedItemViewHolder holder, int position) {
        if (cursor != null && cursor.moveToPosition(position)) {

            // Log to see
            Log.d("CursorData", "Position: " + position);
            Log.d("CursorData", "Title: " + cursor.getString(2));
            Log.d("CursorData", "Description: " + cursor.getString(3));
            Log.d("CursorData", "Price: " + cursor.getString(4));
            Log.d("CursorData", "Location: " + cursor.getString(5));
            Log.d("CursorData", "CategoryID: " + cursor.getString(6));

            // Retrieve data from cursor
            String title = cursor.getString(2);
            String description = cursor.getString(3);
            String price = cursor.getString(4);
            String location = cursor.getString(5);
            String categoryIndex = cursor.getString(6);

            // Bind data to views
            holder.titleTextView.setText(title);
            holder.descriptionTextView.setText(description);
            holder.priceTextView.setText(price);
            holder.locationTextView.setText(location);
            // Check if categoryIndex is not null before setting the text
            if (categoryIndex != null) {
                holder.categoryTextView.setText(categoryIndex);
            } else {
                // Handle null categoryIndex here, e.g., set a default value
                holder.categoryTextView.setText("Unknown Category");
            }
        }else {
            // Handle case where cursor is null or not positioned correctly
            // For example, you can set default values for views
            holder.titleTextView.setText("N/A");
            holder.descriptionTextView.setText("N/A");
            holder.priceTextView.setText("N/A");
            holder.locationTextView.setText("N/A");
            holder.categoryTextView.setText("N/A");
        }
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    static class FeaturedItemViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView descriptionTextView;
        private TextView priceTextView;
        private TextView locationTextView;
        private TextView categoryTextView;

        FeaturedItemViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
        }

    }
}

