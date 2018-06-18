package org.esiea.arellano.sylla.library_project;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder>{

    private JSONArray books;
    public BookAdapter (JSONArray books){
        this.books=books;
    }


    public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lI = LayoutInflater.from(parent.getContext());
        View view = lI.inflate(R.layout.rv_book_element,parent,false);
        return new BookHolder(view);
    }


    public void onBindViewHolder(BookHolder holder, int position) {
        try {
            holder.name.setText(books.getJSONObject(position).getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public int getItemCount() {
        if(books != null) {
            return books.length();
        }
        else{
            return 0;
        }
    }

    public class BookHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public BookHolder(View view) {
            super(view);
            this.name=(TextView) view.findViewById(R.id.textView2);
        }
    }

    public void setNewBook(JSONArray newBook){
        this.books=newBook;
        notifyDataSetChanged();
        Log.d("TAG", String.valueOf(getItemCount()));
    }
}
