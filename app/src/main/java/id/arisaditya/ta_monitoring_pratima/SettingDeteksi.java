package id.arisaditya.ta_monitoring_pratima;

public class SettingDeteksi {
    private Integer BtnReset;
    private Integer BtnSetting;
    private Integer DeteksiKebakaran;
    private Integer DeteksiPergerakan;
    private Integer ScheduleCapture;
    private Integer JarakSetting;
    private String Token;
    private Integer FreeMemory;
    private Integer MemoryUsed;
    private Integer StorageMemory;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public Integer getBtnReset() {
        return BtnReset;
    }

    public void setBtnReset(Integer btnReset) {
        BtnReset = btnReset;
    }

    public Integer getBtnSetting() {
        return BtnSetting;
    }

    public void setBtnSetting(Integer btnSetting) {
        BtnSetting = btnSetting;
    }

    public Integer getDeteksiKebakaran() {
        return DeteksiKebakaran;
    }

    public void setDeteksiKebakaran(Integer deteksiKebakaran) {
        DeteksiKebakaran = deteksiKebakaran;
    }

    public Integer getDeteksiPergerakan() {
        return DeteksiPergerakan;
    }

    public void setDeteksiPergerakan(Integer deteksiPergerakan) {
        DeteksiPergerakan = deteksiPergerakan;
    }

    public Integer getScheduleCapture() {
        return ScheduleCapture;
    }

    public void setScheduleCapture(Integer scheduleCapture) {
        ScheduleCapture = scheduleCapture;
    }

    public Integer getJarakSetting() {
        return JarakSetting;
    }

    public void setJarakSetting(Integer jarakSetting) {
        JarakSetting = jarakSetting;
    }

    public Integer getFreeMemory() {
        return FreeMemory;
    }

    public void setFreeMemory(Integer freeMemory) {
        FreeMemory = freeMemory;
    }

    public Integer getMemoryUsed() {
        return MemoryUsed;
    }

    public void setMemoryUsed(Integer memoryUsed) {
        MemoryUsed = memoryUsed;
    }

    public Integer getStorageMemory() {
        return StorageMemory;
    }

    public void setStorageMemory(Integer storageMemory) {
        StorageMemory = storageMemory;
    }

}
