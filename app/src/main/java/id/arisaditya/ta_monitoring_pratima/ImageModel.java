package id.arisaditya.ta_monitoring_pratima;

public class ImageModel {
    String  Tanggal, Waktu, Image;

    public ImageModel() {
    }

    public ImageModel(String tanggal, String waktu, String image) {
        Tanggal = tanggal;
        Waktu = waktu;
        Image = image;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public String getWaktu() {
        return Waktu;
    }

    public void setWaktu(String waktu) {
        Waktu = waktu;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
