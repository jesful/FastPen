package hcmute.edu.vn.fastpen.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import hcmute.edu.vn.fastpen.Global;
import hcmute.edu.vn.fastpen.Model.BinhLuan;
import hcmute.edu.vn.fastpen.Model.TaiKhoan;
import hcmute.edu.vn.fastpen.R;

// The adapter class which
// extends RecyclerView Adapter
public class BinhLuanChiTietSanPhamAdapter extends RecyclerView.Adapter<BinhLuanChiTietSanPhamAdapter.MyView>
{
    // List with String type
    private final ArrayList<BinhLuan> arr_BinhLuan;

    // View Holder class which
    // extends RecyclerView.ViewHolder
    public static class MyView extends RecyclerView.ViewHolder
    {
        private final TextView txtView_Ten_BinhLuan;
        private final TextView txtView_BaiViet_BinhLuan;
        private final TextView txtView_ThoiGianDangBinhLuan_BinhLuan;
        private final RatingBar ratingBar_SoSaoSanPham_BinhLuan;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);

            // initialise TextView with id
            txtView_Ten_BinhLuan = view.findViewById(R.id.txtView_Ten_BinhLuan);
            txtView_BaiViet_BinhLuan = view.findViewById(R.id.txtView_BaiViet_BinhLuan);
            txtView_ThoiGianDangBinhLuan_BinhLuan = view.findViewById(R.id.txtView_ThoiGianDangBinhLuan_BinhLuan);
            ratingBar_SoSaoSanPham_BinhLuan = view.findViewById(R.id.ratingBar_SoSaoSanPham_BinhLuan);
        }
    }

    // Constructor for adapter class
    // which takes a list of String type
    public BinhLuanChiTietSanPhamAdapter(ArrayList<BinhLuan> arr_BinhLuan)
    {
        this.arr_BinhLuan = arr_BinhLuan;
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_binhluan_chitietsanpham, parent, false);

        // return itemView
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyView holder, int i)
    {
        // Lấy dữ liệu trên firebase
        // Database Reference
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("TaiKhoan/" + arr_BinhLuan.get(i).getTenTaiKhoan());
        dbref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Lấy dữ liệu của tài khoản
                TaiKhoan tk = snapshot.getValue(TaiKhoan.class);

                if(tk != null)
                {
                    // Sau khi đã lấy hết dữ liệu tài khoản thì
                    holder.txtView_Ten_BinhLuan.setText(tk.getTen());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Set the text of each item of
        // Recycler view with the list items
        holder.txtView_BaiViet_BinhLuan.setText(String.valueOf(arr_BinhLuan.get(i).getBaiViet()));
        holder.txtView_ThoiGianDangBinhLuan_BinhLuan.setText(arr_BinhLuan.get(i).getThoiGianDangBinhLuan());
        holder.ratingBar_SoSaoSanPham_BinhLuan.setRating(arr_BinhLuan.get(i).getSoSao());
    }

    @Override
    public int getItemCount()
    {
        return arr_BinhLuan.size();
    }
}