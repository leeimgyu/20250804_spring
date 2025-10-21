package com.example.api.service;

import com.example.api.dto.JournalDTO;
import com.example.api.dto.PageRequestDTO;
import com.example.api.dto.PageResultDTO;
import com.example.api.entity.Journal;
import com.example.api.entity.Members;
import com.example.api.entity.Photos;
import com.example.api.repository.CommentsRepository;
import com.example.api.repository.JournalRepository;
import com.example.api.repository.PhotosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class JournalServiceImpl implements JournalService {
  private final JournalRepository journalRepository;
  private final PhotosRepository photosRepository;
  private final CommentsRepository commentsRepository;

  @Value("${com.example.upload.path}")
  private String uploadPath;

  @Override
  public PageResultDTO<JournalDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
    Pageable pageable = pageRequestDTO.getPageable(Sort.by("jno").descending());
    Page<Object[]> result = journalRepository.searchPage(pageRequestDTO.getType(),
        pageRequestDTO.getKeyword(), pageable);
    Function<Object[], JournalDTO> fn = new Function<Object[], JournalDTO>() {
      @Override
      public JournalDTO apply(Object[] objects) {
        return entityToDTO(
            (Journal) objects[0],
            (List<Photos>) (Arrays.asList((Photos)objects[1])),
            (Members) objects[2],
            (Long) objects[3],
            (Long) objects[4]
        );
      }
    };
    return new PageResultDTO<>(result, fn);
  }

  @Override
  public Long register(JournalDTO journalDTO) {
    Map<String, Object> entityMap = dtoToEntity(journalDTO);
    Journal journal = (Journal) entityMap.get("journal");
    journalRepository.save(journal);
    List<Photos> photosList = (List<Photos>) entityMap.get("photosList");
    photosList.forEach(photos -> photosRepository.save(photos));
    return journal.getJno();
  }

  @Override
  public JournalDTO get(Long jno) {
    List<Object[]> result = journalRepository.getJournalWithAll(jno);
    // j, p, m, sum(coalesce(c.likes, 0)), count(distinct c)
    Journal journal = (Journal) result.get(0)[0];
    List<Photos> photosList = new ArrayList<>();
    result.forEach(objects -> {
      photosList.add((Photos) objects[1]);
    });
    Members members = (Members) result.get(0)[2];
    Long likes = (Long) result.get(0)[3];
    Long commentCnt = (Long) result.get(0)[4];
    return entityToDTO(journal, photosList, members, likes, commentCnt);
  }

  @Override
  public void modify(JournalDTO journalDTO) {

  }

  @Override
  public List<String> removeWithCommentsAndPhotos(Long jno) {
    return List.of();
  }

  @Override
  public void removePhotosByUUID(String uuid) {

  }
}