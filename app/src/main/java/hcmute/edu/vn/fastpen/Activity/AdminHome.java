package hcmute.edu.vn.fastpen.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hcmute.edu.vn.fastpen.Model.Global;
import hcmute.edu.vn.fastpen.Model.TaiKhoan;
import hcmute.edu.vn.fastpen.R;

public class AdminHome extends AppCompatActivity {
    // ánh xạ với View để thực hiện các sự kiện khi người dùng thao tác
    private CardView quanLyDonHang, quanLySanPham, quanLyDanhMuc, quanLyThuongHieu, quanLyTaiKhoan;
    private TextView txt_TenTaiKhoan;

    // Lưu tên tài khoản, truy xuất dữ liệu trên Firebase
    private String idUser = "user1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        SetID();
        //TaiDuLieu();
        txt_TenTaiKhoan.setText(Global.getTk().getTenTaiKhoan());

        Event();
    }

    // Lấy dữ liệu TaiKhoan từ firebase
    private void TaiDuLieu() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("TaiKhoan/" + idUser);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("Snap: " + snapshot);
                if(snapshot.exists()){
                    System.out.println("Hey");
                    TaiKhoan taiKhoan = snapshot.getValue(TaiKhoan.class);
                    txt_TenTaiKhoan.setText(taiKhoan.getTenTaiKhoan());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Thực thi các sự kiện trong Activity
    private void Event() {
        ChuyenTrang();

    }

    // Chuyển đến các Activity tương ứng với các mục chức năng
    private void ChuyenTrang() {
        quanLyDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, QuanLyDonHang.class);
                startActivity(intent);
            }
        });
        quanLySanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, QuanLySanPham.class);
                startActivity(intent);
            }
        });
        quanLyThuongHieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, QuanLyThuongHieu.class);
                startActivity(intent);
            }
        });
        quanLyDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, QuanLyDanhMuc.class);
                startActivity(intent);
            }
        });
        quanLyTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, QuanLyTaiKhoan.class);
                startActivity(intent);
            }
        });
    }

    //Gán các id vào các biến
    private void SetID() {
        quanLyDonHang = findViewById(R.id.card_QuanLyDonHang);
        quanLySanPham = findViewById(R.id.card_QuanLySanPham);
        quanLyDanhMuc = findViewById(R.id.card_QuanLyDanhMuc);
        quanLyThuongHieu = findViewById(R.id.card_QuanLyThuongHieu);
        quanLyTaiKhoan = findViewById(R.id.card_QuanLyTaiKhoan);
        txt_TenTaiKhoan = findViewById(R.id.txtTenTaiKhoan);
    }

}