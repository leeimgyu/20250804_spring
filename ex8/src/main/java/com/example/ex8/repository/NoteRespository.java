package com.example.ex8.repository;

import com.example.ex8.entity.Note;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoteRespository extends JpaRepository<Note, Long> {
  @EntityGraph(attributePaths = "writer", type = EntityGraph.EntityGraphType.LOAD)
  @Query("select n from Note n where n.num = :num ")
  Optional<Note> getWithWriter(Long num);

  // 만약에 ClubMember의 roleSet까지 가져 올때는 {"writer", "writer.roleSet"} 하면 됨
  @EntityGraph(attributePaths = {"writer"}, type = EntityGraph.EntityGraphType.LOAD)
  @Query("select n from Note n where n.writer.email = :email ")
  List<Note> getList(String email);

}
