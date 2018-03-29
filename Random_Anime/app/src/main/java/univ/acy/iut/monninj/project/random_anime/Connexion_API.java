package univ.acy.iut.monninj.project.random_anime;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class Connexion_API {


    private URL url;
    private HttpURLConnection connection = null;

    public Connexion_API(int id) {

        try {
            this.url = new URL("http://api.jikan.me/anime/" + id);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            this.connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        this.connection.setRequestProperty("Accept", "application/vnd.api+json");
        this.connection.setRequestProperty("Content-Type", "application/vnd.api+json");
        this.connection.setDoOutput(true);

        BufferedReader read = null;
        try {
            read = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        String line = null;
        String chaine = null;
        try {
            while ((line = read.readLine()) != null) {

                System.out.println(line);
                chaine = line;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.connection.disconnect();
        JSONObject anime = null;
        JSONObject envoie = null;
        try {
            anime = new JSONObject(chaine);
            envoie = new JSONObject();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            envoie.put("link", anime.get("link_canonical"));
            envoie.put("title", anime.get("title"));
            envoie.put("synopsis", anime.get("synopsis"));
            envoie.put("image_url", anime.get("image_url"));
        } catch (JSONException e) {

            e.printStackTrace();
        }

    }



}
