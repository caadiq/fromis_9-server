package com.beemer.fromis9server.introduction.service;

import com.beemer.fromis9server.introduction.dto.*;
import com.beemer.fromis9server.introduction.model.BannerImages;
import com.beemer.fromis9server.introduction.model.DebutDate;
import com.beemer.fromis9server.introduction.model.Members;
import com.beemer.fromis9server.introduction.repository.BannerImagesRepository;
import com.beemer.fromis9server.introduction.repository.DebutDateRepository;
import com.beemer.fromis9server.introduction.repository.MembersRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntroductionService {
    private final BannerImagesRepository bannerImagesRepository;
    private final DebutDateRepository debutDateRepository;
    private final MembersRepository membersRepository;

    private static final String BASE_IMAGE_URL = "https://pledis.co.kr/resources/_data/file/bbsData/";

    @Autowired
    public IntroductionService(BannerImagesRepository bannerImagesRepository, DebutDateRepository debutDateRepository, MembersRepository membersRepository) {
        this.bannerImagesRepository = bannerImagesRepository;
        this.debutDateRepository = debutDateRepository;
        this.membersRepository = membersRepository;
    }

    public IntroductionDTO getIntroduction() {
        List<BannerImages> bannerImages = bannerImagesRepository.findAll();
        List<BannerImagesDTO> bannerImagesDTO = bannerImages.stream()
                .map(bannerImage -> {
                    BannerImagesDTO dto = new BannerImagesDTO();
                    dto.setImageUrl(bannerImage.getImageUrl());
                    return dto;
                }).collect(Collectors.toList());

        DebutDate debutDateEntity = debutDateRepository.findAll().get(0);
        DebutDateDTO debutDateDTO = new DebutDateDTO();
        debutDateDTO.setDebutDate(debutDateEntity.getDebutDate());

        List<Members> members = membersRepository.findAll();
        List<MembersDTO> membersDTO = members.stream()
                .sorted(Comparator.comparing(Members::getBirth))
                .map(member -> {
                    MembersDTO dto = new MembersDTO();
                    dto.setName(member.getName());
                    dto.setImageUrl(member.getImageUrl());
                    dto.setBirth(member.getBirth());
                    dto.setPosition(member.getPosition());
                    dto.setInstagram(member.getInstagram());
                    return dto;
                }).collect(Collectors.toList());

        IntroductionDTO responseDTO = new IntroductionDTO();
        responseDTO.setBannerImages(bannerImagesDTO);
        responseDTO.setDebutDate(debutDateDTO);
        responseDTO.setMembers(membersDTO);

        return responseDTO;
    }

    public void updateMemberProfileImage() {
        try {
            String jsonUrl = "https://pledis.co.kr/resources/_data/json/frontend/KOR/artist/fromisnine/profile.json";
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(jsonUrl, String.class);
            String jsonResponse = response.getBody();

            JsonNode rootNode = new ObjectMapper().readTree(jsonResponse);
            JsonNode profileList = rootNode.path("profile_list");

            for (JsonNode member : profileList) {
                String memberName = member.path("subject").asText();
                String bbsFile = member.path("bbs_file").asText();
                String imageUrl = BASE_IMAGE_URL + bbsFile;

                Members memberEntity = membersRepository.findByName(memberName).orElse(new Members());
                memberEntity.setImageUrl(imageUrl);
                membersRepository.save(memberEntity);
            }
        } catch (Exception ignored) {

        }
    }
}