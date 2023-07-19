package id.arisaditya.ta_monitoring_pratima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ImageSchedule extends AppCompatActivity {

    public static final String DEVICE_ID = "device_id";

    RecyclerView recyclerView;
    AdapterImage adapterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_schedule);

        recyclerView = findViewById(R.id.recycleImage);
        String id = getIntent().getStringExtra(DEVICE_ID);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseRecyclerOptions<ImageModel> options =
                new FirebaseRecyclerOptions.Builder<ImageModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(id + "/ScheduleGambar"), ImageModel.class)
                        .build();

        adapterImage = new AdapterImage(options);
        recyclerView.setAdapter(adapterImage);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterImage.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterImage.stopListening();
    }
}