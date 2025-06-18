# Build stage
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# 依存関係をキャッシュするためにビルドに必要なファイルを先にコピー
COPY build.gradle settings.gradle gradlew gradlew.bat ./
COPY gradle ./gradle
COPY expensecalendar-backend/build.gradle ./expensecalendar-backend/

# gradlewに実行権限を付与（Linux環境なら必須）
RUN chmod +x gradlew

# ソースコードをコピー
COPY expensecalendar-backend/src ./expensecalendar-backend/src

# ビルド実行（デーモンを使わずクリーンビルド）
RUN ./gradlew clean build --no-daemon

# Runtime stage
FROM eclipse-temurin:17-jre

WORKDIR /app

# build stageからビルド済みjarだけをコピー
COPY --from=build /app/expensecalendar-backend/build/libs/*.jar app.jar

# アプリ起動
CMD ["java", "-jar", "app.jar"]