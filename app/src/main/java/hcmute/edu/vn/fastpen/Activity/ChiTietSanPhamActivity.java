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
import hcmute.edu.vn.fastpen.Model.Global;
import hcmute.edu.vn.fastpen.Model.BinhLuan;
import hcmute.edu.vn.fastpen.Model.DanhMuc;
import hcmute.edu.vn.fastpen.Model.GioHang;
import hcmute.edu.vn.fastpen.Model.SanPham;
import hcmute.edu.vn.fastpen.Model.ThuongHieu;
import hcmute.edu.vn.fastpen.R;

public class ChiTietSanPhamActivity extends AppCompatActivity
{
    // Image View
    private ImageView imgView_HinhAnhSanPham_ChiTietSanPham, imgView_HinhAnhThuongHieu_ChiTietSanPham;
    // Text View
    private TextView txtView_TenSanPham_ChiTietSanPham, txtView_SLBinhLuan_ChiTietSanPham, txtView_SLDaBan_ChiTietSanPham, txtView_SlHangConLai_ChiTietSanPham, txtView_Gia_ChiTietSanPham, txtView_TenDanhMuc_ChiTietSanPham, txtView_TenThuongHieu_ChiTietSanPham, txtView_MoTa_ChiTietSanPham, txtView_TenThuongHieu1_ChiTietSanPham, txtView_SLBinhLuan1_ChiTietSanPham, txtView_SoSaoSanPham_ChiTietSanPham, txtView_SoSaoSanPham1_ChiTietSanPham;
    // Rating Bar
    private RatingBar ratingBar_SoSaoSanPham_ChiTietSanPham, ratingBar_SoSaoSanPham1_ChiTietSanPham;
    // Progress Bar
    private ProgressBar progressBar_HinhAnhSanPham_ChiTietSanPham, progressBar_HinhAnhThuongHieu_ChiTietSanPham;
    // Database Reference
    private DatabaseReference dbref;
    // Recycler View
    private RecyclerView recyclerView_SanPhamCungDanhMuc_ChiTietSanPham, recyclerView_SanPhamCungThuongHieu_ChiTietSanPham, recyclerView_BinhLuan_ChiTietSanPham;
    // Array List để lưu trữ dữ liệu dùng để adapt lên recycler view
    private ArrayList<SanPham> arr_SanPhamCungDanhMuc, arr_SanPhamCungThuongHieu;
    private ArrayList<BinhLuan> arr_BinhLuan;
    private ArrayList<GioHang> arr_GioHang;
    // Layout Manager dùng để quản lý layout cho recycler view
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    // Linear Layout Manager dùng để quản lý các thành phần linear layout của recycler view
    private LinearLayoutManager linearLayoutManager;
    // Adapter để adapt dữ liệu từ array lên recycler view
    private SanPhamChiTietSanPhamAdapter sanPhamChiTietSanPhamAdapter;
    private BinhLuanChiTietSanPhamAdapter binhLuanChiTietSanPhamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Set view hiển thị của activity
        setContentView(R.layout.activity_chi_tiet_san_pham);

