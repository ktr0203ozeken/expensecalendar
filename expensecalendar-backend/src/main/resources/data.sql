-- ユーザーデータの挿入
INSERT INTO users (username, password, role)
VALUES 
('user1','$2a$10$er93XnfZ3P62/.KRbXLrf.9wdZaIO35n6sQHHdB6jhrGCqqX0sb0u', 'USER'),
('user2','$2a$10$OtijxZbUIfomg8p3w0gDyey50F6I41elanLP.tiC.vqp2l2BQa6TW', 'USER'),
('user3','$2a$10$aCOrvlmjb9R7u6hjAbw8O.ht3mRLBVcjJfPdGjLlA2duKdrANMMBG', 'USER'),
('demo','$2a$10$RS0fMETMlv1BGx2Vno73XOO6J8qPha05LgrkB2.AmSLrtSWhB8ex6', 'USER')
ON CONFLICT (username) DO NOTHING;

--ジャンルデータの挿入
INSERT INTO genres (id,name) VALUES
(1,'食費'),
(2,'交通費'),
(3,'日用品'),
(4,'娯楽'),
(5,'その他')
ON CONFLICT (id) DO NOTHING;
-- 修正後の支出データ（genre_id を使う）
INSERT INTO expenses (user_id, date, genre_id, amount, description)
VALUES 
(1, '2025-06-02', 1, 1000, 'ランチ代'),
(1, '2025-06-17', 2, 500, 'バス代'),
(1, '2025-06-19', 4, 2000, '映画チケット'),

(1, '2025-07-01', 1, 1000, 'ランチ代'),
(1, '2025-07-04', 2, 500, 'バス代'),
(1, '2025-07-15', 4, 2000, '映画チケット'),

(1, '2025-08-08', 1, 1000, 'ランチ代'),
(1, '2025-08-13', 2, 500, 'バス代'),
(1, '2025-08-25', 4, 2000, '映画チケット');