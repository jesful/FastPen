package hcmute.edu.vn.fastpen.Model;

public class TaiKhoan
{
    private String TenTaiKhoan;
    private String MatKhau;
    private String Ten;
    private String DiaChi;
    private String SDT;
    private String Email;
    private boolean Quyen;

    public TaiKhoan() {
    }

    public TaiKhoan(String tenTaiKhoan, String matKhau, String ten, String diaChi, String sdt, String email, boolean quyen) {
        TenTaiKhoan = tenTaiKhoan;
        MatKhau = matKhau;
        Ten = ten;
        DiaChi = diaChi;
        SDT = sdt;
        Email = email;
        Quyen = quyen;
    }

    public String getTenTaiKhoan() {
        return TenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        TenTaiKhoan = tenTaiKhoan;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String sdt) {
        SDT = sdt;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public boolean isQuyen() {
        return Quyen;
    }

    public void setQuyen(boolean quyen) {
        Quyen = quyen;
    }
}