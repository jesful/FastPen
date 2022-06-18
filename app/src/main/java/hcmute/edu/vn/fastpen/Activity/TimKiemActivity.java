package hcmute.edu.vn.fastpen.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hcmute.edu.vn.fastpen.Adapter.DanhMucTimKiemAdapter;
import hcmute.edu.vn.fastpen.Adapter.SanPhamTrangChuAdapter;
import hcmute.edu.vn.fastpen.Adapter.ThuongHieuTimKiemAdapter;
import hcmute.edu.vn.fastpen.Model.DanhMuc;
import hcmute.edu.vn.fastpen.Model.SanPham;
import hcmute.edu.vn.fastpen.Model.ThuongHieu;
import hcmute.edu.vn.fastpen.R;

public class TimKiemActivity extends AppCompatActivity
{
    // Image View
    private ImageView imgView_Loc_TimKiem;
    // Linear layout
    private LinearLayout linearLayout_ThietLapLoc_TimKiem;
    private LinearLayout linearLayout_LocTheoDanhMuc_TimKiem;
    private LinearLayout linearLayout_LocTheoThuongHieu_TimKiem;
    private LinearLayout linearLayout_LocTheoGia_TimKiem;
    private LinearLayout linearLayout_LocTheoGia1_TimKiem;
    private LinearLayout linearLayout_ApDungLoc_TimKiem;
    // Recycler view
    private RecyclerView recyclerView_SanPham_TimKiem;
    // Grid view
    private GridView gridView_LocDanhMuc_TimKiem, gridView_LocThuongHieu_TimKiem;
    // Edit text
    private EditText editTxt_TimKiem_TimKiem, editTxt_LocTheoGiaTu_TimKiem, editTxt_LocTheoGiaDen_TimKiem;
    // Array List để lưu trữ dữ liệu dùng để adapt lên recycler view
    private ArrayList<SanPham> arr_SanPham;
    private ArrayList<DanhMuc> arr_DanhMuc;
    private ArrayList<ThuongHieu> arr_ThuongHieu;
    private ArrayList<Integer> arr_LocDanhMuc;
    private ArrayList<Integer> arr_LocThuongHieu;
    // Adapter để adapt dữ liệu từ array lên recycler view
    private SanPhamTrangChuAdapter sanPhamTrangChuAdapter;
    private DanhMucTimKiemAdapter danhMucTimKiemAdapter;
    private ThuongHieuTimKiemAdapter thuongHieuTimKiemAdapter;
    // Database references
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Set view hiển thị của activity
        setContentView(R.layout.activity_tim_kiem);

        // Click vào icon mũi tên để quay về trang trước đó bằng cách finish activity hiện tại
        ImageView imgView_QuayVe_TimKiem = findViewById(R.id.imgView_QuayVe_TimKiem);
        imgView_QuayVe_TimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Khởi tạo các biến linear layout dùng cho việc chọn kiểu lọc
        linearLayout_ThietLapLoc_TimKiem = findViewById(R.id.linearLayout_ThietLapLoc_TimKiem);
        imgView_Loc_TimKiem = findViewById(R.id.imgView_Loc_TimKiem);
        linearLayout_ApDungLoc_TimKiem = findViewById(R.id.linearLayout_ApDungLoc_TimKiem);

