FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

# 1. Sistem paketleri + FastR native bağımlılıkları
RUN apt-get update && apt-get install -y \
    curl wget tar \
    libcurl4-openssl-dev \
    libssl-dev \
    libxml2-dev \
    libgomp1 \
    && rm -rf /var/lib/apt/lists/*

# 2. GraalVM CE Java 17 (FastR destekli sürüm)
RUN mkdir -p /opt/graalvm && \
    wget -qO- https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-22.3.0/graalvm-ce-java17-linux-amd64-22.3.0.tar.gz \
    | tar -xz --strip-components=1 -C /opt/graalvm

# 3. Ortam değişkenleri
ENV JAVA_HOME=/opt/graalvm
ENV PATH="/opt/graalvm/bin:$PATH"

# 4. FastR kurulumu
RUN gu install R

# 5. R paketleri (FastR içinden)
RUN R -e "install.packages('lattice', repos='https://cloud.r-project.org')"

WORKDIR /app
COPY target/*.jar app.jar
COPY src/main/resources/plot.R plot.R

EXPOSE 8080

ENTRYPOINT ["/opt/graalvm/bin/java", "-Dpolyglot.engine.WarnInterpreterOnly=false", "-jar", "app.jar"]
