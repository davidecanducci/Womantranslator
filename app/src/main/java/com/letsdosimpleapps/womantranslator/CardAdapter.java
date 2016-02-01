package com.letsdosimpleapps.womantranslator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Davide Canducci on 12/11/2015.
 */
public class CardAdapter extends RecyclerView.Adapter implements Filterable {

    private ArrayList<CardInfo> cardList;
    private static Typeface face;
    private ArrayList<CardInfo> mStringFilterList;
    private ValueFilter valueFilter;

    public CardAdapter(ArrayList cardList, Context context) {

        this.cardList = cardList;
        mStringFilterList = cardList;
        face = Typeface.createFromAsset(context.getAssets(), "fonts/CutiePop.ttf");
    }

    @Override
    public int getItemCount() {

        return cardList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder cardViewHolder, int i) {
        CardInfo ci = (CardInfo) cardList.get(i);
        ((CardViewHolder) cardViewHolder).vName.setText(ci.name);
        ((CardViewHolder) cardViewHolder).vName.setTextColor(Color.BLACK);
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, viewGroup, false);
        Animation animation = AnimationUtils.loadAnimation(viewGroup.getContext(), (R.anim.abc_popup_exit));
        itemView.startAnimation(animation);

        return new CardViewHolder(itemView);
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        protected TextView vName;

        public CardViewHolder(View v) {
            super(v);

            vName = (TextView) v.findViewById(R.id.textView);
            vName.setTypeface(face);
        }
    }


    @Override
    public Filter getFilter() {
        if(valueFilter==null) {

            valueFilter=new ValueFilter();
        }

        return valueFilter;
    }

    private class ValueFilter extends Filter {

        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if(constraint!=null && constraint.length()>0){
                Log.i("MELLEEEELEEE", "FDSAFD" + constraint);
                ArrayList<CardInfo> filterList=new ArrayList<CardInfo>();
                for(int i=0;i<mStringFilterList.size();i++){

                    if((mStringFilterList.get(i).name.toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        CardInfo item = new CardInfo();
                        item.name = mStringFilterList.get(i).name;

                        filterList.add(item);
                    }
                }
                results.count=filterList.size();
                results.values=filterList;
            }else{
                results.count=mStringFilterList.size();
                results.values=mStringFilterList;
            }
            return results;
        }


        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            cardList= (ArrayList<CardInfo>) results.values;
            notifyDataSetChanged();
        }
    }

    public ArrayList<CardInfo> getDataUpdated(){
        ArrayList<CardInfo> clonato = (ArrayList) cardList.clone();
        return clonato;
    }

}