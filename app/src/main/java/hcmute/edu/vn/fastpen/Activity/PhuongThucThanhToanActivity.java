package hcmute.edu.vn.fastpen.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import hcmute.edu.vn.fastpen.R;

public class PhuongThucThanhToanActivity extends AppCompatActivity
{
    // Radio button
    private RadioButton radioBtn_1_PhuongThucThanhToan, radioBtn_2_PhuongThucThanhToan, radioBtn_3_PhuongThucThanhToan, radioBtn_4_PhuongThucThanhToan, radioBtn_5_PhuongThucThanhToan, radioBtn_6_PhuongThucThanhToan, radioBtn_7_PhuongThucThanhToan;
    // Shared Preferences
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Set view hiển thị của activity
        setContentView(R.layout.activity_phuong_thuc_thanh_toan);

        // Click vào icon mũi tên để quay về trang trước đó bằng cách finish activity hiện tại
        ImageView imgView_QuayVe_PhuongThucThanhToan = findViewById(R.id.imgView_QuayVe_PhuongThucThanhToan);
        imgView_QuayVe_PhuongThucThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Nếu quay về trang thanh toán bằng cách này thì sẽ không truyền dữ liệu phương thức thanh toán về bằng intent
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        // Khởi tạo các textview tổng tiền sản phẩm, phí vận chuyển, thành tiền
        TextView txtView_TienHang_PhuongThucThanhToan = findViewById(R.id.txtView_TienHang_PhuongThucThanhToan);
        TextView txtView_PhiVanChuyen_PhuongThucThanhToan = findViewById(R.id.txtView_PhiVanChuyen_PhuongThucThanhToan);
        TextView txtView_ThanhTien_PhuongThucThanhToan = findViewById(R.id.txtView_ThanhTien_PhuongThucThanhToan);
        // Khởi tạo các radio button và linearlayout của các radio button tương ứng
        // Cài đặt chức năng click vào từng linear layout sẽ thay đổi phương thức thanh toán tương ứng
        radioBtn_1_PhuongThucThanhToan = findViewById(R.id.radioBtn_1_PhuongThucThanhToan);
        radioBtn_2_PhuongThucThanhToan = findViewById(R.id.radioBtn_2_PhuongThucThanhToan);
        radioBtn_3_PhuongThucThanhToan = findViewById(R.id.radioBtn_3_PhuongThucThanhToan);
        radioBtn_4_PhuongThucThanhToan = findViewById(R.id.radioBtn_4_PhuongThucThanhToan);
        radioBtn_5_PhuongThucThanhToan = findViewById(R.id.radioBtn_5_PhuongThucThanhToan);
        radioBtn_6_PhuongThucThanhToan = findViewById(R.id.radioBtn_6_PhuongThucThanhToan);
        radioBtn_7_PhuongThucThanhToan = findViewById(R.id.radioBtn_7_PhuongThucThanhToan);

        LinearLayout linearLayout_RadioBtn1_PhuongThucThanhToan = findViewById(R.id.linearLayout_RadioBtn1_PhuongThucThanhToan);
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

        LinearLayout linearLayout_RadioBtn2_PhuongThucThanhToan = findViewById(R.id.linearLayout_RadioBtn2_PhuongThucThanhToan);
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

        LinearLayout linearLayout_RadioBtn3_PhuongThucThanhToan = findViewById(R.id.linearLayout_RadioBtn3_PhuongThucThanhToan);
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

        LinearLayout linearLayout_RadioBtn4_PhuongThucThanhToan = findViewById(R.id.linearLayout_RadioBtn4_PhuongThucThanhToan);
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

        LinearLayout linearLayout_RadioBtn5_PhuongThucThanhToan = findViewById(R.id.linearLayout_RadioBtn5_PhuongThucThanhToan);
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

        LinearLayout linearLayout_RadioBtn6_PhuongThucThanhToan = findViewById(R.id.linearLayout_RadioBtn6_PhuongThucThanhToan);
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

        LinearLayout linearLayout_RadioBtn7_PhuongThucThanhToan = findViewById(R.id.linearLayout_RadioBtn7_PhuongThucThanhToan);
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

        // Khởi tạo shared preferences có tên dataGiaoHang, chế độ MODE_PRIVATE
        sharedPreferences = getSharedPreferences("dataGiaoHang", MODE_PRIVATE);
        // Set dữ liệu các text view bằng dữ liệu được chia sẻ từ shared preferences
        txtView_TienHang_PhuongThucThanhToan.setText(sharedPreferences.getString("th", ""));
        txtView_PhiVanChuyen_PhuongThucThanhToan.setText(sharedPreferences.getString("pvch", ""));
        txtView_ThanhTien_PhuongThucThanhToan.setText(sharedPreferences.getString("tc", ""));

        // Set check cho radio button tương ứng với dữ liệu được chia sẻ từ shared preferences
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

        // Linear layout khi click vào sẽ xác nhận chọn phương thức thanh toán đang được check
        LinearLayout linearLayout_Chon_PhuongThucThanhToan = findViewById(R.id.linearLayout_Chon_PhuongThucThanhToan);
        linearLayout_Chon_PhuongThucThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String pttt;
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

                // Dùng intent putExtra để truyền dữ liệu phương thức thanh toán về activity thanh toán
                Intent data = new Intent();
                // Truyền data vào intent
                data.putExtra("pttt", pttt);
                // Đặt resultCode là Activity.RESULT_OK để thể hiện đã thành công và có chứa kết quả trả về
                setResult(Activity.RESULT_OK, data);
                // gọi hàm finish() để đóng Activity hiện tại và trở về activity thanh toán
                finish();
            }
        });
    }
}