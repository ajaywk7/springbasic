package com.socialmedia.socialmedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class ImageController {

    static final String BASE_URL = "/images";
    static final String REST_URL = "/api";
    static final String FILENAME = "{filename:.+}";


    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


   //Thymleaf x Spring

    @RequestMapping(value = "/")
    public String index(Model model, Pageable pageable ){
        Page<Image> images = imageService.findPage(pageable);
        if(images.hasPrevious()){
            model.addAttribute("prev",pageable.previousOrFirst() );
        }
        if(images.hasNext()){
            model.addAttribute("next",pageable.next() );
        }
        model.addAttribute("images",images);
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST , value = BASE_URL )
    public String createFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        try {
            //System.out.println("coming");
            imageService.createImage(file);
            redirectAttributes.addFlashAttribute("flash.message","uploaded");
        }
        catch (Exception e)
        {
            redirectAttributes.addFlashAttribute("flash.message","failed =>"+e.getMessage());
        }
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.POST , value = BASE_URL+ "/delete/"+ FILENAME  )
    public String deleteFile(@PathVariable String filename, RedirectAttributes redirectAttributes){
        try {
            System.out.println("coming");
            imageService.deleteImage(filename);
            redirectAttributes.addFlashAttribute("flash.message","deleted");
        }
        catch (IOException|RuntimeException e)
        {
            System.out.println("error");
            redirectAttributes.addFlashAttribute("flash.message","delete failed =>"+e.getMessage());
        }
        return "redirect:/";
    }

    //REST
    @RequestMapping(method = RequestMethod.POST , value = REST_URL )
    @ResponseBody
    public ResponseEntity<?> createImage(@RequestParam("file") MultipartFile file ){
        try {
            System.out.println("coming");
            imageService.createImage(file);
            return ResponseEntity.ok()
                    .body("created successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("cant upload image");
        }
    }
    //curl  -v -X POST -F file=@C:\Users\ajayw\Pictures\tg.jpg localhost:8080/images

    @RequestMapping(method = RequestMethod.GET , value = BASE_URL + "/"+FILENAME +"/raw")
    @ResponseBody
    public ResponseEntity<?> getImage(@PathVariable String filename){
        try {
            Resource file =  imageService.findOneImage(filename);
            return ResponseEntity.ok()
                    .contentLength(file.contentLength())
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(file.getInputStream()));
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("cant fetch image");
        }
    }
    //curl  -v -X GET localhost:8080/images/tg.jpg/raw

    @RequestMapping(method = RequestMethod.DELETE , value = REST_URL +"/check" )
    @ResponseBody
    public ResponseEntity<?> check(){


            return ResponseEntity.badRequest().body("check");

    }

    @RequestMapping(method = RequestMethod.DELETE , value = REST_URL + "/"+FILENAME )
    @ResponseBody
    public ResponseEntity<?> deleteImage(@PathVariable String filename){
        try {
            imageService.deleteImage(filename);
            return ResponseEntity.ok()

                    .body("deleted successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("cant delete image" + e.getMessage());
        }
    }
    //curl  -v -X DELETE localhost:8080/images/tg.jpg/raw

    //    @RequestMapping(method = RequestMethod.GET , value = BASE_URL + "/test")
//    @ResponseBody
//    public ResponseEntity<?> test(){
//            return ResponseEntity.ok()
//                    .body("test");
//    }
}


