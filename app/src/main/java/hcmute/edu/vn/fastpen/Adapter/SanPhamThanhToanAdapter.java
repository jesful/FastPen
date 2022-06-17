package hcmute.edu.vn.fastpen.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import hcmute.edu.vn.fastpen.Model.GioHang;
import hcmute.edu.vn.fastpen.Model.SanPham;
import hcmute.edu.vn.fastpen.R;

// The adapter class which
// extends RecyclerView Adapter
public class SanPhamThanhToanAdapter extends RecyclerView.Adapter<SanPhamThanhToanAdapter.MyView>
{
    // List with String type
    private final ArrayList<GioHang> arr_GioHang;

    // View Holder class which
    // extends RecyclerView.ViewHolder
    public static class MyView extends RecyclerView.ViewHolder
    {
        private final ImageView imgView_HinhAnhSanPham_ThanhToan;
        private final TextView txtView_TenSanPham_ThanhToan;
        private final TextView txtView_SoLuongSanPham_ThanhToan;
        private final TextView txtView_GiaTien_ThanhToan;
        private final ProgressBar progressBar_HinhAnhSanPham_ThanhToan;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);

            // initialise TextView with id
            imgView_HinhAnhSanPham_ThanhToan = view.findViewById(R.id.imgView_HinhAnhSanPham_ThanhToan);
            txtView_TenSanPham_ThanhToan = view.findViewById(R.id.txtView_TenSanPham_ThanhToan);
            txtView_SoLuongSanPham_ThanhToan = view.findViewById(R.id.txtView_SoLuongSanPham_ThanhToan);
            txtView_GiaTien_ThanhToan = view.findViewById(R.id.txtView_GiaTien_ThanhToan);
            progressBar_HinhAnhSanPham_ThanhToan = view.findViewById(R.id.progressBar_HinhAnhSanPham_ThanhToan);
        }
    }

    // Constructor for adapter class
    // which takes a list of String type
    public SanPhamThanhToanAdapter(ArrayList<GioHang> arr_GioHang)
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sanpham_thanhtoan, parent, false);

        // return itemView
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyView holder, int position)
    {
        // Set the text of each item of
        // Recycler view with the list items
        holder.txtView_SoLuongSanPham_ThanhToan.setText(String.valueOf(arr_GioHang.get(position).getSoLuong()));

        int id = arr_GioHang.get(position).getIdSanPham();
        // Database Reference
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("SanPham/" + id);
        // Lấy dữ liệu của sản phẩm có idSanPham bằng id được lưu trữ để lấy thông tin của sản phẩm
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                SanPham sp = snapshot.getValue(SanPham.class);

                if (sp != null)
                {
                    GetImage(holder.imgView_HinhAnhSanPham_ThanhToan,sp.getHinhAnh(),holder.progressBar_HinhAnhSanPham_ThanhToan);
                    holder.txtView_TenSanPham_ThanhToan.setText(sp.getTenSanPham());
                    holder.txtView_GiaTien_ThanhToan.setText(String.valueOf(sp.getGia()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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