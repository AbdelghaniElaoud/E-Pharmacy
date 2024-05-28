package com.epharmacy.app.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.epharmacy.app.model.Media;
import com.epharmacy.app.model.User;
import com.epharmacy.app.repository.MediaRepository;
import com.epharmacy.app.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class MediaService {
    private final MediaRepository repository;
    private final UserRepository userRepository;

    public MediaService(MediaRepository repository,
                        UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Optional<Media> findById(Long id){
        return repository.findById(id);
    }
    public List<Media> getAllCategories(){
        return repository.findAll();
    }

    public Media save(Media item){
        return repository.save(item);
    }
    public void delete(Media item){
        repository.delete(item);
    }
    public void delete(Iterable<Media> items){
        repository.deleteAll(items);
    }
    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        return getCloudinary().uploader()
                .upload(multipartFile.getBytes(),
                        Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();
    }
    public Cloudinary getCloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dptgucred",
                "api_key", "189769338287882",
                "api_secret", "MaK0roxq1XzxML2McSOos7YZFD8"));
    }


    public User addProfile(Long userId, MultipartFile photo) throws IOException {

        User user = userRepository.findById(userId).get();


        String link = uploadFile(photo);
        Media media = Media.builder().link(link).altText(user.getUsername()).build();
        save(media);
        user.setProfilePhoto(media);

        userRepository.save(user);

        return userRepository.save(user);

    }
}
