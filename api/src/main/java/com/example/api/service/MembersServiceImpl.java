package com.example.api.service;

import com.example.api.repository.MembersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class MembersServiceImpl implements MembersService{
  private final MembersRepository membersRepository;

}