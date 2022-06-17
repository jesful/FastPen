package hcmute.edu.vn.fastpen.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import hcmute.edu.vn.fastpen.Adapter.BinhLuanChiTietSanPhamAdapter;
import hcmute.edu.vn.fastpen.Adapter.SanPhamChiTietSanPhamAdapter;
import hcmute.edu.vn.fastpen.Model.BinhLuan;
import hcmute.edu.vn.fastpen.Model.DanhMuc;
import hcmute.edu.vn.fastpen.Model.SanPham;
import hcmute.edu.vn.fastpen.Model.ThuongHieu;
import hcmute.edu.vn.fastpen.R;

public class TatCaBinhLuanActivity extends AppCompatActivity
{
    private TextView txtView_SoSaoSanPham_TatCaBinhLuan, txtView_SLBinhLuan_TatCaBinhLuan, txtView_SLBinhLuan5S_TatCaBinhLuan, txtView_SLBinhLuan4S_TatCaBinhLuan, txtView_SLBinhLuan3S_TatCaBinhLuan, txtView_SLBinhLuan2S_TatCaBinhLuan, txtView_SLBinhLuan1S_TatCaBinhLuan;
    private RatingBar ratingBar_SoSaoSanPham_TatCaBinhLuan;
    private LinearLayout linearLayout_LocBinhLuan5S_TatCaBinhLuan, linearLayout_LocBinhLuan4S_TatCaBinhLuan, linearLayout_LocBinhLuan3S_TatCaBinhLuan, linearLayout_LocBinhLuan2S_TatCaBinhLuan, linearLayout_LocBinhLuan1S_TatCaBinhLuan;
    // Object
    private BinhLuan bl;
    // Tổng số lượng bình luận của sản phẩm đang được hiển thị
    private int slbl;
    // Tổng số sao đánh giá của sản phẩm đang được hiển thị
    private float sosao;
    // Số lượng đánh giá 5 sao
    private int sosao5;
    // Số lượng đánh giá 4 sao
    private int sosao4;
    // Số lượng đánh giá 3 sao
    private int sosao3;
    // Số lượng đánh giá 2 sao
    private int sosao2;
    // Số lượng đánh giá 1 sao
    private int sosao1;
    private int id;
    // Database Reference
    private DatabaseReference dbref;
    // Recycler View object
    private RecyclerView recyclerView_BinhLuan_TatCaBinhLuan;
    // Array list for recycler view data source
    private ArrayList<BinhLuan> arr_BinhLuan;
    private ArrayList<Integer> arr_LocBinhLuan;
    // Layout Manager
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    // Adapter class object
    private BinhLuanChiTietSanPhamAdapter binhLuanChiTietSanPhamAdapter;
    // Linear Layout Manager
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tat_ca_binh_luan);

        //
        ImageView imgView_QuayVe_TatCaBinhLuan = findViewById(R.id.imgView_QuayVe_TatCaBinhLuan);
        imgView_QuayVe_TatCaBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Lấy id sản phẩm đã lưu trữ bằng getExtra
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        // Số lượng bình luận của khách hàng đối với sản phẩm trong phần bình luận
        txtView_SLBinhLuan_TatCaBinhLuan = findViewById(R.id.txtView_SLBinhLuan_TatCaBinhLuan);
        // Tổng số sao đánh giá của khách bình luận trong phần bình luận
        ratingBar_SoSaoSanPham_TatCaBinhLuan = findViewById(R.id.ratingBar_SoSaoSanPham_TatCaBinhLuan);
        // Tổng số sao đánh giá hiển thị bằng số của khách đánh giá trong phần bình luận
        txtView_SoSaoSanPham_TatCaBinhLuan = findViewById(R.id.txtView_SoSaoSanPham_TatCaBinhLuan);
        txtView_SLBinhLuan5S_TatCaBinhLuan = findViewById(R.id.txtView_SLBinhLuan5S_TatCaBinhLuan);
        txtView_SLBinhLuan4S_TatCaBinhLuan = findViewById(R.id.txtView_SLBinhLuan4S_TatCaBinhLuan);
        txtView_SLBinhLuan3S_TatCaBinhLuan = findViewById(R.id.txtView_SLBinhLuan3S_TatCaBinhLuan);
        txtView_SLBinhLuan2S_TatCaBinhLuan = findViewById(R.id.txtView_SLBinhLuan2S_TatCaBinhLuan);
        txtView_SLBinhLuan1S_TatCaBinhLuan = findViewById(R.id.txtView_SLBinhLuan1S_TatCaBinhLuan);

        // Lấy dữ liệu bình luận và đổ lên recycler view
        // initialisation with id's
        recyclerView_BinhLuan_TatCaBinhLuan = findViewById(R.id.recyclerView_BinhLuan_TatCaBinhLuan);
        recyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

        // Set LayoutManager on Recycler View
        recyclerView_BinhLuan_TatCaBinhLuan.setLayoutManager(recyclerViewLayoutManager);

        // Set Horizontal Layout Manager
        // for Recycler view
        linearLayoutManager = new LinearLayoutManager(TatCaBinhLuanActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView_BinhLuan_TatCaBinhLuan.setLayoutManager(linearLayoutManager);
        arr_BinhLuan = new ArrayList<>();
        GetDataBinhLuan();

        arr_LocBinhLuan = new ArrayList<>(Collections.nCopies(5, 0));
        linearLayout_LocBinhLuan5S_TatCaBinhLuan = findViewById(R.id.linearLayout_LocBinhLuan5S_TatCaBinhLuan);
        linearLayout_LocBinhLuan5S_TatCaBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(arr_LocBinhLuan.get(4) == 1)
                {
                    arr_LocBinhLuan.set(4, 0);
                    linearLayout_LocBinhLuan5S_TatCaBinhLuan.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                }
                else
                {
                    arr_LocBinhLuan.set(4, 1);
                    linearLayout_LocBinhLuan5S_TatCaBinhLuan.setBackgroundResource(R.drawable.border_linearlayout_loctheososao_tatcabinhluan);
                }
                GetDataLocBinhLuan();
            }
        });

        linearLayout_LocBinhLuan4S_TatCaBinhLuan = findViewById(R.id.linearLayout_LocBinhLuan4S_TatCaBinhLuan);
        linearLayout_LocBinhLuan4S_TatCaBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arr_LocBinhLuan.get(3) == 1)
                {
                    arr_LocBinhLuan.set(3, 0);
                    linearLayout_LocBinhLuan4S_TatCaBinhLuan.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                }
                else
                {
                    arr_LocBinhLuan.set(3, 1);
                    linearLayout_LocBinhLuan4S_TatCaBinhLuan.setBackgroundResource(R.drawable.border_linearlayout_loctheososao_tatcabinhluan);
                }
                GetDataLocBinhLuan();
            }
        });

        linearLayout_LocBinhLuan3S_TatCaBinhLuan = findViewById(R.id.linearLayout_LocBinhLuan3S_TatCaBinhLuan);
        linearLayout_LocBinhLuan3S_TatCaBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arr_LocBinhLuan.get(2) == 1)
                {
                    arr_LocBinhLuan.set(2, 0);
                    linearLayout_LocBinhLuan3S_TatCaBinhLuan.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                }
                else
                {
                    arr_LocBinhLuan.set(2, 1);
                    linearLayout_LocBinhLuan3S_TatCaBinhLuan.setBackgroundResource(R.drawable.border_linearlayout_loctheososao_tatcabinhluan);
                }
                GetDataLocBinhLuan();
            }
        });

        linearLayout_LocBinhLuan2S_TatCaBinhLuan = findViewById(R.id.linearLayout_LocBinhLuan2S_TatCaBinhLuan);
        linearLayout_LocBinhLuan2S_TatCaBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arr_LocBinhLuan.get(1) == 1)
                {
                    arr_LocBinhLuan.set(1, 0);
                    linearLayout_LocBinhLuan2S_TatCaBinhLuan.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                }
                else
                {
                    arr_LocBinhLuan.set(1, 1);
                    linearLayout_LocBinhLuan2S_TatCaBinhLuan.setBackgroundResource(R.drawable.border_linearlayout_loctheososao_tatcabinhluan);
                }
                GetDataLocBinhLuan();
            }
        });

        linearLayout_LocBinhLuan1S_TatCaBinhLuan = findViewById(R.id.linearLayout_LocBinhLuan1S_TatCaBinhLuan);
        linearLayout_LocBinhLuan1S_TatCaBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arr_LocBinhLuan.get(0) == 1)
                {
                    arr_LocBinhLuan.set(0, 0);
                    linearLayout_LocBinhLuan1S_TatCaBinhLuan.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                }
                else
                {
                    arr_LocBinhLuan.set(0, 1);
                    linearLayout_LocBinhLuan1S_TatCaBinhLuan.setBackgroundResource(R.drawable.border_linearlayout_loctheososao_tatcabinhluan);
                }
                GetDataLocBinhLuan();
            }
        });
    }

    private void GetDataBinhLuan()
    {
        // Lấy dữ liệu của các bình luận của sản phẩm đang được hiển thị thông tin chi tiết
        // Lấy dữ liệu trên firebase và thêm vào array list
        dbref = FirebaseDatabase.getInstance().getReference("BinhLuan");
        Query query = dbref.orderByChild("idSanPham").equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Thiết lập giá trị ban đầu của tổng số lượng bình luận tổng đánh giá sao của sản phẩm đang được hiển thị
                slbl = 0;
                sosao = 0;

                // Làm trống mảng
                arr_BinhLuan.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    // Lấy dữ liệu của 1 bình luận
                    bl = dataSnapshot.getValue(BinhLuan.class);

                    if(bl != null)
                    {
                        // Thêm dữ liệu vừa lấy vào array list BinhLuan
                        arr_BinhLuan.add(bl);
                        // Số lượng bình luận của sản phẩm tăng thêm 1
                        slbl++;
                        int sosaotam = bl.getSoSao();
                        // Số sao đánh giá của bình luận được cộng vào tổng số sao đánh giá bình luận của sản phẩm
                        sosao = sosao + sosaotam;
                        if (sosaotam == 5)
                        {
                            sosao5++;
                        }
                        else if (sosaotam == 4)
                        {
                            sosao4++;
                        }
                        else if (sosaotam == 3)
                        {
                            sosao3++;
                        }
                        else if (sosaotam == 2)
                        {
                            sosao2++;
                        }
                        else
                        {
                            sosao1++;
                        }
                    }
                }

                // Sau khi đã lấy hết dữ liệu và thêm vào mảng thì dùng adapter để đổ dữ liệu lên recycler view
                binhLuanChiTietSanPhamAdapter = new BinhLuanChiTietSanPhamAdapter(arr_BinhLuan);
                // Set adapter on recycler view
                recyclerView_BinhLuan_TatCaBinhLuan.setAdapter(binhLuanChiTietSanPhamAdapter);
                //
                txtView_SoSaoSanPham_TatCaBinhLuan.setText(String.valueOf(Math.round(sosao/slbl * 10) / 10.0));
                txtView_SLBinhLuan5S_TatCaBinhLuan.setText(String.valueOf(sosao5));
                txtView_SLBinhLuan4S_TatCaBinhLuan.setText(String.valueOf(sosao4));
                txtView_SLBinhLuan3S_TatCaBinhLuan.setText(String.valueOf(sosao3));
                txtView_SLBinhLuan2S_TatCaBinhLuan.setText(String.valueOf(sosao2));
                txtView_SLBinhLuan1S_TatCaBinhLuan.setText(String.valueOf(sosao1));
                ratingBar_SoSaoSanPham_TatCaBinhLuan.setRating(sosao/slbl);
                txtView_SLBinhLuan_TatCaBinhLuan.setText(String.valueOf(slbl));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void GetDataLocBinhLuan()
    {
        // Lấy dữ liệu của các bình luận của sản phẩm đang được hiển thị thông tin chi tiết
        // Lấy dữ liệu trên firebase và thêm vào array list
        dbref = FirebaseDatabase.getInstance().getReference("BinhLuan");
        Query query = dbref.orderByChild("idSanPham").equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Làm trống mảng
                arr_BinhLuan.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    // Lấy dữ liệu của 1 bình luận
                    bl = dataSnapshot.getValue(BinhLuan.class);

                    if(bl != null)
                    {
                        // Thêm dữ liệu vừa lấy vào array list BinhLuan
                        arr_BinhLuan.add(bl);
                    }
                }

                ArrayList<Integer> arr_LocBinhLuan2 = new ArrayList<>();
                for(int i = 0; i < 5; i++)
                {
                    if(arr_LocBinhLuan.get(i) == 1)
                    {
                        arr_LocBinhLuan2.add(i+1);
                    }
                }

                if(arr_LocBinhLuan2.size() == 0 || arr_LocBinhLuan2.size() == 5)
                {
                    // Sau khi đã lấy hết dữ liệu và thêm vào mảng thì dùng adapter để đổ dữ liệu lên recycler view
                    binhLuanChiTietSanPhamAdapter = new BinhLuanChiTietSanPhamAdapter(arr_BinhLuan);
                }
                else
                {
                    ArrayList<BinhLuan> arr_BinhLuanTemp = new ArrayList<>();
                    for(int i = 0; i < arr_BinhLuan.size(); i++)
                    {
                        for (int j = 0; j < arr_LocBinhLuan2.size(); j++)
                        {
                            if(arr_BinhLuan.get(i).getSoSao() == arr_LocBinhLuan2.get(j))
                            {
                                arr_BinhLuanTemp.add(arr_BinhLuan.get(i));
                                break;
                            }
                        }
                    }

                    // Sau khi đã lấy hết dữ liệu và thêm vào mảng thì dùng adapter để đổ dữ liệu lên recycler view
                    binhLuanChiTietSanPhamAdapter = new BinhLuanChiTietSanPhamAdapter(arr_BinhLuanTemp);
                }

                // Set adapter on recycler view
                recyclerView_BinhLuan_TatCaBinhLuan.setAdapter(binhLuanChiTietSanPhamAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}