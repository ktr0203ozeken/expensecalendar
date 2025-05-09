-- ユーザーデータの挿入
INSERT INTO users (id, username, password, role)
VALUES 
(1,'user1','$2a$10$er93XnfZ3P62/.KRbXLrf.9wdZaIO35n6sQHHdB6jhrGCqqX0sb0u', 'USER'),
(2,'user2','$2a$10$OtijxZbUIfomg8p3w0gDyey50F6I41elanLP.tiC.vqp2l2BQa6TW', 'USER'),
(3,'user3','$2a$10$aCOrvlmjb9R7u6hjAbw8O.ht3mRLBVcjJfPdGjLlA2duKdrANMMBG', 'USER')
ON CONFLICT (username) DO NOTHING;

INSERT INTO expenses (user_id,date, category, amount, description)
VALUES 
(1,'2025-04-02', '食費', 1000, 'ランチ代'),
(1,'2025-04-17', '交通費', 500, 'バス代'),
(1,'2025-04-19', '娯楽', 2000, '映画チケット'),

(1,'2025-05-01', '交通費', 1000, 'ランチ代'),
(1,'2025-05-04', '食費', 500, 'バス代'),
(1,'2025-05-15', '娯楽', 2000, '映画チケット'),

(1,'2025-06-08', '娯楽', 1000, 'ランチ代'),
(1,'2025-06-13', '交通費', 500, 'バス代'),
(1,'2025-06-25', '食費', 2000, '映画チケット');