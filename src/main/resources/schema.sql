-- 支出テーブルを作成
CREATE TABLE IF NOT EXISTS expenses (
    id               BIGSERIAL PRIMARY KEY,  --  ID
    date           DATE NOT NULL,                --  日付
    category    VARCHAR(50) NOT NULL,  --  カテゴリ
    amount      INTEGER NOT NULL,           --  金額
    description TEXT,                                    --  説明
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE --  ユーザーID
);

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER'
);