        // Click vào icon mũi tên để quay về trang trước đó bằng cách finish activity hiện tại
        ImageView imgView_QuayVe_ChiTietSanPham = findViewById(R.id.imgView_QuayVe_ChiTietSanPham);
        imgView_QuayVe_ChiTietSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Click vào icon giỏ hàng để chuyển sang trang giỏ hàng
        ImageView imgView_GioHang_ChiTietSanPham = findViewById(R.id.imgView_GioHang_ChiTietSanPham);
        imgView_GioHang_ChiTietSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent;
                // Nếu chưa đăng nhập, tài khoản sẽ là null thì chuyển sang activity đăng nhập
                if(Global.getTk() == null)
                {
                    intent = new Intent(ChiTietSanPhamActivity.this, DangNhapActivity.class);
                }
                // Nếu đã đăng nhập thì chuyển sang activity giỏ hàng
                else
                {
                    intent = new Intent(ChiTietSanPhamActivity.this, GioHangActivity.class);
                }
                startActivity(intent);
            }
        });

        // Array list các sản phẩm có cùng danh mục với sản phẩm hiện tại
        arr_SanPhamCungDanhMuc = new ArrayList<>();
        // Array list các sản phẩm có cùng thương hiệu với sản phẩm hiện tại
        arr_SanPhamCungThuongHieu = new ArrayList<>();
        // Array list các bình luận của sản phẩm hiện tại
        arr_BinhLuan = new ArrayList<>();
        // Array list các các sản phẩm hiện có trong giỏ hàng dùng cho việc thêm hàng vào giỏ
        arr_GioHang = new ArrayList<>();

        // Lấy id sản phẩm đã lưu trữ bằng getIntExtra của intent và để vào biến id
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);

        // Chạy hàm lấy dữ liệu dựa vào id sản phẩm và set dữ liệu đó lên activity chi tiết sản phẩm
        GetDataChiTietSanPham(id);

        // Linear layout khi click vào sẽ chuyển sang activity tất cả bình luân của sản phẩm hiện tại
        LinearLayout linearLayout_XemTatCaBinhLuan_ChiTietSanPham = findViewById(R.id.linearLayout_XemTatCaBinhLuan_ChiTietSanPham);
        linearLayout_XemTatCaBinhLuan_ChiTietSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang activity tất cả bình luận có truyền tham số id là id của sản phẩm hiện tại dùng để lấy dữ liệu tất cả bình luận của sản phẩm hiện tại
                Intent intent = new Intent(ChiTietSanPhamActivity.this, TatCaBinhLuanActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        // Linear layout click vào sẽ thêm sản phẩm vào giỏ hàng
        LinearLayout linearLayout_ThemHangVaoGio_ChiTietSanPham = findViewById(R.id.linearLayout_ThemHangVaoGio_ChiTietSanPham);
        linearLayout_ThemHangVaoGio_ChiTietSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // Nếu chưa đăng nhập, tài khoản sẽ là null thì chuyển sang activity đăng nhập
                if(Global.getTk() == null)
                {
                    Intent intent;
                    intent = new Intent(ChiTietSanPhamActivity.this, DangNhapActivity.class);
                    startActivity(intent);
                }
                // Nếu đã đăng nhập thì chạy hàm thêm hàng vào giỏ
                else
                {
                    ThemHangVaoGio(id);
                }
            }
        });
    }

    // Hàm set dữ liệu hình ảnh
    public void GetImage(ImageView imageView, String ten, ProgressBar progressBar)
    {
        // Kết nối với Firebase Storage với đường dẫn Image/tên hình ảnh
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Image/" + ten);
        try {
            // Tạo file jpg tạm
            File localFile = File.createTempFile("tempfile", ".jpg");
            // Dùng file tạm vừa tạo để chứa dữ liệu hình ảnh vừa lấy về từ đường dẫn trên Firebase storage
            storageReference.getFile(localFile)
                    // Nếu lấy thành công
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Không hiển thị progress bar loading
                            progressBar.setVisibility(View.GONE);
                            // Set hình ảnh lên image view tương ứng dựa vào dữ liệu hình ảnh vừa lấy được
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            imageView.setImageBitmap(bitmap);
                        }
                    })
                    // Nếu lấy thất bại
                    .addOnFailureListener(new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                        }
                    })
                    // Nếu đang trong quá trình lấy dữ liệu hình ảnh
                    .addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onProgress(@NonNull FileDownloadTask.TaskSnapshot snapshot) {
                            // Hiển thị progress bar loading trên hình ảnh có nền trắng
                            progressBar.setVisibility(View.VISIBLE);
                            imageView.setImageResource(R.drawable.plain_white_background_211387);
                        }
                    });
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // Hàm lấy và set dữ liệu chi tiết sản phẩm
    private void GetDataChiTietSanPham(int id)
    {
        // Khởi tạo các biến component của layout activity chi tiết sản phẩm
        // Hình ảnh sản phẩm
        imgView_HinhAnhSanPham_ChiTietSanPham = findViewById(R.id.imgView_HinhAnhSanPham_ChiTietSanPham);
        // Progress Bar loading trong lúc đang load hình ảnh sản phẩm
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
        // Tên danh mục của sản phẩm
        txtView_TenDanhMuc_ChiTietSanPham = findViewById(R.id.txtView_TenDanhMuc_ChiTietSanPham);
        // Tên thương hiệu của sản phẩm
        txtView_TenThuongHieu_ChiTietSanPham = findViewById(R.id.txtView_TenThuongHieu_ChiTietSanPham);
        // Hình ảnh thương hiệu của sản phẩm
        imgView_HinhAnhThuongHieu_ChiTietSanPham = findViewById(R.id.imgView_HinhAnhThuongHieu_ChiTietSanPham);
        // Progress Bar loading trong lúc load hình ảnh thương hiệu
        progressBar_HinhAnhThuongHieu_ChiTietSanPham = findViewById(R.id.progressBar_HinhAnhThuongHieu_ChiTietSanPham);
        // Mô tả thêm về sản phẩm
        txtView_MoTa_ChiTietSanPham = findViewById(R.id.txtView_MoTa_ChiTietSanPham);
        // Tên thương hiệu của sản phẩm trong phần các sản phẩm cùng thương hiệu
        txtView_TenThuongHieu1_ChiTietSanPham = findViewById(R.id.txtView_TenThuongHieu1_ChiTietSanPham);
        // Số lượng bình luận của khách hàng đối với sản phẩm trong phần bình luận
        txtView_SLBinhLuan1_ChiTietSanPham = findViewById(R.id.txtView_SLBinhLuan1_ChiTietSanPham);
        // Tổng số sao đánh giá của khách bình luận
        ratingBar_SoSaoSanPham_ChiTietSanPham = findViewById(R.id.ratingBar_SoSaoSanPham_ChiTietSanPham);
        // Tổng số sao đánh giá của khách bình luận
        ratingBar_SoSaoSanPham1_ChiTietSanPham = findViewById(R.id.ratingBar_SoSaoSanPham1_ChiTietSanPham);
        // Tổng số sao đánh giá hiển thị bằng số của khách bình luận
        txtView_SoSaoSanPham_ChiTietSanPham = findViewById(R.id.txtView_SoSaoSanPham_ChiTietSanPham);
        // Tổng số sao đánh giá hiển thị bằng số của khách bình luận
        txtView_SoSaoSanPham1_ChiTietSanPham = findViewById(R.id.txtView_SoSaoSanPham1_ChiTietSanPham);

        // Kết nối với firebase database và truy cập vào đường dẫn SanPham/id sản phẩm
        dbref = FirebaseDatabase.getInstance().getReference("SanPham/" + id);
        // Lấy dữ liệu của các sản phẩm có idSanPham bằng tham số id được truyền vào để lấy đúng thông tin của sản phẩm
        dbref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Lấy object sản phẩm từ database và để vào biến sp
                SanPham sp = snapshot.getValue(SanPham.class);

                // Nếu biến sp khác null(có dữ liệu)
                if(sp != null)
                {
                    // Lấy dữ liệu của biến sp và set dữ liệu đó lên layout của activity
                    // Set hình ảnh sản phẩm
                    GetImage(imgView_HinhAnhSanPham_ChiTietSanPham,sp.getHinhAnh(),progressBar_HinhAnhSanPham_ChiTietSanPham);
                    // Set tên sản phẩm
                    txtView_TenSanPham_ChiTietSanPham.setText(sp.getTenSanPham());
                    // Set số lượng sản phẩm đã bán
                    txtView_SLDaBan_ChiTietSanPham.setText(String.valueOf(sp.getSoLuongDaBan()));
                    // Set số lượng sản phẩm còn lại
                    txtView_SlHangConLai_ChiTietSanPham.setText(String.valueOf(sp.getSoLuong()));
                    // Set giá sản phẩm
                    txtView_Gia_ChiTietSanPham.setText(String.valueOf(sp.getGia()));
                    // Set mô tả sản phẩm
                    txtView_MoTa_ChiTietSanPham.setText(sp.getMoTa());

                    // Kết nối với firebase database và truy cập vào đường dẫn DanhMuc/id danh mục của sản phẩm hiện tại
                    dbref = FirebaseDatabase.getInstance().getReference("DanhMuc/" + sp.getIdDanhMuc());
                    dbref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            // Lấy object danh mục từ database và để vào biến dm
                            DanhMuc dm = snapshot.getValue(DanhMuc.class);

                            // Nếu biến dm khác null
                            if (dm != null) {
                                // Set tên danh mục của sản phẩm hiện tại từ dữ liệu của biến dm
                                txtView_TenDanhMuc_ChiTietSanPham.setText(dm.getTenDanhMuc());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    // Kết nối với firebase database và truy cập vào đường dẫn ThuongHieu/id thương hiệu của sản phẩm hiện tại
                    dbref = FirebaseDatabase.getInstance().getReference("ThuongHieu/" + sp.getIdThuongHieu());
                    dbref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // Lấy object thương hiệu từ database và để vào biến th
                            ThuongHieu th = snapshot.getValue(ThuongHieu.class);

                            // Nếu th khác null
                            if (th != null) {
                                // Lấy dữ liệu của biến th và set dữ liệu đó lên layout của activity
                                // Set hình ảnh thương hiệu
                                GetImage(imgView_HinhAnhThuongHieu_ChiTietSanPham, th.getHinhAnhThuongHieu(), progressBar_HinhAnhThuongHieu_ChiTietSanPham);
                                // Set tên thương hiệu ở hai text view tên thương hiệu trên layout activity
                                txtView_TenThuongHieu_ChiTietSanPham.setText(th.getTenThuongHieu());
                                txtView_TenThuongHieu1_ChiTietSanPham.setText(th.getTenThuongHieu());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    // Lấy dữ liệu sản phẩm cùng danh mục và đổ lên recycler view
                    // Khởi tạo biến recycler view
                    recyclerView_SanPhamCungDanhMuc_ChiTietSanPham = findViewById(R.id.recyclerView_SanPhamCungDanhMuc_ChiTietSanPham);
                    // Khởi tạo biến recycler view layout manager
                    recyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

                    // Set LayoutManager cho Recycler View
                    recyclerView_SanPhamCungDanhMuc_ChiTietSanPham.setLayoutManager(recyclerViewLayoutManager);

                    // Set Horizontal Layout Manager cho Recycler view
                    linearLayoutManager = new LinearLayoutManager(ChiTietSanPhamActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView_SanPhamCungDanhMuc_ChiTietSanPham.setLayoutManager(linearLayoutManager);

                    // Kết nối firebase với đường dẫn SanPham
                    dbref = FirebaseDatabase.getInstance().getReference("SanPham");
                    // Dùng orderByChild để lấy những sản phẩm có id danh mục giống id danh mục của sản phẩm hiện tại
                    Query query = dbref.orderByChild("idDanhMuc").equalTo(sp.getIdDanhMuc());
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            // Lấy dữ liệu của các sản phẩm có cùng danh mục với sản phẩm đang được hiển thị thông tin chi tiết
                            // Làm trống mảng để khi dữ liệu trên firebase có sự thay đổi thì add dữ liệu vào mảng sẽ không làm dữ liệu trong mảng bị trùng lặp
                            arr_SanPhamCungDanhMuc.clear();

                            for (DataSnapshot dataSnapshot : snapshot.getChildren())
                            {
                                // Lấy object SanPham từ firebase và thêm vào mảng
                                SanPham sp1 = dataSnapshot.getValue(SanPham.class);
                                arr_SanPhamCungDanhMuc.add(sp1);
                            }

                            // Sau khi đã lấy hết dữ liệu xuống mảng thì dùng adapter để đổ dữ liệu lên recycler view
                            sanPhamChiTietSanPhamAdapter = new SanPhamChiTietSanPhamAdapter(arr_SanPhamCungDanhMuc, new SanPhamChiTietSanPhamAdapter.OnItemClickListener()
                            {
                                @Override
                                // Khi click vào 1 item trong recycler view
                                public void onItemClick(View view, int i) {
                                    // Chuyển qua trang Chi tiết sản phẩm
                                    Intent intent = new Intent(ChiTietSanPhamActivity.this, ChiTietSanPhamActivity.class);
                                    // Lưu trữ lại id của sản phẩm bằng putExtra
                                    intent.putExtra("id", arr_SanPhamCungDanhMuc.get(i).getIdSanPham());
                                    startActivity(intent);
                                }
                            });
                            // Dùng adapter để set dữ liệu cho recycler view các sản phẩm cùng danh muc với sản phẩm hiện tại
                            recyclerView_SanPhamCungDanhMuc_ChiTietSanPham.setAdapter(sanPhamChiTietSanPhamAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    // Lấy dữ liệu sản phẩm cùng thương hiệu và đổ lên recycler view
                    // Khởi tạo biến recycler view
                    recyclerView_SanPhamCungThuongHieu_ChiTietSanPham = findViewById(R.id.recyclerView_SanPhamCungThuongHieu_ChiTietSanPham);
                    // Khởi tạo biến recycler view layout manager
                    recyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

                    // Set LayoutManager cho Recycler View
                    recyclerView_SanPhamCungThuongHieu_ChiTietSanPham.setLayoutManager(recyclerViewLayoutManager);

                    // Set Horizontal Layout Manager cho Recycler view
                    linearLayoutManager = new LinearLayoutManager(ChiTietSanPhamActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView_SanPhamCungThuongHieu_ChiTietSanPham.setLayoutManager(linearLayoutManager);

                    // Kết nối firebase với đường dẫn SanPham
                    dbref = FirebaseDatabase.getInstance().getReference("SanPham");
                    // Dùng orderByChild để lấy những sản phẩm có id thương hiệu giống id thương hiệu của sản phẩm hiện tại
                    Query query1 = dbref.orderByChild("idThuongHieu").equalTo(sp.getIdThuongHieu());
                    query1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            // Lấy dữ liệu của các sản phẩm có cùng thương hiệu với sản phẩm đang được hiển thị thông tin chi tiết
                            // Làm trống mảng để khi dữ liệu trên firebase có sự thay đổi thì add dữ liệu vào mảng sẽ không làm dữ liệu trong mảng bị trùng lặp
                            arr_SanPhamCungThuongHieu.clear();

                            for (DataSnapshot dataSnapshot : snapshot.getChildren())
                            {
                                // Lấy object SanPham từ firebase và thêm vào mảng
                                SanPham sp2 = dataSnapshot.getValue(SanPham.class);
                                arr_SanPhamCungThuongHieu.add(sp2);
                            }

                            // Sau khi đã lấy hết dữ liệu xuống mảng thì dùng adapter để đổ dữ liệu lên recycler view
                            sanPhamChiTietSanPhamAdapter = new SanPhamChiTietSanPhamAdapter(arr_SanPhamCungThuongHieu, new SanPhamChiTietSanPhamAdapter.OnItemClickListener()
                            {
                                @Override
                                // Khi click vào 1 item trong recycler view
                                public void onItemClick(View view, int i) {
                                    // Chuyển qua trang Chi tiết sản phẩm
                                    Intent intent = new Intent(ChiTietSanPhamActivity.this, ChiTietSanPhamActivity.class);
                                    // Lưu trữ lại id của sản phẩm bằng putExtra
                                    intent.putExtra("id", arr_SanPhamCungThuongHieu.get(i).getIdSanPham());
                                    startActivity(intent);
                                }
                            });
                            // Dùng adapter để set dữ liệu cho recycler view các sản phẩm cùng thương hiệu với sản phẩm hiện tại
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
        // Khởi tạo biến recycler view
        recyclerView_BinhLuan_ChiTietSanPham = findViewById(R.id.recyclerView_BinhLuan_ChiTietSanPham);
        // Khởi tạo biến recycler view layout manager
        recyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

        // Set LayoutManager cho Recycler View
        recyclerView_BinhLuan_ChiTietSanPham.setLayoutManager(recyclerViewLayoutManager);

        // Set Horizontal Layout Manager cho Recycler view
        linearLayoutManager = new LinearLayoutManager(ChiTietSanPhamActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView_BinhLuan_ChiTietSanPham.setLayoutManager(linearLayoutManager);

        // Kết nối firebase với đường dẫn BinhLuan
        dbref = FirebaseDatabase.getInstance().getReference("BinhLuan");
        // Dùng orderByChild để lấy những bình luận có cùng id sản phẩm với sản phẩm hiện tại
        Query query = dbref.orderByChild("idSanPham").equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Lấy dữ liệu của các bình luận của sản phẩm hiện tại
                // Làm trống mảng để khi dữ liệu trên firebase có sự thay đổi thì add dữ liệu vào mảng sẽ không làm dữ liệu trong mảng bị trùng lặp
                arr_BinhLuan.clear();
                // Thiết lập giá trị ban đầu của tổng số lượng bình luận tổng đánh giá sao của sản phẩm hiện tại
                // Tổng số lượng bình luận của sản phẩm hiện tại
                int slbl = 0;
                // Tổng số sao đánh giá của sản phẩm hiện tại
                float sosao = 0;

                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    // Lấy dữ liệu của 1 bình luận và bỏ vào biến bl
                    BinhLuan bl = dataSnapshot.getValue(BinhLuan.class);

                    // Nếu bl khác null
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

                // Đảo arr_BinhLuan lại và thêm vào arr_BinhLuanTemp để có được danh sách các bình luận được sắp xếp theo thời gian từ gần đến xa so với hiện tại
                ArrayList<BinhLuan> arr_BinhLuanTemp = new ArrayList<>();
                for(int i = arr_BinhLuan.size() - 1; i >= 0; i--)
                {
                    arr_BinhLuanTemp.add(arr_BinhLuan.get(i));
                }

                // Sau khi đã lấy hết dữ liệu và thêm vào mảng thì dùng adapter để đổ dữ liệu lên recycler view
                binhLuanChiTietSanPhamAdapter = new BinhLuanChiTietSanPhamAdapter(arr_BinhLuanTemp);
                // Dùng adapter để set dữ liệu cho recycler view các bình luận của sản phẩm hiện tại
                recyclerView_BinhLuan_ChiTietSanPham.setAdapter(binhLuanChiTietSanPhamAdapter);
                // Set Text view hiển thị đánh giá bằng chữ của khách hàng đối với sản phẩm
                String danhgiabangchu = String.valueOf(Math.round(sosao / slbl * 10) / 10.0);
                txtView_SoSaoSanPham_ChiTietSanPham.setText(danhgiabangchu);
                txtView_SoSaoSanPham1_ChiTietSanPham.setText(danhgiabangchu);
                // Set Text view hiển thị đánh giá bằng sao của khách hàng đối với sản phẩm
                float danhgiabangsao = sosao / slbl;
                ratingBar_SoSaoSanPham_ChiTietSanPham.setRating(danhgiabangsao);
                ratingBar_SoSaoSanPham1_ChiTietSanPham.setRating(danhgiabangsao);
                // Set Text view hiển thị số lượng bình luận của khách hàng đối với sản phẩm
                txtView_SLBinhLuan_ChiTietSanPham.setText(String.valueOf(slbl));
                txtView_SLBinhLuan1_ChiTietSanPham.setText(String.valueOf(slbl));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Hàm thêm hàng vào giỏ
    private void ThemHangVaoGio(int id)
    {
        // Kết nối firebase với đường dẫn GioHang/tên tài khoản đang được sử dụng
        dbref = FirebaseDatabase.getInstance().getReference("GioHang/" + Global.getTk().getTenTaiKhoan());
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Lấy dữ liệu của giỏ hàng hiện tại
                // Làm trống mảng để khi dữ liệu trên firebase có sự thay đổi thì add dữ liệu vào mảng sẽ không làm dữ liệu trong mảng bị trùng lặp
                arr_GioHang.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    // Lấy dữ liệu của 1 object giỏ hàng và bỏ vào biến gh
                    GioHang gh = dataSnapshot.getValue(GioHang.class);
                    arr_GioHang.add(gh);
                }

                // Sau khi đã lấy được dữ liệu giỏ hàng hiện tại thì duyệt qua array để lấy dữ liệu các sản phẩm trong giỏ hàng
                for(int i = 0; i < arr_GioHang.size(); i++)
                {
                    // Duyệt qua từng sản phẩm trong giỏ hàng để kiểm tra xem sản phẩm cần thêm vào giỏ đã có trong giỏ hay chưa
                    // Nếu đã có trong giỏ thì tăng số lượng thêm 1
                    if (arr_GioHang.get(i).getIdSanPham() == id)
                    {
                        // Lấy dữ liệu sản phẩm đó bỏ vào biến gh
                        GioHang gh = arr_GioHang.get(i);
                        // Lấy số lượng của sản phẩm đó trong giỏ
                        int soluong = gh.getSoLuong();
                        // Tăng số lượng thêm 1
                        gh.setSoLuong(soluong + 1);
                        // Set lại số lượng sản phẩm đó trong giỏ trên firebase
                        dbref.child(String.valueOf(id)).setValue(gh);
                        // Thông báo thêm sản phẩm vào giỏ thành công
                        Toast.makeText(getApplicationContext(),"Thêm hàng vào giỏ thành công", Toast.LENGTH_SHORT).show();
                        // Thoát khỏi hàm thêm hàng vào giỏ
                        return;
                    }
                }

                // Nếu chưa có trong giỏ thì thêm sản phẩm vào giỏ với số lượng 1 và set dữ liệu sản phẩm mới vào giỏ trên firebase
                GioHang gh = new GioHang(Global.getTk().getTenTaiKhoan(), id, 1);
                dbref.child(String.valueOf(id)).setValue(gh);
                // Thông báo thêm sản phẩm vào giỏ thành công
                Toast.makeText(getApplicationContext(),"Thêm hàng vào giỏ thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}