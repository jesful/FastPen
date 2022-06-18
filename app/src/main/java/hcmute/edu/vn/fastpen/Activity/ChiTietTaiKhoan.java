package hcmute.edu.vn.fastpen.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import hcmute.edu.vn.fastpen.Model.LoadingDialog;
import hcmute.edu.vn.fastpen.Model.TaiKhoan;
import hcmute.edu.vn.fastpen.R;

public class ChiTietTaiKhoan extends AppCompatActivity {
    // ánh xạ với View để thực hiện các sự kiện khi người dùng thao tác
    private ImageView img_Add, img_Back, img_Edit;
    private EditText edt_TenNguoiDung, edt_DiaChi, edt_SoDienThoai, edt_Email, edt_VaiTro;
    private TextView txt_TenTaiKhoan;

    // Chứa dữ liệu từ intent chuyển qua
    private TaiKhoan taiKhoan = new TaiKhoan();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_tai_khoan);
        SetID();
        GetIntent();
        TaiDuLieu();
        Event();
    }

    //Lấy dữ liệu từ Firebase lưu vào các biến
    private void TaiDuLieu() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("TaiKhoan");

        String id = String.valueOf(taiKhoan.getTenTaiKhoan());

        Query query = myRef.orderByChild(id);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                final Handler handler = new Handler();
                final LoadingDialog dialog = new LoadingDialog(ChiTietTaiKhoan.this);
                dialog.startLoadingDialog();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String tempID = snapshot.getKey();
                        System.out.println("Id: " + tempID);
                        if(tempID.equals(id))
                        {
                            taiKhoan = snapshot.getValue(TaiKhoan.class);

                            txt_TenTaiKhoan.setText(taiKhoan.getTenTaiKhoan());
                            edt_TenNguoiDung.setText(taiKhoan.getTen());
                            edt_DiaChi.setText(taiKhoan.getDiaChi());
                            edt_SoDienThoai.setText(taiKhoan.getSDT());
                            edt_Email.setText(taiKhoan.getEmail());

                            if(taiKhoan.isQuyen())
                            {
                                String role = "Admin";
                                edt_VaiTro.setText(role);
                            }
                            else {
                                String role = "Khách";
                                edt_VaiTro.setText(role);
                            }
                        }
                        dialog.dismissLoadingDialog();
                    }
                }, 1500);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Lấy dữ liệu từ QuanLyTaiKhoan
    private void GetIntent() {
        Intent intent = getIntent();
        taiKhoan.setTenTaiKhoan( intent.getStringExtra("taiKhoan"));
    }

    // thực thi các sự kiện khi người dùng thao tác
    private void Event() {
        img_Add.setVisibility(View.GONE);
        img_Edit.setVisibility(View.GONE);
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // gán id vào các biến
    private void SetID() {
        img_Add = findViewById(R.id.img_Add);
        img_Back = findViewById(R.id.img_Back);
        img_Edit = findViewById(R.id.img_Edit);
        txt_TenTaiKhoan = findViewById(R.id.txt_TenTaiKhoan);
        edt_DiaChi = findViewById(R.id.edt_DiaChi);
        edt_Email = findViewById(R.id.edt_Email);
        edt_SoDienThoai = findViewById(R.id.edt_SoDienThoai);
        edt_VaiTro = findViewById(R.id.edt_VaiTro);
        edt_TenNguoiDung = findViewById(R.id.edt_TenNguoiDung);
    }

}