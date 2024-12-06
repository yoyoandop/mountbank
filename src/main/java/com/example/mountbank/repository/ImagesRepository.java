package com.example.mountbank.repository;

import com.example.mountbank.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {
    // 可定義額外查詢方法，例如通過書名查找
    Images findByBookName(String bookName);
}



