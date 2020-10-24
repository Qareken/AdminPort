package uzkor.aziz.adminIntra.AdminPort.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uzkor.aziz.adminIntra.AdminPort.domain.NewsFiles;

import java.util.List;

@Repository
public interface NewsFileRepository extends CrudRepository<NewsFiles, Long> {
    @Query("select f from NewsFiles as f where f.news.id=?1")
    List<NewsFiles> findFilesByNewsId(Long id);
    @Modifying
    @Query("delete  from NewsFiles as f where f.news.id=?1 and f.modifiedName in (?2)")
    void deleteFilesByUserIdAndImagesNames(Long id, List<String> removeImages);
    @Modifying
    @Query("delete  from NewsFiles as f where f.news.id=?1 ")
    void deleteFilesByNewsId(Long userId);
}
