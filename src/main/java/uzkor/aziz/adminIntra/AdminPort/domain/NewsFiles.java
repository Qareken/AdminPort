package uzkor.aziz.adminIntra.AdminPort.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "news_files")
public class NewsFiles implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String fileName;
    private String modifiedName;
    private String fileExtension;
    @ManyToOne()
    @JoinColumn(name = "news_id")
    private News news;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getModifiedName() {
        return modifiedName;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}