        // Cài đặt thay đổi về giao diện khi click vào các lựa chọn lọc
        // Các lựa chọn được chọn thì chuyển sang màu xanh, bỏ chọn lọc thì chuyển sang màu xám
        // Có 3 lựa chọn lọc chính là theo danh mục, thương hiệu, giá, click vào sẽ mở ra linear lựa chọn tương ứng
        LinearLayout linearLayout_Loc_TimKiem = findViewById(R.id.linearLayout_Loc_TimKiem);
        linearLayout_Loc_TimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(linearLayout_ThietLapLoc_TimKiem.getVisibility() == View.VISIBLE)
                {
                    linearLayout_ThietLapLoc_TimKiem.setVisibility(View.GONE);
                    gridView_LocDanhMuc_TimKiem.setVisibility(View.GONE);
                    linearLayout_LocTheoDanhMuc_TimKiem.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                    gridView_LocThuongHieu_TimKiem.setVisibility(View.GONE);
                    linearLayout_LocTheoThuongHieu_TimKiem.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                    linearLayout_LocTheoGia1_TimKiem.setVisibility(View.GONE);
                    linearLayout_LocTheoGia_TimKiem.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                    linearLayout_ApDungLoc_TimKiem.setVisibility(View.GONE);
                    imgView_Loc_TimKiem.setImageResource(R.drawable.ic_baseline_filter_alt_off_24);
                }
                else
                {
                    linearLayout_ThietLapLoc_TimKiem.setVisibility(View.VISIBLE);
                    imgView_Loc_TimKiem.setImageResource(R.drawable.ic_baseline_filter_alt_24);
                }
            }
        });

        gridView_LocDanhMuc_TimKiem = findViewById(R.id.gridView_LocDanhMuc_TimKiem);

        linearLayout_LocTheoDanhMuc_TimKiem = findViewById(R.id.linearLayout_LocTheoDanhMuc_TimKiem);
        linearLayout_LocTheoDanhMuc_TimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gridView_LocDanhMuc_TimKiem.getVisibility() == View.VISIBLE)
                {
                    gridView_LocDanhMuc_TimKiem.setVisibility(View.GONE);
                    linearLayout_LocTheoDanhMuc_TimKiem.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                    linearLayout_ApDungLoc_TimKiem.setVisibility(View.GONE);
                }
                else
                {
                    gridView_LocDanhMuc_TimKiem.setVisibility(View.VISIBLE);
                    linearLayout_LocTheoDanhMuc_TimKiem.setBackgroundResource(R.drawable.border_linearlayout_loctheososao_tatcabinhluan);
                    gridView_LocThuongHieu_TimKiem.setVisibility(View.GONE);
                    linearLayout_LocTheoThuongHieu_TimKiem.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                    linearLayout_LocTheoGia1_TimKiem.setVisibility(View.GONE);
                    linearLayout_LocTheoGia_TimKiem.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                    linearLayout_ApDungLoc_TimKiem.setVisibility(View.VISIBLE);
                }
            }
        });

        gridView_LocThuongHieu_TimKiem  = findViewById(R.id.gridView_LocThuongHieu_TimKiem);

        linearLayout_LocTheoThuongHieu_TimKiem = findViewById(R.id.linearLayout_LocTheoThuongHieu_TimKiem);
        linearLayout_LocTheoThuongHieu_TimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gridView_LocThuongHieu_TimKiem.getVisibility() == View.VISIBLE)
                {
                    gridView_LocThuongHieu_TimKiem.setVisibility(View.GONE);
                    linearLayout_LocTheoThuongHieu_TimKiem.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                    linearLayout_ApDungLoc_TimKiem.setVisibility(View.GONE);
                }
                else
                {
                    gridView_LocThuongHieu_TimKiem.setVisibility(View.VISIBLE);
                    linearLayout_LocTheoThuongHieu_TimKiem.setBackgroundResource(R.drawable.border_linearlayout_loctheososao_tatcabinhluan);
                    gridView_LocDanhMuc_TimKiem.setVisibility(View.GONE);
                    linearLayout_LocTheoDanhMuc_TimKiem.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                    linearLayout_LocTheoGia1_TimKiem.setVisibility(View.GONE);
                    linearLayout_LocTheoGia_TimKiem.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                    linearLayout_ApDungLoc_TimKiem.setVisibility(View.VISIBLE);
                }
            }
        });

        linearLayout_LocTheoGia1_TimKiem = findViewById(R.id.linearLayout_LocTheoGia1_TimKiem);

        linearLayout_LocTheoGia_TimKiem = findViewById(R.id.linearLayout_LocTheoGia_TimKiem);
        linearLayout_LocTheoGia_TimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(linearLayout_LocTheoGia1_TimKiem.getVisibility() == View.VISIBLE)
                {
                    linearLayout_LocTheoGia1_TimKiem.setVisibility(View.GONE);
                    linearLayout_LocTheoGia_TimKiem.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                    linearLayout_ApDungLoc_TimKiem.setVisibility(View.GONE);
                }
                else
                {
                    linearLayout_LocTheoGia1_TimKiem.setVisibility(View.VISIBLE);
                    linearLayout_LocTheoGia_TimKiem.setBackgroundResource(R.drawable.border_linearlayout_loctheososao_tatcabinhluan);
                    gridView_LocDanhMuc_TimKiem.setVisibility(View.GONE);
                    linearLayout_LocTheoDanhMuc_TimKiem.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                    gridView_LocThuongHieu_TimKiem.setVisibility(View.GONE);
                    linearLayout_LocTheoThuongHieu_TimKiem.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                    linearLayout_ApDungLoc_TimKiem.setVisibility(View.VISIBLE);
                }
            }
        });

        // Khởi tạo array list các sản phẩm hiển thị trên recycler view
        arr_SanPham = new ArrayList<>();
        // Chạy hàm lấy dữ liệu sản phẩm trên firebase và dùng adapter để set dữ liệu lên recycler view
        GetDataSanPham();

        // Khởi tạo edit text lọc theo giá
        editTxt_LocTheoGiaTu_TimKiem = findViewById(R.id.editTxt_LocTheoGiaTu_TimKiem);
        editTxt_LocTheoGiaDen_TimKiem = findViewById(R.id.editTxt_LocTheoGiaDen_TimKiem);

        // Khởi tạo edit text tìm kiếm theo tên sản phẩm
        editTxt_TimKiem_TimKiem = findViewById(R.id.editTxt_TimKiem_TimKiem);
        editTxt_TimKiem_TimKiem.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Gọi trước khi text thay đổi
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Gọi khi text thay đổi
            }

            @Override
            // Sau khi text trên edit text có sự thay đổi thì thực hiện lấy từ khóa trên edit text và lọc theo các lựa chọn lọc
            public void afterTextChanged(Editable editable)
            {
                //Gọi sau khi text thay đổi
                String tukhoa = "";
                tukhoa = String.valueOf(editTxt_TimKiem_TimKiem.getText()).trim();
                GetDataSanPhamTimLoc(tukhoa);
            }
        });

        // Lấy danh sách danh mục sản phẩm
        // Khởi tạo Array list các danh mục
        arr_DanhMuc = new ArrayList<>();
        arr_LocDanhMuc = new ArrayList<>();
        // Adapter dùng để đưa danh sách danh mục lên grid view, tham số vào là activity hiện tại, layout của các ô trong grid view và array list DanhMuc
        danhMucTimKiemAdapter = new DanhMucTimKiemAdapter(TimKiemActivity.this, R.layout.cell_loc, arr_DanhMuc);
        gridView_LocDanhMuc_TimKiem.setAdapter(danhMucTimKiemAdapter);

        // Lấy dữ liệu danh mục trên firebase và dùng adapter để đổ dữ liệu lên grid view
        GetDataDanhMuc();

        // Khi click vào 1 item trong grid view lọc danh mục thì sẽ thay đổi trạng thái trong array lọc danh mục để cho biết lựa chọn lọc nào đang được chọn
        gridView_LocDanhMuc_TimKiem.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                if(arr_LocDanhMuc.get(i) == 1)
                {
                    arr_LocDanhMuc.set(i, 0);
                    gridView_LocDanhMuc_TimKiem.getChildAt(i).setBackgroundResource(R.drawable.border_cell_loc1);
                }
                else
                {
                    arr_LocDanhMuc.set(i, 1);
                    gridView_LocDanhMuc_TimKiem.getChildAt(i).setBackgroundResource(R.drawable.border_cell_loc);
                }
            }
        });

        // Lấy danh sách thương hiệu sản phẩm
        // Khởi tạo Array list các sản phẩm
        arr_ThuongHieu = new ArrayList<>();
        arr_LocThuongHieu = new ArrayList<>();
        // Adapter dùng để đưa danh sách thương hiệu lên grid view, tham số vào là activity hiện tại, layout của các ô trong grid view và array list ThuongHieu
        thuongHieuTimKiemAdapter = new ThuongHieuTimKiemAdapter(TimKiemActivity.this, R.layout.cell_loc, arr_ThuongHieu);
        gridView_LocThuongHieu_TimKiem.setAdapter(thuongHieuTimKiemAdapter);

        // Lấy dữ liệu thương hiệu trên firebase và dùng adapter để đổ dữ liệu lên grid view
        GetDataThuongHieu();

        // Khi click vào 1 item trong grid view lọc thương hiệu thì sẽ thay đổi trạng thái trong array lọc thương hiệu để cho biết lựa chọn lọc nào đang được chọn
        gridView_LocThuongHieu_TimKiem.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                if(arr_LocThuongHieu.get(i) == 1) {
                    arr_LocThuongHieu.set(i, 0);
                    gridView_LocThuongHieu_TimKiem.getChildAt(i).setBackgroundResource(R.drawable.border_cell_loc1);
                }
                else {
                    arr_LocThuongHieu.set(i, 1);
                    gridView_LocThuongHieu_TimKiem.getChildAt(i).setBackgroundResource(R.drawable.border_cell_loc);
                }
            }
        });

        // Khởi tạo linear layout thiết lập lại
        LinearLayout linearLayout_ThietLapLai_TimKiem = findViewById(R.id.linearLayout_ThietLapLai_TimKiem);
        // Khi click vào sẽ thiết lạp lại tất cả lựa chọn lọc hiện tại
        linearLayout_ThietLapLai_TimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < arr_DanhMuc.size(); i++)
                {
                    arr_LocDanhMuc.set(i,0);
                    gridView_LocDanhMuc_TimKiem.getChildAt(i).setBackgroundResource(R.drawable.border_cell_loc1);
                }

                for(int j = 0; j < arr_ThuongHieu.size(); j++)
                {
                    arr_LocThuongHieu.set(j,0);
                    gridView_LocThuongHieu_TimKiem.getChildAt(j).setBackgroundResource(R.drawable.border_cell_loc1);
                }

                editTxt_LocTheoGiaTu_TimKiem.setText("");
                editTxt_LocTheoGiaDen_TimKiem.setText("");
            }
        });

        // Khởi tạo linear layout áp dụng lọc
        LinearLayout linearLayout_ApDung_TimKiem = findViewById(R.id.linearLayout_ApDung_TimKiem);
        // Khi click vào sẽ áp dụng các lựa chọn lọc hiện tại
        linearLayout_ApDung_TimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                linearLayout_ThietLapLoc_TimKiem.setVisibility(View.GONE);
                gridView_LocDanhMuc_TimKiem.setVisibility(View.GONE);
                linearLayout_LocTheoDanhMuc_TimKiem.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                gridView_LocThuongHieu_TimKiem.setVisibility(View.GONE);
                linearLayout_LocTheoThuongHieu_TimKiem.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                linearLayout_LocTheoGia1_TimKiem.setVisibility(View.GONE);
                linearLayout_LocTheoGia_TimKiem.setBackgroundResource(R.drawable.border_linearlayout_loctheososao1_tatcabinhluan);
                linearLayout_ApDungLoc_TimKiem.setVisibility(View.GONE);
                imgView_Loc_TimKiem.setImageResource(R.drawable.ic_baseline_filter_alt_off_24);

                String tukhoa = "";
                tukhoa = String.valueOf(editTxt_TimKiem_TimKiem.getText()).trim();
                GetDataSanPhamTimLoc(tukhoa);
            }
        });
    }

    // Lấy dữ liệu sản phẩm trên firebase và set lên recycler view
    private void GetDataSanPham()
    {
        // Khởi tạo recycler view sản phẩm
        recyclerView_SanPham_TimKiem = findViewById(R.id.recyclerView_SanPham_TimKiem);
        // Tạo biến với giá trị là 2
        int numberOfColumns = 2;
        // Set LayoutManager cho Recycler View, layout theo kiểu grid view có 2 cột
        recyclerView_SanPham_TimKiem.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        // Kết nối firebase với đường dẫn SanPham
        dbref = FirebaseDatabase.getInstance().getReference("SanPham");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Lấy dữ liệu của các sản phẩm có cùng danh mục với sản phẩm đang được hiển thị thông tin chi tiết
                // Làm trống mảng để khi dữ liệu trên firebase có sự thay đổi thì add dữ liệu vào mảng sẽ không làm dữ liệu trong mảng bị trùng lặp
                arr_SanPham.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    // Lấy object SanPham từ firebase và thêm vào mảng
                    SanPham sp = dataSnapshot.getValue(SanPham.class);
                    arr_SanPham.add(sp);
                }

                // Sau khi đã lấy hết dữ liệu xuống mảng thì dùng adapter để đổ dữ liệu lên recycler view
                sanPhamTrangChuAdapter = new SanPhamTrangChuAdapter(arr_SanPham, new SanPhamTrangChuAdapter.OnItemClickListener()
                {
                    @Override
                    // KLhi click vào 1 item trong recycler view
                    public void onItemClick(View view, int i) {
                        // Chuyển qua trang Chi tiết sản phẩm
                        Intent intent = new Intent(TimKiemActivity.this, ChiTietSanPhamActivity.class);
                        // Lưu trữ lại id của sản phẩm bằng putExtra
                        intent.putExtra("id", arr_SanPham.get(i).getIdSanPham());
                        startActivity(intent);
                    }
                });
                recyclerView_SanPham_TimKiem.setAdapter(sanPhamTrangChuAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Hàm lấy dữ liệu sản phẩm trên firebase và set lên recycler view theo các lựa chọn lọc
    private void GetDataSanPhamTimLoc(String tukhoa)
    {
        // Kết nối firebase với đường dẫn SanPham
        dbref = FirebaseDatabase.getInstance().getReference("SanPham");

        dbref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Lấy dữ liệu của các sản phẩm có cùng danh mục với sản phẩm đang được hiển thị thông tin chi tiết
                // Làm trống mảng để khi dữ liệu trên firebase có sự thay đổi thì add dữ liệu vào mảng sẽ không làm dữ liệu trong mảng bị trùng lặp
                arr_SanPham.clear();
                // Khởi tạo các array list temp để lưu trữ các lựa chọn lọc theo danh mục, thương hiệu
                ArrayList<SanPham> arr_SanPhamTemp = new ArrayList<>();
                ArrayList<Integer> arr_DanhMucTemp = new ArrayList<>();
                ArrayList<Integer> arr_ThuongHieuTemp = new ArrayList<>();

                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    SanPham sp = dataSnapshot.getValue(SanPham.class);
                    if(sp != null)
                    {
                        // Lấy dữ liệu trên firebase và kiểm tra với từ khóa trên edit text khung tìm kiếm, nếu cóp chứa từ khóa trong tên thì thêm vào array sản phẩm
                        if(sp.getTenSanPham().toLowerCase().contains(tukhoa.toLowerCase()))
                        {
                            arr_SanPham.add(sp);
                        }
                    }
                }

                // Khởi tạo hai biến đếm số lượng lựa chọn lọc theo danh mục và thương hiệu
                int demdm = 0;
                int demth = 0;

                for(int i = 0; i < arr_LocDanhMuc.size(); i++)
                {
                    // Với mỗi lựa chọn lọc danh mục thì thêm vào arr danh mục temp để biết lọc theo danh mục nào, tăng số lượng lựa chọn lọc theo danh mục thêm 1
                    if(arr_LocDanhMuc.get(i) == 1)
                    {
                        arr_DanhMucTemp.add(arr_DanhMuc.get(i).getIdDanhMuc());
                        demdm++;
                    }
                }

                for(int i = 0; i < arr_LocThuongHieu.size(); i++)
                {
                    // Với mỗi lựa chọn lọc thương hiệu thì thêm vào arr thương hiệu temp để biết lọc theo thương hiệu nào, tăng số lượng lựa chọn lọc theo thương hiệu thêm 1
                    if(arr_LocThuongHieu.get(i) == 1)
                    {
                        arr_ThuongHieuTemp.add(arr_ThuongHieu.get(i).getIdThuongHieu());
                        demth++;
                    }
                }

                // Cộng số lượng lựa chọn lọc danh mục avf thương hiệu
                int dem = demdm + demth;

                // Khưởi tọa 2 biến chứa số trên hai edit text lọc giá sản phẩm từ bao nhiêu và lọc giá sản phẩm đến bao nhiêu
                int locgiatu;
                int locgiaden;
                if(String.valueOf(editTxt_LocTheoGiaTu_TimKiem.getText()).trim().equals(""))
                {
                    locgiatu = 0;
                }
                else
                {
                    locgiatu = Integer.parseInt(String.valueOf(editTxt_LocTheoGiaTu_TimKiem.getText()));
                }

                if(String.valueOf(editTxt_LocTheoGiaDen_TimKiem.getText()).trim().equals(""))
                {
                    locgiaden = 999999;
                }
                else
                {
                    locgiaden = Integer.parseInt(String.valueOf(editTxt_LocTheoGiaDen_TimKiem.getText()));
                }

                // Kiểm tra nếu không chọn lọc hoặc lọc hết toàn bộ thì set dữ liệu theo array sản phẩm ban đầu
                if(((dem == 0) || (dem == arr_DanhMuc.size() + arr_ThuongHieu.size())) && locgiatu == 0 && locgiaden == 999999)
                {
                    // Sau khi đã lấy hết dữ liệu xuống mảng thì dùng adapter để set dữ liệu lên recycler view
                    sanPhamTrangChuAdapter = new SanPhamTrangChuAdapter(arr_SanPham, new SanPhamTrangChuAdapter.OnItemClickListener()
                    {
                        @Override
                        // Click item
                        public void onItemClick(View view, int i) {
                            // Chuyển qua trang Chi tiết sản phẩm
                            Intent intent = new Intent(TimKiemActivity.this, ChiTietSanPhamActivity.class);
                            // Lưu trữ lại id của sản phẩm bằng putExtra
                            intent.putExtra("id", arr_SanPham.get(i).getIdSanPham());
                            startActivity(intent);
                        }
                    });
                }
                else
                    // Nếu lọc theo giá
                    if (((dem == 0) || (dem == arr_DanhMuc.size() + arr_ThuongHieu.size())) && (locgiatu != 0 || locgiaden != 999999))
                    {
                        for(int i = 0; i < arr_SanPham.size(); i++)
                        {
                            // Kiểm tra giá các sản phẩm nằm trong khoản lọc giá từ và lọc giá đến
                            if(arr_SanPham.get(i).getGia() >= locgiatu && arr_SanPham.get(i).getGia() <= locgiaden)
                            {
                                arr_SanPhamTemp.add(arr_SanPham.get(i));
                            }
                        }
                        // Sau khi đã lấy hết dữ liệu xuống mảng thì dùng adapter để set dữ liệu lên recycler view
                        sanPhamTrangChuAdapter = new SanPhamTrangChuAdapter(arr_SanPhamTemp, new SanPhamTrangChuAdapter.OnItemClickListener()
                        {
                            @Override
                            // khi click item
                            public void onItemClick(View view, int i) {
                                // Chuyển qua trang Chi tiết sản phẩm
                                Intent intent = new Intent(TimKiemActivity.this, ChiTietSanPhamActivity.class);
                                // Lưu trữ lại id của sản phẩm bằng putExtra
                                intent.putExtra("id", arr_SanPham.get(i).getIdSanPham());
                                startActivity(intent);
                            }
                        });
                    }
                    else
                        // Khi lọc theo danh mục hoặc thương hiệu
                        if(((dem != 0) && (dem != arr_DanhMuc.size() + arr_ThuongHieu.size())) && locgiatu == 0 && locgiaden == 999999)
                        {
                            // Chỉ lọc theo thương hiệu
                            if(demdm == 0)
                            {
                                for(int i = 0; i < arr_SanPham.size(); i++)
                                {
                                    for (int j = 0; j < arr_ThuongHieuTemp.size(); j++)
                                    {
                                        if (arr_SanPham.get(i).getIdThuongHieu() == arr_ThuongHieuTemp.get(j))
                                        {
                                            arr_SanPhamTemp.add(arr_SanPham.get(i));
                                            break;
                                        }
                                    }
                                }
                            }
                            else
                                // Chỉ lọc theo danh mục
                                if(demth == 0)
                                {
                                    for(int i = 0; i < arr_SanPham.size(); i++)
                                    {
                                        for (int j = 0; j < arr_DanhMucTemp.size(); j++)
                                        {
                                            if (arr_SanPham.get(i).getIdDanhMuc() == arr_DanhMucTemp.get(j))
                                            {
                                                arr_SanPhamTemp.add(arr_SanPham.get(i));
                                                break;
                                            }
                                        }
                                    }
                                }
                                // Lọc theo cả hai
                                else
                                {
                                    for(int i = 0; i < arr_SanPham.size(); i++)
                                    {
                                        for (int j = 0; j < arr_DanhMucTemp.size(); j++)
                                        {
                                            if (arr_SanPham.get(i).getIdDanhMuc() == arr_DanhMucTemp.get(j))
                                            {
                                                for (int k = 0; k < arr_ThuongHieuTemp.size(); k++)
                                                {
                                                    if (arr_SanPham.get(i).getIdThuongHieu() == arr_ThuongHieuTemp.get(k))
                                                    {
                                                        arr_SanPhamTemp.add(arr_SanPham.get(i));
                                                        break;
                                                    }
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }

                            // Sau khi đã lấy hết dữ liệu xuống mảng thì dùng adapter để đổ dữ liệu lên recycler view
                            sanPhamTrangChuAdapter = new SanPhamTrangChuAdapter(arr_SanPhamTemp, new SanPhamTrangChuAdapter.OnItemClickListener()
                            {
                                @Override
                                public void onItemClick(View view, int i) {
                                    // Chuyển qua trang Chi tiết sản phẩm
                                    Intent intent = new Intent(TimKiemActivity.this, ChiTietSanPhamActivity.class);
                                    // Lưu trữ lại id của sản phẩm bằng putExtra
                                    intent.putExtra("id", arr_SanPham.get(i).getIdSanPham());
                                    startActivity(intent);
                                }
                            });
                        }
                        // Lọc theo danh mục, giá, thương hiệu, từ khóa
                        else
                        {
                            if(demdm == 0)
                            {
                                for(int i = 0; i < arr_SanPham.size(); i++)
                                {
                                    if(arr_SanPham.get(i).getGia() >= locgiatu && arr_SanPham.get(i).getGia() <= locgiaden)
                                    {
                                        for (int j = 0; j < arr_ThuongHieuTemp.size(); j++)
                                        {
                                            if (arr_SanPham.get(i).getIdThuongHieu() == arr_ThuongHieuTemp.get(j))
                                            {
                                                arr_SanPhamTemp.add(arr_SanPham.get(i));
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            else
                                if(demth == 0)
                                {
                                    for(int i = 0; i < arr_SanPham.size(); i++)
                                    {
                                        if(arr_SanPham.get(i).getGia() >= locgiatu && arr_SanPham.get(i).getGia() <= locgiaden)
                                        {
                                            for (int j = 0; j < arr_DanhMucTemp.size(); j++)
                                            {
                                                if (arr_SanPham.get(i).getIdDanhMuc() == arr_DanhMucTemp.get(j))
                                                {
                                                    arr_SanPhamTemp.add(arr_SanPham.get(i));
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    for(int i = 0; i < arr_SanPham.size(); i++)
                                    {
                                        if(arr_SanPham.get(i).getGia() >= locgiatu && arr_SanPham.get(i).getGia() <= locgiaden)
                                        {
                                            for (int j = 0; j < arr_DanhMucTemp.size(); j++)
                                            {
                                                if (arr_SanPham.get(i).getIdDanhMuc() == arr_DanhMucTemp.get(j))
                                                {
                                                    for (int k = 0; k < arr_ThuongHieuTemp.size(); k++)
                                                    {
                                                        if (arr_SanPham.get(i).getIdThuongHieu() == arr_ThuongHieuTemp.get(k))
                                                        {
                                                            arr_SanPhamTemp.add(arr_SanPham.get(i));
                                                            break;
                                                        }
                                                    }
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }

                            // Sau khi đã lấy hết dữ liệu xuống mảng thì dùng adapter để set dữ liệu lên recycler view
                            sanPhamTrangChuAdapter = new SanPhamTrangChuAdapter(arr_SanPhamTemp, new SanPhamTrangChuAdapter.OnItemClickListener()
                            {
                                @Override
                                // item click
                                public void onItemClick(View view, int i) {
                                    // Chuyển qua trang Chi tiết sản phẩm
                                    Intent intent = new Intent(TimKiemActivity.this, ChiTietSanPhamActivity.class);
                                    // Lưu trữ lại id của sản phẩm bằng putExtra
                                    intent.putExtra("id", arr_SanPham.get(i).getIdSanPham());
                                    startActivity(intent);
                                }
                            });
                        }

                recyclerView_SanPham_TimKiem.setAdapter(sanPhamTrangChuAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Hàm lấy dữ liệu danh mục
    private void GetDataDanhMuc()
    {
        dbref = FirebaseDatabase.getInstance().getReference("DanhMuc");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Lấy dữ liệu của các sản phẩm có cùng danh mục với sản phẩm đang được hiển thị thông tin chi tiết
                // Làm trống mảng để khi dữ liệu trên firebase có sự thay đổi thì add dữ liệu vào mảng sẽ không làm dữ liệu trong mảng bị trùng lặp
                arr_DanhMuc.clear();
                arr_LocDanhMuc.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    // Lấy dữ liệu add vào array để set lên grid view và cho trạng thái ban đầu là không lọc
                    DanhMuc dm = dataSnapshot.getValue(DanhMuc.class);
                    arr_DanhMuc.add(dm);
                    arr_LocDanhMuc.add(0);
                }

                // Sau khi đã lấy hết dữ liệu xuống mảng thì dùng adapter để đổ dữ liệu lên grid view
                danhMucTimKiemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void GetDataThuongHieu()
    {
        dbref = FirebaseDatabase.getInstance().getReference("ThuongHieu");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Lấy dữ liệu của các sản phẩm có cùng danh mục với sản phẩm đang được hiển thị thông tin chi tiết
                // Làm trống mảng để khi dữ liệu trên firebase có sự thay đổi thì add dữ liệu vào mảng sẽ không làm dữ liệu trong mảng bị trùng lặp
                arr_ThuongHieu.clear();
                arr_LocThuongHieu.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    // Lấy dữ liệu add vào array để set lên grid view và cho trạng thái ban đầu là không lọc
                    ThuongHieu th = dataSnapshot.getValue(ThuongHieu.class);
                    arr_ThuongHieu.add(th);
                    arr_LocThuongHieu.add(0);
                }

                // Sau khi đã lấy hết dữ liệu xuống mảng thì dùng adapter để đổ dữ liệu lên grid view
                thuongHieuTimKiemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}