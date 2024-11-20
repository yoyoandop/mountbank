package com.example.mountbank.model;

import javax.persistence.*; // 使用 javax.persistence.* 進行 JPA 映射
import java.io.Serializable;

@Entity
@Table(name = "images") // 對應 MySQL 表 "images"
public class Images implements Serializable {

    // 主鍵 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主鍵
    @Column(name = "id", nullable = false, unique = true) // 不可為 NULL 且唯一
    private Long id;

    // 書名 (可為 NULL)
    @Column(name = "book_name", nullable = false, length = 255) // 最多 255 字符
    private String bookName;

    // 圖片 URL (不可為 NULL)
    @Column(name = "url", nullable = false, length = 512) // 最多 512 字符
    private String url;

    // 照片名稱 (不可為 NULL)
    @Column(name = "image_name", nullable = false, length = 255) // 最多 255 字符
    private String imageName;

    // 無參構造方法 (JPA 必須)
    public Images() {
    }

    // 帶參構造方法
    public Images(String bookName, String url, String imageName) {
        this.bookName = bookName;
        this.url = url;
        this.imageName = imageName;
    }

    // Getter 和 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    // toString 方法
    @Override
    public String toString() {
        return "Images{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", url='" + url + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
