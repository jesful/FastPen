package hcmute.edu.vn.fastpen.Model;

public class Global {
    private static TaiKhoan tk;

    public Global() {
    }

    public static TaiKhoan getTk() {
        return tk;
    }

    public static void setTk(TaiKhoan tk) {
        Global.tk=tk;
    }
}
