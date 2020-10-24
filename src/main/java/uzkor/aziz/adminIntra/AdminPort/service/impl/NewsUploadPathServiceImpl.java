package uzkor.aziz.adminIntra.AdminPort.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uzkor.aziz.adminIntra.AdminPort.service.NewsUploadPathService;


import javax.servlet.ServletContext;
import java.io.File;

@Service
@Transactional
public class NewsUploadPathServiceImpl implements NewsUploadPathService {

    private final ServletContext servletContext;

    public NewsUploadPathServiceImpl(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public File getFilePath(String modifiedFileName, String path) {
        boolean exists=new File(servletContext.getRealPath("/"+path+"/")).exists();
        if(!exists){
            System.out.println("Not exist");
            new File(servletContext.getRealPath("/"+path+"/")).mkdir();
        }
        String modifiedFilePath=servletContext.getRealPath("/"+path+"/"+File.separator+modifiedFileName);
        File newsFiles=new File(modifiedFilePath);
        return newsFiles;
    }


}
