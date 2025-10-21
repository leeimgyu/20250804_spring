package com.example.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"journal", "members"})

public class Comments extends BasicEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cno;

  @ManyToOne(fetch = FetchType.LAZY)
  private Journal journal;
  @ManyToOne(fetch = FetchType.LAZY)
  private Members members;

  private Long likes; // 별점
  private String text; // 한줄평

  public void setLikes(Long likes) {this.likes = likes;}
  public void setText(String text) {this.text = text;}
}
