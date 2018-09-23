FROM art-hq.intranet.qualys.com:5001/qualys-base/centos7/java
ADD HelloWorldApp/target/hello-world-app*.jar /root/app.jar
ADD entrypoint.sh /root
VOLUME /tmp
VOLUME /tmp/target
EXPOSE 8090
ENTRYPOINT ["/root/entrypoint.sh"]
CMD ["start"]