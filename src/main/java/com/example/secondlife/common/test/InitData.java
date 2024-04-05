//package com.example.secondlife.common.test;
//
//import com.example.secondlife.domain.comment.dto.CommentRequest;
//import com.example.secondlife.domain.comment.service.CommentService;
//import com.example.secondlife.domain.post.dto.PostDto;
//import com.example.secondlife.domain.post.enumType.Forum;
//import com.example.secondlife.domain.post.service.PostService;
//import com.example.secondlife.domain.user.dto.JoinRequest;
//import com.example.secondlife.domain.user.entity.User;
//import com.example.secondlife.domain.user.enumType.Region;
//import com.example.secondlife.domain.user.enumType.Role;
//import com.example.secondlife.domain.user.repository.UserRepository;
//import com.example.secondlife.domain.user.service.UserService;
//import com.example.secondlife.domain.user.valueType.Introduction;
//import java.time.LocalDate;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@RequiredArgsConstructor
//@Slf4j
//@Component
//public class InitData {
//    private final UserService userService;
//    private final UserRepository userRepository;
//    private final PostService postService;
//    private final CommentService commentService;
//    private final BCryptPasswordEncoder passwordEncoder;
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void init() {
//        log.info("init()");
//
//        JoinRequest joinRequestL1 = new JoinRequest(
//                "test",
//                "test",
//                "nickname1",
//                Region.GANGWON,
//                LocalDate.now(),
//                "일반 회원입니다."
//        );
//        JoinRequest joinRequestL2 = new JoinRequest(
//                "test2",
//                "test2",
//                "nickname2",
//                Region.SEOUL,
//                LocalDate.now(),
//                "인증된 회원입니다."
//        );
//        JoinRequest joinRequestAdmin = new JoinRequest(
//                "admin",
//                "admin",
//                "admin",
//                Region.SEOUL,
//                LocalDate.now(),
//                "ADMIN 입니다."
//        );
//
//        userService.save(joinRequestL1);
//        userRepository.save(joinRequestToL2User(joinRequestL2));
//        userRepository.save(joinRequestToAdminUser(joinRequestAdmin));
//
//        for (int i = 0; i < 110; i++) {
//            PostDto postDto = new PostDto("title" + i + i, "content" + i + i, Forum.FREE);
//            postService.save(2L, postDto);
//        }
//        for (int i = 0; i < 110; i++) {
//            PostDto postDto = new PostDto("반갑습니다" + i, "안녕하세요" + i, Forum.FREE);
//            postService.save(1L, postDto);
//        }
//
//        CommentRequest commentRequest1 = new CommentRequest("comment1");
//        CommentRequest commentRequest2 = new CommentRequest("comment2");
//        commentService.save(1L, 1L, commentRequest1);
//        commentService.save(1L, 1L, commentRequest2);
//        commentService.save(1L, 1L, commentRequest2);
//        commentService.save(1L, 1L, commentRequest2);
//        commentService.save(1L, 1L, commentRequest2);
//        commentService.save(1L, 1L, commentRequest2);
//        commentService.save(1L, 1L, commentRequest2);
//        commentService.save(1L, 1L, commentRequest2);
//        commentService.save(1L, 1L, commentRequest2);
//        commentService.save(1L, 1L, commentRequest2);
//        commentService.save(1L, 1L, commentRequest2);
//        commentService.save(1L, 1L, commentRequest2);
//        commentService.save(1L, 1L, commentRequest2);
//
//    }
//
//    public User joinRequestToL2User(JoinRequest request) {
//        log.info("joinRequestToL2User()");
//
//        Introduction introduction = new Introduction(request.getRegion(), request.getBirthDate(),
//                request.getSelfIntroduction());
//
//        return User.builder()
//                .loginId(request.getLoginId())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .nickname(request.getNickname())
//                .introduction(introduction)
//                .role(Role.L2)
//                .build();
//
//    }
//
//    public User joinRequestToAdminUser(JoinRequest request) {
//        log.info("joinRequestToAdminUser()");
//
//        Introduction introduction = new Introduction(request.getRegion(), request.getBirthDate(),
//                request.getSelfIntroduction());
//
//        return User.builder()
//                .loginId(request.getLoginId())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .nickname(request.getNickname())
//                .introduction(introduction)
//                .role(Role.ADMIN)
//                .build();
//
//    }
//
//}
