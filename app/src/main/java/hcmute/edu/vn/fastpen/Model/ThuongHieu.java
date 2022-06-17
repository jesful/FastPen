package hcmute.edu.vn.fastpen.Model;

public class ThuongHieu
{
    private int IdThuongHieu;
    private String TenThuongHieu;
    private String HinhAnhThuongHieu;

    public ThuongHieu()
    {
    }

    public ThuongHieu(int idThuongHieu, String tenThuongHieu, String hinhAnhThuongHieu)
    {
        IdThuongHieu = idThuongHieu;
        TenThuongHieu = tenThuongHieu;
        HinhAnhThuongHieu = hinhAnhThuongHieu;
    }

    public int getIdThuongHieu()
    {
        return IdThuongHieu;
    }

    public void setIdThuongHieu(int idThuongHieu)
    {
        IdThuongHieu = idThuongHieu;
    }

    public String getTenThuongHieu()
    {
        return TenThuongHieu;
    }

    public void setTenThuongHieu(String tenThuongHieu)
    {
        TenThuongHieu = tenThuongHieu;
    }

    public String getHinhAnhThuongHieu() {
        return HinhAnhThuongHieu;
    }

    public void setHinhAnhThuongHieu(String hinhAnhThuongHieu) {
        HinhAnhThuongHieu = hinhAnhThuongHieu;
    }
}