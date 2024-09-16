package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // User 객체 -> User Table
public class User {

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id = null;

    // null여부, 길이, 컬럼 = 필드 매핑(같으면 생략)
    @Column(nullable = false, length = 20, name = "name")
    private String name;
    private Integer age;

    // 연관관계의 주인, 삭제시 관련 객체도 지움, 관계가 끊어진 데이터 제거
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) // 1 : N
    private List<UserLoanHistory> userLoanHistories = new ArrayList<>();

    protected User() {}

    public User(String name, Integer age) {
        if (name == null || name.isBlank()) {
            throw  new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다", name));
        }
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Long getId() {
        return id;
    }

    public void updateName(String name){
        this.name = name;
    }

    public void loanBook(String bookName) {
        this.userLoanHistories.add(new UserLoanHistory(this, bookName));
    }

    public void returnBook(String bookName) {
        UserLoanHistory targetHistory = this.userLoanHistories.stream()
                .filter(history -> history.getBookName().equals(bookName))
                .findFirst().orElseThrow(IllegalArgumentException::new);
        targetHistory.doReturn();
    }
}
