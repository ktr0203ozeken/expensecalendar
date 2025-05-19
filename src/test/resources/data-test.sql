-- ユーザーデータ
MERGE INTO users (id, username, password, role) KEY(id) VALUES 
(1, 'user1', '$2a$10$er93XnfZ3P62/.KRbXLrf.9wdZaIO35n6sQHHdB6jhrGCqqX0sb0u', 'USER');
MERGE INTO users (id, username, password, role) KEY(id) VALUES 
(2, 'user2', '$2a$10$OtijxZbUIfomg8p3w0gDyey50F6I41elanLP.tiC.vqp2l2BQa6TW', 'USER');
MERGE INTO users (id, username, password, role) KEY(id) VALUES 
(3, 'user3', '$2a$10$aCOrvlmjb9R7u6hjAbw8O.ht3mRLBVcjJfPdGjLlA2duKdrANMMBG', 'USER');

-- ジャンルデータの挿入
-- ジャンルデータ
MERGE INTO genres (id, name) KEY(id) VALUES (1, '食費');
MERGE INTO genres (id, name) KEY(id) VALUES (2, '交通費');
MERGE INTO genres (id, name) KEY(id) VALUES (3, '日用品');
MERGE INTO genres (id, name) KEY(id) VALUES (4, '娯楽');
MERGE INTO genres (id, name) KEY(id) VALUES (5, 'その他');

-- 支出データの挿入
INSERT INTO expenses (user_id, date, genre_id, amount, description) VALUES
(1, '2025-04-02', 1, 1000, 'ランチ代');
INSERT INTO expenses (user_id, date, genre_id, amount, description) VALUES
(1, '2025-04-17', 2, 500, 'バス代');
INSERT INTO expenses (user_id, date, genre_id, amount, description) VALUES
(1, '2025-04-19', 4, 2000, '映画チケット');