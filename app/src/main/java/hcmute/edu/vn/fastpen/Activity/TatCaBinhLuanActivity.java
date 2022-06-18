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
import java.util.Collections;

import hcmute.edu.vn.fastpen.Adapter.BinhLuanChiTietSanPhamAdapter;
import hcmute.edu.vn.fastpen.Model.BinhLuan;
import hcmute.edu.vn.fastpen.R;

public class TatCaBinhLuanActivity extends AppCompatActivity
{
    // Text view
    private TextView txtView_SoSaoSanPham_TatCaBinhLuan, txtView_SLBinhLuan_TatCaBinhLuan, txtView_SLBinhLuan5S_TatCaBinhLuan, txtView_SLBinhLuan4S_TatCaBinhLuan, txtView_SLBinhLuan3S_TatCaBinhLuan, txtView_SLBinhLuan2S_TatCaBinhLuan, txtView_SLBinhLuan1S_TatCaBinhLuan;
    // Rating bar
    private RatingBar ratingBar_SoSaoSanPham_TatCaBinhLuan;
    // Linear layout
    private LinearLayout linearLayout_LocBinhLuan5S_TatCaBinhLuan, linearLayout_LocBinhLuan4S_TatCaBinhLuan, linearLayout_LocBinhLuan3S_TatCaBinhLuan, linearLayout_LocBinhLuan2S_TatCaBinhLuan, linearLayout_LocBinhLuan1S_TatCaBinhLuan;
    // Object bình luận
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
    // Recycler View
    private RecyclerView recyclerView_BinhLuan_TatCaBinhLuan;
    // Array List để lưu trữ dữ liệu dùng để adapt lên recycler view
    private ArrayList<BinhLuan> arr_BinhLuan;
    private ArrayList<Integer> arr_LocBinhLuan;
    // Adapter để adapt dữ liệu từ array lên recycler view
    private BinhLuanChiTietSanPhamAdapter binhLuanChiTietSanPhamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Set view hiển thị của activity
        setContentView(R.layout.activity_tat_ca_binh_luan);

        // Click vào icon mũi tên để quay về trang trước đó bằng cách finish activity hiện tại
        ImageView imgView_QuayVe_TatCaBinhLuan = findViewById(R.id.imgView_QuayVe_TatCaBinhLuan);
        imgView_QuayVe_TatCaBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Lấy id sản phẩm đã lưu trữ bằng getExtra và bỏ vào biến id, biến này dùng để xác định cần lấy dữ liệu của bình luận về sản phẩm nào
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        // Số lượng bình luận của khách hàng đối với sản phẩm trong phần bình luận
        txtView_SLBinhLuan_TatCaBinhLuan = findViewById(R.id.txtView_SLBinhLuan_TatCaBinhLuan);
        // Tổng số sao đánh giá của khách bình luận trong phần bình luận
        ratingBar_SoSaoSanPham_TatCaBinhLuan = findViewById(R.id.ratingBar_SoSaoSanPham_TatCaBinhLuan);
        // Tổng số sao đánh giá hiển thị bằng số của khách đánh giá trong phần bình luận
        txtView_SoSaoSanPham_TatCaBinhLuan = findViewById(R.id.txtView_SoSaoSanPham_TatCaBinhLuan);
        // Số lượng bình luận đánh giá 5 sao cho sản phẩm
        txtView_SLBinhLuan5S_TatCaBinhLuan = findViewById(R.id.txtView_SLBinhLuan5S_TatCaBinhLuan);
        // Số lượng bình luận đánh giá 4 sao cho sản phẩm
        txtView_SLBinhLuan4S_TatCaBinhLuan = findViewById(R.id.txtView_SLBinhLuan4S_TatCaBinhLuan);
        // Số lượng bình luận đánh giá 3 sao cho sản phẩm
        txtView_SLBinhLuan3S_TatCaBinhLuan = findViewById(R.id.txtView_SLBinhLuan3S_TatCaBinhLuan);
        // Số lượng bình luận đánh giá 2 sao cho sản phẩm
        txtView_SLBinhLuan2S_TatCaBinhLuan = findViewById(R.id.txtView_SLBinhLuan2S_TatCaBinhLuan);
        // Số lượng bình luận đánh giá 1 sao cho sản phẩm
        txtView_SLBinhLuan1S_TatCaBinhLuan = findViewById(R.id.txtView_SLBinhLuan1S_TatCaBinhLuan);

