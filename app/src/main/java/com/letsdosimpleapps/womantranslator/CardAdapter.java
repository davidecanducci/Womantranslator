package com.letsdosimpleapps.womantranslator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Davide Canducci on 12/11/2015.
 */
public class CardAdapter extends RecyclerView.Adapter {

    private List cardList;
    private static Typeface face;

    public CardAdapter(List cardList, Context context) {

        this.cardList = cardList;
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
        protected ImageView vImage;

        public CardViewHolder(View v) {
            super(v);

            vName = (TextView) v.findViewById(R.id.textView);
            vImage = (ImageView) v.findViewById(R.id.image);
            vName.setTypeface(face);
        }
    }
}