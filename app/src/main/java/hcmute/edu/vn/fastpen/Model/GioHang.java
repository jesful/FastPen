package hcmute.edu.vn.fastpen.Model;

public class GioHang
{
    private String TenTaiKhoan;
    private int IdSanPham;
    private int SoLuong;

    public GioHang()
    {
    }

    public GioHang(String tenTaiKhoan, int idSanPham, int soLuong) {
        TenTaiKhoan = tenTaiKhoan;
        IdSanPham = idSanPham;
        SoLuong = soLuong;
    }

    public String getTenTaiKhoan() {
        return TenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        TenTaiKhoan = tenTaiKhoan;
    }

    public int getIdSanPham() {
        return IdSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        IdSanPham = idSanPham;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }
}