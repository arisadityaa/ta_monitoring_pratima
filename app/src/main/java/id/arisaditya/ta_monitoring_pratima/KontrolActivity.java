package id.arisaditya.ta_monitoring_pratima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class KontrolActivity extends AppCompatActivity {

    public static final String DEVICE_ID = "device_id";
    private TextView viewSetSchedule;
    private TextView viewSchedule;
    private TextView viewSetJarakKontrol;
    private TextView viewStatusKebakaran;
    private TextView viewStatusPergerakan;


    private Button btnDeteksiKebakaran;
    private Button btnDeteksiPergerakan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontrol);

        String id = getIntent().getStringExtra(DEVICE_ID);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refKontrol = database.getReference(id + "/StatusDeteksi");

        viewSchedule = findViewById(R.id.viewSchedule);
        viewSetJarakKontrol = findViewById(R.id.viewSetJarakKontrol);
        viewSetSchedule = findViewById(R.id.viewSetSchedule);
        viewStatusKebakaran = findViewById(R.id.viewStatusKebakaran);
        viewStatusPergerakan = findViewById(R.id.viewStatusPergerakan);

        btnDeteksiKebakaran = findViewById(R.id.btnDeteksiKebakaran);
        Button btnResetJarak = findViewById(R.id.btnResetJarak);
        Button btnSetingJarak = findViewById(R.id.btnSetingJarak);
        btnDeteksiPergerakan = findViewById(R.id.btnDeteksiPergerakan);
        Button btnSetSchedule = findViewById(R.id.btnSetSchedule);

        refKontrol.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SettingDeteksi settingDeteksi = snapshot.getValue(SettingDeteksi.class);
                int deteksiPergerakan = settingDeteksi.getDeteksiPergerakan();
                int deteksiKebakaran = settingDeteksi.getDeteksiKebakaran();
                int jarakSetting = settingDeteksi.getJarakSetting();
                int scheduleCapture = settingDeteksi.getScheduleCapture();


                viewSetJarakKontrol.setText(String.valueOf(jarakSetting) + " CM");
                viewSchedule.setText(String.valueOf(scheduleCapture)+" Menit");
                viewStatusKebakaran.setText(deteksiKebakaran == 1 ? "Aktif" : "Mati");
                viewStatusPergerakan.setText(deteksiPergerakan == 1 ? "Aktif" : "Mati");

                btnDeteksiPergerakan.setText(deteksiPergerakan==1 ? "Matikan Deteksi Pergerakan": "Hidupkan Deteksi Pergerakan");

                btnDeteksiPergerakan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refKontrol.child("DeteksiPergerakan").setValue(deteksiPergerakan==1? 0 : 1);
                    }
                });

                btnDeteksiKebakaran.setText(deteksiKebakaran == 1? "Matikan Deteksi Kebakaran":"Hidupkan Deteksi Kebakaran");
                btnDeteksiKebakaran.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refKontrol.child("DeteksiKebakaran").setValue(deteksiKebakaran == 1? 0 : 1);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Spinner spinner = findViewById(R.id.waktuSchedule);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.schedule, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long duration) {

                String selectedItem  = adapterView.getItemAtPosition(position).toString();
                viewSetSchedule.setText(selectedItem);
//                Toast.makeText(getApplicationContext(),selectedItem,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnResetJarak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refKontrol.child("BtnReset").setValue(1);
            }
        });
        btnSetingJarak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refKontrol.child("BtnSetting").setValue(1);
            }
        });

        btnSetSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int setSchedule = Integer.parseInt(viewSetSchedule.getText().toString());
                refKontrol.child("ScheduleCapture").setValue(setSchedule);
            }
        });
    }
}