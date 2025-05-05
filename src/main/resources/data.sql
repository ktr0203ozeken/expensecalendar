INSERT INTO expenses (date, category, amount, description)
VALUES 
('2025-04-02', '食費', 1000, 'ランチ代'),
('2025-04-17', '交通費', 500, 'バス代'),
('2025-04-19', '娯楽', 2000, '映画チケット'),

('2025-05-01', '交通費', 1000, 'ランチ代'),
('2025-05-04', '食費', 500, 'バス代'),
('2025-05-15', '娯楽', 2000, '映画チケット'),

('2025-06-08', '娯楽', 1000, 'ランチ代'),
('2025-06-13', '交通費', 500, 'バス代'),
('2025-06-25', '食費', 2000, '映画チケット');

-- ユーザーデータの挿入
INSERT INTO users (username, password, role)
VALUES 
('admin', '$2a$10$H/H.MtfUyYHfC3/LADAUTex79lpcff4qtlFO.SuXxlOQDOZNnXlbu', 'ADMIN')  -- パスワードは 'adminpassword' のハッシュ
ON CONFLICT (username) DO NOTHING;