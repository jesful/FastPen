package hcmute.edu.vn.fastpen.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import hcmute.edu.vn.fastpen.R;

public class PhuongThucThanhToanActivity extends AppCompatActivity
{
    private ImageView imgView_QuayVe_PhuongThucThanhToan;
    private TextView txtView_TienHang_PhuongThucThanhToan, txtView_PhiVanChuyen_PhuongThucThanhToan, txtView_ThanhTien_PhuongThucThanhToan;
    private LinearLayout linearLayout_Chon_PhuongThucThanhToan, linearLayout_RadioBtn1_PhuongThucThanhToan, linearLayout_RadioBtn2_PhuongThucThanhToan, linearLayout_RadioBtn3_PhuongThucThanhToan, linearLayout_RadioBtn4_PhuongThucThanhToan, linearLayout_RadioBtn5_PhuongThucThanhToan, linearLayout_RadioBtn6_PhuongThucThanhToan, linearLayout_RadioBtn7_PhuongThucThanhToan;
    private RadioButton radioBtn_1_PhuongThucThanhToan, radioBtn_2_PhuongThucThanhToan, radioBtn_3_PhuongThucThanhToan, radioBtn_4_PhuongThucThanhToan, radioBtn_5_PhuongThucThanhToan, radioBtn_6_PhuongThucThanhToan, radioBtn_7_PhuongThucThanhToan;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phuong_thuc_thanh_toan);

        imgView_QuayVe_PhuongThucThanhToan = findViewById(R.id.imgView_QuayVe_PhuongThucThanhToan);
        imgView_QuayVe_PhuongThucThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        txtView_TienHang_PhuongThucThanhToan = findViewById(R.id.txtView_TienHang_PhuongThucThanhToan);
        txtView_PhiVanChuyen_PhuongThucThanhToan = findViewById(R.id.txtView_PhiVanChuyen_PhuongThucThanhToan);
        txtView_ThanhTien_PhuongThucThanhToan = findViewById(R.id.txtView_ThanhTien_PhuongThucThanhToan);
        radioBtn_1_PhuongThucThanhToan = findViewById(R.id.radioBtn_1_PhuongThucThanhToan);
        radioBtn_2_PhuongThucThanhToan = findViewById(R.id.radioBtn_2_PhuongThucThanhToan);
        radioBtn_3_PhuongThucThanhToan = findViewById(R.id.radioBtn_3_PhuongThucThanhToan);
        radioBtn_4_PhuongThucThanhToan = findViewById(R.id.radioBtn_4_PhuongThucThanhToan);
        radioBtn_5_PhuongThucThanhToan = findViewById(R.id.radioBtn_5_PhuongThucThanhToan);
        radioBtn_6_PhuongThucThanhToan = findViewById(R.id.radioBtn_6_PhuongThucThanhToan);
        radioBtn_7_PhuongThucThanhToan = findViewById(R.id.radioBtn_7_PhuongThucThanhToan);

        linearLayout_RadioBtn1_PhuongThucThanhToan = findViewById(R.id.linearLayout_RadioBtn1_PhuongThucThanhToan);
        linearLayout_RadioBtn1_PhuongThucThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtn_1_PhuongThucThanhToan.setChecked(true);
                radioBtn_2_PhuongThucThanhToan.setChecked(false);
                radioBtn_3_PhuongThucThanhToan.setChecked(false);
                radioBtn_4_PhuongThucThanhToan.setChecked(false);
                radioBtn_5_PhuongThucThanhToan.setChecked(false);
                radioBtn_6_PhuongThucThanhToan.setChecked(false);
                radioBtn_7_PhuongThucThanhToan.setChecked(false);
            }
        });

        radioBtn_1_PhuongThucThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtn_2_PhuongThucThanhToan.setChecked(false);
                radioBtn_3_PhuongThucThanhToan.setChecked(false);
                radioBtn_4_PhuongThucThanhToan.setChecked(false);
                radioBtn_5_PhuongThucThanhToan.setChecked(false);
                radioBtn_6_PhuongThucThanhToan.setChecked(false);
                radioBtn_7_PhuongThucThanhToan.setChecked(false);
            }
        });

        linearLayout_RadioBtn2_PhuongThucThanhToan = findViewById(R.id.linearLayout_RadioBtn2_PhuongThucThanhToan);
        linearLayout_RadioBtn2_PhuongThucThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtn_1_PhuongThucThanhToan.setChecked(false);
                radioBtn_2_PhuongThucThanhToan.setChecked(true);
                radioBtn_3_PhuongThucThanhToan.setChecked(false);
                radioBtn_4_PhuongThucThanhToan.setChecked(false);
                radioBtn_5_PhuongThucThanhToan.setChecked(false);
                radioBtn_6_PhuongThucThanhToan.setChecked(false);
                radioBtn_7_PhuongThucThanhToan.setChecked(false);
            }
        });

        radioBtn_2_PhuongThucThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtn_1_PhuongThucThanhToan.setChecked(false);
                radioBtn_3_PhuongThucThanhToan.setChecked(false);
                radioBtn_4_PhuongThucThanhToan.setChecked(false);
                radioBtn_5_PhuongThucThanhToan.setChecked(false);
                radioBtn_6_PhuongThucThanhToan.setChecked(false);
                radioBtn_7_PhuongThucThanhToan.setChecked(false);
            }
        });

        linearLayout_RadioBtn3_PhuongThucThanhToan = findViewById(R.id.linearLayout_RadioBtn3_PhuongThucThanhToan);
        linearLayout_RadioBtn3_PhuongThucThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtn_1_PhuongThucThanhToan.setChecked(false);
                radioBtn_2_PhuongThucThanhToan.setChecked(false);
                radioBtn_3_PhuongThucThanhToan.setChecked(true);
                radioBtn_4_PhuongThucThanhToan.setChecked(false);
                radioBtn_5_PhuongThucThanhToan.setChecked(false);
                radioBtn_6_PhuongThucThanhToan.setChecked(false);
                radioBtn_7_PhuongThucThanhToan.setChecked(false);
            }
        });

        radioBtn_3_PhuongThucThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtn_1_PhuongThucThanhToan.setChecked(false);
                radioBtn_2_PhuongThucThanhToan.setChecked(false);
                radioBtn_4_PhuongThucThanhToan.setChecked(false);
                radioBtn_5_PhuongThucThanhToan.setChecked(false);
                radioBtn_6_PhuongThucThanhToan.setChecked(false);
                radioBtn_7_PhuongThucThanhToan.setChecked(false);
            }
        });

        linearLayout_RadioBtn4_PhuongThucThanhToan = findViewById(R.id.linearLayout_RadioBtn4_PhuongThucThanhToan);
        linearLayout_RadioBtn4_PhuongThucThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtn_1_PhuongThucThanhToan.setChecked(false);
                radioBtn_2_PhuongThucThanhToan.setChecked(false);
                radioBtn_3_PhuongThucThanhToan.setChecked(false);
                radioBtn_4_PhuongThucThanhToan.setChecked(true);
                radioBtn_5_PhuongThucThanhToan.setChecked(false);
                radioBtn_6_PhuongThucThanhToan.setChecked(false);
                radioBtn_7_PhuongThucThanhToan.setChecked(false);
            }
        });

        radioBtn_4_PhuongThucThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtn_1_PhuongThucThanhToan.setChecked(false);
                radioBtn_2_PhuongThucThanhToan.setChecked(false);
                radioBtn_3_PhuongThucThanhToan.setChecked(false);
                radioBtn_5_PhuongThucThanhToan.setChecked(false);
                radioBtn_6_PhuongThucThanhToan.setChecked(false);
                radioBtn_7_PhuongThucThanhToan.setChecked(false);
            }
        });

        linearLayout_RadioBtn5_PhuongThucThanhToan = findViewById(R.id.linearLayout_RadioBtn5_PhuongThucThanhToan);
        linearLayout_RadioBtn5_PhuongThucThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtn_1_PhuongThucThanhToan.setChecked(false);
                radioBtn_2_PhuongThucThanhToan.setChecked(false);
                radioBtn_3_PhuongThucThanhToan.setChecked(false);
                radioBtn_4_PhuongThucThanhToan.setChecked(false);
                radioBtn_5_PhuongThucThanhToan.setChecked(true);
                radioBtn_6_PhuongThucThanhToan.setChecked(false);
                radioBtn_7_PhuongThucThanhToan.setChecked(false);
            }
        });

        radioBtn_5_PhuongThucThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtn_1_PhuongThucThanhToan.setChecked(false);
                radioBtn_2_PhuongThucThanhToan.setChecked(false);
                radioBtn_3_PhuongThucThanhToan.setChecked(false);
                radioBtn_4_PhuongThucThanhToan.setChecked(false);
                radioBtn_6_PhuongThucThanhToan.setChecked(false);
                radioBtn_7_PhuongThucThanhToan.setChecked(false);
            }
        });

        linearLayout_RadioBtn6_PhuongThucThanhToan = findViewById(R.id.linearLayout_RadioBtn6_PhuongThucThanhToan);
        linearLayout_RadioBtn6_PhuongThucThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtn_1_PhuongThucThanhToan.setChecked(false);
                radioBtn_2_PhuongThucThanhToan.setChecked(false);
                radioBtn_3_PhuongThucThanhToan.setChecked(false);
                radioBtn_4_PhuongThucThanhToan.setChecked(false);
                radioBtn_5_PhuongThucThanhToan.setChecked(false);
                radioBtn_6_PhuongThucThanhToan.setChecked(true);
                radioBtn_7_PhuongThucThanhToan.setChecked(false);
            }
        });

        radioBtn_6_PhuongThucThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtn_1_PhuongThucThanhToan.setChecked(false);
                radioBtn_2_PhuongThucThanhToan.setChecked(false);
                radioBtn_3_PhuongThucThanhToan.setChecked(false);
                radioBtn_4_PhuongThucThanhToan.setChecked(false);
                radioBtn_5_PhuongThucThanhToan.setChecked(false);
                radioBtn_7_PhuongThucThanhToan.setChecked(false);
            }
        });

        linearLayout_RadioBtn7_PhuongThucThanhToan = findViewById(R.id.linearLayout_RadioBtn7_PhuongThucThanhToan);
        linearLayout_RadioBtn7_PhuongThucThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtn_1_PhuongThucThanhToan.setChecked(false);
                radioBtn_2_PhuongThucThanhToan.setChecked(false);
                radioBtn_3_PhuongThucThanhToan.setChecked(false);
                radioBtn_4_PhuongThucThanhToan.setChecked(false);
                radioBtn_5_PhuongThucThanhToan.setChecked(false);
                radioBtn_6_PhuongThucThanhToan.setChecked(false);
                radioBtn_7_PhuongThucThanhToan.setChecked(true);
            }
        });

        radioBtn_7_PhuongThucThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtn_1_PhuongThucThanhToan.setChecked(false);
                radioBtn_2_PhuongThucThanhToan.setChecked(false);
                radioBtn_3_PhuongThucThanhToan.setChecked(false);
                radioBtn_4_PhuongThucThanhToan.setChecked(false);
                radioBtn_5_PhuongThucThanhToan.setChecked(false);
                radioBtn_6_PhuongThucThanhToan.setChecked(false);
            }
        });

        sharedPreferences = getSharedPreferences("dataGiaoHang", MODE_PRIVATE);
        txtView_TienHang_PhuongThucThanhToan.setText(sharedPreferences.getString("th", ""));
        txtView_PhiVanChuyen_PhuongThucThanhToan.setText(sharedPreferences.getString("pvch", ""));
        txtView_ThanhTien_PhuongThucThanhToan.setText(sharedPreferences.getString("tc", ""));

        String pttt = sharedPreferences.getString("pttt", "");
        switch (pttt)
        {
            case "Thanh toán tiền mặt":
                radioBtn_1_PhuongThucThanhToan.setChecked(true);
                break;
            case "Ví MoMo":
                radioBtn_2_PhuongThucThanhToan.setChecked(true);
                break;
            case "Ví Zalo Pay":
                radioBtn_3_PhuongThucThanhToan.setChecked(true);
                break;
            case "Ví Moca|Grab":
                radioBtn_4_PhuongThucThanhToan.setChecked(true);
                break;
            case "Viettel Money":
                radioBtn_5_PhuongThucThanhToan.setChecked(true);
                break;
            case "VNPAY":
                radioBtn_6_PhuongThucThanhToan.setChecked(true);
                break;
            default:
                radioBtn_7_PhuongThucThanhToan.setChecked(true);
                break;
        }

        linearLayout_Chon_PhuongThucThanhToan = findViewById(R.id.linearLayout_Chon_PhuongThucThanhToan);
        linearLayout_Chon_PhuongThucThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String pttt = "";
                if(radioBtn_1_PhuongThucThanhToan.isChecked())
                    pttt = "Thanh toán tiền mặt";
                else
                    if(radioBtn_2_PhuongThucThanhToan.isChecked())
                        pttt = "Ví MoMo";
                    else
                        if(radioBtn_3_PhuongThucThanhToan.isChecked())
                            pttt = "Ví Zalo Pay";
                        else
                            if(radioBtn_4_PhuongThucThanhToan.isChecked())
                                pttt = "Ví Moca|Grab";
                            else
                                if(radioBtn_5_PhuongThucThanhToan.isChecked())
                                    pttt = "Viettel Money";
                                else
                                    if(radioBtn_6_PhuongThucThanhToan.isChecked())
                                        pttt = "VNPAY";
                                    else
                                        pttt = "Thẻ ATM, Visa, Master, JCB";
                Intent data = new Intent();

                // Truyền data vào intent
                data.putExtra("pttt", pttt);

                // Đặt resultCode là Activity.RESULT_OK để thể hiện đã thành công và có chứa kết quả trả về
                setResult(Activity.RESULT_OK, data);

                // gọi hàm finish() để đóng Activity hiện tại và trở về MainActivity.
                finish();
            }
        });
    }


}