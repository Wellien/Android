package univ.acy.iut.monninj.project.random_anime;

import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Created by monninj on 13/03/18.
 */

public class Connexion extends Thread{





        private int id;
        private String ip;
        private Socket socket;
        private String anime;
        private Affichage affichage;
        private final static String TAG=Connexion.class.getName();


        public Connexion(int id,Affichage a){

            this.id=id;
            this.ip="10.102.76.3";
            this.affichage=a;
            this.socket=new Socket();
            super.start();

        }

    @Override
    public void run() {
        super.run();
        Inet4Address ipsrv=null;
        try {
            ipsrv = (Inet4Address) InetAddress.getByName(this.ip);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        InetSocketAddress endpoint=new InetSocketAddress(ipsrv, 8080);
        try {

            this.socket.connect(endpoint);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            write(String.valueOf(this.id).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] response = this.read();
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String anime = new String(response);
        Log.i(TAG,"anime = "+anime);
        this.affichage.take(anime);
        this.affichage.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                TextView Titre = ;


            }
        });

    }
    public void write(byte[] request) throws IOException {

            OutputStream outputStream= this.socket.getOutputStream();
            outputStream.write(request);
            outputStream.flush();

        }

        public byte[] read(){

            byte[] response=new byte [100000];
            try {
                this.socket.getInputStream().read(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }


}
