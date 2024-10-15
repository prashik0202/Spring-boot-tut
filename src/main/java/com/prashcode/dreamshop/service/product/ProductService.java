package com.prashcode.dreamshop.service.product;

import com.prashcode.dreamshop.exceptions.ProductNotFoundException;
import com.prashcode.dreamshop.model.Category;
import com.prashcode.dreamshop.model.Product;
import com.prashcode.dreamshop.repository.CategoryRepository;
import com.prashcode.dreamshop.repository.ProductRepository;
import com.prashcode.dreamshop.request.AddProductRequest;
import com.prashcode.dreamshop.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // constructor injection
public class ProductService implements ProductServiceInft{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    //~~~~~~~~~~~~~~~~~Save Product to Database~~~~~~~~~~~~~~~~~~~~~
    @Override
    public Product addProduct(AddProductRequest request) {
        /*
        check category found in database
        if yes! -> then add the product as category
        if no! -> then save the category
        and then save the new product as newly created category
         */
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> { Category newCategory = new Category(request.getCategory().getName());
            return  categoryRepository.save(newCategory);
        });

        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }
    // end save product function


    // GET PRODUCT BY ID
    @Override
    public Product getProdcutById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }


    // DELETE PRODUCT BY ID
    @Override
    public void deleteProduct(Long id) {
        // find the product by id
        // if product is present then delete else throw runtime exception ProductNotFound
        productRepository.findById(id)
                .ifPresentOrElse(productRepository :: delete,
                        () -> { throw  new ProductNotFoundException("Product not found");});
    }

    // Update Product using product_id
    @Override
    public Product updateProduct(ProductUpdateRequest request, Long id) {
        /*
        1.find the product by id
        2.if we found the product we just save the updated changes and save the changes
        3.if we didn't find product with id we throw Exception ProductNotFound
         */
        return productRepository.findById(id)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository :: save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setPrice(request.getPrice());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);

        return existingProduct;
    }
    // end update product function

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
