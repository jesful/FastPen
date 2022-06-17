package hcmute.edu.vn.fastpen.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hcmute.edu.vn.fastpen.Global;
import hcmute.edu.vn.fastpen.Model.TaiKhoan;
import hcmute.edu.vn.fastpen.R;

public class SuaDiaChiNhanHangActivity extends AppCompatActivity
{
    private ImageView imgView_QuayVe_SuaDiaChiNhanHang;
    private EditText editTxt_Ten_SuaDiaChiNhanHang, editTxt_SDT_SuaDiaChiNhanHang, editTxt_Email_SuaDiaChiNhanHang, editTxt_DiaChi_SuaDiaChiNhanHang;
    private LinearLayout linearLayout_XacNhan_SuaDiaChiNhanHang;
    // Database Reference
    private DatabaseReference dbref;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_dia_chi_nhan_hang);

        imgView_QuayVe_SuaDiaChiNhanHang = findViewById(R.id.imgView_QuayVe_SuaDiaChiNhanHang);
        imgView_QuayVe_SuaDiaChiNhanHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        editTxt_Ten_SuaDiaChiNhanHang = findViewById(R.id.editTxt_Ten_SuaDiaChiNhanHang);
        editTxt_SDT_SuaDiaChiNhanHang = findViewById(R.id.editTxt_SDT_SuaDiaChiNhanHang);
        editTxt_Email_SuaDiaChiNhanHang = findViewById(R.id.editTxt_Email_SuaDiaChiNhanHang);
        editTxt_DiaChi_SuaDiaChiNhanHang = findViewById(R.id.editTxt_DiaChi_SuaDiaChiNhanHang);

        sharedPreferences = getSharedPreferences("dataGiaoHang", MODE_PRIVATE);
        editTxt_Ten_SuaDiaChiNhanHang.setText(sharedPreferences.getString("ten", ""));
        editTxt_SDT_SuaDiaChiNhanHang.setText(sharedPreferences.getString("sdt", ""));
        editTxt_Email_SuaDiaChiNhanHang.setText(sharedPreferences.getString("email", ""));
        editTxt_DiaChi_SuaDiaChiNhanHang.setText(sharedPreferences.getString("diachi", ""));

        linearLayout_XacNhan_SuaDiaChiNhanHang = findViewById(R.id.linearLayout_XacNhan_SuaDiaChiNhanHang);
        linearLayout_XacNhan_SuaDiaChiNhanHang.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                // Tạo một Intent mới để chứa dữ liệu trả về
                Intent data = new Intent();

                // Truyền data vào intent
                data.putExtra("ten", String.valueOf(editTxt_Ten_SuaDiaChiNhanHang.getText()));
                data.putExtra("sdt", String.valueOf(editTxt_SDT_SuaDiaChiNhanHang.getText()));
                data.putExtra("email", String.valueOf(editTxt_Email_SuaDiaChiNhanHang.getText()));
                data.putExtra("diachi", String.valueOf(editTxt_DiaChi_SuaDiaChiNhanHang.getText()));

                // Đặt resultCode là Activity.RESULT_OK để thể hiện đã thành công và có chứa kết quả trả về
                setResult(Activity.RESULT_OK, data);

                // gọi hàm finish() để đóng Activity hiện tại và trở về MainActivity.
                finish();
            }
        });
    }
}