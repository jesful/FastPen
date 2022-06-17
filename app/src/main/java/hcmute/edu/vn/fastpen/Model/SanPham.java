package hcmute.edu.vn.fastpen.Model;

public class SanPham
{
    private int IdSanPham;
    private String TenSanPham;
    private int IdDanhMuc;
    private int IdThuongHieu;
    private int Gia;
    private int SoLuong;
    private String MoTa;
    private String HinhAnh;
    private int SoLuongDaBan;

    public SanPham()
    {
    }

    public SanPham(int idSanPham, String tenSanPham, int idDanhMuc, int idThuongHieu, int gia, int soLuong, String moTa, String hinhAnh, int soLuongDaBan)
    {
        IdSanPham = idSanPham;
        TenSanPham = tenSanPham;
        IdDanhMuc = idDanhMuc;
        IdThuongHieu = idThuongHieu;
        Gia = gia;
        SoLuong = soLuong;
        MoTa = moTa;
        HinhAnh = hinhAnh;
        SoLuongDaBan = soLuongDaBan;
    }

    public int getIdSanPham()
    {
        return IdSanPham;
    }

    public void setIdSanPham(int idSanPham)
    {
        IdSanPham = idSanPham;
    }

    public String getTenSanPham()
    {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham)
    {
        TenSanPham = tenSanPham;
    }

    public int getIdDanhMuc() {
        return IdDanhMuc;
    }

    public void setIdDanhMuc(int idDanhMuc)
    {
        IdDanhMuc = idDanhMuc;
    }

    public int getIdThuongHieu()
    {
        return IdThuongHieu;
    }

    public void setIdThuongHieu(int idThuongHieu)
    {
        IdThuongHieu = idThuongHieu;
    }

    public int getGia()
    {
        return Gia;
    }

    public void setGia(int gia)
    {
        Gia = gia;
    }

    public int getSoLuong()
    {
        return SoLuong;
    }

    public void setSoLuong(int soLuong)
    {
        SoLuong = soLuong;
    }

    public String getMoTa()
    {
        return MoTa;
    }

    public void setMoTa(String moTa)
    {
        MoTa = moTa;
    }

    public String getHinhAnh()
    {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh)
    {
        HinhAnh = hinhAnh;
    }

    public int getSoLuongDaBan()
    {
        return SoLuongDaBan;
    }

    public void setSoLuongDaBan(int soLuongDaBan)
    {
        SoLuongDaBan = soLuongDaBan;
    }
}
