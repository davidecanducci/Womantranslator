package com.letsdosimpleapps.womantranslator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookSdk;

/**
 * Created by Davide Canducci on 12/11/2015.
 */
public class TruePhraseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_phrase);

        FacebookSdk.sdkInitialize(getApplicationContext());

        Intent intent = getIntent();
        final int position = intent.getIntExtra("idTruePhrase", -1);

        if(position == -1)
        {
            Log.i("Errore TruePhrase","non è arrivata la giusta position");
            finish();
        }

        final String[] arrayTruePhrases = getResources().getStringArray(R.array.array_true_phrases);
        final String[] array_original_women_phrases = getResources().getStringArray(R.array.array_original_women_phrases);

        TextView textView_true_phrase = (TextView) findViewById(R.id.textView_true_phrase);
        textView_true_phrase.setText(arrayTruePhrases[position]);

        Button button_share_with_facebook = (Button) findViewById(R.id.button_share_with_facebook);
        button_share_with_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TruePhraseActivity.this, ShareActivity.class);
                intent.putExtra("womanPhrase", array_original_women_phrases[position]);
                intent.putExtra("truePhrase", arrayTruePhrases[position]);
                startActivity(intent);
            }
        });

//		ShareButton shareButton = (ShareButton) findViewById(R.id.fb_share_button);
//		shareButton.setShareContent(
//				new ShareLinkContent.Builder().setContentUrl(Uri.parse("https://developers.facebook.com")).build());

//        LikeView likeView = (LikeView) findViewById(R.id.likeView);
//        likeView.setLikeViewStyle(LikeView.Style.STANDARD);
//        likeView.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);
//        likeView.setObjectIdAndType(
//                "http://inthecheesefactory.com/blog/understand-android-activity-launchmode/en",
//                LikeView.ObjectType.OPEN_GRAPH);
    }
}