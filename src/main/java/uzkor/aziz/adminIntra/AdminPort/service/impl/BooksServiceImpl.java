package uzkor.aziz.adminIntra.AdminPort.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uzkor.aziz.adminIntra.AdminPort.domain.Book;
import uzkor.aziz.adminIntra.AdminPort.domain.News;
import uzkor.aziz.adminIntra.AdminPort.repository.BookRepository;
import uzkor.aziz.adminIntra.AdminPort.service.BookService;
import org.hashids.Hashids;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class BooksServiceImpl implements BookService {
    @Value("${upload.folder}")
    private String uploadFolder;
    private final Hashids hashids;

    private  final BookRepository bookRepository;

    public BooksServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.hashids=new Hashids(getClass().getName(), 6);
    }


    @Override
    public Book save(Book books, MultipartFile multipartFile) {
        books.setCreatedDate(new Date());
        Book dBook=bookRepository.save(books);
        if(multipartFile!=null){
            dBook.setImage_extension(getExtension(multipartFile.getOriginalFilename()));
            dBook.setFileSize(multipartFile.getSize());
            dBook.setContentType(multipartFile.getContentType());
            Date now=new Date();
            File uploadFolder=new File(String.format("%s/upload_files/%d/%d/%d/", this.uploadFolder, 1900+now.getYear(), 1+now.getMonth(), now.getDate()));
            if(!uploadFolder.exists()&&uploadFolder.mkdirs()){
                System.out.println("aytilgan papkalar jaratildi");
            }
            dBook.setHashId(hashids.encode(dBook.getId()));
            dBook.setImage_path(String.format("upload_files/%d/%d/%d/%s.%s",
                    1900+now.getYear(), 1+now.getMonth(),
                    now.getDate(), dBook.getHashId(),
                    dBook.getImage_extension()));
            bookRepository.save(dBook);
            uploadFolder=uploadFolder.getAbsoluteFile();
            File file=new File(uploadFolder, String.format("%s.%s", dBook.getHashId(), dBook.getImage_extension()));
            try {
                multipartFile.transferTo(file);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return  dBook;
    }

    @Override
    public List<Book> findAll() {
        return (List<Book>) bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        Optional<Book> news = bookRepository.findById(id);
        if (news.isPresent()) {
            return news.get();
        }
        return null;
    }


    private String getExtension(String fileName){
        String ext=null;
        if(fileName!=null&&!fileName.isEmpty()){
            int dot=fileName.lastIndexOf('.');
            if(dot>0&&dot<=fileName.length()-2){
                ext=fileName.substring(dot+1);
            }
        }
        return ext;
    }
}
