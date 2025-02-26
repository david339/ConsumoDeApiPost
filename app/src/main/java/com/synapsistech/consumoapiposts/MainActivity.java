package com.synapsistech.consumoapiposts;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.synapsistech.consumoapiposts.Interface.JsonPlaceHolderApi;
import com.synapsistech.consumoapiposts.Model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView mJsontxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mJsontxtView = findViewById(R.id.jsonText);

        getPost();
    }


    private void getPost(){
        /* Instancia de retrofit*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        /*Llamada a la interfaz, metodo create*/
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        /* Llamado al listado del objeto post y al metodo en la interfaz*/
        Call<List<Post>> call = jsonPlaceHolderApi.getPost();
        /* Call back en caso de un error y si es cumplido comunica con los gets*/
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()){
                    mJsontxtView.setText("Codigo: "+response.code());
                    return;
                }

                List<Post> postList = response.body();

                for(Post post: postList){
                    String content = "";
                    //sumando datos en un string
                    content += "userId: "+ post.getUserId() + "\n";
                    content += "id: "+ post.getId() + "\n";
                    content += "title: "+ post.getTitle() + "\n";
                    content += "body: "+ post.getBody() + "\n\n";
                    mJsontxtView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                mJsontxtView.setText(t.getMessage());
            }
        });
    }
}