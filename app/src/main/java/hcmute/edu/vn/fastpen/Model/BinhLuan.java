package hcmute.edu.vn.fastpen.Model;

public class BinhLuan
{
    private int IdBinhLuan;
    private int IdSanPham;
    private String TenTaiKhoan;
    private String BaiViet;
    private int SoSao;
    private String ThoiGianDangBinhLuan;

    public BinhLuan()
    {
    }

    public BinhLuan(int idBinhLuan, int idSanPham, String tenTaiKhoan, String baiViet, int soSao, String thoiGianDangBinhLuan)
    {
        IdBinhLuan = idBinhLuan;
        IdSanPham = idSanPham;
        TenTaiKhoan = tenTaiKhoan;
        BaiViet = baiViet;
        SoSao = soSao;
        ThoiGianDangBinhLuan = thoiGianDangBinhLuan;
    }

    public int getIdBinhLuan() {
        return IdBinhLuan;
    }

    public void setIdBinhLuan(int idBinhLuan) {
        IdBinhLuan = idBinhLuan;
    }

    public int getIdSanPham() {
        return IdSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        IdSanPham = idSanPham;
    }

    public String getTenTaiKhoan() {
        return TenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        TenTaiKhoan = tenTaiKhoan;
    }

    public String getBaiViet() {
        return BaiViet;
    }

    public void setBaiViet(String baiViet) {
        BaiViet = baiViet;
    }

    public int getSoSao() {
        return SoSao;
    }

    public void setSoSao(int soSao) {
        SoSao = soSao;
    }

    public String getThoiGianDangBinhLuan() {
        return ThoiGianDangBinhLuan;
    }

    public void setThoiGianDangBinhLuan(String thoiGianDangBinhLuan) {
        ThoiGianDangBinhLuan = thoiGianDangBinhLuan;
    }
}