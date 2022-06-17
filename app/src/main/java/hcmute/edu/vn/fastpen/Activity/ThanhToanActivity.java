package hcmute.edu.vn.fastpen.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import hcmute.edu.vn.fastpen.Adapter.SanPhamThanhToanAdapter;
import hcmute.edu.vn.fastpen.Global;
import hcmute.edu.vn.fastpen.Model.ChiTietHoaDon;
import hcmute.edu.vn.fastpen.Model.GioHang;
import hcmute.edu.vn.fastpen.Model.HoaDon;
import hcmute.edu.vn.fastpen.Model.TaiKhoan;
import hcmute.edu.vn.fastpen.R;

public class ThanhToanActivity extends AppCompatActivity
{
    private ImageView imgView_HinhAnhThanhToan_ThanhToan;
    private TextView txtView_Ten_ThanhToan, txtView_SDT_ThanhToan, txtView_Email_ThanhToan, txtView_DiaChi_ThanhToan, txtView_PhuongThucThanhToan_ThanhToan, txtView_TienHang_ThanhToan, txtView_PhiVanChuyen_ThanhToan, txtView_GiaTongCong_ThanhToan;
    private EditText editTxt_GhiChu_ThanhToan;
    // Database Reference
    private DatabaseReference dbref;
    // Recycler View object
    private RecyclerView recyclerView_SanPhamTrongGio_ThanhToan;
    // Array list for recycler view data source
    private ArrayList<GioHang> arr_GioHang;
    // Adapter class object
    private SanPhamThanhToanAdapter sanPhamThanhToanAdapter;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        ImageView imgView_QuayVe_ThanhToan = findViewById(R.id.imgView_QuayVe_ThanhToan);
        imgView_QuayVe_ThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtView_Ten_ThanhToan = findViewById(R.id.txtView_Ten_ThanhToan);
        txtView_SDT_ThanhToan = findViewById(R.id.txtView_SDT_ThanhToan);
        txtView_Email_ThanhToan = findViewById(R.id.txtView_Email_ThanhToan);
        txtView_DiaChi_ThanhToan = findViewById(R.id.txtView_DiaChi_ThanhToan);
        GetDataTaiKhoan();

        sharedPreferences = getSharedPreferences("dataGiaoHang", MODE_PRIVATE);
        ImageView imgView_ChonDiaChiNhanHang_ThanhToan = findViewById(R.id.imgView_ChonDiaChiNhanHang_ThanhToan);
        imgView_ChonDiaChiNhanHang_ThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("ten", String.valueOf(txtView_Ten_ThanhToan.getText()));
                editor.putString("sdt", String.valueOf(txtView_SDT_ThanhToan.getText()));
                editor.putString("email", String.valueOf(txtView_Email_ThanhToan.getText()));
                editor.putString("diachi", String.valueOf(txtView_DiaChi_ThanhToan.getText()));
                editor.apply();

