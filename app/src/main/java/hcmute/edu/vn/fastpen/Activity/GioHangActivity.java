package hcmute.edu.vn.fastpen.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hcmute.edu.vn.fastpen.Adapter.BinhLuanChiTietSanPhamAdapter;
import hcmute.edu.vn.fastpen.Adapter.SanPhamChiTietSanPhamAdapter;
import hcmute.edu.vn.fastpen.Adapter.SanPhamGioHangAdapter;
import hcmute.edu.vn.fastpen.Global;
import hcmute.edu.vn.fastpen.Model.BinhLuan;
import hcmute.edu.vn.fastpen.Model.GioHang;
import hcmute.edu.vn.fastpen.Model.SanPham;
import hcmute.edu.vn.fastpen.R;

public class GioHangActivity extends AppCompatActivity
{
    private ImageView imgView_QuayVe_GioHang;
    private TextView txtView_GiaTongCong_GioHang;
    private LinearLayout linearLayout_MuaHang_GioHang;
    // Database Reference
    private DatabaseReference dbref;
    // Recycler View object
    private RecyclerView recyclerView_SanPhamTrongGio_GioHang;
    // Array list for recycler view data source
    private ArrayList<GioHang> arr_GioHang;
    // Layout Manager
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    // Adapter class object
    private SanPhamGioHangAdapter sanPhamGioHangAdapter;
    // Linear Layout Manager
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        imgView_QuayVe_GioHang = findViewById(R.id.imgView_QuayVe_GioHang);
        imgView_QuayVe_GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        arr_GioHang = new ArrayList<>();
        GetDataSanPhamGioHang();

        linearLayout_MuaHang_GioHang = findViewById(R.id.linearLayout_MuaHang_GioHang);
        linearLayout_MuaHang_GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GioHangActivity.this, ThanhToanActivity.class);
                startActivity(intent);
            }
        });
    }

    private void TinhTongTien(ArrayList<GioHang> arr_GioHang)
    {
        txtView_GiaTongCong_GioHang = findViewById(R.id.txtView_GiaTongCong_GioHang);

        int size = arr_GioHang.size();
        final int[] giatong = {0};

        for(int i = 0; i < size; i++)
        {
            int id = arr_GioHang.get(i).getIdSanPham();
            dbref = FirebaseDatabase.getInstance().getReference("SanPham/" + id + "/gia");
            // Lấy dữ liệu của sản phẩm có idSanPham bằng id được lưu trữ để lấy thông tin của sản phẩm
            int finalI = i;
            dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int gia = snapshot.getValue(Integer.class);

                    giatong[0] = giatong[0] + gia * arr_GioHang.get(finalI).getSoLuong();
                    txtView_GiaTongCong_GioHang.setText(String.valueOf(giatong[0]));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void GetDataSanPhamGioHang()
    {
        // Lấy dữ liệu sản phẩm trong giỏ và đổ lên recycler view
        // initialisation with id's
        recyclerView_SanPhamTrongGio_GioHang = findViewById(R.id.recyclerView_SanPhamTrongGio_GioHang);
        recyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

        // Set LayoutManager on Recycler View
        recyclerView_SanPhamTrongGio_GioHang.setLayoutManager(recyclerViewLayoutManager);

        // Set Horizontal Layout Manager
        // for Recycler view
        linearLayoutManager = new LinearLayoutManager(GioHangActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView_SanPhamTrongGio_GioHang.setLayoutManager(linearLayoutManager);

        // Lấy dữ liệu trên firebase và thêm vào array list
        dbref = FirebaseDatabase.getInstance().getReference("GioHang/" + Global.getTenTaiKhoan());
        dbref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Lấy dữ liệu của các sản phẩm trong giỏ
                // Làm trống mảng
                arr_GioHang.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    GioHang gh = dataSnapshot.getValue(GioHang.class);
                    arr_GioHang.add(gh);
                }

                // Sau khi đã lấy hết dữ liệu xuống mảng thì dùng adapter để đổ dữ liệu lên recycler view
                sanPhamGioHangAdapter = new SanPhamGioHangAdapter(arr_GioHang);
                // Set adapter on recycler view
                recyclerView_SanPhamTrongGio_GioHang.setAdapter(sanPhamGioHangAdapter);

                TinhTongTien(arr_GioHang);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}