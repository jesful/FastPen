package hcmute.edu.vn.fastpen.Model;

public class DanhMuc
{
    private int IdDanhMuc;
    private String TenDanhMuc;

    public DanhMuc()
    {
    }

    public DanhMuc(int idDanhMuc, String tenDanhMuc)
    {
        IdDanhMuc = idDanhMuc;
        TenDanhMuc = tenDanhMuc;
    }

    public int getIdDanhMuc()
    {
        return IdDanhMuc;
    }

    public void setIdDanhMuc(int idDanhMuc)
    {
        IdDanhMuc = idDanhMuc;
    }

    public String getTenDanhMuc()
    {
        return TenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc)
    {
        TenDanhMuc = tenDanhMuc;
    }
}