-- ユーザーデータの挿入
INSERT INTO users (id, username, password, role)
VALUES 
(1,'user1','$2a$10$er93XnfZ3P62/.KRbXLrf.9wdZaIO35n6sQHHdB6jhrGCqqX0sb0u', 'USER'),
(2,'user2','$2a$10$OtijxZbUIfomg8p3w0gDyey50F6I41elanLP.tiC.vqp2l2BQa6TW', 'USER'),
(3,'user3','$2a$10$aCOrvlmjb9R7u6hjAbw8O.ht3mRLBVcjJfPdGjLlA2duKdrANMMBG', 'USER'),
(4,'demo','$2a$10$RS0fMETMlv1BGx2Vno73XOO6J8qPha05LgrkB2.AmSLrtSWhB8ex6', 'USER')
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
(1, '2025-04-02', 1, 1000, 'ランチ代'),
(1, '2025-04-17', 2, 500, 'バス代'),
(1, '2025-04-19', 4, 2000, '映画チケット'),

(1, '2025-05-01', 2, 1000, 'ランチ代'),
(1, '2025-05-04', 1, 500, 'バス代'),
(1, '2025-05-15', 4, 2000, '映画チケット'),

(1, '2025-06-08', 4, 1000, 'ランチ代'),
(1, '2025-06-13', 2, 500, 'バス代'),
(1, '2025-06-25', 1, 2000, '映画チケット');