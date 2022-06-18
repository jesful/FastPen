package hcmute.edu.vn.fastpen.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hcmute.edu.vn.fastpen.Adapter.SanPhamGioHangAdapter;
import hcmute.edu.vn.fastpen.Model.Global;
import hcmute.edu.vn.fastpen.Model.GioHang;
import hcmute.edu.vn.fastpen.R;

public class GioHangActivity extends AppCompatActivity
{
    // Text View
    private TextView txtView_GiaTongCong_GioHang;
    // Database Reference
    private DatabaseReference dbref;
    // Recycler View
    private RecyclerView recyclerView_SanPhamTrongGio_GioHang;
    // Array List để lưu trữ dữ liệu dùng để adapt lên recycler view
    private ArrayList<GioHang> arr_GioHang;
    // Adapter để adapt dữ liệu từ array lên recycler view
    private SanPhamGioHangAdapter sanPhamGioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Set view hiển thị của activity
        setContentView(R.layout.activity_gio_hang);

        // Click vào icon mũi tên để quay về trang trước đó bằng cách finish activity hiện tại
        ImageView imgView_QuayVe_GioHang = findViewById(R.id.imgView_QuayVe_GioHang);
        imgView_QuayVe_GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Array list các các sản phẩm hiện có trong giỏ hàng dùng cho việc set dữ liệu giỏ hàng
        arr_GioHang = new ArrayList<>();
        // Chạy hàm lấy dữ liệu giỏ hàng
        GetDataSanPhamGioHang();

        // Linear layout khi click vào sẽ chuyển sang activity thanh toán
        LinearLayout linearLayout_MuaHang_GioHang = findViewById(R.id.linearLayout_MuaHang_GioHang);
        linearLayout_MuaHang_GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang activity ThanhToan
                Intent intent = new Intent(GioHangActivity.this, ThanhToanActivity.class);
                startActivity(intent);
            }
        });
    }

    // Hàm tính tổng tiền các sản phẩm trong giỏ
    private void TinhTongTien(ArrayList<GioHang> arr_GioHang)
    {
        // Khởi tạo biến text view giá tổng cộng của giỏ hàng
        txtView_GiaTongCong_GioHang = findViewById(R.id.txtView_GiaTongCong_GioHang);

        // Lấy số lượng phần tử của array giỏ hàng để vào biến size
        int size = arr_GioHang.size();
        // Tạo biến giatong kiểu dữ liệu int[]
        final int[] giatong = {0};

        // Duyệt qua array giỏ hàng
        for(int i = 0; i < size; i++)
        {
            // Lấy id sản phẩm trong giỏ bỏ vào biến id
            int id = arr_GioHang.get(i).getIdSanPham();
            // Kết nối với firebase bằng đường dẫn SanPham/id sản phẩm/gia
            dbref = FirebaseDatabase.getInstance().getReference("SanPham/" + id + "/gia");
            // Tạo biến finalI với giá trị là i
            int finalI = i;
            dbref.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    // Lấy giá của sản phẩm trong giỏ và bỏ vào biến gia
                    int gia = snapshot.getValue(Integer.class);

                    // Tăng biến giatong[0] mỗi khi duyệt qua 1 phần tử trong giỏ và set Textview giá tổng cộng với giatong[0] lấy được
                    giatong[0] = giatong[0] + gia * arr_GioHang.get(finalI).getSoLuong();
                    txtView_GiaTongCong_GioHang.setText(String.valueOf(giatong[0]));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    // Hàm lấy và set dữ liệu giỏ hàng
    private void GetDataSanPhamGioHang()
    {
        // Lấy dữ liệu sản phẩm trong giỏ và đổ lên recycler view
        // Khởi tạo biến recycler view
        recyclerView_SanPhamTrongGio_GioHang = findViewById(R.id.recyclerView_SanPhamTrongGio_GioHang);
        // Khởi tạo biến recycler view layout manager
        RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

        // Set LayoutManager cho Recycler View
        recyclerView_SanPhamTrongGio_GioHang.setLayoutManager(recyclerViewLayoutManager);

        // Set Horizontal Layout Manager cho Recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GioHangActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView_SanPhamTrongGio_GioHang.setLayoutManager(linearLayoutManager);

        // Kết nối firebase với đường dẫn GioHang/tên tài khoản để lấy giỏ hàng của tài khoản đang được sử dụng
        dbref = FirebaseDatabase.getInstance().getReference("GioHang/" + Global.getTk().getTenTaiKhoan());
        dbref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Lấy dữ liệu của các sản phẩm trong giỏ
                // Làm trống mảng để khi dữ liệu trên firebase có sự thay đổi thì add dữ liệu vào mảng sẽ không làm dữ liệu trong mảng bị trùng
                arr_GioHang.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    // Lấy object GioHang từ firebase và thêm vào mảng
                    GioHang gh = dataSnapshot.getValue(GioHang.class);
                    arr_GioHang.add(gh);
                }

                // Sau khi đã lấy hết dữ liệu xuống mảng thì dùng adapter để đổ dữ liệu lên recycler view
                sanPhamGioHangAdapter = new SanPhamGioHangAdapter(arr_GioHang);
                // Dùng adapter để set dữ liệu cho recycler view giỏ hàng
                recyclerView_SanPhamTrongGio_GioHang.setAdapter(sanPhamGioHangAdapter);

                // Chạy hàm tính tổng tiền của các sản phẩm trong giỏ có tham số array giỏ hàng vừa lấy từ firebase
                TinhTongTien(arr_GioHang);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}