package io.ganguo.image.entity;


/**
 * 文件夹
 * Created by Nereo on 2015/4/7.
 */
public class Folder {
    private String name;
    private String path;
    private int count;
    private SysImageInfo cover;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increment() {
        this.count += 1;
    }

    public SysImageInfo getCover() {
        return cover;
    }

    public void setCover(SysImageInfo cover) {
        this.cover = cover;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Folder other = (Folder) o;
            return this.path.equalsIgnoreCase(other.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
