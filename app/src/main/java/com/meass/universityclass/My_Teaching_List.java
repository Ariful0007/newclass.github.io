package com.meass.universityclass;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

public class My_Teaching_List extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    LottieAnimationView empty_cart;
    DocumentReference documentReference;
    RecyclerView recyclerView;
    ClassAdapter getDataAdapter1;
    List<Classs_Model> getList;
    String url;

    FirebaseUser firebaseUser;
    KProgressHUD progressHUD;
    String cus_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__teaching__list);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("My Teaching");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        getList = new ArrayList<>();
        getDataAdapter1 = new ClassAdapter(getList,"as");
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference =      firebaseFirestore.collection("My_Teach")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("List")
                .document();
        recyclerView = findViewById(R.id.blog_list_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(My_Teaching_List.this));
        recyclerView.setAdapter(getDataAdapter1);
        reciveData();


    }

    private void reciveData() {

        firebaseFirestore.collection("My_Teach")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("List")
               .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange ds : queryDocumentSnapshots.getDocumentChanges()) {
                    if (ds.getType() == DocumentChange.Type.ADDED) {

                 /*String first;
                 first = ds.getDocument().getString("name");
                 Toast.makeText(MainActivity2.this, "" + first, Toast.LENGTH_SHORT).show();*/
                        Classs_Model get = ds.getDocument().toObject(Classs_Model.class);
                        getList.add(get);
                        getDataAdapter1.notifyDataSetChanged();
                    }

                }
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(getApplicationContext(), HomeACTIVITY.class));

        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeACTIVITY.class));
    }
}