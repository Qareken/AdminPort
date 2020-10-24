package uzkor.aziz.adminIntra.AdminPort.service.impl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uzkor.aziz.adminIntra.AdminPort.domain.News;
import uzkor.aziz.adminIntra.AdminPort.domain.NewsFiles;
import uzkor.aziz.adminIntra.AdminPort.repository.NewsFileRepository;
import uzkor.aziz.adminIntra.AdminPort.repository.NewsRepository;
import uzkor.aziz.adminIntra.AdminPort.service.NewsService;
import uzkor.aziz.adminIntra.AdminPort.service.NewsUploadPathService;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsFileRepository newsFileRepository;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private NewsUploadPathService uploadPathService;
    @Autowired
    private ServletContext context;

    @Override
    public News save(News news) {
        news.setCreatedDate(new Date());
        News dbNews = newsRepository.save(news);
        if (dbNews != null && news.getFiles() != null && news.getFiles().size() > 0) {
            for (MultipartFile file : news.getFiles()) {

                if (file != null && StringUtils.hasText(file.getOriginalFilename())) {
                    String fileName = file.getOriginalFilename();
                    String modifiedFileName = FilenameUtils.getBaseName(fileName) + "_" + System.currentTimeMillis() + "." + FilenameUtils.getExtension(fileName);
                    File storeFile = uploadPathService.getFilePath(modifiedFileName, "news");
                    if (storeFile != null) {
                        try {
                            FileUtils.writeByteArrayToFile(storeFile, file.getBytes());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    NewsFiles newsFiles = new NewsFiles();
                    newsFiles.setFileExtension(FilenameUtils.getExtension(fileName));
                    newsFiles.setFileName(fileName);
                    newsFiles.setModifiedName(modifiedFileName);
                    newsFiles.setNews(dbNews);
                    newsFileRepository.save(newsFiles);
                }

            }
        }
        return dbNews;
    }

    @Override
    public News findById(Long id) {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent()) {
            return news.get();
        }
        return null;
    }


    @Override
    public List<NewsFiles> findFilesByNewsId(Long id) {
        return newsFileRepository.findFilesByNewsId(id);
    }


    @Override
    public List<News> findAll() {
        return (List<News>) newsRepository.findAll();
    }

    @Override
    public News update(News news) {
        news.setUpdatedDate(new Date());
        News dbNews = newsRepository.save(news);
        if(news!=null&&news.getRemoveImages()!=null&&news.getRemoveImages().size()>0){
            newsFileRepository.deleteFilesByUserIdAndImagesNames(news.getId(), news.getRemoveImages());
            for(String file:news.getRemoveImages()){
                File dbFile=new File(context.getRealPath("/news" +
                        "/"+File.separator+file));
                if(dbFile.exists());
                dbFile.delete();
            }
        }


        for (MultipartFile file : news.getFiles()) {

            if (file != null && StringUtils.hasText(file.getOriginalFilename())) {
                String fileName = file.getOriginalFilename();
                String modifiedFileName = FilenameUtils.getBaseName(fileName) + "_" + System.currentTimeMillis() + "." + FilenameUtils.getExtension(fileName);
                File storeFile = uploadPathService.getFilePath(modifiedFileName, "newses");
                if (storeFile != null) {
                    try {
                        FileUtils.writeByteArrayToFile(storeFile, file.getBytes());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                NewsFiles newsFiles = new NewsFiles();
                newsFiles.setFileExtension(FilenameUtils.getExtension(fileName));
                newsFiles.setFileName(fileName);
                newsFiles.setModifiedName(modifiedFileName);
                newsFiles.setNews(dbNews);
                newsFileRepository.save(newsFiles);
            }

        }

        return dbNews;
    }

    @Override
    public void deleteFilesByNewsID(Long newsId) {
        List<NewsFiles> newsFiles=newsFileRepository.findFilesByNewsId(newsId);
        if(newsFiles!=null&&newsFiles.size()>0){
            for(NewsFiles dbFiles:newsFiles){
                File dbStoreFile=new File(context.getRealPath("/news/"+File.separator+dbFiles.getModifiedName()));
                if(dbStoreFile.exists()){
                    dbStoreFile.delete();
                }
                newsFileRepository.deleteFilesByNewsId(newsId);
            }
        }
    }

    @Override
    public void deleteNews(Long newsId) {
        newsRepository.deleteById(newsId);
    }
}
