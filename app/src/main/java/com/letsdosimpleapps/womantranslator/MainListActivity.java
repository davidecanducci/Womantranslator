package com.letsdosimpleapps.womantranslator;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.FacebookSdk;

import junit.framework.Assert;

import java.util.ArrayList;

public class MainListActivity extends AppCompatActivity {


    String title = "Card RecyclerView";
    String[] arrayOriginalWomenPhrases;
    RecyclerView recyclerList;
    String[] arrayTruePhrases;
    private CardAdapter cardadapt;
    private ListView listView_original_women_phrases;
    private ArrayList<TranslationModel> arrayList_original_women_phrases = new ArrayList<TranslationModel>();
    private TranslationListAdapter adapter;
    private TextView textView_toolbar_title;
    private static Typeface face;
    private ArrayList<CardInfo> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        // codice per farmi sputare la key hash direttamente da qui
        /*
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
        */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        face = Typeface.createFromAsset(this.getAssets(), "fonts/Nickainley.ttf");

        textView_toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        textView_toolbar_title.setTypeface(face);

        FacebookSdk.sdkInitialize(getApplicationContext());

        recyclerList = (RecyclerView) findViewById(R.id.cardList);
        recyclerList.setHasFixedSize(true);
        LinearLayoutManager layout = new LinearLayoutManager(this);

        layout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layout);

        arrayOriginalWomenPhrases = getResources().getStringArray(R.array.array_original_women_phrases);
        arrayTruePhrases = getResources().getStringArray(R.array.array_true_phrases);
        data = createList(arrayOriginalWomenPhrases.length);
        cardadapt = new CardAdapter(data, this);
        recyclerList.setAdapter(cardadapt);

        recyclerList.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainListActivity.this, TruePhraseActivity.class);
                        data = cardadapt.getDataUpdated();
                        CardInfo newcard = data.get(position);
                        int index = arrayOriginalWomenPhrases[position].indexOf(" "); // prelevo l'indice del primo spazio che trovo
                        String choosenPhrase = new String((newcard.name).substring(0, index)); // dall'indice 0 al primo spazio ottengo la sottostringa con substring
                        position = Integer.parseInt(choosenPhrase) - 1;
                        Log.i("ciao", "" + position+ " melllelelelell "+newcard.name);
                        intent.putExtra("idTruePhrase", position);
                        startActivity(intent);
                    }
                }));

    }

    private ArrayList<CardInfo> createList(int size) {

        ArrayList<CardInfo> result = new ArrayList<CardInfo>();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchmenu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));

        //attivo la funzione di ricerca solo se l'activity ha una listview.

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                cardadapt.getFilter().filter(newText);
                System.out.println("on text chnge text: " + newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                // this is your adapter that will be filtered
                cardadapt.getFilter().filter(query);
                System.out.println("on query submit: " + query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);

        return super.onCreateOptionsMenu(menu);

//        return true;
    }
}

