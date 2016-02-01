package com.letsdosimpleapps.womantranslator;

/**
 * Created by Davide Canducci on 12/11/2015.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Michelangelo on 10/11/2015.
 */
public class ShareActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    final ShareDialog shareDialog = new ShareDialog(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        Intent intent = new Intent(getIntent());
        final String womanPhrase = intent.getStringExtra("womanPhrase");
        final String truePhrase = intent.getStringExtra("truePhrase");

        callbackManager = CallbackManager.Factory.create();

        List<String> permissionNeeds = Arrays.asList("publish_actions");

        //this loginManager helps you eliminate adding a LoginButton to your UI
        loginManager = LoginManager.getInstance();

        loginManager.logInWithPublishPermissions(this, permissionNeeds);

        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                sharePhotoToFacebook(womanPhrase, truePhrase);
            }

            @Override
            public void onCancel() {
                System.out.println("onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                System.out.println(exception.toString());
            }
        });
    }

    private void sharePhotoToFacebook(String womanPhrase, String truePhrase) {
       /* Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption("MACASA BUTTAGLIELO IN CULO")
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
*/
//        ShareLinkContent content = new ShareLinkContent.Builder()
//                .setContentTitle(womanPhrase)
//                .setContentDescription("In realtà voleva dire: "+truePhrase)
//                .setContentUrl(Uri.parse("http://camponebbiu2015.altervista.org/womantranslator/index.html"))
//                .setImageUrl(Uri.parse("http://camponebbiu2015.altervista.org/womantranslator/img/icona.png"))
//                .build();
//        ShareApi shareApi = new ShareApi(content);
//        ShareApi.share(content, null);

        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentTitle(womanPhrase)
                .setContentDescription("In realtà voleva dire: "+truePhrase)
                .setContentUrl(Uri.parse("http://camponebbiu2015.altervista.org/womantranslator/index.html"))

                        //.setImageUrl(Uri.parse("android.resource://de.ginkoboy.flashcards/" + R.drawable.logo_flashcards_pro))
                .setImageUrl(Uri.parse("http://camponebbiu2015.altervista.org/womantranslator/img/icona.png"))
                .build();

        shareDialog.show(linkContent);

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
    }

}
