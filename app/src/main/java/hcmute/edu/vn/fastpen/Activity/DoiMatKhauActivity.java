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

public class DoiMatKhauActivity extends AppCompatActivity {

    EditText editTextMatKhauCu,editTextMatKhauMoi,editTextNhapLai;
    Button buttonDoiMatKhau;
    ImageView imageViewBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);

        Anhxa(); // Ánh xạ các với các biến trong layout
        buttonDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // lấy giá trị từ edittext
                String cu = editTextMatKhauCu.getText().toString().trim();
                String moi = editTextMatKhauMoi.getText().toString().trim();
                String nhaplai = editTextNhapLai.getText().toString().trim();
                // Kiểm tra có nhập đầy đủ thông tin không
                if(cu.equals("")||moi.equals("")||nhaplai.equals(""))
                {
                    Toast.makeText(DoiMatKhauActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    // Kiểm tra mật khẩu cũ có đúng không
                    if(!Global.getTk().getMatKhau().equals(cu)){
                        Toast.makeText(DoiMatKhauActivity.this, "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        // Kiểm tra mật khẩu mới có khác mật khẩu cũ không
                        if(cu.equals(moi)){
                            Toast.makeText(DoiMatKhauActivity.this, "Mật khẩu mới phải khác cũ", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            // Kiểm tra mật khẩu nhập lại có giống mật khảu mới không
                            if(!moi.equals(nhaplai)){
                                Toast.makeText(DoiMatKhauActivity.this, "Nhập lại mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                // Cập nhật lại mật khẩu mới vào Global
                                Global.getTk().setMatKhau(moi);
                                // Cập nhật mật khẩu mới vào firebase
                                DatabaseReference dbref=FirebaseDatabase.getInstance().getReference("TaiKhoan/" + Global.getTk().getTenTaiKhoan());
                                dbref.setValue(Global.getTk());
                                // Thông báo đổi mật khẩu thành công
                                Toast.makeText(DoiMatKhauActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
        //Quay lại trang trước đó
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void Anhxa(){
        editTextMatKhauCu = findViewById(R.id.editText_MatKhauCu_DoiMatKhauActivity);
        editTextMatKhauMoi = findViewById(R.id.editText_MatKhauMoi_DoiMatKhauActivity);
        editTextNhapLai = findViewById(R.id.editText_NhapLaiMatKhauMoi_DoiMatKhauAcivity);

        buttonDoiMatKhau = findViewById(R.id.button_DoiMatKhau_DoiMatKhauActivity);

        imageViewBack = findViewById(R.id.imageView_Back_DoiMatKhauActivity);
    }
}