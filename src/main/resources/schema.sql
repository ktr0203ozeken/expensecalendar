-- 支出テーブルを作成
CREATE TABLE expenses (
    id SERIAL PRIMARY KEY,                 -- ID
    date DATE NOT NULL,                      -- 日付
    category VARCHAR(50) NOT NULL, -- カテゴリ
    amount INTEGER NOT NULL,            -- 金額
    description TEXT                                -- 説明
);