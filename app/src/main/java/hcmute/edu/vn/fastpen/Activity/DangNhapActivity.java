package hcmute.edu.vn.fastpen.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hcmute.edu.vn.fastpen.Model.Global;
import hcmute.edu.vn.fastpen.Model.TaiKhoan;
import hcmute.edu.vn.fastpen.R;

public class DangNhapActivity extends AppCompatActivity {
    Button buttonDangKy,buttonDangNhap;
    EditText editTextTenTaiKhoan, editTextMatKhau;
    ImageView imageViewBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        AnhXa(); // Ánh xạ với các biến trong layout

        buttonDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyễn đến Activity đăng ký
                Intent intent = new Intent(DangNhapActivity.this,DangKyActivity.class);
                startActivity(intent);
            }
        });
        buttonDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy tài khoản và mật khẩu từ edittext
                String tentaikhoan = editTextTenTaiKhoan.getText().toString().trim();
                String matkhau = editTextMatKhau.getText().toString().trim();

                // Kiểm tra tài khoản mật khẩu có giá trị chưa
                if(tentaikhoan.equals("")||matkhau.equals(""))
                {
                    Toast.makeText(DangNhapActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Tạo đường dẫn đến bảng TaiKhoan
                    DatabaseReference dbref=FirebaseDatabase.getInstance().getReference("TaiKhoan/" + tentaikhoan);
                    dbref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                // Nếu tài khoản tồn tại kiển tra mật khẩu có đúng không
                                TaiKhoan tk = snapshot.getValue(TaiKhoan.class);
                                if(tk.getMatKhau().equals(matkhau))
                                {
                                    // Đúng mật khẩu đăng nhập thành công

                                    Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    // Cập nhật tài khoản vào Global
                                    Global.setTk(tk);

                                    if(tk.isQuyen())
                                    {
                                        // Chuyển đến trang admin
                                        Intent intent = new Intent(DangNhapActivity.this,AdminHome.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        // Chuyển về trang Activity
                                        Intent intent = new Intent(DangNhapActivity.this,MainActivity.class);
                                        startActivity(intent);
                                    }
                                }
                                else {
                                    // Mật khẩu không đúng
                                    Toast.makeText(DangNhapActivity.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                // Tài khoản không tồn tại
                                Toast.makeText(DangNhapActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
        // Quay về trang trước đó
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void AnhXa()
    {
        buttonDangKy = findViewById(R.id.button_DangKy_DangNhapActivity);
        buttonDangNhap = findViewById(R.id.button_DangNhap_DangNhapActivity);
        editTextTenTaiKhoan = findViewById(R.id.editText_TenTaiKhoan_DangNhapAvtivity);
        editTextMatKhau = findViewById(R.id.editText_MatKhau_DangNhapActivity);

        imageViewBack = findViewById(R.id.imageView_Back_DangNhapActivity);
    }
}