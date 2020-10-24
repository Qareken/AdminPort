package uzkor.aziz.adminIntra.AdminPort.service;

import org.springframework.web.multipart.MultipartFile;
import uzkor.aziz.adminIntra.AdminPort.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book save(Book books, MultipartFile multipartFile);

    List<Book> findAll();

    Book findById(Long id);
}
