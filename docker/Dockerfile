FROM maven:3.6.2-ibmjava as builder

ENV VER="release_20191026_v8.1.5"

RUN apt update \
  && apt install -y --no-install-recommends \
     git \
  && apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

# Copy source files
RUN mkdir -p /java \
  && cd /java \
  && git clone --depth 1 --branch $VER https://github.com/EhsanTang/ApiManager.git \
  && cd /java/ApiManager/api \
  && sed -ie '/import com.sun.javafx.binding./d' /java/ApiManager/api/src/main/java/cn/crap/utils/generate/MyBatisGenerateUtil.java \
  && mvn package

FROM tomcat:jdk8-adoptopenjdk-openj9

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=builder /java/ApiManager/api/target/api /usr/local/tomcat/webapps/ROOT

EXPOSE 8080

CMD [ "catalina.sh", "run"]


