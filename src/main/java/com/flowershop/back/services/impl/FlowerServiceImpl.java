package com.flowershop.back.services.impl;

import com.flowershop.back.domain.flower.FlowerGetDatabase;
import com.flowershop.back.domain.flower.Flowers;
import com.flowershop.back.exceptions.FlowerAlreadyExistsException;
import com.flowershop.back.exceptions.FlowerNotFoundException;
import com.flowershop.back.services.FlowerService;
import com.flowershop.back.repositories.FlowerRepository;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.stream.Collectors;
import static com.flowershop.back.configuration.UtilsProject.replaceFilename;

@Service
public class FlowerServiceImpl implements FlowerService {
    @Autowired
    FlowerRepository repository;

    @SneakyThrows
    @Override
    @Transactional
    public void updateFlower(String fileName, String id, MultipartFile file) {
        String newFileName = replaceFilename(fileName);
        byte[] bytes = file.getBytes();

        repository.findById(id)
                .filter(existingFlower -> !existingFlower.getFileName().equals(newFileName))
                .map(existingFlower -> {

                    repository.findByFileName(newFileName)
                            .ifPresent(s -> {
                                throw new FlowerAlreadyExistsException("Nome da flor já existe na base de dados");
                            });

                    existingFlower.setFileName(newFileName);
                    existingFlower.setFile(bytes);
                    repository.save(existingFlower);
                    return true;
                }).orElseThrow(() -> new FlowerNotFoundException("Flor não existente na base de dados"));

    }

    @SneakyThrows
    @Override
    public void save(String fileName, MultipartFile file) {
        String newFileName = replaceFilename(fileName);
        byte[] bytes = file.getBytes();

        repository.findByFileName(newFileName)
                .ifPresentOrElse(flowerExists -> {
                            throw new FlowerAlreadyExistsException("Flor já existe, escolha outras informações!");
                        }, ()-> repository.save(new Flowers(new FlowerGetDatabase(newFileName, bytes)))
                );
    }



    @Override
    public List<FlowerGetDatabase> findByName(String fileName) {
        String newFileName = replaceFilename(fileName);
        return this.repository.findByFileName(newFileName)
                .stream()
                .map(flower -> new FlowerGetDatabase(flower.getFileName().replace("%20", " "), flower.getFile()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        repository.findById(id)
                .ifPresentOrElse(
                        flower -> repository.deleteById(id),
                        () -> {
                            throw new FlowerNotFoundException("Não existe flor com este id");
                        }
                );
    }

}
