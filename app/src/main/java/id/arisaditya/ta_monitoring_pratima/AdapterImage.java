package id.arisaditya.ta_monitoring_pratima;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class AdapterImage extends FirebaseRecyclerAdapter<ImageModel, AdapterImage.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterImage(@NonNull FirebaseRecyclerOptions<ImageModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull ImageModel model) {
        holder.viewTanggal.setText(model.getTanggal());
        holder.viewWaktu.setText(model.getWaktu());
        byte[] decodeString = Base64.decode(model.getImage(), Base64.DEFAULT);
        Bitmap decodeBitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
        holder.imgView.setImageBitmap(decodeBitmap);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child( MainActivity.ID_Devices + "/ScheduleGambar")
                        .child(getRef(holder.getAdapterPosition()).getKey()).removeValue();
                Toast.makeText(holder.btnDelete.getContext(), "Gambar Dihapus", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView viewTanggal, viewWaktu;
        ImageView imgView;
        Button btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.imgView);
            viewTanggal = itemView.findViewById(R.id.viewTanggal);
            viewWaktu = itemView.findViewById(R.id.viewWaktu);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
