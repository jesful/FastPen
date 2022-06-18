package hcmute.edu.vn.fastpen.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import hcmute.edu.vn.fastpen.Activity.DangNhapActivity;
import hcmute.edu.vn.fastpen.Activity.DoiMatKhauActivity;
import hcmute.edu.vn.fastpen.Activity.MainActivity;
import hcmute.edu.vn.fastpen.Activity.ThongTinCaNhanActivity;
import hcmute.edu.vn.fastpen.Model.Global;
import hcmute.edu.vn.fastpen.R;


public class TaiKhoanFragment extends Fragment {

    View view;
    LinearLayout linearLayoutDangNhap,linearLayoutDangXuat,linearLayoutDoiMatKhau;
    TextView textViewTaiKhoan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_tai_khoan, container, false);
        Anhxa(); // Ánh xạ với các biến trong layout

        // kiểm tra đã dăng nhập chưa
        if(Global.getTk() != null)
        {
            // Nếu đăng nhập rồi cập nhật tên tại khoản vào textview
            textViewTaiKhoan.setText(Global.getTk().getTen());
            linearLayoutDangXuat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Đăng xuất chuyển tới trang main và cập nhập lại tài khoản trong Global bằng null
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    Global.setTk(null);
                    Toast.makeText(getActivity(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            });
            linearLayoutDoiMatKhau.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Chuyển tới trang đổi mật khẩu
                    Intent intent = new Intent(getActivity(), DoiMatKhauActivity.class);
                    startActivity(intent);
                }
            });
            linearLayoutDangNhap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Chuyển tới trang thông tin cá nhân
                    Intent intent = new Intent(getActivity(), ThongTinCaNhanActivity.class);
                    startActivity(intent);
                }
            });

        }
        else{
            // Nếu chưa đăng nhập
            linearLayoutDangNhap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Chuyển tới trang đăng xuất
                    Intent intent = new Intent(getActivity(), DangNhapActivity.class);
                    startActivity(intent);
                }
            });

        }




        return view;
    }

    public void Anhxa(){
        linearLayoutDangNhap = view.findViewById(R.id.linearLayout_DangNhap_TaiKhoanFragment);
        linearLayoutDangXuat = view.findViewById(R.id.linearLayout_DangXuat_TaiKhoanFragment);
        linearLayoutDoiMatKhau = view.findViewById(R.id.linearLayout_DoiMatKhau_TaiKhoanFragment);

        textViewTaiKhoan = view.findViewById(R.id.textView_DangNhap_TaiKhoanFragment);
    }
}