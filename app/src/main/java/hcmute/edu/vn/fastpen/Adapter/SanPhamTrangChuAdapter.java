package hcmute.edu.vn.fastpen.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

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

public class SanPhamTrangChuAdapter extends BaseAdapter
{
    private final Context context;
    private final int layout;
    private ArrayList<SanPham> arr_SanPham;

    public SanPhamTrangChuAdapter(Context context, int layout, ArrayList<SanPham> arr_SanPham)
    {
        this.context = context;
        this.layout = layout;
        this.arr_SanPham = arr_SanPham;
    }

    @Override
    public int getCount()
    {
        return arr_SanPham.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    private class ViewHolder
    {
        ImageView imgView_HinhAnhSanPham;
        TextView txtView_TenSanPham;
        TextView txtView_SLDaBan;
        TextView txtView_GiaTien;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        SanPhamTrangChuAdapter.ViewHolder holder;
        if (view == null)
        {
            holder = new SanPhamTrangChuAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.imgView_HinhAnhSanPham = view.findViewById(R.id.imgView_HinhAnhSanPham);
            holder.txtView_TenSanPham = view.findViewById(R.id.txtView_TenSanPham);
            holder.txtView_SLDaBan = view.findViewById(R.id.txtView_SLDaBan);
            holder.txtView_GiaTien = view.findViewById(R.id.txtView_GiaTien);
            view.setTag(holder);
        }
        else
        {
            holder = (SanPhamTrangChuAdapter.ViewHolder) view.getTag();
        }

        final SanPham sp = arr_SanPham.get(i);
        GetImage(holder.imgView_HinhAnhSanPham, sp.getHinhAnh());
        holder.txtView_TenSanPham.setText(sp.getTenSanPham());
        holder.txtView_SLDaBan.setText(String.valueOf(sp.getSoLuongDaBan()));
        holder.txtView_GiaTien.setText(String.valueOf(sp.getGia()));

        return view;
    }

    public void GetImage(ImageView imageView, String ten)
    {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Image/" + ten);
        try {
            File localFile = File.createTempFile("tempfile", ".jpg");
            storageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
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
                            imageView.setImageResource(R.drawable.loading);
                        }
                    });
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}