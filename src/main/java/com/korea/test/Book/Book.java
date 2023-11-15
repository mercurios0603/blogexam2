package com.korea.test.Book;

import com.korea.test.Post.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private LocalDateTime createDate;

  @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
  private List<Post> post;
}
