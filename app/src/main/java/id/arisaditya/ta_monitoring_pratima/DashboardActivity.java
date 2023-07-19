package id.arisaditya.ta_monitoring_pratima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    private TextView viewSDStatus1;
    private TextView viewSDStatus2;
    private TextView viewSDMemory1;
    private TextView viewSDMemory2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refDevice1 = database.getReference("1/StatusDeteksi");
        DatabaseReference refDevice2 = database.getReference("2/StatusDeteksi");


        Button btnDevice1 = findViewById(R.id.btnDevice1);
        Button btnDevice2 = findViewById(R.id.btnDevice2);

        viewSDStatus1 = findViewById(R.id.viewSDStatus1);
        viewSDStatus2 = findViewById(R.id.viewSDStatus2);
        viewSDMemory1 = findViewById(R.id.viewSDMemory1);
        viewSDMemory2 = findViewById(R.id.viewSDMemory2);


        refDevice1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SettingDeteksi settingDeteksi = snapshot.getValue(SettingDeteksi.class);
                Integer storageMemory = settingDeteksi.getStorageMemory();
                Integer memoryUsed = settingDeteksi.getMemoryUsed();
                Integer freeMemory = settingDeteksi.getFreeMemory();
                if(storageMemory>0){
                    viewSDStatus1.setText("Available (" + storageMemory.toString() + "MB)");
                    viewSDMemory1.setText(memoryUsed.toString() + "MB Used (" + freeMemory.toString() + "MB Free)");
                }else{
                    viewSDStatus1.setText("No SD Card");
                    viewSDMemory1.setText("None");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        refDevice2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SettingDeteksi settingDeteksi2 = snapshot.getValue(SettingDeteksi.class);
                Integer storageMemory2 = settingDeteksi2.getStorageMemory();
                Integer memoryUsed2 = settingDeteksi2.getMemoryUsed();
                Integer freeMemory2 = settingDeteksi2.getFreeMemory();
                if(storageMemory2>0){
                    viewSDStatus2.setText("Available (" + storageMemory2.toString() + "MB)");
                    viewSDMemory2.setText(memoryUsed2.toString() + "MB Used (" + freeMemory2.toString() + "MB Free)");
                }else{
                    viewSDStatus2.setText("No SD Card");
                    viewSDMemory2.setText("None");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnDevice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(DashboardActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.DEVICE_ID, "1");
                startActivity(intent);
            }
        });

        btnDevice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(DashboardActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.DEVICE_ID, "2");
                startActivity(intent);
            }
        });
    }
}