                // Tạo một Intent để start SuaDiaChiNhanHangActivity
                Intent intent = new Intent(ThanhToanActivity.this, SuaDiaChiNhanHangActivity.class);
                // Start SuaDiaChiNhanHangActivity với request code là 1
                startActivityForResult(intent,1);
            }
        });

        txtView_TienHang_ThanhToan = findViewById(R.id.txtView_TienHang_ThanhToan);
        txtView_PhiVanChuyen_ThanhToan = findViewById(R.id.txtView_PhiVanChuyen_ThanhToan);
        txtView_GiaTongCong_ThanhToan = findViewById(R.id.txtView_GiaTongCong_ThanhToan);
        arr_GioHang = new ArrayList<>();
        GetDataSanPhamGioHang();

        txtView_PhuongThucThanhToan_ThanhToan = findViewById(R.id.txtView_PhuongThucThanhToan_ThanhToan);
        String ptttMacDinh = "Thanh toán tiền mặt";
        txtView_PhuongThucThanhToan_ThanhToan.setText(ptttMacDinh);
        imgView_HinhAnhThanhToan_ThanhToan = findViewById(R.id.imgView_HinhAnhThanhToan_ThanhToan);
        imgView_HinhAnhThanhToan_ThanhToan.setImageResource(R.drawable.icon_payment_method_cod);

        ImageView imgView_ChonPhuongThucThanhToan_ThanhToan = findViewById(R.id.imgView_ChonPhuongThucThanhToan_ThanhToan);
        imgView_ChonPhuongThucThanhToan_ThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("pttt", String.valueOf(txtView_PhuongThucThanhToan_ThanhToan.getText()));
                editor.putString("th", String.valueOf(txtView_TienHang_ThanhToan.getText()));
                editor.putString("pvch", String.valueOf(txtView_PhiVanChuyen_ThanhToan.getText()));
                editor.putString("tc", String.valueOf(txtView_GiaTongCong_ThanhToan.getText()));
                editor.apply();

                Intent intent = new Intent(ThanhToanActivity.this, PhuongThucThanhToanActivity.class);
                startActivityForResult(intent,2);
            }
        });

        editTxt_GhiChu_ThanhToan = findViewById(R.id.editTxt_GhiChu_ThanhToan);

        LinearLayout linearLayout_DatHang_ThanhToan = findViewById(R.id.linearLayout_DatHang_ThanhToan);
        linearLayout_DatHang_ThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ThemDonDatHang();
            }
        });
    }

    // Khi kết quả được trả về từ Activity khác, hàm onActivityResult sẽ được gọi.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Kiểm tra requestCode có trùng với REQUEST_CODE vừa dùng
        if(requestCode == 1)
        {
            // resultCode được set bởi SuaDiaChiNhanHangActivity
            // RESULT_OK chỉ ra rằng kết quả này đã thành công
            if(resultCode == Activity.RESULT_OK)
            {
                // Nhận dữ liệu từ Intent trả về
                txtView_Ten_ThanhToan.setText(data.getStringExtra("ten"));
                txtView_SDT_ThanhToan.setText(data.getStringExtra("sdt"));
                txtView_Email_ThanhToan.setText(data.getStringExtra("email"));
                txtView_DiaChi_ThanhToan.setText(data.getStringExtra("diachi"));
            }
        }

        if(requestCode == 2)
        {
            // resultCode được set bởi SuaDiaChiNhanHangActivity
            // RESULT_OK chỉ ra rằng kết quả này đã thành công
            if(resultCode == Activity.RESULT_OK)
            {
                // Nhận dữ liệu từ Intent trả về
                String pttt = data.getStringExtra("pttt");
                txtView_PhuongThucThanhToan_ThanhToan.setText(pttt);
                switch (pttt)
                {
                    case "Thanh toán tiền mặt":
                        imgView_HinhAnhThanhToan_ThanhToan.setImageResource(R.drawable.icon_payment_method_cod);
                        break;
                    case "Ví MoMo":
                        imgView_HinhAnhThanhToan_ThanhToan.setImageResource(R.drawable.icon_payment_method_momo);
                        break;
                    case "Ví Zalo Pay":
                        imgView_HinhAnhThanhToan_ThanhToan.setImageResource(R.drawable.icon_payment_method_zalo_pay);
                        break;
                    case "Ví Moca|Grab":
                        imgView_HinhAnhThanhToan_ThanhToan.setImageResource(R.drawable.icon_payment_method_moca);
                        break;
                    case "Viettel Money":
                        imgView_HinhAnhThanhToan_ThanhToan.setImageResource(R.drawable.icon_payment_method_viettelmoney);
                        break;
                    case "VNPAY":
                        imgView_HinhAnhThanhToan_ThanhToan.setImageResource(R.drawable.icon_payment_method_vnpay);
                        break;
                    default:
                        imgView_HinhAnhThanhToan_ThanhToan.setImageResource(R.drawable.icon_payment_method_credit);
                        break;
                }
            }
        }
    }

    private void GetDataTaiKhoan()
    {
        // Lấy dữ liệu trên firebase
        dbref = FirebaseDatabase.getInstance().getReference("TaiKhoan/" + Global.getTenTaiKhoan());
        dbref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Lấy dữ liệu của tài khoản
                TaiKhoan tk = snapshot.getValue(TaiKhoan.class);

                if(tk != null) {
                    // Sau khi đã lấy hết dữ liệu tài khoản thì
                    txtView_Ten_ThanhToan.setText(tk.getTen());
                    txtView_SDT_ThanhToan.setText(tk.getSDT());
                    txtView_Email_ThanhToan.setText(tk.getEmail());
                    txtView_DiaChi_ThanhToan.setText(tk.getDiaChi());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void TinhTongTien(ArrayList<GioHang> arr_GioHang)
    {
        int pvch = 30000;
        String pvch1 = String.valueOf(pvch);
        txtView_PhiVanChuyen_ThanhToan.setText(pvch1);

        int size = arr_GioHang.size();
        final int[] giahang = {0};
        final int[] giatong = {pvch};

        for(int i = 0; i < size; i++)
        {
            int id = arr_GioHang.get(i).getIdSanPham();
            dbref = FirebaseDatabase.getInstance().getReference("SanPham/" + id + "/gia");
            // Lấy dữ liệu của sản phẩm có idSanPham bằng id được lưu trữ để lấy thông tin của sản phẩm
            int finalI = i;
            dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    int gia = snapshot.getValue(Integer.class);

                    giahang[0] = giahang[0] + gia * arr_GioHang.get(finalI).getSoLuong();
                    giatong[0] = giatong[0] + gia * arr_GioHang.get(finalI).getSoLuong();
                    txtView_TienHang_ThanhToan.setText(String.valueOf(giahang[0]));
                    txtView_GiaTongCong_ThanhToan.setText(String.valueOf(giatong[0]));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void GetDataSanPhamGioHang()
    {
        // Lấy dữ liệu sản phẩm trong giỏ và đổ lên recycler view
        // initialisation with id's
        recyclerView_SanPhamTrongGio_ThanhToan = findViewById(R.id.recyclerView_SanPhamTrongGio_ThanhToan);
        // Layout Manager
        RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

        // Set LayoutManager on Recycler View
        recyclerView_SanPhamTrongGio_ThanhToan.setLayoutManager(recyclerViewLayoutManager);

        // Set Horizontal Layout Manager
        // for Recycler view
        // Linear Layout Manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ThanhToanActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView_SanPhamTrongGio_ThanhToan.setLayoutManager(linearLayoutManager);

        // Lấy dữ liệu trên firebase và thêm vào array list
        dbref = FirebaseDatabase.getInstance().getReference("GioHang/" + Global.getTenTaiKhoan());
        dbref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Lấy dữ liệu của các sản phẩm trong giỏ
                // Làm trống mảng
                arr_GioHang.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    GioHang gh = dataSnapshot.getValue(GioHang.class);
                    arr_GioHang.add(gh);
                }

                // Sau khi đã lấy hết dữ liệu xuống mảng thì dùng adapter để đổ dữ liệu lên recycler view
                sanPhamThanhToanAdapter = new SanPhamThanhToanAdapter(arr_GioHang);
                // Set adapter on recycler view
                recyclerView_SanPhamTrongGio_ThanhToan.setAdapter(sanPhamThanhToanAdapter);

                TinhTongTien(arr_GioHang);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ThemDonDatHang()
    {
        dbref = FirebaseDatabase.getInstance().getReference("HoaDon");
        dbref.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                HoaDon hd = null;
                
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    hd = dataSnapshot.getValue(HoaDon.class);
                }

                if(hd != null)
                {
                    // Lấy id hóa đơn từ hàm tự tăng để có id hóa đơn ko trùng với id các hóa đơn cũ
                    Query query = dbref.limitToLast(1);
                    query.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            for (DataSnapshot dataSnapshot: snapshot.getChildren())
                            {
                                HoaDon hd = dataSnapshot.getValue(HoaDon.class);
                                if (hd != null)
                                {
                                    int idhd = hd.getIdHoaDon();
                                    idhd++;
                                    // Lấy thời gian hiện tại ở Việt Nam
                                    Date date = new Date();
                                    Locale locale = new Locale("vi","VN");
                                    SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy HH:mm", locale);
                                    String ngaylaphd = ft.format(date);
                                    // Kiểm tra phương thức thanh toán có phải tiền mặt hay không
                                    boolean tienMat = String.valueOf(txtView_PhuongThucThanhToan_ThanhToan.getText()).equals("Thanh toán tiền mặt");
                                    // Tạo object HoaDon
                                    HoaDon hd1 = new HoaDon(idhd, Global.getTenTaiKhoan(), ngaylaphd, Integer.parseInt(String.valueOf(txtView_PhiVanChuyen_ThanhToan.getText())), Integer.parseInt(String.valueOf(txtView_GiaTongCong_ThanhToan.getText())), 0, tienMat, String.valueOf(txtView_Ten_ThanhToan.getText()), String.valueOf(txtView_SDT_ThanhToan.getText()), String.valueOf(txtView_Email_ThanhToan.getText()), String.valueOf(txtView_DiaChi_ThanhToan.getText()), String.valueOf(editTxt_GhiChu_ThanhToan.getText()));
                                    // Thiết lập reference
                                    dbref = FirebaseDatabase.getInstance().getReference("HoaDon/" + idhd);
                                    // Cập nhật dữ liệu lên firebase
                                    dbref.setValue(hd1);

                                    // Thêm giỏ hàng vào chi tiết hóa đơn
                                    int finalIdhd = idhd;
                                    arr_GioHang.clear();
                                    dbref = FirebaseDatabase.getInstance().getReference("GioHang/" + Global.getTenTaiKhoan());
                                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot dataSnapshot: snapshot.getChildren())
                                            {
                                                GioHang gh = dataSnapshot.getValue(GioHang.class);
                                                if(gh != null)
                                                {
                                                    arr_GioHang.add(gh);
                                                }
                                            }

                                            dbref = FirebaseDatabase.getInstance().getReference("ChiTietHoaDon/" + finalIdhd);
                                            for(int i = 0; i < arr_GioHang.size(); i++)
                                            {
                                                ChiTietHoaDon cthd = new ChiTietHoaDon(finalIdhd, arr_GioHang.get(i).getIdSanPham(), arr_GioHang.get(i).getSoLuong());
                                                // Cập nhật dữ liệu lên firebase
                                                dbref.child(String.valueOf(arr_GioHang.get(i).getIdSanPham())).setValue(cthd);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    // Xóa giỏ hàng hiện tại
                                    dbref = FirebaseDatabase.getInstance().getReference("GioHang/" + Global.getTenTaiKhoan());
                                    dbref.removeValue();

                                    Toast.makeText(getApplicationContext(),"Đặt hàng thành công", Toast.LENGTH_SHORT).show();

                                    // Chuyển về trang chủ
                                    Intent intent = new Intent(ThanhToanActivity.this, MainActivity.class);
                                    // Start SuaDiaChiNhanHangActivity với request code là 1
                                    startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error)
                        {

                        }
                    });
                }
                else
                {
                    // Lấy thời gian hiện tại ở Việt Nam
                    Date date = new Date();
                    Locale locale = new Locale("vi","VN");
                    SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy HH:mm", locale);
                    String ngaylaphd = ft.format(date);
                    // Kiểm tra phương thức thanh toán có phải tiền mặt hay không
                    boolean tienMat = String.valueOf(txtView_PhuongThucThanhToan_ThanhToan.getText()).equals("Thanh toán tiền mặt");
                    // Tạo object HoaDon
                    HoaDon hd1 = new HoaDon(1, Global.getTenTaiKhoan(), ngaylaphd, Integer.parseInt(String.valueOf(txtView_PhiVanChuyen_ThanhToan.getText())), Integer.parseInt(String.valueOf(txtView_GiaTongCong_ThanhToan.getText())), 0, tienMat, String.valueOf(txtView_Ten_ThanhToan.getText()), String.valueOf(txtView_SDT_ThanhToan.getText()), String.valueOf(txtView_Email_ThanhToan.getText()), String.valueOf(txtView_DiaChi_ThanhToan.getText()), String.valueOf(editTxt_GhiChu_ThanhToan.getText()));
                    // Thiết lập reference
                    dbref = FirebaseDatabase.getInstance().getReference("HoaDon/1");
                    // Cập nhật dữ liệu lên firebase
                    dbref.setValue(hd1);

                    // Thêm giỏ hàng vào chi tiết hóa đơn
                    arr_GioHang.clear();
                    dbref = FirebaseDatabase.getInstance().getReference("GioHang/" + Global.getTenTaiKhoan());
                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot: snapshot.getChildren())
                            {
                                GioHang gh = dataSnapshot.getValue(GioHang.class);
                                if(gh != null)
                                {
                                    arr_GioHang.add(gh);
                                }
                            }

                            dbref = FirebaseDatabase.getInstance().getReference("ChiTietHoaDon/1");
                            for(int i = 0; i < arr_GioHang.size(); i++)
                            {
                                ChiTietHoaDon cthd = new ChiTietHoaDon(1, arr_GioHang.get(i).getIdSanPham(), arr_GioHang.get(i).getSoLuong());
                                // Cập nhật dữ liệu lên firebase
                                dbref.child(String.valueOf(arr_GioHang.get(i).getIdSanPham())).setValue(cthd);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    // Xóa giỏ hàng hiện tại
                    dbref = FirebaseDatabase.getInstance().getReference("GioHang/" + Global.getTenTaiKhoan());
                    dbref.removeValue();

                    Toast.makeText(getApplicationContext(),"Đặt hàng thành công", Toast.LENGTH_SHORT).show();

                    // Chuyển về trang chủ
                    Intent intent = new Intent(ThanhToanActivity.this, MainActivity.class);
                    // Start SuaDiaChiNhanHangActivity với request code là 1
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}