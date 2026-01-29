package com.board.domain;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@SequenceGenerator(name = "JPABOARD_SEQ_GEN", // 시퀀스 제너레이터 이름
		sequenceName = "JPABOARD_SEQ", // 시퀀스 이름
		initialValue = 1, // 시작값
		allocationSize = 1 // 시퀀스가 증가하는 단위
)
@Table(name = "JPABOARD") // DB 테이블명 (자바클래스명과 같으면 필요없음)
public class Board {

	@Id // PrimaryKey
	@GeneratedValue(strategy = GenerationType.SEQUENCE, // 사용할 변수를 시퀀스로 선택
			generator = "JPABOARD_SEQ_GEN") // 식별자 생성기를 설정해 놓은 JPABOARD_SEQ_GEN로 설정
	@Column(name = "NO") // DB컬럼명
	private Long no;

	@Column(name = "TITLE") // DB컬럼명
	private String title;

	@Column(name = "CONTENT") // DB컬럼명
	private String content;

	@Column(name = "WRITER") // DB컬럼명
	private String writer;

	@CreationTimestamp // SYSDATE
	@Column(name = "REG_DATE") // DB컬럼명
	private Date regDate;

}
