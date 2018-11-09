package semanaquince.conteo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Conteo extends AppCompatActivity {

    Button btnFirmar1, btnFirmar2;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteo);

        btnFirmar1 = findViewById(R.id.btn_firmar1);
        btnFirmar2 = findViewById(R.id.btn_firmar2);

        db = FirebaseDatabase.getInstance();

        btnFirmar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.getReference().child("firmas").child("peticion1").push().setValue("F");
            }
        });

        btnFirmar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.getReference().child("firmas").child("peticion2").push().setValue("V");
            }
        });

        db.getReference().child("firmas").child("peticion1")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String firmas = "FIRMAR ("+dataSnapshot.getChildrenCount()+")";
                btnFirmar1.setText(firmas);

                //cada dato es un string
                for( DataSnapshot dato: dataSnapshot.getChildren() ){
                    Log.e("CLAVE", ""+dato.getKey());
                    Log.e("VALOR", ""+dato.getValue());

                    String valor = dato.getValue(String.class);
                    Log.e("VALOR TRANSFORMADO", ""+valor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        db.getReference().child("firmas").child("peticion2")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String firmas = "FIRMAR ("+dataSnapshot.getChildrenCount()+")";
                        btnFirmar2.setText(firmas);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
