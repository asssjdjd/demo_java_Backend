package com.example.demo.services;

import com.example.demo.models.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    // tao cai respository chua san pham va khoa chinh cua no
    // lay du lieu tu database va upload no len database
}
