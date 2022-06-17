package hcmute.edu.vn.fastpen.Model;

public class ChiTietHoaDon
{
    private int IdHoaDon;
    private int IdSanPham;
    private int SoLuong;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(int idHoaDon, int idSanPham, int soLuong) {
        IdHoaDon = idHoaDon;
        IdSanPham = idSanPham;
        SoLuong = soLuong;
    }

    public int getIdHoaDon() {
        return IdHoaDon;
    }

    public void setIdHoaDon(int idHoaDon) {
        IdHoaDon = idHoaDon;
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