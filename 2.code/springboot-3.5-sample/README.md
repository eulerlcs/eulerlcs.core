# demo

このプロジェクトは Spring Boot アプリケーションです。

## 起動時に application-h2.yaml だけを有効化する方法

通常の起動では src/main/resources/application.yaml が読み込まれます。
application-h2.yaml のみを使いたい場合は、起動時に設定ファイルの読み込み先を明示します。

### 1. Maven で起動する場合

以下のコマンドで application-h2.yaml のみを読み込みます。

mvnw spring-boot:run "-Dspring-boot.run.arguments=--spring.config.location=classpath:/application-h2.yaml"

Windows で cmd を使う場合は次でも実行できます。

mvnw.cmd spring-boot:run "-Dspring-boot.run.arguments=--spring.config.location=classpath:/application-h2.yaml"

### 2. jar 実行の場合

java -jar target/demo-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:/application-h2.yaml

### 3. 環境変数で指定する場合

Windows (cmd):

set SPRING_CONFIG_LOCATION=classpath:/application-h2.yaml
mvnw.cmd spring-boot:run

macOS/Linux:

export SPRING_CONFIG_LOCATION=classpath:/application-h2.yaml
./mvnw spring-boot:run

## 補足

spring.profiles.active=h2 を使うと application-h2.yaml は読み込まれますが、
application.yaml も併せて読み込まれます。
application.yaml を無効化したい場合は、上記の spring.config.location を利用してください。
