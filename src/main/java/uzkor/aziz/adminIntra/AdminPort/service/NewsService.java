package uzkor.aziz.adminIntra.AdminPort.service;

import uzkor.aziz.adminIntra.AdminPort.domain.News;
import uzkor.aziz.adminIntra.AdminPort.domain.NewsFiles;

import java.util.List;


public interface NewsService {

    News save(News news);
    News findById(Long id);
    List<NewsFiles> findFilesByNewsId(Long id);
    List<News> findAll();
    News update(News news);
    void deleteFilesByNewsID(Long userId);
    void deleteNews(Long newsId);
}
