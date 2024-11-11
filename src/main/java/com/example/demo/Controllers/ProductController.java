package com.example.demo.Controllers;

import ch.qos.logback.core.model.Model;
import com.example.demo.models.Product;
import com.example.demo.models.ProductDto;
import com.example.demo.services.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;


@org.springframework.stereotype.Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository repo;

    // tao read
    @GetMapping({"" ,"/"})
    public String showProductList(Model model){
        List<Product> products = repo.findAll(Sort.by(Sort.Direction.DESC,"id"));
        // lay tat ca danh sach san pham tu co so du lieu va luu ket qua vao list
        model.addAttribute("products",products);
        //  se them ten cua du lieu nay la products
        return "products/index";
        // tu do co the su dung no trong html
        //        <li th:each="product : ${products}">
        //        <span th:text="${product.name}"></span>
        //    </li>
    }

    // create
    @GetMapping("/create")
    public String showCreatepage(Model model){
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "products/CreateProduct";
    }

    // update
    @PostMapping("/create")
    // day du lieu
    public String crearProduct(
            @Valid @ModeAttribute ProductDto productDto,
            BindingResult result
            // check xem result co chuan k
     ){
        if(productDto.getImageFile().isEmtpy()) {
            results.addErro(new FieldError("productDto", "imageFile", "the image file is emty"))
        }

        if(result.hasErorrs()) {
            return "products/CreateProduct";
        }

        // save image file

        MultipartFile image = productDto.getImageFile();
        Date createdAt = new Date();
        String storageFileName = createdAt.getTime() + "_" +image.getOriginalFilename();

        try{
            String uploadDir = "public/images";
            Path uploadPath = Paths.get(uploadDir);

            if(!Files.exits(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try(InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream,Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }

        }catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }

        Product product = new Product();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCatagory());
        product.setPrice(productDto.getPrice());
        product.setDescirption(productDto.getDescription());
        product.setCretedAt(creatAt);
        product.setImageFileName(storageFileName);

        repo.save(product);

        return "redirecct:/products";
    }

    @GetMapping("/edit")
    public String showEditPage(
            Model model,
            @RequestParam int id
    ){
        try{
            Product product = repo.findById(id).get();
            model.addAttribute("product", product);

            Product productDto = new Product();
            productDto.setName(productDto.getName());
            productDto.setBrand(productDto.getBrand());
            productDto.setCategory(productDto.getCatagory());
            productDto.setPrice(productDto.getPrice());
            productDto.setDescirption(productDto.getDescription());

            model.addAttribute("productDto", productDto);


        }catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }

        return "products/EditProduct"
    }

    @PostMapping("/edit")
    public String updateProduct(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute ProductDto productDto,
            BindingResult result
    ){
        try{
            Product product = repo.findById(id).get();
            model.addAttribute("product",product);

            if(result.hasErrors){
                return "products/EditProduct";
            }

            if(!productDto.getImageFile().isEmpty()) {
                // delete old image
                String uploadDir = "public/images/";
                Path oldImagePath = Paths.get(uploadDir + product.getImageFileName());

                try{
                    Files.delete(oldImagePath);
                }catch (Exception ex) {
                    System.out.println("Exception: " + ex.getMessage());
                }

                // save new image file
                MultipartFile image = productDto.getImageFile();
                Date createdAt = new Date();
                String storeageFileName = createdAt.getTime() + "_" + image.getOriginalFile();

                try(InputStream inputStream = image.getInputStream()){
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName),StandardCopyOption.REPLACE_EXISTING);
                }

                product.setImageFileName(storageFileName);

                product.setName(productDto.getName());
                product.setBrand(productDto.getBrand());
                product.setCategory(productDto.getCatagory());
                product.setPrice(productDto.getPrice());
                product.setDescirption(productDto.getDescription());

                repo.save(product);

            }
        }cacth(Exception ex){
            System.out.println("Errors !");
        }

        return "redirect:/products";
    }

    @GetMapping("/delete")
    public String deleteProduct(
            @RequestParam int id
    ){
        Product product = repo.findById(id).get();

        Path imagePath = Paths.get("public/images/" + product.getImageFileName());
        try{
            Files.delete(imagePath);

        }catch (Exception ex){
            System.out.println("Exception : " + ex.getMessage());
        }
        repo.delete(product);

    }


}


