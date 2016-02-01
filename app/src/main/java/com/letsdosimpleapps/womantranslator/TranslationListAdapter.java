package com.letsdosimpleapps.womantranslator;

/**
 * Created by Davide Canducci on 12/11/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TranslationListAdapter extends BaseAdapter {

    private ArrayList<TranslationModel> arrayList_original_women_phrases;
    private Context context;

    public TranslationListAdapter(Context context, ArrayList<TranslationModel> original_women_phrases) {
        this.arrayList_original_women_phrases = original_women_phrases;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList_original_women_phrases.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList_original_women_phrases.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // TODO faremo dice max holder pattern
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View base = LayoutInflater.from(context).inflate(R.layout.original_phrase_item, parent, false);
        TextView textView_original_women_phrase = (TextView) base.findViewById(R.id.textView_original_phrase);
        textView_original_women_phrase
                .setText(arrayList_original_women_phrases.get(position).string_original_women_phrase);

        return base;
    }
}
