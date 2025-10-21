package com.example.api.repository;

import com.example.api.entity.Journal;
import com.example.api.repository.search.SearchJournalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;

public interface JournalRepository extends JpaRepository<Journal, Long>, SearchJournalRepository {


  @Query("select j, sum(coalesce(c.likes, 0)), count(distinct c)" +
      "from Journal j left outer join Comments c" +
      "on c.journal = j where j.members.mid = :mid group by j ")
  Page getListMyPage(Pageable pageable, @Param("mid") Long mid);


  @Query("select j, p, sum(coalesce(c.likes, 0)), count(distinct c) " +
      "from Journal j " +
      "left outer join photos p on p.journal = j " +
      "left outer join Comments c on c.journal = j " +
      "where j.members.mid = :mid group by j ")
  Page getListMyPagePhotos(Pageable pageable, @Param("mid") Long mid);

  // 멤버별 저널 보기 :: MY Page(마이페이지에서)
  @Query("select j, p, sum(coalesce(c.likes, 0)), count(distinct c) " +
      "from Journal j " +
      "left outer join photos p on p.journal = j " +
      "left outer join Comments c on c.journal = j " +
      "where p.pno = (select max(p2.pno) from Photos p2 where p2.journal = j)" +
      "j.members.mid = :mid group by j ")
  Page getListMyJournalPhotosDefault(Pageable pageable, @Param("mid") Long mid);

  // 모든 저널 목록 보기 :: on main(메인화면에서)
  @Query("select j, p, sum(coalesce(c.likes, 0)), count(distinct c) " +
      "from Journal j " +
      "left outer join photos p on p.journal = j " +
      "left outer join Comments c on c.journal = j " +
      "where p.pno = (select max(p2.pno) from Photos p2 where p2.journal = j)" +
      "group by j ")
  Page getListAllJournalPhotosDefault(Pageable pageable);

  // Journal 상세보기
  @Query("select j, p, m, sum(coalesce(c.likes, 0)), count(distinct c) " +
      "from Journal j " +
      "left outer join photos p on p.journal = j " +
      "left outer join Comments c on c.journal = j " +
      "left outer join Members m on j.members = m " +
      "where j.jno = :jno" +
      "group by p ")
  List<Object[]> getJournalWithAll(@Param("jno") Long jno);
}
