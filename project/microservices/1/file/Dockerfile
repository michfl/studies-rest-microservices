FROM debian

RUN apt update && \
    apt -y upgrade && \
    apt -y install default-jre

COPY ./entrypoint.sh /home/
COPY ./app.jar /home/app/

ENTRYPOINT ["/home/entrypoint.sh"]

EXPOSE 8080