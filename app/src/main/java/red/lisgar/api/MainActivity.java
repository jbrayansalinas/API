package red.lisgar.api;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import red.lisgar.api.entidades.Personajes;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Personajes> listaPersonajes;
    private RequestQueue rq;
    private RecyclerView recyclerMain;
    private AdaptadorPersonajes adaptadorPersonajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaPersonajes=new ArrayList<>();
        rq= Volley.newRequestQueue(this);
        /*for (int f=0;f<20;f++){*/
            cargarPersonaje();
            recyclerMain = findViewById(R.id.reccylerMain);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerMain.setLayoutManager(linearLayoutManager);
            adaptadorPersonajes = new AdaptadorPersonajes();
            recyclerMain.setAdapter(adaptadorPersonajes);
        /*}*/
    }

    private void cargarPersonaje() {
        String url = "https://rickandmortyapi.com/api/character";
        JsonObjectRequest requerimiento = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String valor = response.get("results").toString();
                            JSONArray arreglo = new JSONArray(valor);
                            JSONObject objecto =new JSONObject(arreglo.get(0).toString());
                            String name = objecto.getString("name");
                            String status = objecto.getString("status");
                            String species = objecto.getString("species");
                            String gender = objecto.getString("gender");
                            String image = objecto.getString("image");
                            String urll = objecto.getString("url");
                            String nameOrigin = objecto.getJSONObject("origin").getString("name");
                            String urlOrigin = objecto.getJSONObject("origin").getString("url");
                            String nameLocation = objecto.getJSONObject("location").getString("name");
                            String urllocation = objecto.getJSONObject("location").getString("url");
                            Personajes personajes = new Personajes(name, status, species, gender, nameOrigin, urlOrigin, nameLocation, urllocation, image, urll);
                            listaPersonajes.add(personajes);
                            adaptadorPersonajes.notifyItemRangeInserted(listaPersonajes.size(), 1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        rq.add(requerimiento);
    }
    private class AdaptadorPersonajes extends RecyclerView.Adapter<AdaptadorPersonajes.AdaptadorPersonajesHolder> {

        public class AdaptadorPersonajesHolder extends RecyclerView.ViewHolder {
            TextView nombreTarjeta;
            TextView estadoTarjeta;
            TextView especieTarjeta;
            TextView generoTarjeta;
            TextView locationTarjeta;
            TextView primerAparicionTarjeta;
            ImageView imgTarjeta;
            public AdaptadorPersonajesHolder(@NonNull View itemView) {
                super(itemView);
                nombreTarjeta= itemView.findViewById(R.id.nombreTarjeta);
                estadoTarjeta= itemView.findViewById(R.id.estadoTarjeta);
                especieTarjeta= itemView.findViewById(R.id.especieTarjeta);
                generoTarjeta= itemView.findViewById(R.id.generoTarjeta);
                locationTarjeta= itemView.findViewById(R.id.locationTarjeta);
                primerAparicionTarjeta= itemView.findViewById(R.id.primerAparicionTarjeta);
                imgTarjeta= itemView.findViewById(R.id.imgTarjeta);
            }
            public void imprimir (int position){
                nombreTarjeta.setText(listaPersonajes.get(position).getName());
                estadoTarjeta.setText(listaPersonajes.get(position).getStatus());
                especieTarjeta.setText(listaPersonajes.get(position).getSpecies());
                generoTarjeta.setText(listaPersonajes.get(position).getGender());
                locationTarjeta.setText(listaPersonajes.get(position).getNamelocation());
                primerAparicionTarjeta.setText(listaPersonajes.get(position).getNameorigin());
                recuperarImagen(listaPersonajes.get(position).getImage(), imgTarjeta);
            }
            public void recuperarImagen (String foto, ImageView iv){
                ImageRequest peticion = new ImageRequest(foto,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                iv.setImageBitmap(response);
                            }
                        },
                        0,
                        0,
                        null,
                        null,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                rq.add(peticion);
            }
        }

        @NonNull
        @Override
        public AdaptadorPersonajesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorPersonajesHolder(getLayoutInflater().inflate(R.layout.layout_tarjeta,parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorPersonajesHolder holder, int position) {
            holder.imprimir(position);
        }

        @Override
        public int getItemCount() {
            return listaPersonajes.size();
        }
    }
}