FROM maven
#COPY . .
#RUN apt-get install mvn -y
RUN mkdir -p /code

RUN apt update -y && \
    apt install ssh -y

RUN mkdir -p /root/.ssh && \
    chmod 0700 /root/.ssh && \
    ssh-keyscan github.com > /root/.ssh/known_hosts

ADD ssh_prv_key /root/.ssh/id_rsa
RUN chmod 600 /root/.ssh/id_rsa

RUN mkdir -p /var/log/credgenics_logs/
RUN chmod 777 -R /var/log/credgenics_logs/

COPY ./ /code
WORKDIR /code

RUN mvn clean install
RUN java -jar /target/kafka-project-0.0.1-SNAPSHOT.jar