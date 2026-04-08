ARG APP_INSIGHTS_AGENT_VERSION=3.7.7

# Application image

FROM hmctsprod.azurecr.io/base/java:openjdk-11-distroless-1.2

COPY lib/AI-Agent.xml /opt/app/
COPY build/libs/prl-wa-task-configuration.jar /opt/app/

EXPOSE 4090
CMD [ "prl-wa-task-configuration.jar" ]
