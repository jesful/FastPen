package hcmute.edu.vn.fastpen.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hcmute.edu.vn.fastpen.Activity.ChiTietSanPhamActivity;
import hcmute.edu.vn.fastpen.Activity.GioHangActivity;
import hcmute.edu.vn.fastpen.Activity.TimKiemActivity;
import hcmute.edu.vn.fastpen.Adapter.SanPhamTrangChuAdapter;
import hcmute.edu.vn.fastpen.Model.BinhLuan;
import hcmute.edu.vn.fastpen.Model.DanhMuc;
import hcmute.edu.vn.fastpen.Model.SanPham;
import hcmute.edu.vn.fastpen.Model.TaiKhoan;
import hcmute.edu.vn.fastpen.Model.ThuongHieu;
import hcmute.edu.vn.fastpen.R;

public class TrangChuFragment extends Fragment
{
    private ArrayList<SanPham> arr_SanPham;
    private GridView gridView_SanPham_TrangChu;
    private SanPhamTrangChuAdapter sanPhamTrangChuAdapter;
    private DatabaseReference dbref;

    public TrangChuFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_trang_chu, container, false);

        // Click vào linear layout khung tìm kiếm để chuyển sang trang tìm kiếm và lọc sản phẩm
        LinearLayout linearLayout_TimKiem_TrangChu = view.findViewById(R.id.linearLayout_TimKiem_TrangChu);
        linearLayout_TimKiem_TrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TimKiemActivity.class);
                startActivity(intent);
            }
        });

        // Click vào hình icon giỏ hàng để chuyển sang trang giỏ hàng
        ImageView imgView_GioHang_TrangChu = view.findViewById(R.id.imgView_GioHang_TrangChu);
        imgView_GioHang_TrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GioHangActivity.class);
                startActivity(intent);
            }
        });

        // GridView để hiển thị danh sách sản phẩm
        gridView_SanPham_TrangChu = view.findViewById(R.id.gridView_SanPham_TrangChu);
        // Array list các sản phẩm
        arr_SanPham = new ArrayList<>();

        //AddData();
        // Lấy dữ liệu sản phẩm trên firebase và dùng adapter để đổ dữ liệu lên grid view
        GetDataSanPham();

        // Xem thông tin chi tiết sản phẩm khi click vào sản phẩm
        gridView_SanPham_TrangChu.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                // Chuyển qua trang Chi tiết sản phẩm
                Intent intent = new Intent(getActivity(), ChiTietSanPhamActivity.class);
                // Lưu trữ lại id của sản phẩm bằng putExtra
                intent.putExtra("id", arr_SanPham.get(i).getIdSanPham());
                startActivity(intent);
            }
        });

        return view;
    }

    private void AddData()
    {
        SanPham sp = new SanPham(1,"Bút bi Thiên Long Flexoffice FO-039 PLUS/VN 4MC - Mực đạt tiêu chuẩn châu Âu",1,1,3910,10,"- Mực đạt tiêu chuẩn châu Âu EN 71/3, Mỹ ASTM D-4236.\n" + "\n" + "- Nét viết trơn, êm, mực ra đều và liên tục.","12345678910.jpg",32);
        dbref = FirebaseDatabase.getInstance().getReference("SanPham/1");
        dbref.setValue(sp);
        SanPham sp1 = new SanPham(2,"Bút lông bảng FlexOffice FO-WB-015",2,3,9000,20,"- Bút được sản xuất theo công nghệ hiện đại.\n" + "- Viết tốt, trơn, êm trên bảng trắng, thủy tinh và những bề mặt nhẵn bóng.\n" + "- Bề rộng nét viết 2.5mm\n" + "- Sử dụng mực mới, tốt, màu mực đậm, tươi sáng, dễ dàng xóa sạch ngay cả khi viết trên bảng lâu, không để lại bóng mực sau khi lau bảng và các bề mặt nhẵn bóng.\n" + "- Bơm mực dễ dàng, bao bì được thiết kế đẹp.\n" + "- Đầu bút xóa thuận tiện khi sử dụng.\n" + "- Mực không độc hại, đạt tiêu chuẩn an toàn quốc tế.\n" + "- Sợi Polyeste.\n" + "- Nắp bút có “ink stopper“ ( vòng tròn giữ mực trên đầu bút, giúp giữ cho nét viết có màu ổn định trong suốt quá trình sử dụng, lưu trữ, bảo quản.","12345678910.jpg",5);
        dbref = FirebaseDatabase.getInstance().getReference("SanPham/2");
        dbref.setValue(sp1);
        SanPham sp2 = new SanPham(3,"Bút lông kim Điểm 10 Doraemon FL-08/DO",3,2,8100,30,"- Đầu bi 0.5mm, sản xuất tại Thụy Sỹ, bọc kim loại.\n" + "- Lượng mực viết dài 1200-1500m, đều và không bị lem\n" + "- Nét viết thanh mảnh, nhỏ, trơn, êm","12345678910.jpg",3);
        dbref = FirebaseDatabase.getInstance().getReference("SanPham/3");
        dbref.setValue(sp2);
        SanPham sp3 = new SanPham(4,"Bút chì mỹ thuật Thiên Long 5B GP-024",4,1,7200,40,"Bút chì mỹ thuật Thiên Long 5B GP-024 thích hợp cho các hoạt động như ghi chép, vẽ nháp, học tập.\n" + "\n" + "\n" + "\n" + "Đặc điểm:\n" + "\n" + "- Ruột chì mềm, nét đậm, ít bột chì\n" + "- Thân gỗ mềm dễ chuốt\n" + "- Bền đẹp không gãy chì\n" + "- Bút dùng để viết, vẽ phác thảo trên giấy tập học sinh, sổ tay, giấy photocopy, gỗ hoặc giấy vẽ chuyên dụng\n" + "- Lướt rất nhẹ nhàng trên bề mặt viết\n" + "- Dùng để đánh bóng các bức vẽ, đạt đến nhiều mức độ sáng tối khác nhau. Ngoài ra khá hữu dụng trong việc tô đậm vào ô trả lời trắc nghiệm nhanh nhất.\n" + "- Thân lục giác, 5B.\n" + "- Thân bút được thiết kế hiện đại với họa tiết xoắn quanh bút cho cây bút sinh động và thu hút\n" + "\n" + "Bảo quản:\n" + "\n" + "- Tránh va đập mạnh làm gãy chì.\n" + "- Tránh xa nguồn nhiệt .","12345678910.jpg",100);
        dbref = FirebaseDatabase.getInstance().getReference("SanPham/4");
        dbref.setValue(sp3);
        SanPham sp4 = new SanPham(5,"Bút dạ quang Colokit HL-C01",5,1,9000,50,"Bút dạ quang Colokit HL-C01 có những đặc điểm nổi bật\n" + "- Bút dạ quang Colokit HL-C01 có 2 đầu bút giúp đa dạng nét viết, thuận tiện khi sử dụng. Sản phẩm được sản xuất theo công nghệ hiện đại, đạt tiêu chuẩn chất lượng quốc tế.\n" + "- Kiểu dáng thon gọn, trẻ trung Màu dạ quang mạnh, không làm lem nét chữ của mực khi viết chồng lên và không để lại vết khi qua photocopy đây là đặt điểm vượt trội của Bút dạ quang Colokit HL-C01.\n" + "\n" + "Đặc tính sản phẩm Bút dạ quang Colokit HL-C01\n" + "- Màu mực tươi sáng, phản quang tốt. Nét viết hoặc đánh dấu đều và liên tục. Không độc hại.\n" + "- Thiết kế hai đầu bút thuận tiện. Đầu bút và ruột bút bằng polyester. Vỏ bọc bằng nhựa PP. Bề rộng nét viết:\n" + "- Đầu bút nhỏ 2.5 mm và đầu bút lớn 3.5 mm (màu vàng).\n" + "- Bút dạ quang Colokit HL-C01 có 3 cặp màu mực: Vàng- Cam, Vàng - Hồng, Vàng - Xanh lá.\n" + "\n" + "Tuổi thọ và bảo quản:\n" + "- Tuổi thọ trung bình của sản phẩm: 24 tháng tính từ ngày sản xuất.\n" + "- Bảo quản nơi khô ráo, thoáng mát, tuyệt đối tránh xa nguồn nhiệt, hóa chất.\n" + "- Tránh ánh nắng trực tiếp chiếu vào sản phẩm.\n" + "\n" + "Khuyến cáo:\n" + "- Đậy nắp ngay sau khi sử dụng.\n" + "- Không đánh dấu, không viết lên các bề mặt không phải là giấy.\n" + "- Tránh làm bẩn hoặc thấm mực lên quần, áo, túi áo, vật có bề mặt thấm hút cao.","12345678910.jpg",44);
        dbref = FirebaseDatabase.getInstance().getReference("SanPham/5");
        dbref.setValue(sp4);
        SanPham sp5 = new SanPham(6,"Bút GEL B GELB-C01 5 màu vỉ 5",6,1,17500,60,"Tính năng nổi bật:\n" + "\n" + "Công nghệ mực butter gel tiên tiến cho nét viết êm trơn và màu sắc đậm hơn mực bút bi.\n" + "\n" + "Đặc điểm bút GelB-01:\n" + "Đầu bi: 0.7 mm, dạng cone sản suất tại Thụy Sĩ.\n" + "Bút dạng đậy nắp.\n" + "Mực đạt chuẩn: EN 71, FHSA , Iso 12757-2, TSCA,EINCES\n" + "\n" + "Lợi ích:\n" + "\n" + "Tiên phong tại Việt Nam trong việc sử dụng mực mới, mực Butter Gel hội tụ đủ các ưu điểm của mực nước và mực bút bi. \n" + "\n" + "Màu mực tươi, đều, đậm và bền màu, nét viết trơn êm.\n" + "Kiểu dáng sang trọng, lịch sự, dắt viết có bề mặt rộng thuận tiện cho việc in quảng cáo.\n" + "\n" + "\n" + "\n" + "Đặc điểm nổi bật:\n" + "\n" + "Bút Gel là nhãn hàng Bút viết ra đời nhằm mang đến sự tiện lợi, thoải mái cho người sử dụng. Thiết kế bút phù hợp với người Việt, tạo cho người viết không bị mỏi tay. Đầu bi cao cấp giúp mực trơn mượt, phù hợp cho người viết nhiều, tốc ký như học sinh phổ thông, sinh viên. Vỏ bút làm bằng nhựa trong giúp người viết dễ dàng kiểm tra lượng mực của bút, viết được đến khi hết mực mà không lo tắc hay lắng cặn.\n" + "\n" + "Với thiết kế trang nhã, màu sắc tươi sáng phù hợp với nhu cầu sử dụng của nhiều người, Bút Gel đã trở thành người bạn đồng hành của nhiều học sinh.","12345678910.jpg",22);
        dbref = FirebaseDatabase.getInstance().getReference("SanPham/6");
        dbref.setValue(sp5);
        SanPham sp6 = new SanPham(7,"Bút Gel Papermate 400St 0.7 mm - Mực đen",7,6,23100,55,"Paper Mate® InkJoy® có hệ thống truyền mực tiên tiến giúp dòng mực ổn định, trơn và êm\n" + "\n" + "Học tập và làm việc với nhiều niềm vui từ hệ thống màu sắc phong phú và rực rỡ\n" + "\n" + "Dòng mực chảy tự do như suy nghĩ của bạn vậy. Với Inkjoy, sáng tạo là không giới hạn\n" + "\n" + "Giúp bạn phân loại các ghi chú dễ dàng và khoa học hơn\n" + "Làm nổi bật các ghi chú quan trọng\n" + "Phương pháp này giúp ghi nhớ thông tin trong cả ngắn hạn và dài hạn\n" + "Giúp tiết kiệm thời gian cho việc ôn tập giữa kỳ, cuối kỳ\n","12345678910.jpg",0);
        dbref = FirebaseDatabase.getInstance().getReference("SanPham/7");
        dbref.setValue(sp6);
        SanPham sp7 = new SanPham(8,"Bút xóa kéo FlexOffice FO-CT-02",8,3,12600,100,"Khác với sản phẩm bút xóa truyền thống, băng xóa kéo FO-CT02 là kết hợp của sự tiện lợi, nhanh chóng, vô cùng an toàn và thân thiện với môi trường. Sản phẩm được thiết kế trẻ trung, năng động, màu sắc tươi sáng, đây là sản phẩm rất phù hợp cho giới văn phòng hiện đại.\n" + "\n" + "Đặc tính :\n" + "- An toàn, không độc hại\n" + "- Băng dài 8m, rộng 5mm, dẻo dai, độ bám dính tốt\n" + "- Thiết kế hiện đại, kiểu dáng nhỏ gọn, tiện dụng, xóa nhẹ, êm tay\n" + "- Bề mặt xóa nhẵn mịn, không để lại vết khi scan, fax","12345678910.jpg",11);
        dbref = FirebaseDatabase.getInstance().getReference("SanPham/8");
        dbref.setValue(sp7);
        SanPham sp8 = new SanPham(9,"Bút máy Điểm 10 TP-FTC02",9,2,89100,30,"- Trọng lượng: tương đối – không nặng, cũng không nhẹ, vừa tay cầm.\n" + "- Bút sử dụng ngòi không mài, được làm bằng thép cao cấp, xi titanium màu vàng siêu bền.\n" + "- Dùng ống mực FPIC-02, cắm vào là sử dụng đơn giản và tiện lợi.\n" + "- Bút có 2 màu thân hồng và xanh dễ dàng cho các bé chọn lựa.\n" + "\n" + "- Đầu ngòi viết được mài bằng máy nên ổn định chất lượng và đồng nhất, dễ dàng viết được nét thanh nét đậm.\n" + "- Dắt viết kim loại tạo sự sang trọng và khác biệt với các loại bút máy thông thường trên thị trường, “Điểm 10” và “Bút luyện chữ đẹp” chính là chất lượng của viết.\n" + "- Quanh vòng tròn tảm ngay chỗ cầm tay có những vòng răng để không bị mỏi tay khi cầm.","12345678910.jpg",66);
        dbref = FirebaseDatabase.getInstance().getReference("SanPham/9");
        dbref.setValue(sp8);
        SanPham sp9 = new SanPham(10,"Bút lông dầu Sharpie Twin Tip Đỏ 32202",10,7,17500,0,"Sharpie, thương hiệu truyền cảm hứng cho mọi người để lại dấu ấn cá nhân đậm nét. Là một biểu tượng văn hóa bởi các vận động viên, ngôi sao thể thao và điện ảnh dùng làm bút ký tên. Liên tục đổi mới để bạn đánh dấu mình trên từng chặng đường Sống, Học tập, Vui Chơi, Làm việc. Đánh dấu trên nhiều bề mặt, giúp tổ chức, phân loại dễ dàng. Thích hợp cho những ai không ngừng sáng tạo và không ngại thể hiện bản thân\n" + "\n" + "Dòng sản phẩm Twin Tip - Permanent Markers\n" + "\n" + "DÀNH CHO?\n" + "\n" + "Bất cứ ai mạnh dạn muốn để lại dấu ấn của họ! - Chuyên gia văn phòng, nghệ sĩ, thợ thủ công, sinh viên, thương nhân, bà mẹ.\n" + "Đối với những người muốn thể hiện bản thân theo bất kỳ cách nào họ muốn và thích sự linh hoạt, kết hợp các đường nét cực kỳ chính xác và tốt.\n" + "DÙNG ĐỂ?\n" + "\n" + "Có thể được sử dụng để đánh dấu trên các diện tích bề mặt nhỏ hơn như dây, mảnh gỗ, CD & DVD hoặc để viết hàng ngày để sắp xếp danh sách, ghi chú hoặc tạo lịch trình mã hóa màu\n" + "CÁC TÍNH NĂNG\n" + "\n" + "Dấu mực vĩnh viễn trên giấy, nhựa, kim loại và hầu hết các bề mặt khác\n" + "Màu sắc rực rỡ\n" + "Mực khô nhanh, lâu phai và chống nước\n" + "Độ dài viết: F 240 mét và UF 1200 mét\n" + "ĐẦU BÚT:\n" + "\n" + "Đầu ~1.0mm\n" + "Đầu ~0.5mm","12345678910.jpg",16);
        dbref = FirebaseDatabase.getInstance().getReference("SanPham/10");
        dbref.setValue(sp9);
        SanPham sp10 = new SanPham(11,"Ống mực Bizner BIZ-WBIC01",11,4,7200,0,"- Ống mực Bizner BIZ-WBIC01 dễ dàng thay thế, cắm là sử dụng, tiện lợi và không lo bị bẩn tay như bơm mực truyền thống.\n" + "\n" + "- Có 3 màu mực đỏ, xanh, đen (xem theo màu đuôi ống mực)","12345678910.jpg",16);
        dbref = FirebaseDatabase.getInstance().getReference("SanPham/11");
        dbref.setValue(sp10);

        DanhMuc dm = new DanhMuc(1,"Bút bi");
        dbref = FirebaseDatabase.getInstance().getReference("DanhMuc/1");
        dbref.setValue(dm);
        DanhMuc dm1 = new DanhMuc(2,"Bút lông bảng");
        dbref = FirebaseDatabase.getInstance().getReference("DanhMuc/2");
        dbref.setValue(dm1);
        DanhMuc dm2 = new DanhMuc(3,"Bút lông kim");
        dbref = FirebaseDatabase.getInstance().getReference("DanhMuc/3");
        dbref.setValue(dm2);
        DanhMuc dm3 = new DanhMuc(4,"Bút chì");
        dbref = FirebaseDatabase.getInstance().getReference("DanhMuc/4");
        dbref.setValue(dm3);
        DanhMuc dm4 = new DanhMuc(5,"Bút dạ quang");
        dbref = FirebaseDatabase.getInstance().getReference("DanhMuc/5");
        dbref.setValue(dm4);
        DanhMuc dm5 = new DanhMuc(6,"Bút Gel B");
        dbref = FirebaseDatabase.getInstance().getReference("DanhMuc/6");
        dbref.setValue(dm5);
        DanhMuc dm6 = new DanhMuc(7,"Bút Gel");
        dbref = FirebaseDatabase.getInstance().getReference("DanhMuc/7");
        dbref.setValue(dm6);
        DanhMuc dm7 = new DanhMuc(8,"Bút xóa");
        dbref = FirebaseDatabase.getInstance().getReference("DanhMuc/8");
        dbref.setValue(dm7);
        DanhMuc dm8 = new DanhMuc(9,"Bút máy luyện chữ");
        dbref = FirebaseDatabase.getInstance().getReference("DanhMuc/9");
        dbref.setValue(dm8);
        DanhMuc dm9 = new DanhMuc(10,"Bút lông dầu");
        dbref = FirebaseDatabase.getInstance().getReference("DanhMuc/10");
        dbref.setValue(dm9);
        DanhMuc dm10 = new DanhMuc(11,"Phụ kiện bút viết");
        dbref = FirebaseDatabase.getInstance().getReference("DanhMuc/11");
        dbref.setValue(dm10);

        ThuongHieu th = new ThuongHieu(1,"Thiên Long","12345678910.jpg");
        dbref = FirebaseDatabase.getInstance().getReference("ThuongHieu/1");
        dbref.setValue(th);
        ThuongHieu th1 = new ThuongHieu(2,"Điểm 10","12345678910.jpg");
        dbref = FirebaseDatabase.getInstance().getReference("ThuongHieu/2");
        dbref.setValue(th1);
        ThuongHieu th2 = new ThuongHieu(3,"Flex Office","12345678910.jpg");
        dbref = FirebaseDatabase.getInstance().getReference("ThuongHieu/3");
        dbref.setValue(th2);
        ThuongHieu th3 = new ThuongHieu(4,"Bizner","12345678910.jpg");
        dbref = FirebaseDatabase.getInstance().getReference("ThuongHieu/4");
        dbref.setValue(th3);
        ThuongHieu th4 = new ThuongHieu(5,"Colokit","12345678910.jpg");
        dbref = FirebaseDatabase.getInstance().getReference("ThuongHieu/5");
        dbref.setValue(th4);
        ThuongHieu th5 = new ThuongHieu(6,"Paper Mate","12345678910.jpg");
        dbref = FirebaseDatabase.getInstance().getReference("ThuongHieu/6");
        dbref.setValue(th5);
        ThuongHieu th6 = new ThuongHieu(7,"Sharpie","12345678910.jpg");
        dbref = FirebaseDatabase.getInstance().getReference("ThuongHieu/7");
        dbref.setValue(th6);

        BinhLuan bl = new BinhLuan(1,1,"long","Dùng tốt", 5, "22/05/2022 18:20");
        dbref = FirebaseDatabase.getInstance().getReference("BinhLuan/1");
        dbref.setValue(bl);
        BinhLuan bl1 = new BinhLuan(1,1,"nhat","Dùng tốt 1", 4, "23/05/2022 20:20");
        dbref = FirebaseDatabase.getInstance().getReference("BinhLuan/2");
        dbref.setValue(bl1);
        BinhLuan bl2 = new BinhLuan(1,1,"hung","Dùng tốt 2", 4, "24/05/2022 07:20");
        dbref = FirebaseDatabase.getInstance().getReference("BinhLuan/3");
        dbref.setValue(bl2);
        BinhLuan bl3 = new BinhLuan(1,1,"phuoc","Dùng tốt 3", 3, "25/05/2022 09:30");
        dbref = FirebaseDatabase.getInstance().getReference("BinhLuan/4");
        dbref.setValue(bl3);
        BinhLuan bl4 = new BinhLuan(1,2,"tin","Dùng tốt 4", 4, "27/05/2022 11:00");
        dbref = FirebaseDatabase.getInstance().getReference("BinhLuan/5");
        dbref.setValue(bl4);
        BinhLuan bl5 = new BinhLuan(1,2,"phat","Dùng tốt 5", 1, "29/05/2022 11:30");
        dbref = FirebaseDatabase.getInstance().getReference("BinhLuan/6");
        dbref.setValue(bl5);
        BinhLuan bl6 = new BinhLuan(1,3,"long","Dùng tốt 6", 3, "30/05/2022 17:36");
        dbref = FirebaseDatabase.getInstance().getReference("BinhLuan/7");
        dbref.setValue(bl6);
        BinhLuan bl7 = new BinhLuan(1,3,"nhat","Dùng tốt 7", 5, "15/06/2022 18:20");
        dbref = FirebaseDatabase.getInstance().getReference("BinhLuan/8");
        dbref.setValue(bl7);
        BinhLuan bl8 = new BinhLuan(1,4,"hung","Dùng tốt 8", 5, "22/05/2022 19:20");
        dbref = FirebaseDatabase.getInstance().getReference("BinhLuan/9");
        dbref.setValue(bl8);
        BinhLuan bl9 = new BinhLuan(1,4,"phuoc","Dùng tốt 9", 4, "22/05/2022 20:17");
        dbref = FirebaseDatabase.getInstance().getReference("BinhLuan/10");
        dbref.setValue(bl9);

        TaiKhoan tk = new TaiKhoan("long", "123", "Trần Hoàng Long", "A", "0922010019", "long@gmail.com", false);
        dbref = FirebaseDatabase.getInstance().getReference("TaiKhoan/long");
        dbref.setValue(tk);

        TaiKhoan tk1 = new TaiKhoan("nhat", "456", "Nguyễn Thành Nhất", "B", "0922054672", "nhat@gmail.com", true);
        dbref = FirebaseDatabase.getInstance().getReference("TaiKhoan/nhat");
        dbref.setValue(tk1);

        TaiKhoan tk2 = new TaiKhoan("hung", "789", "Trương Trần Gia Hưng", "C", "0923333319", "hung@gmail.com", false);
        dbref = FirebaseDatabase.getInstance().getReference("TaiKhoan/hung");
        dbref.setValue(tk2);
    }

    private void GetDataSanPham()
    {
        dbref = FirebaseDatabase.getInstance().getReference("SanPham");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Làm trống mảng
                arr_SanPham.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    SanPham sp = dataSnapshot.getValue(SanPham.class);
                    arr_SanPham.add(sp);
                }

                // Sau khi đã lấy hết dữ liệu xuống mảng thì dùng adapter để đổ dữ liệu lên grid view
                // Adapter dùng để đưa danh sách sản phẩm lên grid view, tham số vào là activity hiện tại, layout của các ô trong grid view và array list SanPham
                sanPhamTrangChuAdapter = new SanPhamTrangChuAdapter(getActivity(), R.layout.cell_sanpham, arr_SanPham);
                gridView_SanPham_TrangChu.setAdapter(sanPhamTrangChuAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}