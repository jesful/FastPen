package hcmute.edu.vn.fastpen.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import hcmute.edu.vn.fastpen.Adapter.BinhLuanChiTietSanPhamAdapter;
import hcmute.edu.vn.fastpen.Adapter.SanPhamChiTietSanPhamAdapter;
import hcmute.edu.vn.fastpen.Global;
import hcmute.edu.vn.fastpen.Model.BinhLuan;
import hcmute.edu.vn.fastpen.Model.DanhMuc;
import hcmute.edu.vn.fastpen.Model.GioHang;
import hcmute.edu.vn.fastpen.Model.SanPham;
import hcmute.edu.vn.fastpen.Model.ThuongHieu;
import hcmute.edu.vn.fastpen.R;

public class ChiTietSanPhamActivity extends AppCompatActivity
{
    private ImageView imgView_HinhAnhSanPham_ChiTietSanPham, imgView_HinhAnhThuongHieu_ChiTietSanPham;
    private TextView txtView_TenSanPham_ChiTietSanPham, txtView_SLBinhLuan_ChiTietSanPham, txtView_SLDaBan_ChiTietSanPham, txtView_SlHangConLai_ChiTietSanPham, txtView_Gia_ChiTietSanPham, txtView_TenDanhMuc_ChiTietSanPham, txtView_TenThuongHieu_ChiTietSanPham, txtView_MoTa_ChiTietSanPham, txtView_TenThuongHieu1_ChiTietSanPham, txtView_SLBinhLuan1_ChiTietSanPham, txtView_SoSaoSanPham_ChiTietSanPham, txtView_SoSaoSanPham1_ChiTietSanPham;
    private RatingBar ratingBar_SoSaoSanPham_ChiTietSanPham, ratingBar_SoSaoSanPham1_ChiTietSanPham;
    private ProgressBar progressBar_HinhAnhSanPham_ChiTietSanPham, progressBar_HinhAnhThuongHieu_ChiTietSanPham;
    // Database Reference
    private DatabaseReference dbref;
    // Recycler View object
    private RecyclerView recyclerView_SanPhamCungDanhMuc_ChiTietSanPham, recyclerView_SanPhamCungThuongHieu_ChiTietSanPham, recyclerView_BinhLuan_ChiTietSanPham;
    // Array list for recycler view data source
    private ArrayList<SanPham> arr_SanPhamCungDanhMuc, arr_SanPhamCungThuongHieu;
    private ArrayList<BinhLuan> arr_BinhLuan;
    private ArrayList<GioHang> arr_GioHang;
    // Layout Manager
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    // Adapter class object
    private SanPhamChiTietSanPhamAdapter sanPhamChiTietSanPhamAdapter;
    private BinhLuanChiTietSanPhamAdapter binhLuanChiTietSanPhamAdapter;
    // Linear Layout Manager
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        // Click vào icon mũi tên để quay về trang trước đó
        ImageView imgView_QuayVe_ChiTietSanPham = findViewById(R.id.imgView_QuayVe_ChiTietSanPham);
        imgView_QuayVe_ChiTietSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Click vào hình icon giỏ hàng để chuyển sang trang giỏ hàng
        ImageView imgView_GioHang_ChiTietSanPham = findViewById(R.id.imgView_GioHang_ChiTietSanPham);
        imgView_GioHang_ChiTietSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietSanPhamActivity.this, GioHangActivity.class);
                startActivity(intent);
            }
        });

        // Array list của sản phẩm có cùng danh mục với sản phẩm đang được hiển thị thông tin chi tiết
        arr_SanPhamCungDanhMuc = new ArrayList<>();
        // Array list của sản phẩm có cùng thương hiệu với sản phẩm đang được hiển thị thông tin chi tiết
        arr_SanPhamCungThuongHieu = new ArrayList<>();
        // Array list của bình luận của sản phẩm đang được hiện thị thông tin chi tiết
        arr_BinhLuan = new ArrayList<>();
        // Array list của các sản phẩm trong giỏ hàng dùng cho việc thêm hàng vào giỏ
        arr_GioHang = new ArrayList<>();

        // Lấy id sản phẩm đã lưu trữ bằng getExtra
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);

        GetDataChiTietSanPham(id);

        LinearLayout linearLayout_XemTatCaBinhLuan_ChiTietSanPham = findViewById(R.id.linearLayout_XemTatCaBinhLuan_ChiTietSanPham);
        linearLayout_XemTatCaBinhLuan_ChiTietSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietSanPhamActivity.this, TatCaBinhLuanActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        LinearLayout linearLayout_ThemHangVaoGio_ChiTietSanPham = findViewById(R.id.linearLayout_ThemHangVaoGio_ChiTietSanPham);
        linearLayout_ThemHangVaoGio_ChiTietSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ThemHangVaoGio(id);
            }
        });
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

    private void GetDataChiTietSanPham(int id)
    {
        // Hình ảnh sản phẩm
        imgView_HinhAnhSanPham_ChiTietSanPham = findViewById(R.id.imgView_HinhAnhSanPham_ChiTietSanPham);
        // Progress Bar chờ đợi trong lúc load hình ảnh sản phẩm
        progressBar_HinhAnhSanPham_ChiTietSanPham = findViewById(R.id.progressBar_HinhAnhSanPham_ChiTietSanPham);
        // Tên sản phẩm
        txtView_TenSanPham_ChiTietSanPham = findViewById(R.id.txtView_TenSanPham_ChiTietSanPham);
        // Số lượng bình luận của khách hàng đối với sản phẩm
        txtView_SLBinhLuan_ChiTietSanPham = findViewById(R.id.txtView_SLBinhLuan_ChiTietSanPham);
        // Số lượng sản phẩm đã bán được
        txtView_SLDaBan_ChiTietSanPham = findViewById(R.id.txtView_SLDaBan_ChiTietSanPham);
        // Số lượng sản phẩm còn lại
        txtView_SlHangConLai_ChiTietSanPham = findViewById(R.id.txtView_SlHangConLai_ChiTietSanPham);
        // Giá của sản phẩm
        txtView_Gia_ChiTietSanPham = findViewById(R.id.txtView_Gia_ChiTietSanPham);
        // Danh mục của sản phẩm
        txtView_TenDanhMuc_ChiTietSanPham = findViewById(R.id.txtView_TenDanhMuc_ChiTietSanPham);
        // Thương hiệu của sản phẩm
        txtView_TenThuongHieu_ChiTietSanPham = findViewById(R.id.txtView_TenThuongHieu_ChiTietSanPham);
        // Hình ảnh thương hiệu của sản phẩm
        imgView_HinhAnhThuongHieu_ChiTietSanPham = findViewById(R.id.imgView_HinhAnhThuongHieu_ChiTietSanPham);
        // Progress Bar chờ đợi trong lúc load hình ảnh thương hiệu
        progressBar_HinhAnhThuongHieu_ChiTietSanPham = findViewById(R.id.progressBar_HinhAnhThuongHieu_ChiTietSanPham);
        // Mô tả thêm về sản phẩm
        txtView_MoTa_ChiTietSanPham = findViewById(R.id.txtView_MoTa_ChiTietSanPham);
        // Thương hiệu của sản phẩm trong phần các sản phẩm cùng thương hiệu
        txtView_TenThuongHieu1_ChiTietSanPham = findViewById(R.id.txtView_TenThuongHieu1_ChiTietSanPham);
        // Số lượng bình luận của khách hàng đối với sản phẩm trong phần bình luận
        txtView_SLBinhLuan1_ChiTietSanPham = findViewById(R.id.txtView_SLBinhLuan1_ChiTietSanPham);
        // Tổng số sao đánh giá của khách bình luận trong phần bình luận
        ratingBar_SoSaoSanPham_ChiTietSanPham = findViewById(R.id.ratingBar_SoSaoSanPham_ChiTietSanPham);
        // Tổng số sao đánh giá của khách bình luận trong phần bình luận
        ratingBar_SoSaoSanPham1_ChiTietSanPham = findViewById(R.id.ratingBar_SoSaoSanPham1_ChiTietSanPham);
        // Tổng số sao đánh giá hiển thị bằng số của khách bình luận trong phần bình luận
        txtView_SoSaoSanPham_ChiTietSanPham = findViewById(R.id.txtView_SoSaoSanPham_ChiTietSanPham);
        // Tổng số sao đánh giá hiển thị bằng số của khách bình luận trong phần bình luận
        txtView_SoSaoSanPham1_ChiTietSanPham = findViewById(R.id.txtView_SoSaoSanPham1_ChiTietSanPham);

        dbref = FirebaseDatabase.getInstance().getReference("SanPham/" + id);
        // Lấy dữ liệu của các sản phẩm có idSanPham bằng id được lưu trữ để lấy đúng thông tin của sản phẩm được click vào
        dbref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                SanPham sp = snapshot.getValue(SanPham.class);

                if(sp != null)
                {
                    GetImage(imgView_HinhAnhSanPham_ChiTietSanPham,sp.getHinhAnh(),progressBar_HinhAnhSanPham_ChiTietSanPham);
                    txtView_TenSanPham_ChiTietSanPham.setText(sp.getTenSanPham());
                    txtView_SLDaBan_ChiTietSanPham.setText(String.valueOf(sp.getSoLuongDaBan()));
                    txtView_SlHangConLai_ChiTietSanPham.setText(String.valueOf(sp.getSoLuong()));
                    txtView_Gia_ChiTietSanPham.setText(String.valueOf(sp.getGia()));
                    txtView_MoTa_ChiTietSanPham.setText(sp.getMoTa());

                    dbref = FirebaseDatabase.getInstance().getReference("DanhMuc/" + sp.getIdDanhMuc());
                    dbref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            DanhMuc dm = snapshot.getValue(DanhMuc.class);

                            if (dm != null) {
                                txtView_TenDanhMuc_ChiTietSanPham.setText(dm.getTenDanhMuc());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    dbref = FirebaseDatabase.getInstance().getReference("ThuongHieu/" + sp.getIdThuongHieu());
                    dbref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ThuongHieu th = snapshot.getValue(ThuongHieu.class);

                            if (th != null) {
                                GetImage(imgView_HinhAnhThuongHieu_ChiTietSanPham, th.getHinhAnhThuongHieu(), progressBar_HinhAnhThuongHieu_ChiTietSanPham);
                                txtView_TenThuongHieu_ChiTietSanPham.setText(th.getTenThuongHieu());
                                txtView_TenThuongHieu1_ChiTietSanPham.setText(th.getTenThuongHieu());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    // Lấy dữ liệu sản phẩm cùng danh mục và đổ lên recycler view
                    // initialisation with id's
                    recyclerView_SanPhamCungDanhMuc_ChiTietSanPham = findViewById(R.id.recyclerView_SanPhamCungDanhMuc_ChiTietSanPham);
                    recyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

                    // Set LayoutManager on Recycler View
                    recyclerView_SanPhamCungDanhMuc_ChiTietSanPham.setLayoutManager(recyclerViewLayoutManager);

                    // Set Horizontal Layout Manager
                    // for Recycler view
                    linearLayoutManager = new LinearLayoutManager(ChiTietSanPhamActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView_SanPhamCungDanhMuc_ChiTietSanPham.setLayoutManager(linearLayoutManager);

                    // Lấy dữ liệu trên firebase và thêm vào array list
                    dbref = FirebaseDatabase.getInstance().getReference("SanPham");
                    Query query = dbref.orderByChild("idDanhMuc").equalTo(sp.getIdDanhMuc());
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            // Lấy dữ liệu của các sản phẩm có cùng danh mục với sản phẩm đang được hiển thị thông tin chi tiết
                            // Làm trống mảng
                            arr_SanPhamCungDanhMuc.clear();

                            for (DataSnapshot dataSnapshot : snapshot.getChildren())
                            {
                                SanPham sp1 = dataSnapshot.getValue(SanPham.class);
                                arr_SanPhamCungDanhMuc.add(sp1);
                            }

                            // Sau khi đã lấy hết dữ liệu xuống mảng thì dùng adapter để đổ dữ liệu lên recycler view
                            sanPhamChiTietSanPhamAdapter = new SanPhamChiTietSanPhamAdapter(arr_SanPhamCungDanhMuc);
                            // Set adapter on recycler view
                            recyclerView_SanPhamCungDanhMuc_ChiTietSanPham.setAdapter(sanPhamChiTietSanPhamAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    // Lấy dữ liệu sản phẩm cùng thương hiệu và đổ lên recycler view
                    // initialisation with id's
                    recyclerView_SanPhamCungThuongHieu_ChiTietSanPham = findViewById(R.id.recyclerView_SanPhamCungThuongHieu_ChiTietSanPham);
                    recyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

                    // Set LayoutManager on Recycler View
                    recyclerView_SanPhamCungThuongHieu_ChiTietSanPham.setLayoutManager(recyclerViewLayoutManager);

                    // Set Horizontal Layout Manager
                    // for Recycler view
                    linearLayoutManager = new LinearLayoutManager(ChiTietSanPhamActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView_SanPhamCungThuongHieu_ChiTietSanPham.setLayoutManager(linearLayoutManager);

                    // Lấy dữ liệu trên firebase và thêm vào array list
                    dbref = FirebaseDatabase.getInstance().getReference("SanPham");
                    Query query1 = dbref.orderByChild("idThuongHieu").equalTo(sp.getIdThuongHieu());
                    query1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            // Lấy dữ liệu của các sản phẩm có cùng thương hiệu với sản phẩm đang được hiển thị thông tin chi tiết
                            // Làm trống mảng
                            arr_SanPhamCungThuongHieu.clear();

                            for (DataSnapshot dataSnapshot : snapshot.getChildren())
                            {
                                SanPham sp2 = dataSnapshot.getValue(SanPham.class);
                                arr_SanPhamCungThuongHieu.add(sp2);
                            }

                            // Sau khi đã lấy hết dữ liệu xuống mảng thì dùng adapter để đổ dữ liệu lên recycler view
                            sanPhamChiTietSanPhamAdapter = new SanPhamChiTietSanPhamAdapter(arr_SanPhamCungThuongHieu);
                            // Set adapter on recycler view
                            recyclerView_SanPhamCungThuongHieu_ChiTietSanPham.setAdapter(sanPhamChiTietSanPhamAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Lấy dữ liệu bình luận và đổ lên recycler view
        // initialisation with id's
        recyclerView_BinhLuan_ChiTietSanPham = findViewById(R.id.recyclerView_BinhLuan_ChiTietSanPham);
        recyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

        // Set LayoutManager on Recycler View
        recyclerView_BinhLuan_ChiTietSanPham.setLayoutManager(recyclerViewLayoutManager);

        // Set Horizontal Layout Manager
        // for Recycler view
        linearLayoutManager = new LinearLayoutManager(ChiTietSanPhamActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView_BinhLuan_ChiTietSanPham.setLayoutManager(linearLayoutManager);

        // Lấy dữ liệu trên firebase và thêm vào array list
        dbref = FirebaseDatabase.getInstance().getReference("BinhLuan");
        Query query = dbref.orderByChild("idSanPham").equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Lấy dữ liệu của các bình luận của sản phẩm đang được hiển thị thông tin chi tiết
                // Làm trống mảng
                arr_BinhLuan.clear();
                // Thiết lập giá trị ban đầu của tổng số lượng bình luận tổng đánh giá sao của sản phẩm đang được hiển thị
                // Tổng số lượng bình luận của sản phẩm đang được hiển thị
                int slbl = 0;
                // Tổng số sao đánh giá của sản phẩm đang được hiển thị
                float sosao = 0;

                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    // Lấy dữ liệu của 1 bình luận
                    BinhLuan bl = dataSnapshot.getValue(BinhLuan.class);

                    if(bl != null)
                    {
                        // Thêm dữ liệu vừa lấy vào array list BinhLuan
                        arr_BinhLuan.add(bl);
                        // Số lượng bình luận của sản phẩm tăng thêm 1
                        slbl++;
                        // Số sao đánh giá của bình luận được cộng vào tổng số sao đánh giá bình luận của sản phẩm
                        sosao = sosao + bl.getSoSao();
                    }
                }

                // Sau khi đã lấy hết dữ liệu và thêm vào mảng thì dùng adapter để đổ dữ liệu lên recycler view
                binhLuanChiTietSanPhamAdapter = new BinhLuanChiTietSanPhamAdapter(arr_BinhLuan);
                // Set adapter on recycler view
                recyclerView_BinhLuan_ChiTietSanPham.setAdapter(binhLuanChiTietSanPhamAdapter);
                // Text view hiển thị đánh giá bằng chữ của khách hàng đối với sản phẩm
                String danhgiabangchu = String.valueOf(Math.round(sosao / slbl * 10) / 10.0);
                txtView_SoSaoSanPham_ChiTietSanPham.setText(danhgiabangchu);
                txtView_SoSaoSanPham1_ChiTietSanPham.setText(danhgiabangchu);
                // Text view hiển thị đánh giá bằng sao của khách hàng đối với sản phẩm
                float danhgiabangsao = sosao / slbl;
                ratingBar_SoSaoSanPham_ChiTietSanPham.setRating(danhgiabangsao);
                ratingBar_SoSaoSanPham1_ChiTietSanPham.setRating(danhgiabangsao);
                // Text view hiển thị số lượng bình luận của khách hàng đối với sản phẩm
                txtView_SLBinhLuan_ChiTietSanPham.setText(String.valueOf(slbl));
                txtView_SLBinhLuan1_ChiTietSanPham.setText(String.valueOf(slbl));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ThemHangVaoGio(int id)
    {
        dbref = FirebaseDatabase.getInstance().getReference("GioHang/" + Global.getTenTaiKhoan());
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                arr_GioHang.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    GioHang gh = dataSnapshot.getValue(GioHang.class);
                    arr_GioHang.add(gh);
                }

                for(int i = 0; i < arr_GioHang.size(); i++)
                {
                    if (arr_GioHang.get(i).getIdSanPham() == id)
                    {
                        GioHang gh = arr_GioHang.get(i);
                        int soluong = gh.getSoLuong();
                        gh.setSoLuong(soluong + 1);
                        dbref.child(String.valueOf(id)).setValue(gh);
                        Toast.makeText(getApplicationContext(),"Thêm hàng vào giỏ thành công", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                GioHang gh = new GioHang(Global.getTenTaiKhoan(), id, 1);
                dbref.child(String.valueOf(id)).setValue(gh);

                Toast.makeText(getApplicationContext(),"Thêm hàng vào giỏ thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}