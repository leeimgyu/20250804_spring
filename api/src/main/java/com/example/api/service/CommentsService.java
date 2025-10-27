package com.example.api.service;

import com.example.api.dto.CommentsDTO;
import com.example.api.entity.Comments;
import com.example.api.entity.Journal;
import com.example.api.entity.Members;

import java.util.List;

public interface CommentsService {
  Long register(CommentsDTO commentsDTO);

  List<CommentsDTO> getList(Long jno);

  void modify(CommentsDTO commentsDTO);

  void remove(Long cno);

  default Comments dtoToEntity(CommentsDTO commentsDTO) {
    Comments comments = Comments.builder()
        .cno(commentsDTO.getCno())
        .journal(Journal.builder().jno(commentsDTO.getJno()).build())
        .members(Members.builder().mid(commentsDTO.getMid()).build())
        .likes(commentsDTO.getLikes())
        .text(commentsDTO.getText())
        .build();
    return comments;
  }


  default CommentsDTO entityToDto(Comments comments) {
    CommentsDTO commentsDTO = CommentsDTO.builder()
        .cno(comments.getCno())
        .jno(comments.getJournal().getJno())
        .mid(comments.getMembers().getMid())
        .nickname(comments.getMembers().getNickname())
        .email(comments.getMembers().getEmail())
        .likes(comments.getLikes())
        .text(comments.getText())
        .regDate(comments.getRegDate())
        .modDate(comments.getModDate())
        .build();
    return commentsDTO;
  }
}
