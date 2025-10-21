package com.example.api.service;

import com.example.api.repository.CommentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2

public class CommentsServiceImpl implements CommentsServcie{
  private final CommentsRepository commentsRepository;
}

