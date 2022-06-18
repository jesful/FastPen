package hcmute.edu.vn.fastpen.Activity;

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

import hcmute.edu.vn.fastpen.Model.TaiKhoan;
import hcmute.edu.vn.fastpen.R;

public class DangKyActivity extends AppCompatActivity {
    EditText editTextTenTaiKhoan, editTextMatKhau, editTextTen, editTextDiaChi, editTextSoDienThoai, editTextEmail;
    Button buttonDangKy;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        Anhxa(); // Ánh xạ với các biến trong layout
        buttonDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy các giá trị từ các edittext trong layout
                TaiKhoan tk = getTaiKhoan();

                //Kiểm tra có đầy đủ thông tin không
                if(tk.getTenTaiKhoan().equals("")||tk.getMatKhau().equals("")||tk.getTen().equals("")||tk.getDiaChi().equals("")||tk.getEmail().equals("")||tk.getEmail().equals("")){
                    Toast.makeText(DangKyActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Tao đường dẫn lấy giá trị từ bảng tài khoản
                    DatabaseReference dbref=FirebaseDatabase.getInstance().getReference("TaiKhoan/" + tk.getTenTaiKhoan());
                    dbref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                // Nếu lấy được thì tài khoản đã tồn tại
                                Toast.makeText(DangKyActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                // Không lấy được thì thêm tài khoản đó vào bảng TaiKhoan
                                // Đăng ký tài khoản thành công
                                dbref.setValue(tk);
                                Toast.makeText(DangKyActivity.this, "Tạo thành công", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
        // Quay lại trang trước đó
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    public void Anhxa(){
        editTextTenTaiKhoan = findViewById(R.id.edittext_TenTaiKhoan_DangKyActivity);
        editTextMatKhau = findViewById(R.id.edittext_MatKhau_DangKyActivity);
        editTextTen = findViewById(R.id.edittext_Ten_DangKyActivity);
        editTextDiaChi = findViewById(R.id.edittext_DiaChi_DangKyActivity);
        editTextSoDienThoai = findViewById(R.id.edittext_SoDienThoai_DangKyActivity);
        editTextEmail = findViewById(R.id.edittext_Email_DangKyActivity);

        buttonDangKy = findViewById(R.id.button_DangKy_DangKyActivity);

        imageView = findViewById(R.id.imageView_Back_DangKyActivity);
    }
    public TaiKhoan getTaiKhoan(){
        String tentaikhoan = editTextTenTaiKhoan.getText().toString().trim();
        String matkhau = editTextMatKhau.getText().toString().trim();
        String ten = editTextTen.getText().toString().trim();
        String diachi = editTextDiaChi.getText().toString().trim();
        String sodienthoai = editTextSoDienThoai.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        boolean quyen = false;
        TaiKhoan taiKhoan = new TaiKhoan(tentaikhoan,matkhau,ten,diachi,sodienthoai,email,quyen);
        return taiKhoan;
    }
}