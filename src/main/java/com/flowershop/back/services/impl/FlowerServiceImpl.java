package com.flowershop.back.services.impl;

import com.flowershop.back.domain.flower.FlowerGetDatabase;
import com.flowershop.back.domain.flower.Flowers;
import com.flowershop.back.exceptions.FlowerAlreadyExistsException;
import com.flowershop.back.exceptions.FlowerNotFoundException;
import com.flowershop.back.repositories.FlowerRepository;
import com.flowershop.back.services.FlowerService;
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
    FlowerRepository flowerRepository;

    @SneakyThrows
    @Override
    @Transactional
    public void updateFlower(String fileName, String id, MultipartFile file) {
        String newFileName = replaceFilename(fileName);
        byte[] bytes = file.getBytes();

        flowerRepository.findById(id)
                .filter(existingFlower -> !existingFlower.getFilename().equals(newFileName))
                .map(existingFlower -> {

                    flowerRepository.findByFilename(newFileName)
                            .ifPresent(s -> {
                                throw new FlowerAlreadyExistsException("Nome da flor já existe na base de dados");
                            });

                    flowerRepository.findByFile(bytes)
                                    .ifPresent(s -> {
                                        throw new FlowerAlreadyExistsException("Imagem da flor já existe na base de dados");
                                    });

                    existingFlower.setFilename(newFileName);
                    existingFlower.setFile(bytes);
                    flowerRepository.save(existingFlower);
                    return true;
                }).orElseThrow(() -> new FlowerAlreadyExistsException("Flor não existente na base de dados"));

    }

    @SneakyThrows
    @Override
    public void save(String fileName, MultipartFile file) {
        String newFileName = replaceFilename(fileName);
        byte[] bytes = file.getBytes();

        flowerRepository.findByFile(bytes).ifPresent( (s) -> {throw new FlowerAlreadyExistsException("Flor já existe, escolha outra imagem!");});
        flowerRepository.findByFilename(newFileName)
                .ifPresentOrElse(flowerExists -> {
                            throw new FlowerAlreadyExistsException("Flor já existe, escolha outro nome!");
                        }, ()-> flowerRepository.save(new Flowers(new FlowerGetDatabase(newFileName, bytes)))
                );
    }



    @Override
    public List<FlowerGetDatabase> findByName(String fileName) {
        String newFileName = replaceFilename(fileName);
        return this.flowerRepository.findByFilename(newFileName)
                .stream()
                .map(flower -> new FlowerGetDatabase(flower.getFilename().replace("%20", " "), flower.getFile()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        flowerRepository.findById(id)
                .ifPresentOrElse(
                        flower -> flowerRepository.deleteById(id),
                        () -> {
                            throw new FlowerNotFoundException("Não existe flor com este id");
                        }
                );
    }

}
