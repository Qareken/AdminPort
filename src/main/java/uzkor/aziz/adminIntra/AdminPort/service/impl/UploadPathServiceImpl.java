package uzkor.aziz.adminIntra.AdminPort.service.impl;

import com.sun.xml.bind.v2.TODO;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uzkor.aziz.adminIntra.AdminPort.service.UploadPathService;

import javax.servlet.ServletContext;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Service
@Transactional
public class UploadPathServiceImpl implements UploadPathService {

    private final ServletContext servletContext;

    public UploadPathServiceImpl(ServletContext servletContext) {
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
        File bookFiles=new File(modifiedFilePath);
        return bookFiles;
    }


}
