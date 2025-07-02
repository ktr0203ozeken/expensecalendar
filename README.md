# カレンダー家計簿アプリ（**ローカル環境Docker対応**）

このアプリは、**カレンダー形式で支出を管理できる家計簿アプリ**です。日ごとの支出、月ごとの合計金額を一目で確認できるよう設計されています。

## 開発の背景 / 目的

日々の支出を視覚的に管理したいという思いから本アプリを開発しました。  
「どの日にいくら使ったのか」をひと目で確認できるよう、カレンダー形式のUIを採用しています。  
個人開発として、要件定義から実装・デプロイまで一貫して対応しました。

> ℹ️ 本アプリ開発における反省点（設定ファイルの誤公開）や未実装機能の構想などは、README末尾の「補足情報」セクションにまとめています。

## デプロイ先

以下のサービスでデプロイ済みです。

- Heroku: [https://expense-calendar-app-6a9c24a693ce.herokuapp.com/](https://expense-calendar-app-6a9c24a693ce.herokuapp.com/)

---

### テスト用アカウント（ログイン情報）
本アプリには、あらかじめテスト用アカウントを用意しています。  
ログインしてすぐに機能を試していただけます。
（新規登録では入力バリデーションを含む挙動を確認できます）

- ユーザー名：`demo`
- パスワード：`demo`

## 主な機能

- ✅ ユーザー登録・ログイン機能（セキュリティ対応済）
- ✅ 支出の登録・詳細表示・編集・削除
- ✅ 支出ジャンルの分類
- ✅ **カレンダー表示による日別支出の可視化**
- ✅ 月・日ごとの合計支出表示
- ✅ **支出履歴一覧のページネーション対応**（大量データでも快適に閲覧可能）
- ✅ 入力バリデーション（不正な入力防止）


## 📁 ディレクトリ構成

本アプリは、**Spring Boot + Thymeleaf** により画面生成までをバックエンドで完結させているため、`frontend` ディレクトリは設けていません。実務を想定し、`expensecalendar-backend` をサブプロジェクトとした `Multi-Project Build` 構成を採用しています。

```bash
expensecalendar/
├── .env.example                     # 環境変数（テンプレート）
├── build.gradle / settings.gradle   # ルートプロジェクトのGradle設定
├── compose.yaml                     # Docker Compose設定
├── Procfile / system.properties     # Heroku用設定
├── gradlew / gradlew.bat / gradle/  # Gradle Wrapper
├── docs/                            # ER図・画面キャプチャなど
│   └── img/
│
├── expensecalendar-backend/        # Spring Bootバックエンドアプリケーション
│   ├── Dockerfile                  # Spring Bootバックエンド用のDockerビルド設定
│   ├── build.gradle                # backendモジュール専用のGradle設定
│   └── src/
│       ├── main/
│       │   ├── java/com/ozeken/expensecalendar/
│       │   │   ├── controller/        # 各種コントローラ
│       │   │   ├── service/           # サービス層・実装
│       │   │   ├── entity/            # エンティティクラス
│       │   │   ├── mapper/            # Mapperインターフェース
│       │   │   └── ...                # その他補助クラスなど
│       │   │
│       │   └── resources/
│       │       ├── application-dev.properties      # 開発環境用設定
│       │       ├── application-prod.properties     # 本番環境用設定
│       │       ├── schema.sql / data.sql           # 初期データ・スキーマ
│       │       ├── mapper/                         # MyBatis SQLマッパーXML
│       │       ├── templates/                      # Thymeleafテンプレート
│       │       └── static/                         # CSS等
│       │
│       └── test/
│           ├── java/com/ozeken/expensecalendar/      # テストコード（Controller, Service）
│           └── resources/
│               ├── application-test.properties       # テスト用環境設定
│               └── schema-test.sql / data-test.sql   # テスト用スキーマ・データ
│
└── README.md
```

## 📦 使用技術

| 種類       | 技術                           |
|------------|--------------------------------|
| バックエンド | Spring Boot (Java)            |
| フロントエンド | Thymeleaf, HTML, CSS           |
| データベース | PostgreSQL                    |
| ORM        | MyBatis                        |
| ビルド     | Gradle / Docker                        |
| デプロイ   | Heroku   |

## ER図

以下は本アプリケーションのデータベース構成を示したER図です（DBeaverにて作成）。

![ER図](docs/img/expensecalendar-E-R.png)

## 画面サンプル

| ログイン画面 | カレンダー表示画面 |
|---------------|----------------------|
| ![login](docs/img/login.png) | ![calendar](docs/img/calendarView.png) |

## テスト用アカウント（ログイン情報）

本アプリには、あらかじめテスト用アカウントを用意しています。  
ログインしてすぐに機能を試していただけます。
（新規登録では入力バリデーションを含む挙動を確認できます）

- ユーザー名：`demo`
- パスワード：`demo`

## セットアップ手順（Docker）

1. このリポジトリをクローンします。

    ```bash
    git clone https://github.com/ktr0203ozeken/expensecalendar.git
    cd expensecalendar
    ```

2. PostgreSQL を起動し、必要なデータベースとユーザーを作成します。

    ```
    データベース名：expensecalendar_db
    ユーザー名：your_user
    パスワード：your_password
    ```

3. `.env.example` をもとに `.env` を作成します。
    
    ```bash
    cp .env.example .env
    nano .env # or your favorite editor
    ```

4. Docker コンテナをビルド＆起動します。

    ```bash
    docker compose up
    ```

5. ブラウザで以下にアクセスしてアプリを確認します。

    [http://localhost:8080](http://localhost:8080)

## データベース初期化

`schema.sql` にてテーブルが自動生成されます。

```sql
--ユーザーテーブルを作成
CREATE TABLE IF NOT EXISTS users (
    id       SERIAL PRIMARY KEY,          -- ID
    username VARCHAR(50) NOT NULL UNIQUE, -- ユーザー名
    password VARCHAR(255) NOT NULL,       -- パスワード
    role     VARCHAR(20) DEFAULT 'USER'   -- 権限
);

-- ジャンルテーブルを追加
CREATE TABLE IF NOT EXISTS genres (
    id   SERIAL PRIMARY KEY,      -- ID
    name VARCHAR(50) NOT NULL     -- ジャンル名
);

-- 支出テーブルを作成
CREATE TABLE IF NOT EXISTS expenses (
    id          BIGSERIAL PRIMARY KEY,           -- ID
    user_id     INTEGER NOT NULL,                -- ユーザーID（外部キー）
    date        DATE NOT NULL,                   -- 日付
    genre_id    INTEGER NOT NULL,                -- ジャンルID（外部キー）
    amount      BIGINT NOT NULL,                 -- 金額
    description TEXT,                            -- 説明
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_genre FOREIGN KEY (genre_id) REFERENCES genres(id)
);
```

## 補足情報

設定漏れによる情報公開の経験や、未実装機能についての記録はこちらにまとめています

### ⚠️ コミットメッセージ「#28」について（個人情報含む設定ファイルの削除）

プロファイルを分割していた際、誤ってデータベースのパスワードが含まれた設定ファイルを `.gitignore` に指定し忘れ、リポジトリに公開してしまいました。
その後、対象ファイルを削除し、`push --force` を用いて履歴からも除去しました。適切な管理が出来ていなかったため、履歴も汚れてしまい、当時の未熟さを反省しております。

現在は Docker や環境設定ファイルの扱いに関する知識を深め、**環境変数によるパスワード管理**を導入済みです。さらに、**`secret`ディレクトリでの機密情報の分離管理**といった手法についても学習を進めており、今後導入予定です。

この経験を通じて、**公開リポジトリにおける情報管理の重要性**を実感し、再発防止の意識を持って運用に取り組んでいます。

### 📌 Issueタブの活用について

現在未実装の機能や改善点は、GitHubの [Issueタブ](https://github.com/ktr0203ozeken/expensecalendar/issues) にまとめています。  
設計中の機能や将来的な拡張内容も記録しており、開発の意図や方向性を把握しやすくしています。