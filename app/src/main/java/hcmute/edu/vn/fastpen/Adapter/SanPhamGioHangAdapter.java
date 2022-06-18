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

            // Khởi tạo các component trong item của recycler view
            imgView_HinhAnhSanPham_GioHang = view.findViewById(R.id.imgView_HinhAnhSanPham_GioHang);
            imgView_GiamSoLuongSanPham_GioHang = view.findViewById(R.id.imgView_GiamSoLuongSanPham_GioHang);
            imgView_TangSoLuongSanPham_GioHang = view.findViewById(R.id.imgView_TangSoLuongSanPham_GioHang);
            txtView_TenSanPham_GioHang = view.findViewById(R.id.txtView_TenSanPham_GioHang);
            txtView_GiaTien_GioHang = view.findViewById(R.id.txtView_GiaTien_GioHang);
            txtView_SoLuongSanPham_GioHang = view.findViewById(R.id.txtView_SoLuongSanPham_GioHang);
            progressBar_HinhAnhSanPham_GioHang = view.findViewById(R.id.progressBar_HinhAnhSanPham_GioHang);
        }
    }

    // Constructor cho adapter tham số là array GioHang
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
        // Lấy dữ liệu trên firebase theo đường dẫn SanPham/id sản phẩm
        dbref = FirebaseDatabase.getInstance().getReference("SanPham/" + id);
        // Lấy dữ liệu của sản phẩm có idSanPham bằng id được lưu trữ để lấy thông tin của sản phẩm
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Lấy dữ liệu sản phẩm
                SanPham sp = snapshot.getValue(SanPham.class);

                if (sp != null)
                {
                    // Sau khi đã lấy hết dữ liệu sản phẩm thì set hình ảnh sản phẩm, tên sản phẩm, giá tiền
                    GetImage(holder.imgView_HinhAnhSanPham_GioHang,sp.getHinhAnh(),holder.progressBar_HinhAnhSanPham_GioHang);
                    holder.txtView_TenSanPham_GioHang.setText(sp.getTenSanPham());
                    holder.txtView_GiaTien_GioHang.setText(String.valueOf(sp.getGia()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Click vào số lượng sản phẩm
        holder.txtView_SoLuongSanPham_GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // Khởi tạo dialog
                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_soluongsanpham_giohang);

                // Khởi tạo component trong dialog
                EditText editTxt_SoLuongSanPham_GioHangDialog = dialog.findViewById(R.id.editTxt_SoLuongSanPham_GioHangDialog);
                TextView txtView_Huy_GioHangDialog = dialog.findViewById(R.id.txtView_Huy_GioHangDialog);
                TextView txtView_DongY_GioHangDialog = dialog.findViewById(R.id.txtView_DongY_GioHangDialog);

                // Set số lượng sản phẩm trong giỏ hiện tại lên edit text
                editTxt_SoLuongSanPham_GioHangDialog.setText(String.valueOf(arr_GioHang.get(holder.getAdapterPosition()).getSoLuong()));

                // Nếu click hủy thì dismiss dialog
                txtView_Huy_GioHangDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                // Click đồng ý thì lưu lại số lượng sản phẩm đã thay đổi
                txtView_DongY_GioHangDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        int sl = Integer.parseInt(String.valueOf(editTxt_SoLuongSanPham_GioHangDialog.getText()));
                        if(sl > 0)
                        {
                            // Nếu số lượng sản phẩm được ghi lớn hơn 0 thì thay đổi số lượng sản phẩm giỏ
                            dbref = FirebaseDatabase.getInstance().getReference("GioHang/" + Global.getTk().getTenTaiKhoan() + "/" + id + "/soLuong");
                            dbref.setValue(sl);
                        }
                        else
                        {
                            // Nếu số lượng sản phẩm được ghi là 0 thì xóa sản phẩm khỏi giỏ
                            dbref = FirebaseDatabase.getInstance().getReference("GioHang/" + Global.getTk().getTenTaiKhoan() + "/" + id);
                            dbref.removeValue();
                        }

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        // Click vào sẽ giảm số lượng sản phẩm giỏ đi 1
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
                    // Nếu số lượng sản phẩm được ghi là 0 thì xóa sản phẩm khỏi giỏ
                    dbref = FirebaseDatabase.getInstance().getReference("GioHang/" + Global.getTk().getTenTaiKhoan() + "/" + id);
                    dbref.removeValue();
                }
            }
        });

        // Click vào sẽ tăng số lượng sản phẩm giỏ thêm 1
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