package fr.zelytra.histeria.managers.npc;


import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.logs.LogType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Skin implements Serializable {

    private String texture;
    private String signature;

    public Skin(String url) {

        try {
            Histeria.log("Doawnloading skin...", LogType.INFO);
            URL target = new URL("https://api.mineskin.org/generate/url");
            HttpURLConnection con = (HttpURLConnection) target.openConnection();

            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setConnectTimeout(1000);
            con.setReadTimeout(30000);

            DataOutputStream out = new DataOutputStream(con.getOutputStream());

            out.writeBytes("url=" + URLEncoder.encode(url, "UTF-8"));
            out.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            if (con.getResponseCode() != 200) return;

            JSONObject output = (JSONObject) (new JSONParser()).parse(reader);
            JSONObject data = (JSONObject) output.get("data");

            JSONObject texture = (JSONObject) data.get("texture");
            this.texture = (String) texture.get("value");
            this.signature = (String) texture.get("signature");

            con.disconnect();
            Histeria.log("Skin downloaded", LogType.INFO);

        } catch (Throwable t) {
            Histeria.log("Failed to download skin from server", LogType.ERROR);
            t.printStackTrace();
        }
    }

    public String getTexture() {
        return texture;
    }

    public String getSignature() {
        return signature;
    }
}
