INSERT INTO first_category(id, name) VALUES (1, 'building');
INSERT INTO first_category(id, name) VALUES (2, 'civil');
INSERT INTO first_category(id, name) VALUES (3, 'interior');
INSERT INTO first_category(id, name) VALUES (4, 'machine');
INSERT INTO first_category(id, name) VALUES (5, 'electric');
INSERT INTO first_category(id, name) VALUES (6, 'etc');

# 빌딩
INSERT INTO second_category (id, first_category_id, name)
    VALUE (1, 1, '주거');
INSERT INTO second_category (id, first_category_id, name)
    VALUE (2, 1, '상업');
INSERT INTO second_category (id, first_category_id, name)
    VALUE (3, 1, '공공');

# 토목
INSERT INTO second_category (id, first_category_id, name)
    VALUE (3, 2, '도로');
INSERT INTO second_category (id, first_category_id, name)
    VALUE (4, 2, '교량');
INSERT INTO second_category (id, first_category_id, name)
    VALUE (5, 2, '터널');
INSERT INTO second_category (id, first_category_id, name)
    VALUE (6, 2, '댐/수자원');

# 인테리어
INSERT INTO second_category (id, first_category_id, name)
    VALUE (7, 3, '주거');
INSERT INTO second_category (id, first_category_id, name)
    VALUE (8, 3, '상업');
INSERT INTO second_category (id, first_category_id, name)
    VALUE (9, 3, '가구/집기');

# 기계
INSERT INTO second_category (id, first_category_id, name)
    VALUE (10, 4, '기계부품');
INSERT INTO second_category (id, first_category_id, name)
    VALUE (11, 4, '설비');

# 전기
INSERT INTO second_category (id, first_category_id, name)
    VALUE (12, 5, '전기');