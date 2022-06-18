package hcmute.edu.vn.fastpen.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import hcmute.edu.vn.fastpen.Model.IdLib;
import hcmute.edu.vn.fastpen.Model.SanPham;
import hcmute.edu.vn.fastpen.R;

public class ThemSanPham extends AppCompatActivity {
    // ánh xạ với View để thực hiện các sự kiện khi người dùng thao tác
    private EditText edtTenSanPham, edtGia, edtDaBan, edtConLai, edtMoTa;
    private Spinner spin_DanhMuc, spin_ThuongHieu;
    private ImageView imageView, img_Add, img_Edit, img_Back;
    private CardView btnChonAnh, btnLuu;

    //Truy xuất dữ liệu trên Firebase Storage (Storage)
    private FirebaseStorage storage;
    private StorageReference storageReference;

    // chứa dữ liệu khi chọn hình ảnh
    private Uri imageUri;

    // Chứa dữ liệu và hiển thị lên Spinner
    private ArrayAdapter<String> adapterThuongHieu;
    private ArrayAdapter<String> adapterDanhMuc;
    private final ArrayList<String> arrayListThuongHieu = new ArrayList<>();
    private final ArrayList<String> arrayListDanhMuc = new ArrayList<>();

    private String backgroundImageName = "null"; // lưu tên hình ảnh
    private int indexThuongHieu = -1; // vị trí spinner thương hiệu
    private int indexDanhMuc = -1; // vị trí spinner danh mục

    // Mã truy cập
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        SetID();
        LoadSpinnerData();
        Event();
    }

    // Tải dữ liệu từ Firebase và chuyền vào Spinner
    private void LoadSpinnerData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DanhMuc");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    String danhMuc = dataSnapshot.getValue(String.class);
                    arrayListDanhMuc.add(dataSnapshot.getKey());
                    adapterDanhMuc.add(danhMuc);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef = database.getReference("ThuongHieu");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    String thuongHieu = dataSnapshot.getValue(String.class);
                    arrayListThuongHieu.add(dataSnapshot.getKey());
                    adapterThuongHieu.add(thuongHieu);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // thực thi các sự kiến khi người dùng thao tác
    private void Event() {
        img_Add.setVisibility(View.INVISIBLE);
        img_Edit.setVisibility(View.INVISIBLE);
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ThemSanPham.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION);
                }
                else {
                    selectImage();
                }
            }
        });

        spin_DanhMuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                indexDanhMuc = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                indexDanhMuc = 0;
            }
        });

        spin_ThuongHieu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                indexThuongHieu = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                indexThuongHieu = 0;
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(HopLe())
                    LuuDuLieu();
            }
        });
    }

    // Kiểm tra hợp lệ khi nhập dữ liệu
    private boolean HopLe() {
        String ten = edtTenSanPham.getText().toString().trim();
        String gia = edtGia.getText().toString().trim();
        String daBan = edtDaBan.getText().toString().trim();
        String conLai = edtConLai.getText().toString().trim();
        String moTa = edtMoTa.getText().toString().trim();
        if(ten.equals("") || gia.equals("") || daBan.equals("") || conLai.equals("") || moTa.equals("") || backgroundImageName.equals("null")){
            Toast.makeText(getApplicationContext(), "Điền vào chỗ trống hoặc chọn ảnh", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //Lưu dữ liệu vào Firebase
    private void LuuDuLieu() {
        int danhMuc = Integer.parseInt(arrayListDanhMuc.get(indexDanhMuc));
        int thuongHieu = Integer.parseInt(arrayListThuongHieu.get(indexThuongHieu));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("IdLib");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                IdLib idLib = snapshot.getValue(IdLib.class);

                //Thêm dữ liệu
                SanPham sanPham = new SanPham(idLib.getSanPham(), edtTenSanPham.getText().toString(),danhMuc, thuongHieu,
                        Integer.parseInt(edtGia.getText().toString()), Integer.parseInt(edtDaBan.getText().toString()), Integer.parseInt(edtConLai.getText().toString()),
                        edtMoTa.getText().toString(), backgroundImageName);

                DatabaseReference myRef = database.getReference("SanPham/" + sanPham.getIdSanPham());
                DatabaseReference myRef1 = database.getReference("IdLib/sanPham");

                myRef.setValue(sanPham, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {  //Tăng id
                        Toast.makeText(ThemSanPham.this, "Đã lưu", Toast.LENGTH_SHORT).show();
                        CaiDatMacDinh();
                    }
                });
                uploadImage();
                myRef1.setValue(idLib.getSanPham()+1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    // Cài đặt dữ liệu mặc định
    private void CaiDatMacDinh() {
        edtTenSanPham.setText("");
        edtMoTa.setText("");
        edtConLai.setText("");
        edtGia.setText("");
        edtDaBan.setText("");
        backgroundImageName = "null";
        imageView.setImageResource(R.drawable.user_icon);
    }

    //Chuyển đến Bộ sưu tập của máy và chọn hình
    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(intent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }
    }

    // Kiểm tra quyền truy cập
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                selectImage();
            }
            else {
                Toast.makeText(ThemSanPham.this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Lấy dữ liệu trả về sau khi chọn hình và lưu vào các biến
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK){
            if(data != null){
                imageUri = data.getData();
                if(imageUri != null){
                    imageView.setImageURI(imageUri);
                    backgroundImageName = getFileName(imageUri, getApplicationContext());
                }
            }
        }
    }


    // Tải hình ảnh lên Storage
    private void uploadImage() {
        final String randomKey = backgroundImageName;
        StorageReference riverRef = storageReference.child("Image/ " + randomKey);

        riverRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    }
                });
    }

    // Lấy tên file ảnh đã chọn
    @SuppressLint("Range")
    private String getFileName (Uri uri, Context context){
        String res = null;
        if(uri.getScheme().equals("content")){
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null );
            try {
                if(cursor != null && cursor.moveToFirst()){
                    res = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                }
            }finally {
                cursor.close();
            }
            if (res == null){
                res = uri.getPath();
                int cutt = res.lastIndexOf('/');
                if (cutt != -1){
                    res = res.substring(cutt+1);
                }
            }
        }
        return res;
    }

    // Gán id vào các biến, cài đặt adapter và thiết lập ban đầu
    private void SetID() {
        edtTenSanPham = findViewById(R.id.edt_TenSanPham);
        spin_DanhMuc = findViewById(R.id.spin_DanhMucSanPham);
        spin_ThuongHieu = findViewById(R.id.spin_ThuongHieuSanPham);
        edtGia = findViewById(R.id.edt_GiaSanPham);
        edtDaBan = findViewById(R.id.edt_SoLuongSanPhamDaBan);
        edtConLai = findViewById(R.id.edt_SoLuongSanPhamConLai);
        edtMoTa = findViewById(R.id.edt_MoTaSanPham);
        btnChonAnh = findViewById(R.id.btn_ChonAnh);
        btnLuu = findViewById(R.id.btn_Luu);
        imageView = findViewById(R.id.img_HinhSanPham);
        img_Add = findViewById(R.id.img_Add);
        img_Edit = findViewById(R.id.img_Edit);
        img_Back = findViewById(R.id.img_Back);

        adapterDanhMuc = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        adapterThuongHieu = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        spin_DanhMuc.setAdapter(adapterDanhMuc);
        spin_ThuongHieu.setAdapter(adapterThuongHieu);
    }

}