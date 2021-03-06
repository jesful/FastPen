package hcmute.edu.vn.fastpen.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

import hcmute.edu.vn.fastpen.Model.ChiTietHoaDon;
import hcmute.edu.vn.fastpen.Model.SanPham;
import hcmute.edu.vn.fastpen.R;

public class ChiTietHoaDonAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ChiTietHoaDon> chiTietHoaDonList;
    private SanPham sp;

    public ChiTietHoaDonAdapter(Context context, int layout, List<ChiTietHoaDon> chiTietHoaDonList) {
        this.context=context;
        this.layout=layout;
        this.chiTietHoaDonList=chiTietHoaDonList;
    }

    @Override
    public int getCount() {
        return chiTietHoaDonList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder
    {
        TextView textViewTenBut,textViewSoLuong,textViewTongTien;
        ImageView imageViewBut;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null)
        {
            // ??nh x??? v???i c??c bi???n trong layout
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.textViewTenBut = (TextView) view.findViewById(R.id.textView_TenBut_ChiTietHoaDon);
            holder.textViewSoLuong = (TextView) view.findViewById(R.id.textView_SoLuong_ChiTietHoaDon);
            holder.textViewTongTien = (TextView) view.findViewById(R.id.textView_TongTien_ChiTietHoaDon);
            holder.imageViewBut = (ImageView) view.findViewById(R.id.imageView_But_ChiTietHoaDon);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        final ChiTietHoaDon chiTietHD = chiTietHoaDonList.get(i);
        // L???y th??ng tin s???n ph???m t??? id s???n ph???m
        getSanPham(chiTietHD.getIdSanPham());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Tao ???????ng d???n l???y ???nh trong storage
                StorageReference storageReference = FirebaseStorage.getInstance().getReference("Image/ " + sp.getHinhAnh());
                try {
                    File localFile = File.createTempFile("tempfile", ".jpg");
                    storageReference.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    // G??n ???nh v??o imageView
                                    holder.imageViewBut.setImageBitmap(bitmap);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Th??ng b??o l???y ???nh th???t b???i
                                    Toast.makeText(context.getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                // G??n t??n b??t v??o textViewBut
                holder.textViewTenBut.setText(sp.getTenSanPham());

                //L???y c??c gi?? tr??? s??? l?????ng v?? t???ng ti???n g??n v??o textview
                String soluong =String.valueOf(chiTietHD.getSoLuong());
                String tien =String.valueOf(chiTietHD.getSoLuong() * sp.getGia());
                holder.textViewSoLuong.setText("S??? l?????ng: "+soluong);
                holder.textViewTongTien.setText("T???ng ti???n: "+tien);

            }
        },1500);
        return view;
    }
    public void getSanPham(int idSanPham){
        // t???o ???????ng d???n t???i b???ng SanPham
        DatabaseReference dbref=FirebaseDatabase.getInstance().getReference("SanPham/" + idSanPham);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    // n???u s???n ph???m t???n t???i th?? l???y s???n ph???m ???? t??? firebase
                    sp =snapshot.getValue(SanPham.class);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}
