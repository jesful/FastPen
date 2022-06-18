package hcmute.edu.vn.fastpen.Model;

public class HoaDon
{
    private int IdHoaDon;
    private String TenTaiKhoan;
    private String Ngay;
    private int PhiVanChuyen;
    private int TongTien;
    private int TrangThai;
    private boolean TienMat;
    private String Ten;
    private String SDT;
    private String Email;
    private String DiaChi;
    private String GhiChu;

    public HoaDon() {
    }

    public HoaDon(int idHoaDon, String tenTaiKhoan, String ngay, int phiVanChuyen, int tongTien, int trangThai, boolean tienMat, String ten, String sdt, String email, String diaChi, String ghiChu)
    {
        IdHoaDon = idHoaDon;
        TenTaiKhoan = tenTaiKhoan;
        Ngay = ngay;
        PhiVanChuyen = phiVanChuyen;
        TongTien = tongTien;
        TrangThai = trangThai;
        TienMat = tienMat;
        Ten = ten;
        SDT = sdt;
        Email = email;
        DiaChi = diaChi;
        GhiChu = ghiChu;
    }

    public int getIdHoaDon() {
        return IdHoaDon;
    }

    public void setIdHoaDon(int idHoaDon) {
        IdHoaDon = idHoaDon;
    }

    public String getTenTaiKhoan() {
        return TenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        TenTaiKhoan = tenTaiKhoan;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public int getPhiVanChuyen() {
        return PhiVanChuyen;
    }

    public void setPhiVanChuyen(int phiVanChuyen) {
        PhiVanChuyen = phiVanChuyen;
    }

    public int getTongTien() {
        return TongTien;
    }

    public void setTongTien(int tongTien) {
        TongTien = tongTien;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

    public boolean getTienMat() {
        return TienMat;
    }

    public void setTienMat(boolean tienMat) {
        TienMat = tienMat;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }
}