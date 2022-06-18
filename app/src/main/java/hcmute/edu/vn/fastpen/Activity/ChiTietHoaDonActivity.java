package hcmute.edu.vn.fastpen.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hcmute.edu.vn.fastpen.Adapter.ChiTietHoaDonAdapter;
import hcmute.edu.vn.fastpen.Model.ChiTietHoaDon;
import hcmute.edu.vn.fastpen.R;

public class ChiTietHoaDonActivity extends AppCompatActivity {
    ListView listView_ChiTietHD;
    ArrayList<ChiTietHoaDon> array_ChiTietHoaDon;
    ChiTietHoaDonAdapter adapter;
    ImageView imageViewBack;
    TextView textViewTen,textViewDiaChi,textViewSDT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hoa_don);
        // Ánh xạ các biến trong layout
        listView_ChiTietHD = (ListView)findViewById(R.id.listView_HoaDon_ChiTietHoaDonActivity) ;
        array_ChiTietHoaDon = new ArrayList<>();
        adapter = new ChiTietHoaDonAdapter(this, R.layout.dong_chi_tiet_hoa_don, array_ChiTietHoaDon);
        listView_ChiTietHD.setAdapter(adapter);

        textViewTen = findViewById(R.id.textView_Ten_ChiTietHoaDonActivity);
        textViewDiaChi = findViewById(R.id.textView_DiaChi_ChiTietHoaDonActivity);
        textViewSDT = findViewById(R.id.textView_SDT_ChiTietHoaDonActivity);

        imageViewBack = findViewById(R.id.imageView_Back_ChiTietHoaDonActivity);

        // Quay lại trang trước
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //Lấy giá trị tên, địa chỉ, sdt của người đặt hàng
        Intent intent = getIntent();
        int id =  intent.getIntExtra("id", 0);
        String ten = intent.getStringExtra("ten");
        String diachi = intent.getStringExtra("diachi");
        String sdt = intent.getStringExtra("sdt");

        // Gán các giá trị tên, địa chỉ, sdt vào textview
        textViewTen.setText(ten);
        textViewDiaChi.setText(diachi);
        textViewSDT.setText(sdt);

        //Lấy data chi tiết hóa đơn từ firebase
        getDataChiTietHoaDon(id);
    }
    public void getDataChiTietHoaDon(int id){
        // Truy cập vào bảng ChiTietHoaDon
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("ChiTietHoaDon");
        array_ChiTietHoaDon.clear();
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    ChiTietHoaDon sp = dataSnapshot.getValue(ChiTietHoaDon.class);
                    if(sp.getIdHoaDon()== id){ // Kiểm tra id để lấy các chi tiết hóa đơn
                        array_ChiTietHoaDon.add(sp); // Thêm chi tiết hóa đơn vào trong mảng
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}