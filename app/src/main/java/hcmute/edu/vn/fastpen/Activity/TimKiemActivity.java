package hcmute.edu.vn.fastpen.Activity;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
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
    private ImageView imgView_QuayVe_TimKiem, imgView_Loc_TimKiem;
    private LinearLayout linearLayout_Loc_TimKiem, linearLayout_ThietLapLoc_TimKiem, linearLayout_LocTheoDanhMuc_TimKiem, linearLayout_LocTheoThuongHieu_TimKiem, linearLayout_LocTheoGia_TimKiem, linearLayout_LocTheoGia1_TimKiem, linearLayout_ApDungLoc_TimKiem, linearLayout_ThietLapLai_TimKiem, linearLayout_ApDung_TimKiem;
    private GridView gridView_SanPham_TimKiem, gridView_LocDanhMuc_TimKiem, gridView_LocThuongHieu_TimKiem;
    private EditText editTxt_TimKiem_TimKiem, editTxt_LocTheoGiaTu_TimKiem, editTxt_LocTheoGiaDen_TimKiem;
    private ArrayList<SanPham> arr_SanPham;
    private ArrayList<DanhMuc> arr_DanhMuc;
    private ArrayList<ThuongHieu> arr_ThuongHieu;
    private ArrayList<Integer> arr_LocDanhMuc;
    private ArrayList<Integer> arr_LocThuongHieu;
    private SanPhamTrangChuAdapter sanPhamTrangChuAdapter;
    private DanhMucTimKiemAdapter danhMucTimKiemAdapter;
    private ThuongHieuTimKiemAdapter thuongHieuTimKiemAdapter;
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);

        imgView_QuayVe_TimKiem = findViewById(R.id.imgView_QuayVe_TimKiem);
        imgView_QuayVe_TimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        linearLayout_ThietLapLoc_TimKiem = findViewById(R.id.linearLayout_ThietLapLoc_TimKiem);
        imgView_Loc_TimKiem = findViewById(R.id.imgView_Loc_TimKiem);
        linearLayout_ApDungLoc_TimKiem = findViewById(R.id.linearLayout_ApDungLoc_TimKiem);

        linearLayout_Loc_TimKiem = findViewById(R.id.linearLayout_Loc_TimKiem);
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

        // Lấy danh sách sản phẩm
        gridView_SanPham_TimKiem = findViewById(R.id.gridView_SanPham_TimKiem);
        // Array list các sản phẩm
        arr_SanPham = new ArrayList<>();

        // Lấy dữ liệu sản phẩm trên firebase và dùng adapter để đổ dữ liệu lên grid view
        GetDataSanPham();

        editTxt_LocTheoGiaTu_TimKiem = findViewById(R.id.editTxt_LocTheoGiaTu_TimKiem);
        editTxt_LocTheoGiaDen_TimKiem = findViewById(R.id.editTxt_LocTheoGiaDen_TimKiem);

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
            public void afterTextChanged(Editable editable)
            {
                //Gọi sau khi text thay đổi
                String tukhoa = "";
                tukhoa = String.valueOf(editTxt_TimKiem_TimKiem.getText()).trim();
                GetDataSanPhamTimLoc(tukhoa);
            }
        });

        // Lấy danh sách danh mục sản phẩm
        // Array list các danh mục
        arr_DanhMuc = new ArrayList<>();
        arr_LocDanhMuc = new ArrayList<>();
        // Adapter dùng để đưa danh sách sản phẩm lên grid view, tham số vào là activity hiện tại, layout của các ô trong grid view và array list SanPham
        danhMucTimKiemAdapter = new DanhMucTimKiemAdapter(TimKiemActivity.this, R.layout.cell_loc, arr_DanhMuc);
        gridView_LocDanhMuc_TimKiem.setAdapter(danhMucTimKiemAdapter);

        // Lấy dữ liệu sản phẩm trên firebase và dùng adapter để đổ dữ liệu lên grid view
        GetDataDanhMuc();

        // Xem thông tin chi tiết sản phẩm khi click vào sản phẩm
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
        // Array list các sản phẩm
        arr_ThuongHieu = new ArrayList<>();
        arr_LocThuongHieu = new ArrayList<>();
        // Adapter dùng để đưa danh sách sản phẩm lên grid view, tham số vào là activity hiện tại, layout của các ô trong grid view và array list SanPham
        thuongHieuTimKiemAdapter = new ThuongHieuTimKiemAdapter(TimKiemActivity.this, R.layout.cell_loc, arr_ThuongHieu);
        gridView_LocThuongHieu_TimKiem.setAdapter(thuongHieuTimKiemAdapter);

        // Lấy dữ liệu sản phẩm trên firebase và dùng adapter để đổ dữ liệu lên grid view
        GetDataThuongHieu();

        // Xem thông tin chi tiết sản phẩm khi click vào sản phẩm
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

        linearLayout_ThietLapLai_TimKiem = findViewById(R.id.linearLayout_ThietLapLai_TimKiem);
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

        linearLayout_ApDung_TimKiem = findViewById(R.id.linearLayout_ApDung_TimKiem);
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

    private void GetDataSanPham()
    {
        // Adapter dùng để đưa danh sách sản phẩm lên grid view, tham số vào là activity hiện tại, layout của các ô trong grid view và array list SanPham
        sanPhamTrangChuAdapter = new SanPhamTrangChuAdapter(TimKiemActivity.this, R.layout.cell_sanpham, arr_SanPham);
        gridView_SanPham_TimKiem.setAdapter(sanPhamTrangChuAdapter);

        dbref = FirebaseDatabase.getInstance().getReference("SanPham");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Làm trống mảng
                arr_SanPham.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    SanPham sp = dataSnapshot.getValue(SanPham.class);
                    if(sp != null) {
                        arr_SanPham.add(sp);
                    }
                }

                // Sau khi đã lấy hết dữ liệu xuống mảng thì dùng adapter để đổ dữ liệu lên grid view
                sanPhamTrangChuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void GetDataSanPhamTimLoc(String tukhoa)
    {
        dbref = FirebaseDatabase.getInstance().getReference("SanPham");

        dbref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Làm trống mảng
                arr_SanPham.clear();
                ArrayList<SanPham> arr_SanPhamTemp = new ArrayList<>();
                ArrayList<Integer> arr_DanhMucTemp = new ArrayList<>();
                ArrayList<Integer> arr_ThuongHieuTemp = new ArrayList<>();

                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    SanPham sp = dataSnapshot.getValue(SanPham.class);
                    if(sp != null)
                    {
                        if(sp.getTenSanPham().toLowerCase().contains(tukhoa.toLowerCase()))
                        {
                            arr_SanPham.add(sp);
                        }
                    }
                }

                int demdm = 0;
                int demth = 0;

                for(int i = 0; i < arr_LocDanhMuc.size(); i++)
                {
                    if(arr_LocDanhMuc.get(i) == 1)
                    {
                        arr_DanhMucTemp.add(arr_DanhMuc.get(i).getIdDanhMuc());
                        demdm++;
                    }
                }

                for(int i = 0; i < arr_LocThuongHieu.size(); i++)
                {
                    if(arr_LocThuongHieu.get(i) == 1)
                    {
                        arr_ThuongHieuTemp.add(arr_ThuongHieu.get(i).getIdThuongHieu());
                        demth++;
                    }
                }

                int dem = demdm + demth;

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

                if(((dem == 0) || (dem == arr_DanhMuc.size() + arr_ThuongHieu.size())) && locgiatu == 0 && locgiaden == 999999)
                {
                    // Adapter dùng để đưa danh sách sản phẩm lên grid view, tham số vào là activity hiện tại, layout của các ô trong grid view và array list SanPham
                    sanPhamTrangChuAdapter = new SanPhamTrangChuAdapter(TimKiemActivity.this, R.layout.cell_sanpham, arr_SanPham);
                }
                else
                    if (((dem == 0) || (dem == arr_DanhMuc.size() + arr_ThuongHieu.size())) && (locgiatu != 0 || locgiaden != 999999))
                    {
                        for(int i = 0; i < arr_SanPham.size(); i++)
                        {
                            if(arr_SanPham.get(i).getGia() >= locgiatu && arr_SanPham.get(i).getGia() <= locgiaden)
                            {
                                arr_SanPhamTemp.add(arr_SanPham.get(i));
                            }
                        }
                        // Adapter dùng để đưa danh sách sản phẩm lên grid view, tham số vào là activity hiện tại, layout của các ô trong grid view và array list SanPham
                        sanPhamTrangChuAdapter = new SanPhamTrangChuAdapter(TimKiemActivity.this, R.layout.cell_sanpham, arr_SanPhamTemp);
                    }
                    else
                        if(((dem != 0) && (dem != arr_DanhMuc.size() + arr_ThuongHieu.size())) && locgiatu == 0 && locgiaden == 999999)
                        {
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

                            // Adapter dùng để đưa danh sách sản phẩm lên grid view, tham số vào là activity hiện tại, layout của các ô trong grid view và array list SanPham
                            sanPhamTrangChuAdapter = new SanPhamTrangChuAdapter(TimKiemActivity.this, R.layout.cell_sanpham, arr_SanPhamTemp);
                        }
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

                            // Adapter dùng để đưa danh sách sản phẩm lên grid view, tham số vào là activity hiện tại, layout của các ô trong grid view và array list SanPham
                            sanPhamTrangChuAdapter = new SanPhamTrangChuAdapter(TimKiemActivity.this, R.layout.cell_sanpham, arr_SanPhamTemp);
                        }

                gridView_SanPham_TimKiem.setAdapter(sanPhamTrangChuAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void GetDataDanhMuc()
    {
        dbref = FirebaseDatabase.getInstance().getReference("DanhMuc");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Làm trống mảng
                arr_DanhMuc.clear();
                arr_LocDanhMuc.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
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
                // Làm trống mảng
                arr_ThuongHieu.clear();
                arr_LocThuongHieu.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
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