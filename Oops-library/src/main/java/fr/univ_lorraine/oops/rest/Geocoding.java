package fr.univ_lorraine.oops.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.awt.Desktop;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Geocoding {

    private WebResource service;
    
    public Geocoding(){
         this.initializeService("http://api-adresse.data.gouv.fr/");
    }

    private void initializeService(String url) {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        service = client.resource(
                UriBuilder.fromUri(url).build());
    }

    public String[] callGetCoordinates(String address) {
        String[] latLon = null;

        MultivaluedMap params = new MultivaluedMapImpl();
        params.add("q", address);

        String responseRaw = service
                .queryParams(params)
                .path("search")
                .get(String.class);

        JSONParser parser = new JSONParser();

        try {
            JSONObject o = (JSONObject) parser.parse(responseRaw);
            JSONArray so = (JSONArray) o.get("features");
            if (so.size() != 0) {
                JSONObject o2 = (JSONObject) so.get(0);
                String raw = ((JSONObject) o2.get("geometry")).get("coordinates").toString();
                String reponse = raw.replaceAll("\\[", "").replaceAll("\\]", "");

                String latitude = reponse.split(",")[1];
                String longitude = reponse.split(",")[0];

                latLon = new String[]{latitude, longitude};
            }

        } catch (ParseException ex) {
            Logger.getLogger(Geocoding.class.getName()).log(Level.SEVERE, null, ex);
        }

        return latLon;
    }

    public static void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
