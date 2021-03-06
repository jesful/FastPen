package hcmute.edu.vn.fastpen.Adapter;

import android.app.Dialog;
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

import hcmute.edu.vn.fastpen.Model.Global;
import hcmute.edu.vn.fastpen.Model.GioHang;
import hcmute.edu.vn.fastpen.Model.SanPham;
import hcmute.edu.vn.fastpen.R;

// Adapter class extends RecyclerView Adapter
public class SanPhamGioHangAdapter extends RecyclerView.Adapter<SanPhamGioHangAdapter.MyView>
{
    // Array list GioHang
    private final ArrayList<GioHang> arr_GioHang;
    // Database Reference
    private DatabaseReference dbref;

    // View Holder class extends RecyclerView.ViewHolder
    public static class MyView extends RecyclerView.ViewHolder
    {
        private final ImageView imgView_HinhAnhSanPham_GioHang;
        private final ImageView imgView_GiamSoLuongSanPham_GioHang;
        private final ImageView imgView_TangSoLuongSanPham_GioHang;
        private final TextView txtView_TenSanPham_GioHang;
        private final TextView txtView_GiaTien_GioHang;
        private final TextView txtView_SoLuongSanPham_GioHang;
        private final ProgressBar progressBar_HinhAnhSanPham_GioHang;

        public MyView(View view)
        {
            super(view);

            // Kh???i t???o c??c component trong item c???a recycler view
            imgView_HinhAnhSanPham_GioHang = view.findViewById(R.id.imgView_HinhAnhSanPham_GioHang);
            imgView_GiamSoLuongSanPham_GioHang = view.findViewById(R.id.imgView_GiamSoLuongSanPham_GioHang);
            imgView_TangSoLuongSanPham_GioHang = view.findViewById(R.id.imgView_TangSoLuongSanPham_GioHang);
            txtView_TenSanPham_GioHang = view.findViewById(R.id.txtView_TenSanPham_GioHang);
            txtView_GiaTien_GioHang = view.findViewById(R.id.txtView_GiaTien_GioHang);
            txtView_SoLuongSanPham_GioHang = view.findViewById(R.id.txtView_SoLuongSanPham_GioHang);
            progressBar_HinhAnhSanPham_GioHang = view.findViewById(R.id.progressBar_HinhAnhSanPham_GioHang);
        }
    }

    // Constructor cho adapter tham s??? l?? array GioHang
    public SanPhamGioHangAdapter(ArrayList<GioHang> arr_GioHang)
    {
        this.arr_GioHang = arr_GioHang;
    }

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
        holder.txtView_SoLuongSanPham_GioHang.setText(String.valueOf(arr_GioHang.get(position).getSoLuong()));

        int id = arr_GioHang.get(position).getIdSanPham();
        // L???y d??? li???u tr??n firebase theo ???????ng d???n SanPham/id s???n ph???m
        dbref = FirebaseDatabase.getInstance().getReference("SanPham/" + id);
        // L???y d??? li???u c???a s???n ph???m c?? idSanPham b???ng id ???????c l??u tr??? ????? l???y th??ng tin c???a s???n ph???m
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // L???y d??? li???u s???n ph???m
                SanPham sp = snapshot.getValue(SanPham.class);

                if (sp != null)
                {
                    // Sau khi ???? l???y h???t d??? li???u s???n ph???m th?? set h??nh ???nh s???n ph???m, t??n s???n ph???m, gi?? ti???n
                    GetImage(holder.imgView_HinhAnhSanPham_GioHang,sp.getHinhAnh(),holder.progressBar_HinhAnhSanPham_GioHang);
                    holder.txtView_TenSanPham_GioHang.setText(sp.getTenSanPham());
                    holder.txtView_GiaTien_GioHang.setText(String.valueOf(sp.getGia()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Click v??o s??? l?????ng s???n ph???m
        holder.txtView_SoLuongSanPham_GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // Kh???i t???o dialog
                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_soluongsanpham_giohang);

                // Kh???i t???o component trong dialog
                EditText editTxt_SoLuongSanPham_GioHangDialog = dialog.findViewById(R.id.editTxt_SoLuongSanPham_GioHangDialog);
                TextView txtView_Huy_GioHangDialog = dialog.findViewById(R.id.txtView_Huy_GioHangDialog);
                TextView txtView_DongY_GioHangDialog = dialog.findViewById(R.id.txtView_DongY_GioHangDialog);

                // Set s??? l?????ng s???n ph???m trong gi??? hi???n t???i l??n edit text
                editTxt_SoLuongSanPham_GioHangDialog.setText(String.valueOf(arr_GioHang.get(holder.getAdapterPosition()).getSoLuong()));

                // N???u click h???y th?? dismiss dialog
                txtView_Huy_GioHangDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                // Click ?????ng ?? th?? l??u l???i s??? l?????ng s???n ph???m ???? thay ?????i
                txtView_DongY_GioHangDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        int sl = Integer.parseInt(String.valueOf(editTxt_SoLuongSanPham_GioHangDialog.getText()));
                        if(sl > 0)
                        {
                            // N???u s??? l?????ng s???n ph???m ???????c ghi l???n h??n 0 th?? thay ?????i s??? l?????ng s???n ph???m gi???
                            dbref = FirebaseDatabase.getInstance().getReference("GioHang/" + Global.getTk().getTenTaiKhoan() + "/" + id + "/soLuong");
                            dbref.setValue(sl);
                        }
                        else
                        {
                            // N???u s??? l?????ng s???n ph???m ???????c ghi l?? 0 th?? x??a s???n ph???m kh???i gi???
                            dbref = FirebaseDatabase.getInstance().getReference("GioHang/" + Global.getTk().getTenTaiKhoan() + "/" + id);
                            dbref.removeValue();
                        }

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        // Click v??o s??? gi???m s??? l?????ng s???n ph???m gi??? ??i 1
        holder.imgView_GiamSoLuongSanPham_GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sl = Integer.parseInt(String.valueOf(holder.txtView_SoLuongSanPham_GioHang.getText()));
                sl--;
                if(sl > 0)
                {
                    dbref = FirebaseDatabase.getInstance().getReference("GioHang/" + Global.getTk().getTenTaiKhoan() + "/" + id + "/soLuong");
                    dbref.setValue(sl);
                }
                else
                {
                    // N???u s??? l?????ng s???n ph???m ???????c ghi l?? 0 th?? x??a s???n ph???m kh???i gi???
                    dbref = FirebaseDatabase.getInstance().getReference("GioHang/" + Global.getTk().getTenTaiKhoan() + "/" + id);
                    dbref.removeValue();
                }
            }
        });

        // Click v??o s??? t??ng s??? l?????ng s???n ph???m gi??? th??m 1
        holder.imgView_TangSoLuongSanPham_GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sl = Integer.parseInt(String.valueOf(holder.txtView_SoLuongSanPham_GioHang.getText()));
                sl++;
                dbref = FirebaseDatabase.getInstance().getReference("GioHang/" + Global.getTk().getTenTaiKhoan() + "/" + id + "/soLuong");
                dbref.setValue(sl);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return arr_GioHang.size();
    }

    // H??m set h??nh ???nh s???n ph???m
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
                            // L???y th??nh c??ng th?? set h??nh ???nh l??n v?? kh??ng hi???n th??? progress bar
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
                            // Trong khi ?????i load h??nh th?? hi???n th??? progress bar
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