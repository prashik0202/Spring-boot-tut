package com.prashcode.dreamshop.service.product;

import com.prashcode.dreamshop.model.Product;
import com.prashcode.dreamshop.request.AddProductRequest;
import com.prashcode.dreamshop.request.ProductUpdateRequest;

import java.util.List;

public interface ProductServiceInft {

    Product addProduct(AddProductRequest product);
    Product getProdcutById(Long id);
    void deleteProduct(Long id);
    Product updateProduct(ProductUpdateRequest product, Long id);
    List<Product> getAllProducts();
    List<Product> getProductByCategory(String category);
    List<Product> getProductByBrand(String brand);
    List<Product> getProductByCategoryAndBrand(String category,String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String category , String name);

}
