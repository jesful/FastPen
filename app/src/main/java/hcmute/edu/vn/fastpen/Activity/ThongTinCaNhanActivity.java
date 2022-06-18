package hcmute.edu.vn.fastpen.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hcmute.edu.vn.fastpen.Model.Global;
import hcmute.edu.vn.fastpen.R;

public class ThongTinCaNhanActivity extends AppCompatActivity {

    EditText editTextTen,editTextDiaChi,editTextSoDienThoai,editTextEmail;
    Button buttonCapNhat;
    ImageView imageViewBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_ca_nhan);
        Anhxa(); // Ánh xạ với các biến trong layout

        // Lấy các giá trị tên, địa chỉ, SDT, Email của tài khoản từ Global
        editTextTen.setText(Global.getTk().getTen());
        editTextDiaChi.setText(Global.getTk().getDiaChi());
        editTextSoDienThoai.setText(Global.getTk().getSDT());
        editTextEmail.setText(Global.getTk().getEmail());
        buttonCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy các giá trị từ edittext
                String ten = editTextTen.getText().toString().trim();
                String diachi = editTextDiaChi.getText().toString().trim();
                String dienthoai = editTextSoDienThoai.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                //Kiểm tra các giá trị có nhập đủ không
                if(ten.equals("")||diachi.equals("")||dienthoai.equals("")||email.equals("")){
                    Toast.makeText(ThongTinCaNhanActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Cập nhât các giá trị mới vào Global
                    Global.getTk().setTen(ten);
                    Global.getTk().setDiaChi(diachi);
                    Global.getTk().setSDT(dienthoai);
                    Global.getTk().setEmail(email);

                    //Cập nhập các giá trị vào firebase
                    DatabaseReference dbref=FirebaseDatabase.getInstance().getReference("TaiKhoan/" + Global.getTk().getTenTaiKhoan());
                    dbref.setValue(Global.getTk());
                    // thông báo cập nhật thành công
                    Toast.makeText(ThongTinCaNhanActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();

                }

            }
        });
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void Anhxa(){
        editTextTen = findViewById(R.id.editText_Ten_ThongTinCaNhanActivity);
        editTextDiaChi = findViewById(R.id.editText_DiaChi_ThongTinCaNhanActivity);
        editTextSoDienThoai = findViewById(R.id.editText_SoDienThoai_ThongTinCaNhanActivity);
        editTextEmail = findViewById(R.id.edittext_Email_ThongTinCaNhanActivity);

        buttonCapNhat = findViewById(R.id.button_CapNhatThongTin_ThongTinCaNhanActivity);

        imageViewBack = findViewById(R.id.imageView_Back_ThongTinCaNhanActivity);
    }
}