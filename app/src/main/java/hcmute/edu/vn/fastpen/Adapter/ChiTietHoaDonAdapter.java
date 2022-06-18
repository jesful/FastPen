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
            // Ánh xạ với các biến trong layout
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
        // Lấy thông tin sản phẩm từ id sản phẩm
        getSanPham(chiTietHD.getIdSanPham());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Tao đường dẫn lấy ảnh trong storage
                StorageReference storageReference = FirebaseStorage.getInstance().getReference("Image/ " + sp.getHinhAnh());
                try {
                    File localFile = File.createTempFile("tempfile", ".jpg");
                    storageReference.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    // Gán ảnh vào imageView
                                    holder.imageViewBut.setImageBitmap(bitmap);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Thông báo lấy ảnh thất bại
                                    Toast.makeText(context.getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                // Gán tên bút vào textViewBut
                holder.textViewTenBut.setText(sp.getTenSanPham());

                //Lấy các giá trị số lượng và tổng tiền gán vào textview
                String soluong =String.valueOf(chiTietHD.getSoLuong());
                String tien =String.valueOf(chiTietHD.getSoLuong() * sp.getGia());
                holder.textViewSoLuong.setText("Số lượng: "+soluong);
                holder.textViewTongTien.setText("Tổng tiền: "+tien);

            }
        },1500);
        return view;
    }
    public void getSanPham(int idSanPham){
        // tạo đường dẫn tới bảng SanPham
        DatabaseReference dbref=FirebaseDatabase.getInstance().getReference("SanPham/" + idSanPham);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    // nếu sản phẩm tồn tại thì lấy sản phẩm đó từ firebase
                    sp =snapshot.getValue(SanPham.class);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}
