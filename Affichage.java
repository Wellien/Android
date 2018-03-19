package univ.acy.iut.monninj.project.random_anime;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Affichage extends AppCompatActivity {


    private String title=null;
    private String synopsys=null;
    private String link=null;
    private String image=null;

    private int id;
    private String ip;
    private Socket socket;


    private final static String TAG=Affichage.class.getName();

    public void setImage(String image) {
        this.image = image;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setSynopsys(String synopsys) {
        this.synopsys = synopsys;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public String getLink() {
        return link;
    }

    public String getSynopsys() {
        return synopsys;
    }


    public String gettitle() {
        return title;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage);
        Log.i(TAG,"onCreate");
        Intent intent = super.getIntent();
        this.id = intent.getIntExtra("ID",0);
        Connexion connection = new Connexion(id,this);
        
        TextView Titre=super.findViewById(R.id.Titre);
        Titre.setText(gettitle());


    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart");

        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();

    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }

    public void take(String chaine){
        JSONObject anime = null;
        try {
             anime = new JSONObject(chaine);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
           setTitle(anime.getString("title"));
           setSynopsys(synopsys = anime.getString("synopsis"));
           setLink(link = anime.getString("link"));
           setImage(image = anime.getString("image_url"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG,"title = "+this.title);
        Log.i(TAG,"synopsys = "+this.synopsys);
        Log.i(TAG,"link = "+this.link);
        Log.i(TAG,"image = "+this.image);



    }





}
