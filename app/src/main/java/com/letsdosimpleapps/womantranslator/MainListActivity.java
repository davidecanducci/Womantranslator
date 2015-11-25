package com.letsdosimpleapps.womantranslator;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facebook.FacebookSdk;

import junit.framework.Assert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MainListActivity extends AppCompatActivity {

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

    String title = "Card RecyclerView";
    String[] arrayOriginalWomenPhrases;
    RecyclerView recyclerList;
    String[] arrayTruePhrases;

    private ListView listView_original_women_phrases;
    private ArrayList<TranslationModel> arrayList_original_women_phrases = new ArrayList<TranslationModel>();
    private TranslationListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.letsdosimpleapps.womantranslator", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

        FacebookSdk.sdkInitialize(getApplicationContext());

        recyclerList = (RecyclerView) findViewById(R.id.cardList);
        recyclerList.setHasFixedSize(true);
        LinearLayoutManager layout = new LinearLayoutManager(this);

        layout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layout);

        arrayOriginalWomenPhrases = getResources().getStringArray(R.array.array_original_women_phrases);
        arrayTruePhrases = getResources().getStringArray(R.array.array_true_phrases);

        CardAdapter cardadapt = new CardAdapter(createList(arrayOriginalWomenPhrases.length), this);
        recyclerList.setAdapter(cardadapt);

        recyclerList.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainListActivity.this, TruePhraseActivity.class);
                        intent.putExtra("idTruePhrase", position);
                        startActivity(intent);
                    }
                }));

    }

    private List createList(int size) {

        List result = new ArrayList();
        for (int i = 0; i < size; i++) {
            CardInfo card = new CardInfo();
            card.name = arrayOriginalWomenPhrases[i];
            String Imageid = "image" + i;
            card.image = getDrawable(this, Imageid);
            result.add(card);

        }

        return result;
    }

    public static int getDrawable(Context context, String name) {
        Assert.assertNotNull(context);
        Assert.assertNotNull(name);

        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    public void ListDrawer() {

        String[] arrayOriginalWomenPhrases = getResources().getStringArray(R.array.array_original_women_phrases);

        for (int i = 0; i < arrayOriginalWomenPhrases.length; i++) {
            arrayList_original_women_phrases.add(create_list_element(arrayOriginalWomenPhrases[i]));
        }

        adapter = new TranslationListAdapter(MainListActivity.this, arrayList_original_women_phrases);

        listView_original_women_phrases.setAdapter(adapter);

        listView_original_women_phrases.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id) {

                Intent intent = new Intent(MainListActivity.this, TruePhraseActivity.class);
                intent.putExtra("id_true_phrase", position);
                startActivity(intent);

            }

        });

    }

    private TranslationModel create_list_element(String original_women_phrase) {
        TranslationModel single_phrase = new TranslationModel();
        single_phrase.string_original_women_phrase = original_women_phrase;

        return single_phrase;
    }
}

