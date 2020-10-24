package uzkor.aziz.adminIntra.AdminPort.service;

import java.io.File;

public interface NewsUploadPathService {
    File getFilePath(String modifiedFileName, String path);

}
