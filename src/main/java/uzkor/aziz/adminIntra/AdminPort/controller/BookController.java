package uzkor.aziz.adminIntra.AdminPort.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uzkor.aziz.adminIntra.AdminPort.domain.Book;
import uzkor.aziz.adminIntra.AdminPort.domain.News;
import uzkor.aziz.adminIntra.AdminPort.domain.NewsFiles;
import uzkor.aziz.adminIntra.AdminPort.service.BookService;


import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/book")
public class BookController {
    @Value("${upload.folder}")
    private String uploadFolder;
    @Autowired
     private BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String addBook(Model model){
        model.addAttribute("book",new Book());


        return "addBook";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveBook(@ModelAttribute Book book, @RequestParam("file") MultipartFile multipartFile, Model model){
        Book dbBook=bookService.save(book, multipartFile);
        if(dbBook!=null){

            return "redirect:bookList";
        }else{

            model.addAttribute("book", book);
            return "addBook";
        }
    }
    @RequestMapping(value = "/bookList")
    public String bookList(Model model){

        List<Book> bookList = bookService.findAll();
        model.addAttribute("bookList",bookList);

        return "bookList";
    }
    @RequestMapping(value = "/bookInfo/{id}", method = RequestMethod.GET)
    public String bookInfo(@PathVariable Long id, Model model)throws IOException {
        System.out.println(uploadFolder);
        Book book=bookService.findById(id);
        model.addAttribute("path", uploadFolder);
        model.addAttribute("books", book);
        return "bookInfo";
    }
    @GetMapping("/preview/{hashId}")
    public ResponseEntity previewFile(@PathVariable Long id) throws IOException {
        Book fileStorage=bookService.findById(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline: fileName=\""+ URLEncoder.encode(fileStorage.getName()))
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(String.format("%s/%s",uploadFolder, fileStorage.getImage_path() )));
    }


}
