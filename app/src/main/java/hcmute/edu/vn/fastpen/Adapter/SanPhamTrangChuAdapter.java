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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import hcmute.edu.vn.fastpen.Model.SanPham;
import hcmute.edu.vn.fastpen.R;

// Adapter class extends RecyclerView Adapter
public class SanPhamTrangChuAdapter extends RecyclerView.Adapter<SanPhamTrangChuAdapter.ViewHolder>
{
    // Array list SanPham
    private final ArrayList<SanPham> arr_SanPham;
    private final OnItemClickListener mOnItemClickListener;

    // Tạo interface OnItemClickListener
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    // View Holder class extends RecyclerView.ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final ImageView imgView_HinhAnhSanPham;
        private final TextView txtView_TenSanPham;
        private final TextView txtView_SLDaBan;
        private final TextView txtView_GiaTien;
        private final ProgressBar progressBar_HinhAnhSanPham;
        private final View container;

        public ViewHolder(View view)
        {
            super(view);

            // Khởi tạo các component trong item của recycler view
            imgView_HinhAnhSanPham = view.findViewById(R.id.imgView_HinhAnhSanPham);
            txtView_TenSanPham = view.findViewById(R.id.txtView_TenSanPham);
            txtView_SLDaBan = view.findViewById(R.id.txtView_SLDaBan);
            txtView_GiaTien = view.findViewById(R.id.txtView_GiaTien);
            progressBar_HinhAnhSanPham = view.findViewById(R.id.progressBar_HinhAnhSanPham);
            container = view;
        }
    }

    // Constructor cho adapter tham số là array SanPham và OnItemClickListener
    public SanPhamTrangChuAdapter(ArrayList<SanPham> arr_SanPham, OnItemClickListener onItemClickListener)
    {
        this.arr_SanPham = arr_SanPham;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_sanpham, parent, false);

        final ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.container.setOnClickListener(new View.OnClickListener()
        {
            // Khi click thì lấy được vị trí của item trên adapter để sử dụng cho việc lấy dữ liệu của phần tử trong array
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, viewHolder.getAdapterPosition());
            }
        });

        // return itemView
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i)
    {
        // Set dữ liệu lên view của item
        final SanPham sp = arr_SanPham.get(i);
        GetImage(holder.imgView_HinhAnhSanPham, sp.getHinhAnh(), holder.progressBar_HinhAnhSanPham);
        holder.txtView_TenSanPham.setText(sp.getTenSanPham());
        holder.txtView_SLDaBan.setText(String.valueOf(sp.getSoLuongDaBan()));
        holder.txtView_GiaTien.setText(String.valueOf(sp.getGia()));
    }

    @Override
    // Trả về kích thước array SanPham
    public int getItemCount()
    {
        return arr_SanPham.size();
    }

    // Hàm set hình ảnh sản phẩm
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
                            // Lấy thành công thì set hình ảnh lên và không hiển thị progress bar
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
                            // Trong khi đợi load hình thì hiển thị progress bar
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