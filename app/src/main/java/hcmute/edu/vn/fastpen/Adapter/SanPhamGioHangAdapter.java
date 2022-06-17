package hcmute.edu.vn.fastpen.Adapter;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import hcmute.edu.vn.fastpen.Model.GioHang;
import hcmute.edu.vn.fastpen.Model.SanPham;
import hcmute.edu.vn.fastpen.R;

// The adapter class which
// extends RecyclerView Adapter
public class SanPhamGioHangAdapter extends RecyclerView.Adapter<SanPhamGioHangAdapter.MyView>
{
    // List with String type
    private final ArrayList<GioHang> arr_GioHang;
    // Database Reference
    private DatabaseReference dbref;

    // View Holder class which
    // extends RecyclerView.ViewHolder
    public static class MyView extends RecyclerView.ViewHolder
    {
        private final ImageView imgView_HinhAnhSanPham_GioHang;
        private final ImageView imgView_GiamSoLuongSanPham_GioHang;
        private final ImageView imgView_TangSoLuongSanPham_GioHang;
        private final TextView txtView_TenSanPham_GioHang;
        private final TextView txtView_GiaTien_GioHang;
        private final TextView txtView_SoLuongSanPham_GioHang;
        private final ProgressBar progressBar_HinhAnhSanPham_GioHang;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);

            // initialise TextView with id
            imgView_HinhAnhSanPham_GioHang = view.findViewById(R.id.imgView_HinhAnhSanPham_GioHang);
            imgView_GiamSoLuongSanPham_GioHang = view.findViewById(R.id.imgView_GiamSoLuongSanPham_GioHang);
            imgView_TangSoLuongSanPham_GioHang = view.findViewById(R.id.imgView_TangSoLuongSanPham_GioHang);
            txtView_TenSanPham_GioHang = view.findViewById(R.id.txtView_TenSanPham_GioHang);
            txtView_GiaTien_GioHang = view.findViewById(R.id.txtView_GiaTien_GioHang);
            txtView_SoLuongSanPham_GioHang = view.findViewById(R.id.txtView_SoLuongSanPham_GioHang);
            progressBar_HinhAnhSanPham_GioHang = view.findViewById(R.id.progressBar_HinhAnhSanPham_GioHang);
        }
    }

    // Constructor for adapter class
    // which takes a list of String type
    public SanPhamGioHangAdapter(ArrayList<GioHang> arr_GioHang)
    {
        this.arr_GioHang = arr_GioHang;
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sanpham_giohang, parent, false);

        // return itemView
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyView holder, int position)
    {
        // Set the text of each item of
        // Recycler view with the list items
        holder.txtView_SoLuongSanPham_GioHang.setText(String.valueOf(arr_GioHang.get(position).getSoLuong()));

        int id = arr_GioHang.get(position).getIdSanPham();
        dbref = FirebaseDatabase.getInstance().getReference("SanPham/" + id);
        // Lấy dữ liệu của sản phẩm có idSanPham bằng id được lưu trữ để lấy thông tin của sản phẩm
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                SanPham sp = snapshot.getValue(SanPham.class);

                if (sp != null)
                {
                    GetImage(holder.imgView_HinhAnhSanPham_GioHang,sp.getHinhAnh(),holder.progressBar_HinhAnhSanPham_GioHang);
                    holder.txtView_TenSanPham_GioHang.setText(sp.getTenSanPham());
                    holder.txtView_GiaTien_GioHang.setText(String.valueOf(sp.getGia()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.imgView_GiamSoLuongSanPham_GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sl = Integer.parseInt(String.valueOf(holder.txtView_SoLuongSanPham_GioHang.getText()));
                sl--;
                if(sl > 0)
                {
                    dbref = FirebaseDatabase.getInstance().getReference("GioHang/" + Global.getTenTaiKhoan() + "/" + id + "/soLuong");
                    dbref.setValue(sl);
                }
                else
                {
                    dbref = FirebaseDatabase.getInstance().getReference("GioHang/" + Global.getTenTaiKhoan() + "/" + id);
                    dbref.removeValue();
                }
            }
        });

        holder.imgView_TangSoLuongSanPham_GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sl = Integer.parseInt(String.valueOf(holder.txtView_SoLuongSanPham_GioHang.getText()));
                sl++;
                dbref = FirebaseDatabase.getInstance().getReference("GioHang/" + Global.getTenTaiKhoan() + "/" + id + "/soLuong");
                dbref.setValue(sl);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return arr_GioHang.size();
    }

    public void GetImage(ImageView imageView, String ten, ProgressBar progressBar)
    {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Image/" + ten);
        try {
            File localFile = File.createTempFile("tempfile", ".jpg");
            storageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot)
                        {
                            progressBar.setVisibility(View.GONE);
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            imageView.setImageBitmap(bitmap);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onProgress(@NonNull FileDownloadTask.TaskSnapshot snapshot) {
                            progressBar.setVisibility(View.VISIBLE);
                            imageView.setImageResource(R.drawable.plain_white_background_211387);
                        }
                    });
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}