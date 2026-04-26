package tlu.web.doan.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) throws IOException {
        return uploadFile(file, "PCMaster_Storage/Product_Image");
    }

    public String uploadFile(MultipartFile file, String folder) throws IOException {
        Map<String, Object> options = ObjectUtils.asMap(
                "folder", folder
        );
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
        return uploadResult.get("url").toString();
    }

    public void deleteFile(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}