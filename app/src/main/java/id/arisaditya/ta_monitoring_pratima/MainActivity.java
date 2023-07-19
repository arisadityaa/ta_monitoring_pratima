package id.arisaditya.ta_monitoring_pratima;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {

    public static final String DEVICE_ID = "device_id";
    public static String ID_Devices;

    private TextView viewJarakSaatIni;
    private TextView viewJarakSeting;
    private TextView viewDeteksiJarak;
    private TextView viewApi;
    private TextView viewNilaiApi;
    private TextView viewSuhu;
    private TextView viewKelembaban;
    private TextView viewDeteksiKebakaran;
    private TextView viewGerakan;
    private TextView viewDeteksiPergerakan;
    private TextView viewFreeMemory;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String id = getIntent().getStringExtra(DEVICE_ID);
        ID_Devices = id;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refReatime = database.getReference(id + "/Realtime");
        DatabaseReference refDeteksi = database.getReference(id + "/StatusDeteksi");
        DatabaseReference refStorage = database.getReference(id + "/StatusDeteksi/FreeMemory");


        Button btnKontrolPerangkat = findViewById(R.id.btnKontrolPerangkat);

        Button btnScheduleImage = findViewById(R.id.btnScheduleImage);

        viewJarakSaatIni = findViewById(R.id.viewJarakSaatIni);
        viewJarakSeting = findViewById(R.id.viewJarakSeting);
        viewDeteksiJarak = findViewById(R.id.viewDeteksiJarak);
        viewApi = findViewById(R.id.viewApi);
        viewNilaiApi = findViewById(R.id.viewNilaiApi);
        viewSuhu = findViewById(R.id.viewSuhu);
        viewKelembaban = findViewById(R.id.viewKelembaban);
        viewDeteksiKebakaran = findViewById(R.id.viewDeteksiKebakaran);
        viewGerakan = findViewById(R.id.viewGerakan);
        viewDeteksiPergerakan = findViewById(R.id.viewDeteksiPergerakan);

        refReatime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RealtimeData realtimeData = snapshot.getValue(RealtimeData.class);
                String DeteksiApi = realtimeData.getApi();
                String DeteksiJarakPratima = realtimeData.getJarakPratima();
                String DeteksiKelembaban = realtimeData.getKelembaban();
                String DeteksiPergerakan = realtimeData.getPergerakan();
                String DeteksiSuhu = realtimeData.getSuhu();

                int nilaiApi = Integer.parseInt(DeteksiApi);

                viewNilaiApi.setText(DeteksiApi);
                viewApi.setText(nilaiApi<300 ? "Ada Api": "Tidak");
                viewJarakSaatIni.setText(DeteksiJarakPratima + " Cm");
                viewSuhu.setText(DeteksiSuhu + " \u2103");
                viewKelembaban.setText(DeteksiKelembaban + " RH");
                viewGerakan.setText(DeteksiPergerakan.equals("1") ? "Bergerak" : "Tidak");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });

        refDeteksi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SettingDeteksi settingDeteksi = snapshot.getValue(SettingDeteksi.class);
                int JarakSetting = settingDeteksi.getJarakSetting();
                int DeteksiKebakaran = settingDeteksi.getDeteksiKebakaran();
                int DeteksiPergerakan = settingDeteksi.getDeteksiPergerakan();

                viewJarakSeting.setText(String.valueOf(JarakSetting) + " Cm");
                viewDeteksiJarak.setText(JarakSetting > 0 ? "Aktif" : "Mati");
                viewDeteksiKebakaran.setText(DeteksiKebakaran == 1 ? "Aktif" : "Mati");
                viewDeteksiPergerakan.setText(DeteksiPergerakan == 1 ? "Aktif" : "Mati");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });


        btnKontrolPerangkat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, KontrolActivity.class);
                intent.putExtra(KontrolActivity.DEVICE_ID, id);
                startActivity(intent);

            }
        });

        btnScheduleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ImageSchedule.class);
                intent.putExtra(ImageSchedule.DEVICE_ID, id);
                startActivity(intent);
            }
        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if(!task.isSuccessful()){
                            Log.w("error", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        String token = task.getResult();
                        Log.d("Token", token);
                        refDeteksi.child("Token").setValue(token);
                    }
                });

        askNotificationPermission();
    }

    // [START ask_post_notifications]
    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }
    // [END ask_post_notifications]
}