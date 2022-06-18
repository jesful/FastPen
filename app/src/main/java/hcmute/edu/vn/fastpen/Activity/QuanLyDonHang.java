package hcmute.edu.vn.fastpen.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hcmute.edu.vn.fastpen.Adapter.ItemQuanLyDonHangAdapter;
import hcmute.edu.vn.fastpen.Model.HoaDon;
import hcmute.edu.vn.fastpen.Model.ItemQuanLyDonHang;
import hcmute.edu.vn.fastpen.Model.LoadingDialog;
import hcmute.edu.vn.fastpen.R;

public class QuanLyDonHang extends AppCompatActivity {
    // ánh xạ với View để thực hiện các sự kiện khi người dùng thao tác
    private ListView listView;
    private ImageView imageView, img_Add, img_Edit;

    // Chứa dữ liệu và hiển thị lên View
    private ArrayList<ItemQuanLyDonHang> arrayList;
    private ItemQuanLyDonHangAdapter adapter;

    //Lưu dữ liệu, dùng để chuyển dữ liệu qua ChiTietDonHang
    private ArrayList<HoaDon> arrayHoaDon = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_don_hang);
        SetID();
        TaiDuLieu();
        Event();
    }

    // Lấy dữ liệu từ Firebase để gán vào các biến
    private void TaiDuLieu() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("HoaDon");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                arrayHoaDon.clear();
                if(snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        HoaDon hoaDon = dataSnapshot.getValue(HoaDon.class);

                        //GetUser(hoaDon.getTenTaiKhoan());
                        final Handler handler = new Handler();
                        final LoadingDialog dialog = new LoadingDialog(QuanLyDonHang.this);
                        dialog.startLoadingDialog();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                ItemQuanLyDonHang item = new ItemQuanLyDonHang(hoaDon.getIdHoaDon(), hoaDon.getEmail(), hoaDon.getNgay());
                                arrayList.add(item);
                                arrayHoaDon.add(hoaDon);
                                adapter.notifyDataSetChanged();
                                dialog.dismissLoadingDialog();
                            }
                        }, 1500);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // thực thi các sự kiến khi người dùng thao tác
    private void Event() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(QuanLyDonHang.this, ChiTietDonHang.class);
                intent.putExtra("hoaDon", arrayHoaDon.get(i).getIdHoaDon());
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        img_Add.setVisibility(View.INVISIBLE);
        img_Edit.setVisibility(View.INVISIBLE);
    }

    // Gán id vào các biến, cài đặt adapter và thiết lập ban đầu
    private void SetID() {
        listView = findViewById(R.id.list_QuanLyDonHang);
        imageView = findViewById(R.id.img_Back);
        img_Add = findViewById(R.id.img_Add);
        img_Edit = findViewById(R.id.img_Edit);
        arrayList = new ArrayList<>();
        adapter = new ItemQuanLyDonHangAdapter(this, arrayList);

        listView.setAdapter(adapter);
    }
}