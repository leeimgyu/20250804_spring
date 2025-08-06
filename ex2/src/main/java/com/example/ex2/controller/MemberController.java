package com.example.ex2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

  @RequestMapping("/join") //경로는 멤버에 있는 조인으로 간다
  public void join() {

  }
}
