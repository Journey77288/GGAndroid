package io.ganguo.image.entity;

public class SysImageInfo {
    private String name;
    private String path;
    private String lat;
    private String lon;
    private String date;
    private String nation;
    private String province;

    public SysImageInfo() {
    }

    public SysImageInfo(String name, String path, String date) {
        this.name = name;
        this.path = path;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return "SysImageInfo{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", date='" + date + '\'' +
                ", nation='" + nation + '\'' +
                ", province='" + province + '\'' +
                '}';
    }
}