package com.onetool.server.qna.repository;

import com.onetool.server.qna.QnaBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QnaRepository extends JpaRepository<QnaBoard, Long> {
    @Query("SELECT q FROM QnaBoard q ORDER BY q.createdAt DESC")
    List<QnaBoard> findAllQnaBoardsOrderedByCreatedDate();

    Optional<QnaBoard> findById(Long boardId);
}
