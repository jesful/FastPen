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

// The adapter class which
// extends RecyclerView Adapter
public class SanPhamChiTietSanPhamAdapter extends RecyclerView.Adapter<SanPhamChiTietSanPhamAdapter.ViewHolder>
{
    // List with String type
    private final ArrayList<SanPham> arr_SanPham;
    private final SanPhamChiTietSanPhamAdapter.OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    // View Holder class which
    // extends RecyclerView.ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final ImageView imgView_HinhAnhSanPham;
        private final TextView txtView_TenSanPham;
        private final TextView txtView_SLDaBan;
        private final TextView txtView_GiaTien;
        private final ProgressBar progressBar_HinhAnhSanPham;
        private final View container;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public ViewHolder(View view)
        {
            super(view);

            // initialise TextView with id
            imgView_HinhAnhSanPham = view.findViewById(R.id.imgView_HinhAnhSanPham);
            txtView_TenSanPham = view.findViewById(R.id.txtView_TenSanPham);
            txtView_SLDaBan = view.findViewById(R.id.txtView_SLDaBan);
            txtView_GiaTien = view.findViewById(R.id.txtView_GiaTien);
            progressBar_HinhAnhSanPham = view.findViewById(R.id.progressBar_HinhAnhSanPham);
            container = view;
        }
    }

    // Constructor for adapter class
    // which takes a list of String type
    public SanPhamChiTietSanPhamAdapter(ArrayList<SanPham> arr_SanPham, OnItemClickListener onItemClickListener)
    {
        this.arr_SanPham = arr_SanPham;
        mOnItemClickListener = onItemClickListener;
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_sanpham_chitietsanpham, parent, false);

        final ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.container.setOnClickListener(new View.OnClickListener()
        {
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
        // Set the text of each item of
        // Recycler view with the list items
        GetImage(holder.imgView_HinhAnhSanPham, arr_SanPham.get(i).getHinhAnh(), holder.progressBar_HinhAnhSanPham);
        holder.txtView_TenSanPham.setText(arr_SanPham.get(i).getTenSanPham());
        holder.txtView_SLDaBan.setText(String.valueOf(arr_SanPham.get(i).getSoLuongDaBan()));
        holder.txtView_GiaTien.setText(String.valueOf(arr_SanPham.get(i).getGia()));
    }

    @Override
    public int getItemCount()
    {
        return arr_SanPham.size();
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
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
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