        // Lấy dữ liệu bình luận và đổ lên recycler view
        // Khởi tạo biến recycler view
        recyclerView_BinhLuan_TatCaBinhLuan = findViewById(R.id.recyclerView_BinhLuan_TatCaBinhLuan);
        // Khởi tạo biến recycler view layout manager
        RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

        // Set LayoutManager cho Recycler View
        recyclerView_BinhLuan_TatCaBinhLuan.setLayoutManager(recyclerViewLayoutManager);

        // Set Vertical Layout Manager cho Recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TatCaBinhLuanActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView_BinhLuan_TatCaBinhLuan.setLayoutManager(linearLayoutManager);

        // Khởi tạo array bình luận
        arr_BinhLuan = new ArrayList<>();
        // Chạy hàm lấy dữ liệu bình luận và đổ lên recycler view
        GetDataBinhLuan();

        // Khởi tạo array lọc bình luận với 5 phần tử là 0
        // Array này dùng để xác định khách hiện đang lọc bình luận theo số sao là bao nhiêu
        arr_LocBinhLuan = new ArrayList<>(Collections.nCopies(5, 0));
        // Khởi tạo các linear layout dùng để click vào để xác định lọc bình luận theo số sao là bao nhiêu
        // Cài đặt chức năng khi click vào: thay đổi hiển thị, thay đổi dữ liệu trong array lọc bình luận
        linearLayout_LocBinhLuan5S_TatCaBinhLuan = findViewById(R.id.linearLayout_LocBinhLuan5S_TatCaBinhLuan);
        linearLayout_LocBinhLuan5S_TatCaBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // Nếu giá trị tương ứng trong array lọc bình luận đang là 1 thì nghĩa là đang lọc các bình luận 5 sao
                if(arr_LocBinhLuan.get(4) == 1)
                {
                    // Khi click vào sẽ đổi giá trị thành 0 là không lọc
                    arr_LocBinhLuan.set(4, 0);
                    // Đổi background để thay đổi hiển thị cho người dùng
                    linearLayout_LocBinhLuan5S_TatCaBinhLuan.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                }
                // Ngược lại, đổi giá trị trong array lọc bình luận thành 1 và thay đổi background hiển thị sang dạng đã chọn lọc
                else
                {
                    arr_LocBinhLuan.set(4, 1);
                    linearLayout_LocBinhLuan5S_TatCaBinhLuan.setBackgroundResource(R.drawable.border_linearlayout_loctheososao_tatcabinhluan);
                }
                // Chạy hàm lấy dữ liệu bình luận theo lựa chọn lọc và set dữ liệu đó lên recycler view
                GetDataLocBinhLuan();
            }
        });

        linearLayout_LocBinhLuan4S_TatCaBinhLuan = findViewById(R.id.linearLayout_LocBinhLuan4S_TatCaBinhLuan);
        linearLayout_LocBinhLuan4S_TatCaBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Nếu giá trị tương ứng trong array lọc bình luận đang là 1 thì nghĩa là đang lọc các bình luận 4 sao
                if(arr_LocBinhLuan.get(3) == 1)
                {
                    // Khi click vào sẽ đổi giá trị thành 0 là không lọc
                    arr_LocBinhLuan.set(3, 0);
                    // Đổi background để thay đổi hiển thị cho người dùng
                    linearLayout_LocBinhLuan4S_TatCaBinhLuan.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                }
                // Ngược lại, đổi giá trị trong array lọc bình luận thành 1 và thay đổi background hiển thị sang dạng đã chọn lọc
                else
                {
                    arr_LocBinhLuan.set(3, 1);
                    linearLayout_LocBinhLuan4S_TatCaBinhLuan.setBackgroundResource(R.drawable.border_linearlayout_loctheososao_tatcabinhluan);
                }
                // Chạy hàm lấy dữ liệu bình luận theo lựa chọn lọc và set dữ liệu đó lên recycler view
                GetDataLocBinhLuan();
            }
        });

        linearLayout_LocBinhLuan3S_TatCaBinhLuan = findViewById(R.id.linearLayout_LocBinhLuan3S_TatCaBinhLuan);
        linearLayout_LocBinhLuan3S_TatCaBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Nếu giá trị tương ứng trong array lọc bình luận đang là 1 thì nghĩa là đang lọc các bình luận 3 sao
                if(arr_LocBinhLuan.get(2) == 1)
                {
                    // Khi click vào sẽ đổi giá trị thành 0 là không lọc
                    arr_LocBinhLuan.set(2, 0);
                    // Đổi background để thay đổi hiển thị cho người dùng
                    linearLayout_LocBinhLuan3S_TatCaBinhLuan.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                }
                // Ngược lại, đổi giá trị trong array lọc bình luận thành 1 và thay đổi background hiển thị sang dạng đã chọn lọc
                else
                {
                    arr_LocBinhLuan.set(2, 1);
                    linearLayout_LocBinhLuan3S_TatCaBinhLuan.setBackgroundResource(R.drawable.border_linearlayout_loctheososao_tatcabinhluan);
                }
                // Chạy hàm lấy dữ liệu bình luận theo lựa chọn lọc và set dữ liệu đó lên recycler view
                GetDataLocBinhLuan();
            }
        });

        linearLayout_LocBinhLuan2S_TatCaBinhLuan = findViewById(R.id.linearLayout_LocBinhLuan2S_TatCaBinhLuan);
        linearLayout_LocBinhLuan2S_TatCaBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Nếu giá trị tương ứng trong array lọc bình luận đang là 1 thì nghĩa là đang lọc các bình luận 2 sao
                if(arr_LocBinhLuan.get(1) == 1)
                {
                    // Khi click vào sẽ đổi giá trị thành 0 là không lọc
                    arr_LocBinhLuan.set(1, 0);
                    // Đổi background để thay đổi hiển thị cho người dùng
                    linearLayout_LocBinhLuan2S_TatCaBinhLuan.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                }
                // Ngược lại, đổi giá trị trong array lọc bình luận thành 1 và thay đổi background hiển thị sang dạng đã chọn lọc
                else
                {
                    arr_LocBinhLuan.set(1, 1);
                    linearLayout_LocBinhLuan2S_TatCaBinhLuan.setBackgroundResource(R.drawable.border_linearlayout_loctheososao_tatcabinhluan);
                }
                // Chạy hàm lấy dữ liệu bình luận theo lựa chọn lọc và set dữ liệu đó lên recycler view
                GetDataLocBinhLuan();
            }
        });

        linearLayout_LocBinhLuan1S_TatCaBinhLuan = findViewById(R.id.linearLayout_LocBinhLuan1S_TatCaBinhLuan);
        linearLayout_LocBinhLuan1S_TatCaBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Nếu giá trị tương ứng trong array lọc bình luận đang là 1 thì nghĩa là đang lọc các bình luận 1 sao
                if(arr_LocBinhLuan.get(0) == 1)
                {
                    // Khi click vào sẽ đổi giá trị thành 0 là không lọc
                    arr_LocBinhLuan.set(0, 0);
                    // Đổi background để thay đổi hiển thị cho người dùng
                    linearLayout_LocBinhLuan1S_TatCaBinhLuan.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                }
                // Ngược lại, đổi giá trị trong array lọc bình luận thành 1 và thay đổi background hiển thị sang dạng đã chọn lọc
                else
                {
                    arr_LocBinhLuan.set(0, 1);
                    linearLayout_LocBinhLuan1S_TatCaBinhLuan.setBackgroundResource(R.drawable.border_linearlayout_loctheososao_tatcabinhluan);
                }
                // Chạy hàm lấy dữ liệu bình luận theo lựa chọn lọc và set dữ liệu đó lên recycler view
                GetDataLocBinhLuan();
            }
        });
    }

    // Hàm lấy và set dữ liệu bình luận
    private void GetDataBinhLuan()
    {
        // Kết nối với firebase database và truy cập vào đường dẫn BinhLuan
        dbref = FirebaseDatabase.getInstance().getReference("BinhLuan");
        // Lấy dữ liệu của các bình luận có idSanPham bằng tham số id để lấy đúng bình luận của sản phẩm
        Query query = dbref.orderByChild("idSanPham").equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Thiết lập giá trị ban đầu của tổng số lượng bình luận tổng đánh giá sao của sản phẩm đang được hiển thị
                slbl = 0;
                sosao = 0;

                // Lấy dữ liệu của các bình luận của sản phẩm
                // Làm trống mảng để khi dữ liệu trên firebase có sự thay đổi thì add dữ liệu vào mảng sẽ không làm dữ liệu trong mảng bị trùng lặp
                arr_BinhLuan.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    // Lấy dữ liệu của 1 bình luận từ firebase và để vào biến bl
                    bl = dataSnapshot.getValue(BinhLuan.class);

                    // Nếu bl khác null
                    if(bl != null)
                    {
                        // Thêm dữ liệu vừa lấy vào array list BinhLuan
                        arr_BinhLuan.add(bl);
                        // Số lượng bình luận của sản phẩm tăng thêm 1
                        slbl++;
                        // Lấy số sao đánh giá của bình luận
                        int sosaotam = bl.getSoSao();
                        // Số sao đánh giá của bình luận được cộng vào tổng số sao đánh giá bình luận của sản phẩm
                        sosao = sosao + sosaotam;
                        // Kiểm tra sao đánh giá của bình luận là mấy sao và tăng số lượng sao bình luận tương ứng
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

                // Đảo arr_BinhLuan lại và thêm vào arr_BinhLuanTemp để có được danh sách các bình luận được sắp xếp theo thời gian từ gần đến xa so với hiện tại
                ArrayList<BinhLuan> arr_BinhLuanTemp = new ArrayList<>();
                for(int i = arr_BinhLuan.size() - 1; i >= 0; i--)
                {
                    arr_BinhLuanTemp.add(arr_BinhLuan.get(i));
                }

                // Sau khi đã lấy hết dữ liệu và thêm vào mảng thì dùng adapter để đổ dữ liệu lên recycler view
                binhLuanChiTietSanPhamAdapter = new BinhLuanChiTietSanPhamAdapter(arr_BinhLuanTemp);
                // Dùng adapter để set dữ liệu cho recycler view các bình luận của sản phẩm hiện tại
                recyclerView_BinhLuan_TatCaBinhLuan.setAdapter(binhLuanChiTietSanPhamAdapter);
                // Set Text view hiển thị đánh giá bằng chữ của khách hàng đối với sản phẩm
                txtView_SoSaoSanPham_TatCaBinhLuan.setText(String.valueOf(Math.round(sosao/slbl * 10) / 10.0));
                // Set text view hiển thị số lượng sao đánh giá của các bình luận
                txtView_SLBinhLuan5S_TatCaBinhLuan.setText(String.valueOf(sosao5));
                txtView_SLBinhLuan4S_TatCaBinhLuan.setText(String.valueOf(sosao4));
                txtView_SLBinhLuan3S_TatCaBinhLuan.setText(String.valueOf(sosao3));
                txtView_SLBinhLuan2S_TatCaBinhLuan.setText(String.valueOf(sosao2));
                txtView_SLBinhLuan1S_TatCaBinhLuan.setText(String.valueOf(sosao1));
                // Set rating bar hiển thị sao đánh giá trung bình của tất cả bình luận sản phẩm
                ratingBar_SoSaoSanPham_TatCaBinhLuan.setRating(sosao/slbl);
                // Set text view hiển thị số lượng bình luận
                txtView_SLBinhLuan_TatCaBinhLuan.setText(String.valueOf(slbl));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Hàm lấy và set dữ liệu bình luận sau khi lọc
    private void GetDataLocBinhLuan()
    {
        // Kết nối với firebase database và truy cập vào đường dẫn BinhLuan
        dbref = FirebaseDatabase.getInstance().getReference("BinhLuan");
        // Lấy dữ liệu của các bình luận có idSanPham bằng tham số id để lấy đúng bình luận của sản phẩm
        Query query = dbref.orderByChild("idSanPham").equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Lấy dữ liệu của các bình luận của sản phẩm
                // Làm trống mảng để khi dữ liệu trên firebase có sự thay đổi thì add dữ liệu vào mảng sẽ không làm dữ liệu trong mảng bị trùng lặp
                arr_BinhLuan.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    // Lấy dữ liệu của 1 bình luận từ firebase và để vào biến bl
                    bl = dataSnapshot.getValue(BinhLuan.class);

                    // Nếu bl khác null
                    if(bl != null)
                    {
                        // Thêm dữ liệu vừa lấy vào array list BinhLuan
                        arr_BinhLuan.add(bl);
                    }
                }

                // Array list chứa các lựa chọn lọc bình luận của người dùng
                ArrayList<Integer> arr_LocBinhLuan2 = new ArrayList<>();
                for(int i = 0; i < 5; i++)
                {
                    // Nếu giá trị trong array lọc bình luận bằng 1 thì là có lọc, thêm số sao cần lọc vào array lọc bình luận 2
                    if(arr_LocBinhLuan.get(i) == 1)
                    {
                        arr_LocBinhLuan2.add(i+1);
                    }
                }

                // Nếu không chọn lựa chọn lọc nào hoặc chọn cả 5 lựa chọn lọc thì set tất cả dữ liệu bình luận lên recycler view
                if(arr_LocBinhLuan2.size() == 0 || arr_LocBinhLuan2.size() == 5)
                {
                    binhLuanChiTietSanPhamAdapter = new BinhLuanChiTietSanPhamAdapter(arr_BinhLuan);
                }
                else
                {
                    // Duyệt từng bình luận và kiểm tra bình luận nào cần lọc
                    ArrayList<BinhLuan> arr_BinhLuanTemp = new ArrayList<>();
                    for(int i = 0; i < arr_BinhLuan.size(); i++)
                    {
                        for (int j = 0; j < arr_LocBinhLuan2.size(); j++)
                        {
                            if(arr_BinhLuan.get(i).getSoSao() == arr_LocBinhLuan2.get(j))
                            {
                                // Nếu đáp ứng điều kiện lọc thì add vào array bình luận temp
                                arr_BinhLuanTemp.add(arr_BinhLuan.get(i));
                                break;
                            }
                        }
                    }

                    // Sau khi đã lấy hết dữ liệu và thêm vào array bình luận temp thì dùng adapter để đổ dữ liệu lên recycler view
                    binhLuanChiTietSanPhamAdapter = new BinhLuanChiTietSanPhamAdapter(arr_BinhLuanTemp);
                }

                // Dùng adapter để set dữ liệu cho recycler view các bình luận của sản phẩm hiện tại
                recyclerView_BinhLuan_TatCaBinhLuan.setAdapter(binhLuanChiTietSanPhamAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}